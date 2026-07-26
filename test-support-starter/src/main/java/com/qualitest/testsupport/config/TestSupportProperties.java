package com.qualitest.testsupport.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * test-support 端点配置项，前缀 qualitest.test-support
 */
@Data
@ConfigurationProperties(prefix = "qualitest.test-support")
public class TestSupportProperties {

    /**
     * 是否启用 snapshot/restore 端点。
     * 关闭时不注册 Controller；生产 profile 下 AutoConfiguration 本身也不会加载。
     */
    private boolean enabled = false;

    /**
     * snapshot、restore 单次调用的最大等待时间（毫秒），超时返回 TIMEOUT 错误。
     */
    private long defaultTimeoutMs = 30_000L;
}
