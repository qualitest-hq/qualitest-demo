package com.demo.scenario.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 测试场景推荐账号
 */
@Getter
@Setter
@NoArgsConstructor
public class TestScenarioAccount implements Serializable {

    private String phone;

    private String password;

    private String remark;
}
