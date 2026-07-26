package com.demo.account.service;

import com.demo.account.apiParams.AccountBalanceRecordApiParams;
import com.demo.account.apiParams.AccountBalanceRechargeApiParams;
import com.demo.account.apiResult.AccountBalanceRecordApiResult;
import com.demo.account.domain.AccountBalanceRecord;
import com.demo.account.params.AccountBalanceRecordParams;
import com.demo.account.result.AccountBalanceRecordResult;
import com.demo.common.enums.AccountBalanceRecordType;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账号余额流水Service接口
 *
 * @author demo
 * @date 2026-06-28
 */
public interface IAccountBalanceRecordService {

    /**
     * 查询账号余额流水
     *
     * @param balanceRecordId 流水主键
     * @return 账号余额流水
     */
    AccountBalanceRecord selectAccountBalanceRecordById(Long balanceRecordId);

    /**
     * 查询账号余额流水Result列表
     *
     * @param params 账号余额流水Params
     * @return 账号余额流水Result集合
     */
    List<AccountBalanceRecordResult> selectAccountBalanceRecordResultList(AccountBalanceRecordParams params);

    /**
     * 获取账号余额流水详细信息
     *
     * @param balanceRecordId 流水主键
     * @return 账号余额流水Result
     */
    AccountBalanceRecordResult selectAccountBalanceRecordResult(Long balanceRecordId);

    /**
     * 查询账号余额流水ApiResult列表（客户端）
     *
     * @param params 账号余额流水ApiParams
     * @return 账号余额流水ApiResult集合
     */
    List<AccountBalanceRecordApiResult> selectAccountBalanceRecordApiResultList(AccountBalanceRecordApiParams params);

    /**
     * 获取账号余额流水ApiResult详细信息（客户端）
     *
     * @param balanceRecordId 流水主键
     * @param accountId         账号ID（归属校验）
     * @return 账号余额流水ApiResult
     */
    AccountBalanceRecordApiResult selectAccountBalanceRecordApiResult(Long balanceRecordId, Long accountId);

    /**
     * 变更账户余额并写入流水
     *
     * @param accountId   账号ID
     * @param signedDelta 带符号变动额（正收入负支出）
     * @param recordType  流水类型
     * @param bizId       关联业务ID
     * @param bizNo       业务单号
     * @param remark      备注
     * @return 流水记录
     */
    AccountBalanceRecord changeBalance(Long accountId,
                                       BigDecimal signedDelta,
                                       AccountBalanceRecordType recordType,
                                       Long bizId,
                                       String bizNo,
                                       String remark);

    /**
     * 客户端模拟充值
     *
     * @param accountId 账号ID
     * @param params    充值参数
     * @return 充值流水
     */
    AccountBalanceRecordApiResult recharge(Long accountId, AccountBalanceRechargeApiParams params);
}
