package com.demo.file.config;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.time.Duration;

/**
 * RustFS S3 客户端与预签名器。仅在 {@code demo.rustfs.enabled=true} 时注册。
 */
@Configuration
@ConditionalOnProperty(prefix = "demo.rustfs", name = "enabled", havingValue = "true")
public class RustfsConfig {

    private static final Logger log = LoggerFactory.getLogger(RustfsConfig.class);

    private S3Client s3Client;
    private S3Presigner s3Presigner;

    @Bean
    public S3Client rustfsS3Client(RustfsProperties properties) {
        StaticCredentialsProvider credentials = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey()));
        s3Client = S3Client.builder()
                .endpointOverride(URI.create(properties.getEndpoint()))
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(credentials)
                .forcePathStyle(true)
                .httpClientBuilder(ApacheHttpClient.builder()
                        .connectionTimeout(Duration.ofSeconds(5))
                        .socketTimeout(Duration.ofSeconds(30)))
                .build();
        ensureBucket(s3Client, properties.getBucket());
        return s3Client;
    }

    @Bean
    public S3Presigner rustfsS3Presigner(RustfsProperties properties) {
        StaticCredentialsProvider credentials = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey()));
        s3Presigner = S3Presigner.builder()
                .endpointOverride(URI.create(properties.getEndpoint()))
                .region(Region.of(properties.getRegion()))
                .credentialsProvider(credentials)
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
        return s3Presigner;
    }

    private void ensureBucket(S3Client client, String bucket) {
        try {
            client.headBucket(HeadBucketRequest.builder().bucket(bucket).build());
            log.info("RustFS bucket 已存在: {}", bucket);
        } catch (NoSuchBucketException e) {
            try {
                client.createBucket(CreateBucketRequest.builder().bucket(bucket).build());
                log.info("RustFS bucket 已创建: {}", bucket);
            } catch (S3Exception createEx) {
                log.warn("RustFS 创建 bucket 失败（不阻断启动）: {} - {}", bucket, createEx.getMessage());
            }
        } catch (Exception e) {
            log.warn("RustFS bucket 检查失败（不阻断启动）: {} - {}", bucket, e.getMessage());
        }
    }

    @PreDestroy
    public void destroy() {
        if (s3Presigner != null) {
            s3Presigner.close();
        }
        if (s3Client != null) {
            s3Client.close();
        }
    }
}
