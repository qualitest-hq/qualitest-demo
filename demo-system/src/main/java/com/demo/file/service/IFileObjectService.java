package com.demo.file.service;

import com.demo.file.apiResult.FileObjectApiResult;
import com.demo.file.apiResult.FileUploadApiResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 进阶文件对象服务（RustFS / 本地兜底）
 */
public interface IFileObjectService {

    /**
     * 上传文件
     *
     * @param file    必填
     * @param bizType 业务分类，可选（如 avatar / product）
     */
    FileUploadApiResult upload(MultipartFile file, String bizType);

    /**
     * 查询对象元信息与预签名 URL
     *
     * @param objectKey 对象 key
     */
    FileObjectApiResult getObjectMeta(String objectKey);

    /**
     * 按 key 删除对象
     *
     * @param objectKey 对象 key
     */
    void delete(String objectKey);
}
