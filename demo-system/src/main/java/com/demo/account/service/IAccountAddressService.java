package com.demo.account.service;

import java.util.List;
import com.demo.account.domain.AccountAddress;
import com.demo.account.params.AccountAddressParams;
import com.demo.account.apiParams.AccountAddressApiParams;
import com.demo.account.apiParams.AccountAddressMySaveApiParams;
import com.demo.account.result.AccountAddressResult;
import com.demo.account.apiResult.AccountAddressApiResult;

/**
 * 账号收货地址Service接口
 * 
 * @author demo
 * @date 2026-06-20
 */
public interface IAccountAddressService {
    /**
     * 查询账号收货地址列表
     *
     * @param accountAddress 账号收货地址
     * @return 账号收货地址集合
     */
    List<AccountAddress> selectAccountAddressList(AccountAddress accountAddress);

    /**
     * 查询账号收货地址
     *
     * @param addressId 账号收货地址主键
     * @return 账号收货地址
     */
    AccountAddress selectAccountAddressById(Long addressId);

    /**
     * 查询账号收货地址Result列表
     *
     * @param params 账号收货地址Params
     * @return 账号收货地址Result集合
     */
    List<AccountAddressResult> selectAccountAddressResultList(AccountAddressParams params);

    /**
     * 获取账号收货地址详细信息
     *
     * @param addressId 账号收货地址主键
     * @return 账号收货地址Result
     */
    AccountAddressResult selectAccountAddressResult(Long addressId);

    /**
     * 查询账号收货地址ApiResult列表（客户端）
     *
     * @param params 账号收货地址ApiParams
     * @return 账号收货地址ApiResult集合
     */
    List<AccountAddressApiResult> selectAccountAddressApiResultList(AccountAddressApiParams params);

    /**
     * 获取账号收货地址ApiResult详细信息（客户端）
     *
     * @param addressId 账号收货地址主键
     * @param accountId 账号ID（校验归属）
     * @return 账号收货地址ApiResult
     */
    AccountAddressApiResult selectAccountAddressApiResult(Long addressId, Long accountId);

    /**
     * 新增收货地址（客户端）；首条地址自动设为默认
     */
    AccountAddressApiResult saveAccountAddressForAccount(Long accountId, AccountAddressMySaveApiParams params);

    /**
     * 修改收货地址（客户端）；校验归属后更新
     */
    void updateAccountAddressForAccount(Long accountId, AccountAddressMySaveApiParams params);

    /**
     * 删除收货地址（客户端）；逻辑删除并校验归属
     */
    void deleteAccountAddressForAccount(Long accountId, Long addressId);

    /**
     * 设为默认收货地址（客户端）；同账号其他地址取消默认
     */
    void setDefaultAccountAddress(Long accountId, Long addressId);

    /**
     * 新增账号收货地址
     * 
     * @param accountAddress 账号收货地址
     * @return 结果
     */
    int insertAccountAddress(AccountAddress accountAddress);

    /**
     * 修改账号收货地址
     * 
     * @param accountAddress 账号收货地址
     * @return 结果
     */
    int updateAccountAddress(AccountAddress accountAddress);

    /**
     * 批量删除账号收货地址
     * 
     * @param addressIdList 需要删除的账号收货地址主键集合
     * @return 结果
     */
    int deleteAccountAddressByIdList(List<Long> addressIdList);

    /**
     * 删除账号收货地址信息
     * 
     * @param addressId 账号收货地址主键
     * @return 结果
     */
    public int deleteAccountAddressById(Long addressId);

    /**
     * 修改账号收货地址为逻辑删除
     *
     * @param addressId 账号收货地址ID
     * @return 结果
     */
    int logicDeleteAccountAddressById(Long addressId);

    /**
     * 批量修改账号收货地址为逻辑删除
     *
     * @param addressIdList 账号收货地址ID集合
     * @return 结果
     */
    int logicDeleteAccountAddressByIdList(List<Long> addressIdList);

    /**
     * 查询账号收货地址数量
     *
     * @param params 账号收货地址Params
     * @return 数量
     */
    int selectAccountAddressCount(AccountAddressParams params);

    /**
     * 按条件查询单条账号收货地址
     *
     * @param params 账号收货地址Params
     * @return 账号收货地址
     */
    AccountAddress selectAccountAddressOne(AccountAddressParams params);
}
