package com.demo.scenario.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 测试场景 manifest 根对象
 */
@Getter
@Setter
@NoArgsConstructor
public class TestScenarioManifest implements Serializable {

    private Integer version;

    private TestScenarioDefaults defaults;

    private List<TestScenarioItem> scenarios;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class TestScenarioDefaults implements Serializable {

        private Boolean autoReset;

        private String dbName;
    }
}
