package com.demo.web.apiController.account;

import com.demo.account.apiParams.AccountBalanceRecordApiParams;
import com.demo.account.apiParams.AccountBalanceRechargeApiParams;
import com.demo.account.apiResult.AccountBalanceRecordApiResult;
import com.demo.account.service.IAccountBalanceRecordService;
import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import com.demo.common.core.page.TableData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账号余额流水客户端 API
 *
 * @api.group 客户端.账号.余额流水
 *
 * @author demo
 * @date 2026-06-28
 */
@RestController
@RequestMapping("/api/account/accountBalanceRecord")
@AllArgsConstructor
public class AccountBalanceRecordApiController extends ApiController {

    private final IAccountBalanceRecordService accountBalanceRecordService;

    /**
     * 我的余额流水
     */
    @GetMapping("/my")
    public R<TableData<AccountBalanceRecordApiResult>> myList(AccountBalanceRecordApiParams params) {
        Long accountId = getAccountId();
        if (params == null) {
            params = new AccountBalanceRecordApiParams();
        }
        params.setAccountId(accountId);
        startPage();
        List<AccountBalanceRecordApiResult> list =
                accountBalanceRecordService.selectAccountBalanceRecordApiResultList(params);
        return getTableData(list);
    }

    /**
     * 我的余额流水详情
     */
    @GetMapping("/my/{balanceRecordId}")
    public R<AccountBalanceRecordApiResult> myDetail(@PathVariable("balanceRecordId") Long balanceRecordId) {
        Long accountId = getAccountId();
        return ok(accountBalanceRecordService.selectAccountBalanceRecordApiResult(balanceRecordId, accountId));
    }

    /**
     * 模拟充值
     */
    @PostMapping("/recharge")
    public R<AccountBalanceRecordApiResult> recharge(@RequestBody AccountBalanceRechargeApiParams params) {
        Long accountId = getAccountId();
        return ok(accountBalanceRecordService.recharge(accountId, params));
    }
}
