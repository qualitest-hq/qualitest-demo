package com.qualitest.testsupport.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * POST /test-support/snapshot 请求体
 */
@Data
public class SnapshotRequest {

    /**
     * 快照范围类型
     */
    private String scope;

    /**
     * 要备份的表名列表
     */
    private List<String> tables;

    /**
     * 关联标签
     */
    private String label;

    /**
     * 附加元信息
     */
    private Map<String, Object> meta;
}
