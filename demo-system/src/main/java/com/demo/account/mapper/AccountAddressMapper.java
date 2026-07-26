package com.demo.account.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.account.domain.AccountAddress;
import com.demo.account.params.AccountAddressParams;
import com.demo.account.apiParams.AccountAddressApiParams;
import com.demo.account.result.AccountAddressResult;
import com.demo.account.apiResult.AccountAddressApiResult;

/**
 * 账号收货地址Mapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface AccountAddressMapper {
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
     * @return 账号收货地址ApiResult
     */
    AccountAddressApiResult selectAccountAddressApiResult(Long addressId);

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
     * 删除账号收货地址
     * 
     * @param addressId 账号收货地址主键
     * @return 结果
     */
    int deleteAccountAddressById(Long addressId);

    /**
     * 批量删除账号收货地址
     * 
     * @param addressIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAccountAddressByIdList(@Param("list") List<Long> addressIdList);

    /**
     * 逻辑删除账号收货地址
     * 
     * @param addressId 账号收货地址主键
     * @return 结果
     */
    int logicDeleteAccountAddressById(Long addressId);

    /**
     * 批量逻辑删除账号收货地址
     * 
     * @param addressIdList 账号收货地址主键集合
     * @return 结果
     */
    int logicDeleteAccountAddressByIdList(@Param("list") List<Long> addressIdList);

    /**
     * 清除账号下默认地址标记
     */
    int clearDefaultByAccountId(@Param("accountId") Long accountId, @Param("excludeAddressId") Long excludeAddressId);
}
