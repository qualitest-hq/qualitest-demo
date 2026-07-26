package com.demo.account.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.demo.account.domain.Account;
import com.demo.account.params.AccountParams;
import com.demo.account.apiParams.AccountApiParams;
import com.demo.account.result.AccountResult;
import com.demo.account.apiResult.AccountApiResult;

/**
 * 用户账号Service接口
 * 
 * @author demo
 * @date 2026-06-20
 */
public interface IAccountService {
    /**
     * 查询用户账号列表
     *
     * @param account 用户账号
     * @return 用户账号集合
     */
    List<Account> selectAccountList(Account account);

    /**
     * 查询用户账号
     *
     * @param accountId 用户账号主键
     * @return 用户账号
     */
    Account selectAccountById(Long accountId);

    /**
     * 查询用户账号Result列表
     *
     * @param params 用户账号Params
     * @return 用户账号Result集合
     */
    List<AccountResult> selectAccountResultList(AccountParams params);

    /**
     * 获取用户账号详细信息
     *
     * @param accountId 用户账号主键
     * @return 用户账号Result
     */
    AccountResult selectAccountResult(Long accountId);

    /**
     * 查询用户账号ApiResult列表（客户端）
     *
     * @param params 用户账号ApiParams
     * @return 用户账号ApiResult集合
     */
    List<AccountApiResult> selectAccountApiResultList(AccountApiParams params);

    /**
     * 获取用户账号ApiResult详细信息（客户端）
     *
     * @param accountId 用户账号主键
     * @return 用户账号ApiResult
     */
    AccountApiResult selectAccountApiResult(Long accountId);

    /**
     * 新增用户账号
     * 
     * @param account 用户账号
     * @return 结果
     */
    int insertAccount(Account account);

    /**
     * 修改用户账号
     * 
     * @param account 用户账号
     * @return 结果
     */
    int updateAccount(Account account);

    /**
     * 批量删除用户账号
     * 
     * @param accountIdList 需要删除的用户账号主键集合
     * @return 结果
     */
    int deleteAccountByIdList(List<Long> accountIdList);

    /**
     * 删除用户账号信息
     * 
     * @param accountId 用户账号主键
     * @return 结果
     */
    public int deleteAccountById(Long accountId);

    /**
     * 修改用户账号为逻辑删除
     *
     * @param accountId 用户账号ID
     * @return 结果
     */
    int logicDeleteAccountById(Long accountId);

    /**
     * 批量修改用户账号为逻辑删除
     *
     * @param accountIdList 用户账号ID集合
     * @return 结果
     */
    int logicDeleteAccountByIdList(List<Long> accountIdList);

    /**
     * 查询用户账号数量
     *
     * @param params 用户账号Params
     * @return 数量
     */
    int selectAccountCount(AccountParams params);

    /**
     * 按条件查询单条用户账号
     *
     * @param params 用户账号Params
     * @return 用户账号
     */
    Account selectAccountOne(AccountParams params);

    /**
     * 赠送账户余额
     *
     * @param accountId 账号ID
     * @param amount    赠送金额
     * @param remark    备注（可选，写入账号 remark）
     */
    void giftBalance(Long accountId, BigDecimal amount, String remark);

    /**
     * 按手机号查询用户账号
     */
    Account selectAccountByMobile(String mobile);

    /**
     * 手机号是否已注册
     */
    boolean existsByMobile(String mobile);

    /**
     * 更新登录信息
     */
    void updateLoginInfo(Long accountId, String ip, Date loginTime);

    /**
     * 更新个人资料
     */
    void updateProfile(Long accountId, String nickName, Integer gender);

    /**
     * 修改密码
     */
    void updatePassword(Long accountId, String oldPassword, String newPassword);
}
