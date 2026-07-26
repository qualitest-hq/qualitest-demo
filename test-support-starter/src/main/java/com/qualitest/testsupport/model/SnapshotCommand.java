package com.qualitest.testsupport.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Map;

/**
 * 传给 SnapshotStrategy 的快照命令（由 HTTP 请求体转换而来）
 */
@Value
@Builder
public class SnapshotCommand {

    /**
     * 快照范围类型，如 tables、schema
     */
    String scope;

    /**
     * scope 为 tables 时要备份的表名列表
     */
    List<String> tables;

    /**
     * 调用方传入的关联标签，便于日志与排查
     */
    String label;

    /**
     * 附加元信息，如环境名、节点名
     */
    Map<String, Object> meta;
}
