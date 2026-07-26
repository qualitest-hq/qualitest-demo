package com.demo.coupon.service;

import java.util.List;
import com.demo.coupon.domain.AccountCoupon;
import com.demo.coupon.params.AccountCouponParams;
import com.demo.coupon.apiParams.AccountCouponApiParams;
import com.demo.coupon.result.AccountCouponResult;
import com.demo.coupon.apiResult.AccountCouponApiResult;

/**
 * 账号优惠券Service接口
 * 
 * @author demo
 * @date 2026-06-20
 */
public interface IAccountCouponService {
    /**
     * 查询账号优惠券列表
     *
     * @param accountCoupon 账号优惠券
     * @return 账号优惠券集合
     */
    List<AccountCoupon> selectAccountCouponList(AccountCoupon accountCoupon);

    /**
     * 查询账号优惠券
     *
     * @param accountCouponId 账号优惠券主键
     * @return 账号优惠券
     */
    AccountCoupon selectAccountCouponById(Long accountCouponId);

    /**
     * 查询账号优惠券Result列表
     *
     * @param params 账号优惠券Params
     * @return 账号优惠券Result集合
     */
    List<AccountCouponResult> selectAccountCouponResultList(AccountCouponParams params);

    /**
     * 获取账号优惠券详细信息
     *
     * @param accountCouponId 账号优惠券主键
     * @return 账号优惠券Result
     */
    AccountCouponResult selectAccountCouponResult(Long accountCouponId);

    /**
     * 查询账号优惠券ApiResult列表（客户端）
     *
     * @param params 账号优惠券ApiParams
     * @return 账号优惠券ApiResult集合
     */
    List<AccountCouponApiResult> selectAccountCouponApiResultList(AccountCouponApiParams params);

    /**
     * 获取账号优惠券ApiResult详细信息（客户端）
     *
     * @param accountCouponId 账号优惠券主键
     * @return 账号优惠券ApiResult
     */
    AccountCouponApiResult selectAccountCouponApiResult(Long accountCouponId);

    /**
     * 新增账号优惠券
     * 
     * @param accountCoupon 账号优惠券
     * @return 结果
     */
    int insertAccountCoupon(AccountCoupon accountCoupon);

    /**
     * 修改账号优惠券
     * 
     * @param accountCoupon 账号优惠券
     * @return 结果
     */
    int updateAccountCoupon(AccountCoupon accountCoupon);

    /**
     * 批量删除账号优惠券
     * 
     * @param accountCouponIdList 需要删除的账号优惠券主键集合
     * @return 结果
     */
    int deleteAccountCouponByIdList(List<Long> accountCouponIdList);

    /**
     * 删除账号优惠券信息
     * 
     * @param accountCouponId 账号优惠券主键
     * @return 结果
     */
    public int deleteAccountCouponById(Long accountCouponId);

    /**
     * 修改账号优惠券为逻辑删除
     *
     * @param accountCouponId 账号优惠券ID
     * @return 结果
     */
    int logicDeleteAccountCouponById(Long accountCouponId);

    /**
     * 批量修改账号优惠券为逻辑删除
     *
     * @param accountCouponIdList 账号优惠券ID集合
     * @return 结果
     */
    int logicDeleteAccountCouponByIdList(List<Long> accountCouponIdList);

    /**
     * 查询账号优惠券数量
     *
     * @param params 账号优惠券Params
     * @return 数量
     */
    int selectAccountCouponCount(AccountCouponParams params);

    /**
     * 按条件查询单条账号优惠券
     *
     * @param params 账号优惠券Params
     * @return 账号优惠券
     */
    AccountCoupon selectAccountCouponOne(AccountCouponParams params);
}
