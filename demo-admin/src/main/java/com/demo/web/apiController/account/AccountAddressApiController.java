package com.demo.web.apiController.account;

import com.demo.account.apiParams.AccountAddressApiParams;
import com.demo.account.apiParams.AccountAddressMySaveApiParams;
import com.demo.account.apiResult.AccountAddressApiResult;
import com.demo.account.service.IAccountAddressService;
import com.demo.common.core.controller.ApiController;
import com.demo.common.core.domain.R;
import com.demo.common.core.page.TableData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账号收货地址客户端 API
 *
 * @api.group 客户端.账号.收货地址
 *
 * @author demo
 * @date 2026-06-20
 */
@RestController
@RequestMapping("/api/account/accountAddress")
@AllArgsConstructor
public class AccountAddressApiController extends ApiController {

    private final IAccountAddressService accountAddressService;

    /**
     * 当前用户地址列表
     */
    @GetMapping("/my")
    public R<TableData<AccountAddressApiResult>> myList(AccountAddressApiParams params) {
        Long accountId = getAccountId();
        if (params == null) {
            params = new AccountAddressApiParams();
        }
        params.setAccountId(accountId);
        startPage();
        List<AccountAddressApiResult> list = accountAddressService.selectAccountAddressApiResultList(params);
        return getTableData(list);
    }

    /**
     * 当前用户单条地址详情
     */
    @GetMapping("/my/{addressId}")
    public R<AccountAddressApiResult> myGetInfo(@PathVariable("addressId") Long addressId) {
        Long accountId = getAccountId();
        return ok(accountAddressService.selectAccountAddressApiResult(addressId, accountId));
    }

    /**
     * 新增当前用户地址
     */
    @PostMapping("/my")
    public R<AccountAddressApiResult> myAdd(@RequestBody AccountAddressMySaveApiParams params) {
        Long accountId = getAccountId();
        return ok(accountAddressService.saveAccountAddressForAccount(accountId, params));
    }

    /**
     * 修改当前用户地址
     */
    @PutMapping("/my")
    public R<Void> myEdit(@RequestBody AccountAddressMySaveApiParams params) {
        Long accountId = getAccountId();
        accountAddressService.updateAccountAddressForAccount(accountId, params);
        return ok();
    }

    /**
     * 设为默认地址
     */
    @PutMapping("/my/{addressId}/default")
    public R<Void> mySetDefault(@PathVariable("addressId") Long addressId) {
        Long accountId = getAccountId();
        accountAddressService.setDefaultAccountAddress(accountId, addressId);
        return ok();
    }

    /**
     * 删除当前用户地址
     */
    @DeleteMapping("/my/{addressId}")
    public R<Void> myRemove(@PathVariable("addressId") Long addressId) {
        Long accountId = getAccountId();
        accountAddressService.deleteAccountAddressForAccount(accountId, addressId);
        return ok();
    }
}
