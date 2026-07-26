package com.qualitest.testsupport.model;

import lombok.Data;

/**
 * POST /test-support/restore 请求体
 */
@Data
public class RestoreRequest {

    /**
     * 要还原的快照标识
     */
    private String snapshotId;
}
