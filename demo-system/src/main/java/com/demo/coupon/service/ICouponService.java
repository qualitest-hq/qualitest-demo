package com.demo.coupon.service;

import java.util.List;
import com.demo.coupon.domain.Coupon;
import com.demo.coupon.params.CouponParams;
import com.demo.coupon.apiParams.CouponApiParams;
import com.demo.coupon.result.CouponResult;
import com.demo.coupon.apiResult.AccountCouponApiResult;
import com.demo.coupon.apiResult.CouponApiResult;

/**
 * 优惠券Service接口
 * 
 * @author demo
 * @date 2026-06-20
 */
public interface ICouponService {
    /**
     * 查询优惠券列表
     *
     * @param coupon 优惠券
     * @return 优惠券集合
     */
    List<Coupon> selectCouponList(Coupon coupon);

    /**
     * 查询优惠券
     *
     * @param couponId 优惠券主键
     * @return 优惠券
     */
    Coupon selectCouponById(Long couponId);

    /**
     * 查询优惠券Result列表
     *
     * @param params 优惠券Params
     * @return 优惠券Result集合
     */
    List<CouponResult> selectCouponResultList(CouponParams params);

    /**
     * 获取优惠券详细信息
     *
     * @param couponId 优惠券主键
     * @return 优惠券Result
     */
    CouponResult selectCouponResult(Long couponId);

    /**
     * 查询优惠券ApiResult列表（客户端）
     *
     * @param params 优惠券ApiParams
     * @return 优惠券ApiResult集合
     */
    List<CouponApiResult> selectCouponApiResultList(CouponApiParams params);

    /**
     * 获取优惠券ApiResult详细信息（客户端）
     *
     * @param couponId 优惠券主键
     * @return 优惠券ApiResult
     */
    CouponApiResult selectCouponApiResult(Long couponId);

    /**
     * 新增优惠券
     * 
     * @param coupon 优惠券
     * @return 结果
     */
    int insertCoupon(Coupon coupon);

    /**
     * 修改优惠券
     * 
     * @param coupon 优惠券
     * @return 结果
     */
    int updateCoupon(Coupon coupon);

    /**
     * 批量删除优惠券
     * 
     * @param couponIdList 需要删除的优惠券主键集合
     * @return 结果
     */
    int deleteCouponByIdList(List<Long> couponIdList);

    /**
     * 删除优惠券信息
     * 
     * @param couponId 优惠券主键
     * @return 结果
     */
    public int deleteCouponById(Long couponId);

    /**
     * 修改优惠券为逻辑删除
     *
     * @param couponId 优惠券ID
     * @return 结果
     */
    int logicDeleteCouponById(Long couponId);

    /**
     * 批量修改优惠券为逻辑删除
     *
     * @param couponIdList 优惠券ID集合
     * @return 结果
     */
    int logicDeleteCouponByIdList(List<Long> couponIdList);

    /**
     * 查询优惠券数量
     *
     * @param params 优惠券Params
     * @return 数量
     */
    int selectCouponCount(CouponParams params);

    /**
     * 按条件查询单条优惠券
     *
     * @param params 优惠券Params
     * @return 优惠券
     */
    Coupon selectCouponOne(CouponParams params);

    /**
     * 领取优惠券
     */
    AccountCouponApiResult receiveCoupon(Long accountId, Long couponId);
}
