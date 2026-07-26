package com.demo.web.controller.test;

import com.demo.common.annotation.Log;
import com.demo.common.core.controller.BaseController;
import com.demo.common.core.domain.R;
import com.demo.common.enums.BusinessType;
import com.demo.scenario.domain.TestScenarioItem;
import com.demo.scenario.result.TestScenarioLoadResult;
import com.demo.scenario.service.ITestScenarioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试场景数据准备
 *
 * @author qualitest
 */
@RestController
@RequestMapping("/web/test/scenario")
@AllArgsConstructor
public class TestScenarioController extends BaseController {

    private final ITestScenarioService testScenarioService;

    @Operation(summary = "测试场景列表")
    @PreAuthorize("@ss.hasPermi('tool:scenario:list')")
    @GetMapping("/list")
    public R<List<TestScenarioItem>> list() {
        return R.ok(testScenarioService.listScenarios());
    }

    @Operation(summary = "当前已加载场景")
    @PreAuthorize("@ss.hasPermi('tool:scenario:list')")
    @GetMapping("/current")
    public R<Map<String, String>> current() {
        Map<String, String> data = new HashMap<>(1);
        String scenarioId = testScenarioService.getCurrentScenarioId();
        data.put("scenarioId", scenarioId);
        return R.ok(data);
    }

    @Operation(summary = "重置基线 seed")
    @PreAuthorize("@ss.hasPermi('tool:scenario:reset')")
    @Log(title = "测试场景", businessType = BusinessType.OTHER)
    @PostMapping("/reset")
    public R<TestScenarioLoadResult> reset() {
        TestScenarioLoadResult result = testScenarioService.resetBaseline();
        return R.ok(result, "基线数据已重置");
    }

    @Operation(summary = "加载测试场景")
    @PreAuthorize("@ss.hasPermi('tool:scenario:load')")
    @Log(title = "测试场景", businessType = BusinessType.OTHER)
    @PostMapping("/load/{id}")
    public R<TestScenarioLoadResult> load(
            @PathVariable("id") String id,
            @RequestParam(value = "noReset", defaultValue = "false") boolean noReset) {
        TestScenarioLoadResult result = testScenarioService.loadScenario(id, !noReset);
        return R.ok(result, "场景 " + result.getScenarioId() + " 已加载");
    }
}
