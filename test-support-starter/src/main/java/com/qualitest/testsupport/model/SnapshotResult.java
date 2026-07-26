package com.qualitest.testsupport.model;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

/**
 * SnapshotStrategy.snapshot 的返回结果
 */
@Value
@Builder
public class SnapshotResult {

    /**
     * 快照唯一标识，restore 时回传
     */
    String snapshotId;

    /**
     * 快照创建时间
     */
    Instant createdAt;

    /**
     * 实际使用的快照范围
     */
    String scope;

    /**
     * 快照状态，如 ready
     */
    String status;
}
