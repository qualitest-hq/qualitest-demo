package com.demo.web.controller.account;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.common.annotation.Log;
import com.demo.common.core.controller.BaseController;
import com.demo.common.core.domain.R;
import com.demo.common.enums.BusinessType;
import com.demo.account.domain.Account;
import com.demo.account.params.AccountParams;
import com.demo.account.params.GiftBalanceParams;
import com.demo.account.result.AccountResult;
import com.demo.account.service.IAccountService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 用户账号Controller
 *
 * @api.group 管理端.账号.用户账号
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/account/account")
@AllArgsConstructor
public class AccountController extends BaseController {

    private final IAccountService accountService;

    /**
     * 查询用户账号列表
     */
    @PreAuthorize("@ss.hasPermi('account:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountParams params) {
        startPage();
        List<AccountResult> list = accountService.selectAccountResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出用户账号列表
     */
    @PreAuthorize("@ss.hasPermi('account:account:export')")
    @Log(title = "用户账号", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AccountParams params) {
        List<AccountResult> list = accountService.selectAccountResultList(params);
        ExcelUtil<AccountResult> util = new ExcelUtil<>(AccountResult.class);
        util.exportExcel(response, list, "用户账号数据");
    }

    /**
     * 获取用户账号详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:account:query')")
    @GetMapping(value = "/{accountId}")
    public R<AccountResult> getInfo(@PathVariable("accountId") Long accountId) {
        return ok(accountService.selectAccountResult(accountId));
    }

    /**
     * 新增用户账号
     */
    @PreAuthorize("@ss.hasPermi('account:account:add')")
    @Log(title = "用户账号", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody Account account) {
        return toR(accountService.insertAccount(account));
    }

    /**
     * 修改用户账号
     */
    @PreAuthorize("@ss.hasPermi('account:account:edit')")
    @Log(title = "用户账号", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody Account account) {
        return toR(accountService.updateAccount(account));
    }

    /**
     * 赠送账户余额
     */
    @PreAuthorize("@ss.hasPermi('account:account:edit')")
    @Log(title = "用户账号", businessType = BusinessType.UPDATE)
    @PutMapping("/giftBalance")
    public R<AccountResult> giftBalance(@RequestBody GiftBalanceParams params) {
        accountService.giftBalance(params.getAccountId(), params.getAmount(), params.getRemark());
        return ok(accountService.selectAccountResult(params.getAccountId()));
    }

    /**
     * 删除用户账号
     */
    @PreAuthorize("@ss.hasPermi('account:account:remove')")
    @Log(title = "用户账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{accountIds}")
    public R<Void> remove(@PathVariable Long[] accountIds) {
        List<Long> idList = Convert.toLongList(accountIds);
        return toR(accountService.logicDeleteAccountByIdList(idList));
    }
}
