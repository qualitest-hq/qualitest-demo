package com.demo.scenario.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 测试场景清单项（manifest.scenarios[]）
 */
@Getter
@Setter
@NoArgsConstructor
public class TestScenarioItem implements Serializable {

    private String id;

    private String name;

    /** success | fail */
    private String type;

    /** 相对 sql/seed/ 的 SQL 路径 */
    private String file;

    private List<String> acceptanceCases;

    private TestScenarioAccount account;

    private String expect;

    private String orderHint;
}
