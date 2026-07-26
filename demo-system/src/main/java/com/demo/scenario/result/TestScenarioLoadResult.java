package com.demo.scenario.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 测试场景加载结果
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestScenarioLoadResult implements Serializable {

    private String scenarioId;

    private String name;

    private boolean resetExecuted;

    private long elapsedMs;
}
