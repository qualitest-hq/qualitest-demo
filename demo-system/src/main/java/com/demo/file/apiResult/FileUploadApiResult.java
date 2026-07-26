package com.demo.file.apiResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 文件上传结果（客户端）
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("FileUploadApiResult")
public class FileUploadApiResult implements Serializable {

    /** 对象存储 key */
    private String objectKey;

    /** 可访问 URL（公开前缀或本地映射） */
    private String url;

    /** 文件大小（字节） */
    private Long size;

    /** Content-Type */
    private String contentType;

    /** 原始文件名 */
    private String originalFilename;

    /**
     * 存储后端：{@code rustfs} 或 {@code local}
     */
    private String storage;
}
