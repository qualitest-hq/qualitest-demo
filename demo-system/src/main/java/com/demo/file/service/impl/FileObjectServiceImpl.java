package com.demo.file.service.impl;

import com.demo.common.config.DemoConfig;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.StringUtils;
import com.demo.common.utils.uuid.IdUtils;
import com.demo.file.apiResult.FileObjectApiResult;
import com.demo.file.apiResult.FileUploadApiResult;
import com.demo.file.config.RustfsProperties;
import com.demo.file.service.IFileObjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 文件对象服务：优先写 RustFS，不可达或未启用时落本地 {@code demo.profile}。
 */
@Service
public class FileObjectServiceImpl implements IFileObjectService {

    private static final Logger log = LoggerFactory.getLogger(FileObjectServiceImpl.class);

    private static final String STORAGE_RUSTFS = "rustfs";
    private static final String STORAGE_LOCAL = "local";
    private static final DateTimeFormatter DAY_FMT = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final Duration PRESIGN_TTL = Duration.ofMinutes(30);

    private final RustfsProperties rustfsProperties;
    private final ObjectProvider<S3Client> s3ClientProvider;
    private final ObjectProvider<S3Presigner> s3PresignerProvider;

    public FileObjectServiceImpl(RustfsProperties rustfsProperties,
                                 ObjectProvider<S3Client> s3ClientProvider,
                                 ObjectProvider<S3Presigner> s3PresignerProvider) {
        this.rustfsProperties = rustfsProperties;
        this.s3ClientProvider = s3ClientProvider;
        this.s3PresignerProvider = s3PresignerProvider;
    }

    @Override
    public FileUploadApiResult upload(MultipartFile file, String bizType) {
        if (file == null || file.isEmpty()) {
            throw new ServiceException("上传文件不能为空");
        }
        String objectKey = buildObjectKey(bizType, file.getOriginalFilename());
        String contentType = StringUtils.isNotEmpty(file.getContentType())
                ? file.getContentType()
                : "application/octet-stream";

        S3Client s3 = s3ClientProvider.getIfAvailable();
        if (rustfsProperties.isEnabled() && s3 != null) {
            try {
                PutObjectRequest put = PutObjectRequest.builder()
                        .bucket(rustfsProperties.getBucket())
                        .key(objectKey)
                        .contentType(contentType)
                        .contentLength(file.getSize())
                        .build();
                try (InputStream in = file.getInputStream()) {
                    s3.putObject(put, RequestBody.fromInputStream(in, file.getSize()));
                }
                return FileUploadApiResult.builder()
                        .objectKey(objectKey)
                        .url(publicUrl(objectKey))
                        .size(file.getSize())
                        .contentType(contentType)
                        .originalFilename(file.getOriginalFilename())
                        .storage(STORAGE_RUSTFS)
                        .build();
            } catch (Exception e) {
                log.warn("RustFS 上传失败，回退本地盘: {}", e.getMessage());
            }
        } else if (rustfsProperties.isEnabled()) {
            log.warn("RustFS 已启用但 S3Client 不可用，回退本地盘");
        }

        return uploadLocal(file, objectKey, contentType);
    }

    @Override
    public FileObjectApiResult getObjectMeta(String objectKey) {
        requireKey(objectKey);
        S3Client s3 = s3ClientProvider.getIfAvailable();
        if (rustfsProperties.isEnabled() && s3 != null) {
            try {
                HeadObjectResponse head = s3.headObject(HeadObjectRequest.builder()
                        .bucket(rustfsProperties.getBucket())
                        .key(objectKey)
                        .build());
                String presigned = null;
                S3Presigner presigner = s3PresignerProvider.getIfAvailable();
                if (presigner != null) {
                    PresignedGetObjectRequest presignedReq = presigner.presignGetObject(
                            GetObjectPresignRequest.builder()
                                    .signatureDuration(PRESIGN_TTL)
                                    .getObjectRequest(b -> b.bucket(rustfsProperties.getBucket()).key(objectKey))
                                    .build());
                    presigned = presignedReq.url().toString();
                }
                Instant lastMod = head.lastModified();
                return FileObjectApiResult.builder()
                        .objectKey(objectKey)
                        .url(publicUrl(objectKey))
                        .presignedUrl(presigned)
                        .size(head.contentLength())
                        .contentType(head.contentType())
                        .storage(STORAGE_RUSTFS)
                        .lastModified(lastMod != null ? Date.from(lastMod) : null)
                        .build();
            } catch (NoSuchKeyException e) {
                throw new ServiceException("文件不存在: " + objectKey);
            } catch (S3Exception e) {
                if (e.statusCode() == 404) {
                    throw new ServiceException("文件不存在: " + objectKey);
                }
                log.warn("RustFS 查询元信息失败，尝试本地: {}", e.getMessage());
            } catch (Exception e) {
                log.warn("RustFS 查询元信息失败，尝试本地: {}", e.getMessage());
            }
        }

        Path local = localPath(objectKey);
        if (!Files.isRegularFile(local)) {
            throw new ServiceException("文件不存在: " + objectKey);
        }
        try {
            return FileObjectApiResult.builder()
                    .objectKey(objectKey)
                    .url(localUrl(objectKey))
                    .presignedUrl(null)
                    .size(Files.size(local))
                    .contentType(Files.probeContentType(local))
                    .storage(STORAGE_LOCAL)
                    .lastModified(Date.from(Files.getLastModifiedTime(local).toInstant()))
                    .build();
        } catch (IOException e) {
            throw new ServiceException("读取本地文件失败: " + e.getMessage());
        }
    }

    @Override
    public void delete(String objectKey) {
        requireKey(objectKey);
        boolean deleted = false;
        S3Client s3 = s3ClientProvider.getIfAvailable();
        if (rustfsProperties.isEnabled() && s3 != null) {
            try {
                s3.deleteObject(DeleteObjectRequest.builder()
                        .bucket(rustfsProperties.getBucket())
                        .key(objectKey)
                        .build());
                deleted = true;
            } catch (Exception e) {
                log.warn("RustFS 删除失败，尝试本地: {}", e.getMessage());
            }
        }
        Path local = localPath(objectKey);
        try {
            if (Files.deleteIfExists(local)) {
                deleted = true;
            }
        } catch (IOException e) {
            log.warn("本地文件删除失败: {}", e.getMessage());
        }
        if (!deleted) {
            throw new ServiceException("文件不存在或删除失败: " + objectKey);
        }
    }

    private FileUploadApiResult uploadLocal(MultipartFile file, String objectKey, String contentType) {
        Path target = localPath(objectKey);
        try {
            Files.createDirectories(target.getParent());
            file.transferTo(target);
        } catch (IOException e) {
            throw new ServiceException("本地保存文件失败: " + e.getMessage());
        }
        log.warn("文件已写入本地兜底路径: {}", target);
        return FileUploadApiResult.builder()
                .objectKey(objectKey)
                .url(localUrl(objectKey))
                .size(file.getSize())
                .contentType(contentType)
                .originalFilename(file.getOriginalFilename())
                .storage(STORAGE_LOCAL)
                .build();
    }

    private String buildObjectKey(String bizType, String originalFilename) {
        String prefix = StringUtils.isNotEmpty(bizType) ? sanitizeSegment(bizType) : "misc";
        String day = LocalDate.now().format(DAY_FMT);
        String safeName = sanitizeFilename(originalFilename);
        return prefix + "/" + day + "/" + IdUtils.fastSimpleUUID() + "_" + safeName;
    }

    private static String sanitizeSegment(String raw) {
        String s = raw.trim().replaceAll("[\\\\/]+", "-");
        s = s.replaceAll("[^a-zA-Z0-9._\\-]", "_");
        return StringUtils.isEmpty(s) ? "misc" : s;
    }

    private static String sanitizeFilename(String originalFilename) {
        if (StringUtils.isEmpty(originalFilename)) {
            return "file.bin";
        }
        String name = Paths.get(originalFilename).getFileName().toString();
        name = name.replaceAll("[\\\\/]+", "_");
        name = name.replaceAll("[^a-zA-Z0-9._\\-\\u4e00-\\u9fa5]", "_");
        if (name.length() > 80) {
            name = name.substring(name.length() - 80);
        }
        return StringUtils.isEmpty(name) ? "file.bin" : name;
    }

    private String publicUrl(String objectKey) {
        String base = rustfsProperties.getPublicBaseUrl();
        if (StringUtils.isEmpty(base)) {
            base = rustfsProperties.getEndpoint() + "/" + rustfsProperties.getBucket();
        }
        if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        return base + "/" + objectKey;
    }

    private String localUrl(String objectKey) {
        return "/profile/upload/" + objectKey;
    }

    private Path localPath(String objectKey) {
        return Paths.get(DemoConfig.getUploadPath(), objectKey.replace("/", java.io.File.separator));
    }

    private static void requireKey(String objectKey) {
        if (StringUtils.isEmpty(objectKey)) {
            throw new ServiceException("objectKey 不能为空");
        }
        if (objectKey.contains("..")) {
            throw new ServiceException("非法 objectKey");
        }
    }
}
