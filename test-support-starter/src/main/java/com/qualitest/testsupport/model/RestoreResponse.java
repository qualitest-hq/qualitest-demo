package com.qualitest.testsupport.model;

import lombok.Builder;
import lombok.Value;

/**
 * POST /test-support/restore 成功响应体
 */
@Value
@Builder
public class RestoreResponse {

    private String snapshotId;

    /**
     * 固定为 restored 表示还原完成
     */
    private String status;
}
