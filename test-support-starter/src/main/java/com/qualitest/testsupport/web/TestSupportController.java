package com.qualitest.testsupport.web;

import com.qualitest.testsupport.config.TestSupportProperties;
import com.qualitest.testsupport.exception.TestSupportException;
import com.qualitest.testsupport.model.*;
import com.qualitest.testsupport.spi.SnapshotStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

/**
 * 被测数据快照与还原 HTTP 端点。
 * 供自动化测试在节点执行前打 checkpoint，失败后将库表还原到快照时点。
 */
@RestController
@RequestMapping("/test-support")
@RequiredArgsConstructor
public class TestSupportController {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    private final SnapshotStrategy snapshotStrategy;
    private final TestSupportProperties properties;

    /**
     * 创建快照。同步执行，成功返回 snapshotId；调用本身兼作被测系统存活探测。
     */
    @PostMapping("/snapshot")
    public SnapshotResponse snapshot(@RequestBody SnapshotRequest request) {
        SnapshotCommand command = SnapshotCommand.builder()
                .scope(request.getScope())
                .tables(request.getTables())
                .label(request.getLabel())
                .meta(request.getMeta())
                .build();
        var result = runWithTimeout(() -> snapshotStrategy.snapshot(command), "snapshot");
        return SnapshotResponse.builder()
                .snapshotId(result.getSnapshotId())
                .createdAt(result.getCreatedAt() != null ? ISO_FORMATTER.format(result.getCreatedAt()) : null)
                .scope(result.getScope())
                .status(result.getStatus())
                .build();
    }

    /**
     * 按 snapshotId 还原数据。同步执行，同一 snapshotId 可多次调用。
     */
    @PostMapping("/restore")
    public RestoreResponse restore(@RequestBody RestoreRequest request) {
        if (!StringUtils.hasText(request.getSnapshotId())) {
            throw new TestSupportException("BAD_REQUEST", "snapshotId is required");
        }
        runWithTimeout(() -> {
            snapshotStrategy.restore(request.getSnapshotId());
            return null;
        }, "restore");
        return RestoreResponse.builder()
                .snapshotId(request.getSnapshotId())
                .status("restored")
                .build();
    }

    /**
     * 在独立线程中执行策略方法，超过配置的超时时间则中断并抛出 TIMEOUT。
     */
    private <T> T runWithTimeout(Callable<T> task, String operation) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<T> future = executor.submit(task);
            return future.get(properties.getDefaultTimeoutMs(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException ex) {
            throw new TestSupportException("TIMEOUT", operation + " timed out after " + properties.getDefaultTimeoutMs() + "ms");
        } catch (ExecutionException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof TestSupportException testSupportException) {
                throw testSupportException;
            }
            String message = cause != null && cause.getMessage() != null ? cause.getMessage() : operation + " failed";
            throw new TestSupportException("INTERNAL_ERROR", message, cause);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new TestSupportException("INTERNAL_ERROR", operation + " interrupted");
        } finally {
            executor.shutdownNow();
        }
    }
}
