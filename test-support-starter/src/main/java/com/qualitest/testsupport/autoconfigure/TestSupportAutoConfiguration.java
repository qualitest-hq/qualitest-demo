package com.qualitest.testsupport.autoconfigure;

import com.qualitest.testsupport.config.TestSupportProperties;
import com.qualitest.testsupport.spi.SnapshotStrategy;
import com.qualitest.testsupport.web.TestSupportController;
import com.qualitest.testsupport.web.TestSupportExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

/**
 * test-support 自动配置。
 * 同时满足：Web 应用、enabled=true、存在 SnapshotStrategy、当前非 prod/production/prd profile 时才注册端点。
 */
@AutoConfiguration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "qualitest.test-support", name = "enabled", havingValue = "true")
@ConditionalOnBean(SnapshotStrategy.class)
@Profile("!prod & !production & !prd")
@EnableConfigurationProperties(TestSupportProperties.class)
public class TestSupportAutoConfiguration {

    @Bean
    public TestSupportController testSupportController(SnapshotStrategy snapshotStrategy,
                                                       TestSupportProperties properties) {
        return new TestSupportController(snapshotStrategy, properties);
    }

    @Bean
    public TestSupportExceptionHandler testSupportExceptionHandler() {
        return new TestSupportExceptionHandler();
    }
}
