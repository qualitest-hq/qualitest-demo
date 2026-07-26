package com.demo.account.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.SecurityUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.account.mapper.AccountMapper;
import com.demo.account.domain.Account;
import com.demo.account.params.AccountParams;
import com.demo.account.apiParams.AccountApiParams;
import com.demo.account.result.AccountResult;
import com.demo.account.apiResult.AccountApiResult;
import com.demo.account.service.IAccountService;
import com.demo.account.service.IAccountBalanceRecordService;
import com.demo.common.enums.AccountBalanceRecordType;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户账号Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private IAccountBalanceRecordService accountBalanceRecordService;

    /**
     * 查询用户账号列表
     *
     * @param account 用户账号
     * @return 用户账号
     */
    @Override
    public List<Account> selectAccountList(Account account) {
        return accountMapper.selectAccountList(account);
    }

    /**
     * 查询用户账号
     *
     * @param accountId 用户账号主键
     * @return 用户账号
     */
    @Override
    public Account selectAccountById(Long accountId) {
        return accountMapper.selectAccountById(accountId);
    }

    /**
     * 查询用户账号Result列表
     *
     * @param params 用户账号Params
     * @return 用户账号Result集合
     */
    @Override
    public List<AccountResult> selectAccountResultList(AccountParams params) {
        return accountMapper.selectAccountResultList(params);
    }

    /**
     * 获取用户账号详细信息
     *
     * @param accountId 用户账号主键
     * @return 用户账号Result
     */
    @Override
    public AccountResult selectAccountResult(Long accountId) {
        return accountMapper.selectAccountResult(accountId);
    }

    /**
     * 查询用户账号ApiResult列表（客户端）
     */
    @Override
    public List<AccountApiResult> selectAccountApiResultList(AccountApiParams params) {
        return accountMapper.selectAccountApiResultList(params);
    }

    /**
     * 获取用户账号ApiResult详细信息（客户端）
     */
    @Override
    public AccountApiResult selectAccountApiResult(Long accountId) {
        return accountMapper.selectAccountApiResult(accountId);
    }

    /**
     * 新增用户账号
     *
     * @param account 用户账号
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAccount(Account account) {
        if (Objects.isNull(account.getAccountId())) {
            account.setAccountId(IdUtil.getSnowflakeNextId());
        }
        account.setCreateTime(DateUtils.getNowDate());
        return accountMapper.insertAccount(account);
    }

    /**
     * 修改用户账号
     *
     * @param account 用户账号
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateAccount(Account account) {
        account.setUpdateTime(DateUtils.getNowDate());
        return accountMapper.updateAccount(account);
    }

    /**
     * 批量删除用户账号
     * 
     * @param accountIdList 需要删除的用户账号主键集合
     * @return 结果
     */
    @Override
    public int deleteAccountByIdList(List<Long> accountIdList) {
        return accountMapper.deleteAccountByIdList(accountIdList);
    }

    /**
     * 删除用户账号信息
     * 
     * @param accountId 用户账号主键
     * @return 结果
     */
    @Override
    public int deleteAccountById(Long accountId) {
        return accountMapper.deleteAccountById(accountId);
    }

    /**
     * 逻辑删除用户账号信息
     * 
     * @param accountId 用户账号主键
     * @return 结果
     */
    @Override
    public int logicDeleteAccountById(Long accountId) {
        return accountMapper.logicDeleteAccountById(accountId);
    }

    /**
     * 批量逻辑删除用户账号信息
     * 
     * @param accountIdList 用户账号主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteAccountByIdList(List<Long> accountIdList) {
        return accountMapper.logicDeleteAccountByIdList(accountIdList);
    }

    /**
     * 查询用户账号数量
     *
     * @param params 用户账号Params
     * @return 数量
     */
    @Override
    public int selectAccountCount(AccountParams params) {
        return accountMapper.selectAccountCount(params);
    }

    /**
     * 按条件查询单条用户账号
     *
     * @param params 用户账号Params
     * @return 用户账号
     */
    @Override
    public Account selectAccountOne(AccountParams params) {
        return accountMapper.selectAccountOne(params);
    }

    /**
     * 管理端赠送账户余额，累加余额并追加备注
     */
    /**
     * 管理端赠送账户余额，累加余额并追加备注
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void giftBalance(Long accountId, BigDecimal amount, String remark) {
        if (accountId == null) {
            throw new ServiceException("账号ID不能为空");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ServiceException("赠送金额须大于0");
        }
        Account account = accountMapper.selectAccountById(accountId);
        if (account == null || (account.getDelFlag() != null && account.getDelFlag() == 1)) {
            throw new ServiceException("账号不存在");
        }
        String note = "赠送余额+" + amount.stripTrailingZeros().toPlainString();
        if (StrUtil.isNotBlank(remark)) {
            note = note + "：" + remark;
        }
        accountBalanceRecordService.changeBalance(
                accountId,
                amount,
                AccountBalanceRecordType.GIFT,
                0L,
                "",
                note);
        Account update = new Account();
        update.setAccountId(accountId);
        update.setRemark(StrUtil.isBlank(account.getRemark()) ? note : account.getRemark() + " | " + note);
        update.setUpdateTime(DateUtils.getNowDate());
        accountMapper.updateAccount(update);
    }

    /**
     * 按手机号查询账号，用于登录校验
     */
    /**
     * 按手机号查询账号，用于登录校验
     */
    @Override
    public Account selectAccountByMobile(String mobile) {
        return accountMapper.selectAccountByMobile(mobile);
    }

    /**
     * 判断手机号是否已注册
     */
    /**
     * 判断手机号是否已注册
     */
    @Override
    public boolean existsByMobile(String mobile) {
        return accountMapper.selectAccountByMobile(mobile) != null;
    }

    /**
     * 登录成功后更新最后登录 IP 与时间
     */
    /**
     * 登录成功后更新最后登录 IP 与时间
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateLoginInfo(Long accountId, String ip, Date loginTime) {
        Account update = new Account();
        update.setAccountId(accountId);
        update.setLastLoginIp(ip);
        update.setLastLoginTime(loginTime);
        update.setUpdateTime(DateUtils.getNowDate());
        accountMapper.updateAccount(update);
    }

    /**
     * 更新昵称与性别
     */
    /**
     * 更新昵称与性别
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateProfile(Long accountId, String nickName, Integer gender) {
        Account account = accountMapper.selectAccountById(accountId);
        if (account == null || (account.getDelFlag() != null && account.getDelFlag() == 1)) {
            throw new ServiceException("账号不存在");
        }
        Account update = new Account();
        update.setAccountId(accountId);
        update.setNickName(nickName);
        if (gender != null) {
            update.setGender(gender);
        }
        update.setUpdateTime(DateUtils.getNowDate());
        accountMapper.updateAccount(update);
    }

    /**
     * 修改密码；校验旧密码后加密写入新密码
     */
    /**
     * 修改密码；校验旧密码后加密写入新密码
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePassword(Long accountId, String oldPassword, String newPassword) {
        Account account = accountMapper.selectAccountById(accountId);
        if (account == null || (account.getDelFlag() != null && account.getDelFlag() == 1)) {
            throw new ServiceException("账号不存在");
        }
        if (!SecurityUtils.matchesPassword(oldPassword, account.getPassword())) {
            throw new ServiceException("旧密码错误");
        }
        Account update = new Account();
        update.setAccountId(accountId);
        update.setPassword(SecurityUtils.encryptPassword(newPassword));
        update.setUpdateTime(DateUtils.getNowDate());
        accountMapper.updateAccount(update);
    }
}
