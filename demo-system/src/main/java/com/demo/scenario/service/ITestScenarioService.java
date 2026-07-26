package com.demo.scenario.service;

import com.demo.scenario.domain.TestScenarioItem;
import com.demo.scenario.result.TestScenarioLoadResult;

import java.util.List;

/**
 * 测试场景数据准备服务
 */
public interface ITestScenarioService {

    List<TestScenarioItem> listScenarios();

    String getCurrentScenarioId();

    TestScenarioLoadResult resetBaseline();

    TestScenarioLoadResult loadScenario(String scenarioId, boolean autoReset);
}
