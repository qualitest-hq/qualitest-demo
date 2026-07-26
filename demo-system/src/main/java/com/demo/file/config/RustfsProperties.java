package com.demo.file.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RustFS（S3 兼容）对象存储配置，绑定 {@code demo.rustfs.*}。
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "demo.rustfs")
public class RustfsProperties {

    /** 是否启用 RustFS；false 时文件 API 走本地 profile 兜底 */
    private boolean enabled = false;

    /** S3 API endpoint，如 http://127.0.0.1:9000 */
    private String endpoint = "http://127.0.0.1:9000";

    private String accessKey = "rustfsadmin";

    private String secretKey = "rustfsadmin";

    private String bucket = "qualitest-demo";

    private String region = "us-east-1";

    /** 对外访问前缀（拼公开 URL），如 http://127.0.0.1:9000/qualitest-demo */
    private String publicBaseUrl;
}
