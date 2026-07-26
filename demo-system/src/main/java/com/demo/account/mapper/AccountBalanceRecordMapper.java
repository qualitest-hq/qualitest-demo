package com.demo.account.mapper;

import com.demo.account.apiParams.AccountBalanceRecordApiParams;
import com.demo.account.apiResult.AccountBalanceRecordApiResult;
import com.demo.account.domain.AccountBalanceRecord;
import com.demo.account.params.AccountBalanceRecordParams;
import com.demo.account.result.AccountBalanceRecordResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账号余额流水Mapper接口
 *
 * @author demo
 * @date 2026-06-28
 */
@Mapper
public interface AccountBalanceRecordMapper {

    /**
     * 查询账号余额流水
     *
     * @param balanceRecordId 流水主键
     * @return 账号余额流水
     */
    AccountBalanceRecord selectAccountBalanceRecordById(Long balanceRecordId);

    /**
     * 获取账号余额流水详细信息
     *
     * @param balanceRecordId 流水主键
     * @return 账号余额流水Result
     */
    AccountBalanceRecordResult selectAccountBalanceRecordResult(Long balanceRecordId);

    /**
     * 获取账号余额流水ApiResult详细信息（客户端）
     */
    AccountBalanceRecordApiResult selectAccountBalanceRecordApiResult(@Param("balanceRecordId") Long balanceRecordId,
                                                                      @Param("accountId") Long accountId);

    /**
     * 查询账号余额流水Result列表
     *
     * @param params 账号余额流水Params
     * @return 账号余额流水Result集合
     */
    List<AccountBalanceRecordResult> selectAccountBalanceRecordResultList(AccountBalanceRecordParams params);

    /**
     * 查询账号余额流水ApiResult列表（客户端）
     */
    List<AccountBalanceRecordApiResult> selectAccountBalanceRecordApiResultList(AccountBalanceRecordApiParams params);

    /**
     * 新增账号余额流水
     *
     * @param record 账号余额流水
     * @return 结果
     */
    int insertAccountBalanceRecord(AccountBalanceRecord record);
}
