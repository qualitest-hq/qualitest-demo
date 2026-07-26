package com.demo.file.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件对象元信息（客户端）
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("FileObjectApiResult")
public class FileObjectApiResult implements Serializable {

    private String objectKey;

    private String url;

    /** 预签名下载 URL（RustFS）；本地兜底时可能为空 */
    private String presignedUrl;

    private Long size;

    private String contentType;

    private String storage;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModified;
}
