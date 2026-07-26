package com.qualitest.testsupport.spi;

import com.qualitest.testsupport.model.SnapshotCommand;
import com.qualitest.testsupport.model.SnapshotResult;

/**
 * 被测系统快照策略接口。
 * Starter 只负责 HTTP 暴露与超时控制，具体备份与还原逻辑由接入方实现。
 */
public interface SnapshotStrategy {

    /**
     * 按命令创建快照，返回快照标识与状态
     */
    SnapshotResult snapshot(SnapshotCommand cmd);

    /**
     * 按快照标识将数据还原到快照时点，需支持重复调用结果不变
     */
    void restore(String snapshotId);
}
