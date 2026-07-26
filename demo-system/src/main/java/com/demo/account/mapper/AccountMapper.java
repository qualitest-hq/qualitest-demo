package com.demo.account.mapper;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.account.domain.Account;
import com.demo.account.params.AccountParams;
import com.demo.account.apiParams.AccountApiParams;
import com.demo.account.result.AccountResult;
import com.demo.account.apiResult.AccountApiResult;

/**
 * 用户账号Mapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface AccountMapper {
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
     * 查询用户账号并加行锁（余额变更）
     */
    Account selectAccountByIdForUpdate(Long accountId);

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
     * 按手机号查询用户账号（精确匹配，未删除）
     */
    Account selectAccountByMobile(@Param("mobile") String mobile);

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
     * 删除用户账号
     * 
     * @param accountId 用户账号主键
     * @return 结果
     */
    int deleteAccountById(Long accountId);

    /**
     * 批量删除用户账号
     * 
     * @param accountIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAccountByIdList(@Param("list") List<Long> accountIdList);

    /**
     * 逻辑删除用户账号
     * 
     * @param accountId 用户账号主键
     * @return 结果
     */
    int logicDeleteAccountById(Long accountId);

    /**
     * 批量逻辑删除用户账号
     * 
     * @param accountIdList 用户账号主键集合
     * @return 结果
     */
    int logicDeleteAccountByIdList(@Param("list") List<Long> accountIdList);

    /**
     * 增加账户余额
     */
    int addAccountBalance(@Param("accountId") Long accountId, @Param("amount") BigDecimal amount);
}
