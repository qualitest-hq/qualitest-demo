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
import com.demo.account.domain.AccountAddress;
import com.demo.account.params.AccountAddressParams;
import com.demo.account.result.AccountAddressResult;
import com.demo.account.service.IAccountAddressService;
import com.demo.common.core.text.Convert;
import com.demo.common.utils.poi.ExcelUtil;
import com.demo.common.core.page.TableDataInfo;

/**
 * 账号收货地址Controller
 *
 * @api.group 管理端.账号.收货地址
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/web/account/accountAddress")
@AllArgsConstructor
public class AccountAddressController extends BaseController {

    private final IAccountAddressService accountAddressService;

    /**
     * 查询账号收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('account:accountAddress:list')")
    @GetMapping("/list")
    public TableDataInfo list(AccountAddressParams params) {
        startPage();
        List<AccountAddressResult> list = accountAddressService.selectAccountAddressResultList(params);
        return getDataTable(list);
    }

    /**
     * 导出账号收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('account:accountAddress:export')")
    @Log(title = "账号收货地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AccountAddressParams params) {
        List<AccountAddressResult> list = accountAddressService.selectAccountAddressResultList(params);
        ExcelUtil<AccountAddressResult> util = new ExcelUtil<>(AccountAddressResult.class);
        util.exportExcel(response, list, "账号收货地址数据");
    }

    /**
     * 获取账号收货地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('account:accountAddress:query')")
    @GetMapping(value = "/{addressId}")
    public R<AccountAddressResult> getInfo(@PathVariable("addressId") Long addressId) {
        return ok(accountAddressService.selectAccountAddressResult(addressId));
    }

    /**
     * 新增账号收货地址
     */
    @PreAuthorize("@ss.hasPermi('account:accountAddress:add')")
    @Log(title = "账号收货地址", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Void> add(@RequestBody AccountAddress accountAddress) {
        return toR(accountAddressService.insertAccountAddress(accountAddress));
    }

    /**
     * 修改账号收货地址
     */
    @PreAuthorize("@ss.hasPermi('account:accountAddress:edit')")
    @Log(title = "账号收货地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Void> edit(@RequestBody AccountAddress accountAddress) {
        return toR(accountAddressService.updateAccountAddress(accountAddress));
    }

    /**
     * 删除账号收货地址
     */
    @PreAuthorize("@ss.hasPermi('account:accountAddress:remove')")
    @Log(title = "账号收货地址", businessType = BusinessType.DELETE)
    @DeleteMapping("/{addressIds}")
    public R<Void> remove(@PathVariable Long[] addressIds) {
        List<Long> idList = Convert.toLongList(addressIds);
        return toR(accountAddressService.logicDeleteAccountAddressByIdList(idList));
    }
}
