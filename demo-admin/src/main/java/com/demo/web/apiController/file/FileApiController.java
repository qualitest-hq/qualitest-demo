package com.demo.web.apiController.file;

import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import com.demo.file.apiResult.FileObjectApiResult;
import com.demo.file.apiResult.FileUploadApiResult;
import com.demo.file.service.IFileObjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 进阶文件 API（RustFS 对象存储）
 *
 * @api.group 客户端.进阶.文件
 *
 * @author demo
 */
@RestController
@RequestMapping("/api/file")
@AllArgsConstructor
public class FileApiController extends ApiController {

    private final IFileObjectService fileObjectService;

    /**
     * 上传文件到 RustFS
     *
     * @param file    上传文件
     * @param bizType 业务分类，如 avatar / product
     */
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R<FileUploadApiResult> upload(
            @RequestPart("file") MultipartFile file,
            @RequestParam(value = "bizType", required = false) String bizType) {
        return ok(fileObjectService.upload(file, bizType));
    }

    /**
     * 查询文件元信息 / 预签名下载 URL
     *
     * @param key 对象 key（上传返回的 objectKey）
     */
    @GetMapping
    public R<FileObjectApiResult> meta(@RequestParam("key") String key) {
        return ok(fileObjectService.getObjectMeta(key));
    }

    /**
     * 按 key 删除文件（清理用例）
     *
     * @param key 对象 key
     */
    @DeleteMapping
    public R<Void> delete(@RequestParam("key") String key) {
        fileObjectService.delete(key);
        return ok();
    }
}
