package com.demo.coupon.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.demo.coupon.domain.AccountCoupon;
import com.demo.coupon.params.AccountCouponParams;
import com.demo.coupon.apiParams.AccountCouponApiParams;
import com.demo.coupon.result.AccountCouponResult;
import com.demo.coupon.apiResult.AccountCouponApiResult;

/**
 * 账号优惠券Mapper接口
 * 
 * @author demo
 * @date 2026-06-20
 */
@Mapper
public interface AccountCouponMapper {
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
     * 删除账号优惠券
     * 
     * @param accountCouponId 账号优惠券主键
     * @return 结果
     */
    int deleteAccountCouponById(Long accountCouponId);

    /**
     * 批量删除账号优惠券
     * 
     * @param accountCouponIdList 需要删除的数据主键集合
     * @return 结果
     */
    int deleteAccountCouponByIdList(@Param("list") List<Long> accountCouponIdList);

    /**
     * 逻辑删除账号优惠券
     * 
     * @param accountCouponId 账号优惠券主键
     * @return 结果
     */
    int logicDeleteAccountCouponById(Long accountCouponId);

    /**
     * 批量逻辑删除账号优惠券
     * 
     * @param accountCouponIdList 账号优惠券主键集合
     * @return 结果
     */
    int logicDeleteAccountCouponByIdList(@Param("list") List<Long> accountCouponIdList);
}
