package com.demo.web.controller.account;

import com.demo.account.params.AccountBalanceRecordParams;
import com.demo.account.result.AccountBalanceRecordResult;
import com.demo.account.service.IAccountBalanceRecordService;
import com.demo.common.annotation.Log;
import com.demo.common.core.controller.BaseController;
import com.demo.common.core.domain.R;
import com.demo.common.core.page.TableDataInfo;
import com.demo.common.enums.BusinessType;
import com.demo.common.utils.poi.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账号余额流水Controller
 *
 * @api.group 管理端.账号.余额流水
 *
 * @author demo
 * @date 2026-06-28
 */
@RestController
@RequestMapping("/web/account/accountBalanceRecord")
@AllArgsConstructor
public class AccountBalanceRecordController extends BaseController {

    private final IAccountBalanceRecordService accountBalanceRecordService;

    /**
     * 查询余额流水列表
     */
    @PreAuthorize("@ss.hasPermi('account:accountBalanceRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountBalanceRecordParams params) {
        startPage();
        List<AccountBalanceRecordResult> list = accountBalanceRecordService.selectAccountBalanceRecordResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出余额流水
     */
    @PreAuthorize("@ss.hasPermi('account:accountBalanceRecord:export')")
    @Log(title = "余额流水", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AccountBalanceRecordParams params) {
        List<AccountBalanceRecordResult> list = accountBalanceRecordService.selectAccountBalanceRecordResultList(params);
        ExcelUtil<AccountBalanceRecordResult> util = new ExcelUtil<>(AccountBalanceRecordResult.class);
        util.exportExcel(response, list, "余额流水数据");
    }

    /**
     * 获取余额流水详情
     */
    @PreAuthorize("@ss.hasPermi('account:accountBalanceRecord:query')")
    @GetMapping("/{balanceRecordId}")
    public R<AccountBalanceRecordResult> getInfo(@PathVariable("balanceRecordId") Long balanceRecordId) {
        return ok(accountBalanceRecordService.selectAccountBalanceRecordResult(balanceRecordId));
    }
}
