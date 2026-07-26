package com.qualitest.testsupport.model;

import lombok.Builder;
import lombok.Value;

/**
 * POST /test-support/snapshot 成功响应体
 */
@Value
@Builder
public class SnapshotResponse {

    private String snapshotId;

    /**
     * ISO-8601 时间字符串
     */
    private String createdAt;

    private String scope;

    private String status;
}
