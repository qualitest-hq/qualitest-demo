package com.demo.account.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.account.apiParams.AccountBalanceRecordApiParams;
import com.demo.account.apiParams.AccountBalanceRechargeApiParams;
import com.demo.account.apiResult.AccountBalanceRecordApiResult;
import com.demo.account.domain.Account;
import com.demo.account.domain.AccountBalanceRecord;
import com.demo.account.mapper.AccountBalanceRecordMapper;
import com.demo.account.mapper.AccountMapper;
import com.demo.account.params.AccountBalanceRecordParams;
import com.demo.account.result.AccountBalanceRecordResult;
import com.demo.account.service.IAccountBalanceRecordService;
import com.demo.common.enums.AccountBalanceChangeType;
import com.demo.common.enums.AccountBalanceRecordType;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 账号余额流水Service业务层处理
 *
 * @author demo
 * @date 2026-06-28
 */
@Service
public class AccountBalanceRecordServiceImpl implements IAccountBalanceRecordService {

    private static final int MONEY_SCALE = 2;

    private static final BigDecimal RECHARGE_MAX = new BigDecimal("10000");

    @Autowired
    private AccountBalanceRecordMapper accountBalanceRecordMapper;

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 查询账号余额流水
     */
    @Override
    public AccountBalanceRecord selectAccountBalanceRecordById(Long balanceRecordId) {
        return accountBalanceRecordMapper.selectAccountBalanceRecordById(balanceRecordId);
    }

    /**
     * 查询账号余额流水Result列表
     */
    @Override
    public List<AccountBalanceRecordResult> selectAccountBalanceRecordResultList(AccountBalanceRecordParams params) {
        return accountBalanceRecordMapper.selectAccountBalanceRecordResultList(params);
    }

    /**
     * 获取账号余额流水详细信息
     */
    @Override
    public AccountBalanceRecordResult selectAccountBalanceRecordResult(Long balanceRecordId) {
        return accountBalanceRecordMapper.selectAccountBalanceRecordResult(balanceRecordId);
    }

    /**
     * 查询账号余额流水ApiResult列表（客户端）
     */
    @Override
    public List<AccountBalanceRecordApiResult> selectAccountBalanceRecordApiResultList(AccountBalanceRecordApiParams params) {
        return accountBalanceRecordMapper.selectAccountBalanceRecordApiResultList(params);
    }

    /**
     * 获取账号余额流水ApiResult详细信息（客户端）
     */
    @Override
    public AccountBalanceRecordApiResult selectAccountBalanceRecordApiResult(Long balanceRecordId, Long accountId) {
        AccountBalanceRecordApiResult result =
                accountBalanceRecordMapper.selectAccountBalanceRecordApiResult(balanceRecordId, accountId);
        if (result == null) {
            throw new ServiceException("流水不存在或无权访问");
        }
        return result;
    }

    /**
     * 变更账户余额并写入流水
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AccountBalanceRecord changeBalance(Long accountId,
                                              BigDecimal signedDelta,
                                              AccountBalanceRecordType recordType,
                                              Long bizId,
                                              String bizNo,
                                              String remark) {
        if (accountId == null) {
            throw new ServiceException("账号ID不能为空");
        }
        if (signedDelta == null || signedDelta.compareTo(BigDecimal.ZERO) == 0) {
            throw new ServiceException("变动金额不能为0");
        }
        if (recordType == null) {
            throw new ServiceException("流水类型不能为空");
        }

        BigDecimal delta = signedDelta.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
        Account account = accountMapper.selectAccountByIdForUpdate(accountId);
        if (account == null || (account.getDelFlag() != null && account.getDelFlag() == 1)) {
            throw new ServiceException("账号不存在");
        }

        BigDecimal balanceBefore = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;
        BigDecimal balanceAfter = balanceBefore.add(delta).setScale(MONEY_SCALE, RoundingMode.HALF_UP);
        if (balanceAfter.compareTo(BigDecimal.ZERO) < 0) {
            throw new ServiceException("余额不足");
        }

        int changeType = delta.compareTo(BigDecimal.ZERO) > 0
                ? AccountBalanceChangeType.INCOME.getCode()
                : AccountBalanceChangeType.EXPENSE.getCode();

        AccountBalanceRecord record = AccountBalanceRecord.builder()
                .balanceRecordId(IdUtil.getSnowflakeNextId())
                .accountId(accountId)
                .recordType(recordType.getCode())
                .changeType(changeType)
                .changeAmount(delta.abs())
                .balanceBefore(balanceBefore)
                .balanceAfter(balanceAfter)
                .bizId(bizId != null ? bizId : 0L)
                .bizNo(bizNo != null ? bizNo : "")
                .delFlag(0)
                .build();
        record.setRemark(remark);
        record.setCreateTime(DateUtils.getNowDate());
        accountBalanceRecordMapper.insertAccountBalanceRecord(record);

        int rows = accountMapper.addAccountBalance(accountId, delta);
        if (rows == 0) {
            throw new ServiceException("余额更新失败");
        }
        return record;
    }

    /**
     * 客户端模拟充值
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AccountBalanceRecordApiResult recharge(Long accountId, AccountBalanceRechargeApiParams params) {
        if (params == null || params.getAmount() == null) {
            throw new ServiceException("充值金额不能为空");
        }
        BigDecimal amount = params.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("充值金额须大于0");
        }
        if (amount.compareTo(RECHARGE_MAX) > 0) {
            throw new ServiceException("单次充值金额不能超过10000");
        }
        String bizNo = "RC" + IdUtil.getSnowflakeNextIdStr();
        String remark = StrUtil.isNotBlank(params.getRemark()) ? params.getRemark().trim() : "模拟充值";
        AccountBalanceRecord record = changeBalance(
                accountId,
                amount,
                AccountBalanceRecordType.RECHARGE,
                0L,
                bizNo,
                remark);
        return accountBalanceRecordMapper.selectAccountBalanceRecordApiResult(record.getBalanceRecordId(), accountId);
    }
}
