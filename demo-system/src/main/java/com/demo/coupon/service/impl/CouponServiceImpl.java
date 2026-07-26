package com.demo.coupon.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.utils.SecurityUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.coupon.mapper.CouponMapper;
import com.demo.coupon.domain.Coupon;
import com.demo.coupon.domain.AccountCoupon;
import com.demo.coupon.params.CouponParams;
import com.demo.coupon.params.AccountCouponParams;
import com.demo.coupon.apiParams.CouponApiParams;
import com.demo.coupon.result.CouponResult;
import com.demo.coupon.apiResult.CouponApiResult;
import com.demo.coupon.apiResult.AccountCouponApiResult;
import com.demo.coupon.mapper.AccountCouponMapper;
import com.demo.coupon.service.ICouponService;
import com.demo.common.exception.ServiceException;
import java.util.Date;
import org.springframework.transaction.annotation.Transactional;

/**
 * 优惠券Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class CouponServiceImpl implements ICouponService {
    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private AccountCouponMapper accountCouponMapper;

    /**
     * 查询优惠券列表
     *
     * @param coupon 优惠券
     * @return 优惠券
     */
    @Override
    public List<Coupon> selectCouponList(Coupon coupon) {
        return couponMapper.selectCouponList(coupon);
    }

    /**
     * 查询优惠券
     *
     * @param couponId 优惠券主键
     * @return 优惠券
     */
    @Override
    public Coupon selectCouponById(Long couponId) {
        return couponMapper.selectCouponById(couponId);
    }

    /**
     * 查询优惠券Result列表
     *
     * @param params 优惠券Params
     * @return 优惠券Result集合
     */
    @Override
    public List<CouponResult> selectCouponResultList(CouponParams params) {
        return couponMapper.selectCouponResultList(params);
    }

    /**
     * 获取优惠券详细信息
     *
     * @param couponId 优惠券主键
     * @return 优惠券Result
     */
    @Override
    public CouponResult selectCouponResult(Long couponId) {
        return couponMapper.selectCouponResult(couponId);
    }

    /**
     * 查询优惠券ApiResult列表（客户端）
     */
    @Override
    public List<CouponApiResult> selectCouponApiResultList(CouponApiParams params) {
        return couponMapper.selectCouponApiResultList(params);
    }

    /**
     * 获取优惠券ApiResult详细信息（客户端）
     */
    @Override
    public CouponApiResult selectCouponApiResult(Long couponId) {
        return couponMapper.selectCouponApiResult(couponId);
    }

    /**
     * 新增优惠券
     *
     * @param coupon 优惠券
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertCoupon(Coupon coupon) {
        if (Objects.isNull(coupon.getCouponId())) {
            coupon.setCouponId(IdUtil.getSnowflakeNextId());
        }
        coupon.setCreateTime(DateUtils.getNowDate());
        return couponMapper.insertCoupon(coupon);
    }

    /**
     * 修改优惠券
     *
     * @param coupon 优惠券
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateCoupon(Coupon coupon) {
        coupon.setUpdateTime(DateUtils.getNowDate());
        return couponMapper.updateCoupon(coupon);
    }

    /**
     * 批量删除优惠券
     * 
     * @param couponIdList 需要删除的优惠券主键集合
     * @return 结果
     */
    @Override
    public int deleteCouponByIdList(List<Long> couponIdList) {
        return couponMapper.deleteCouponByIdList(couponIdList);
    }

    /**
     * 删除优惠券信息
     * 
     * @param couponId 优惠券主键
     * @return 结果
     */
    @Override
    public int deleteCouponById(Long couponId) {
        return couponMapper.deleteCouponById(couponId);
    }

    /**
     * 逻辑删除优惠券信息
     * 
     * @param couponId 优惠券主键
     * @return 结果
     */
    @Override
    public int logicDeleteCouponById(Long couponId) {
        return couponMapper.logicDeleteCouponById(couponId);
    }

    /**
     * 批量逻辑删除优惠券信息
     * 
     * @param couponIdList 优惠券主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteCouponByIdList(List<Long> couponIdList) {
        return couponMapper.logicDeleteCouponByIdList(couponIdList);
    }

    /**
     * 查询优惠券数量
     *
     * @param params 优惠券Params
     * @return 数量
     */
    @Override
    public int selectCouponCount(CouponParams params) {
        return couponMapper.selectCouponCount(params);
    }

    /**
     * 按条件查询单条优惠券
     *
     * @param params 优惠券Params
     * @return 优惠券
     */
    @Override
    public Coupon selectCouponOne(CouponParams params) {
        return couponMapper.selectCouponOne(params);
    }

    /**
     * 客户端领取优惠券；校验库存、有效期与重复领取
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AccountCouponApiResult receiveCoupon(Long accountId, Long couponId) {
        if (couponId == null) {
            throw new ServiceException("优惠券ID不能为空");
        }
        Coupon coupon = couponMapper.selectCouponById(couponId);
        if (coupon == null || (coupon.getDelFlag() != null && coupon.getDelFlag() == 1)) {
            throw new ServiceException("优惠券不存在");
        }
        if (coupon.getStatus() != null && coupon.getStatus() != 0) {
            throw new ServiceException("优惠券已停用");
        }
        Date now = DateUtils.getNowDate();
        if (coupon.getValidStartTime() != null && now.before(coupon.getValidStartTime())) {
            throw new ServiceException("优惠券未开始");
        }
        if (coupon.getValidEndTime() != null && now.after(coupon.getValidEndTime())) {
            throw new ServiceException("优惠券已过期");
        }
        int totalCount = coupon.getTotalCount() != null ? coupon.getTotalCount() : 0;
        int receiveCount = coupon.getReceiveCount() != null ? coupon.getReceiveCount() : 0;
        if (totalCount > 0 && receiveCount >= totalCount) {
            throw new ServiceException("优惠券已领完");
        }
        AccountCouponParams query = AccountCouponParams.builder()
                .accountId(accountId)
                .couponId(couponId)
                .build();
        AccountCoupon existing = accountCouponMapper.selectAccountCouponOne(query);
        if (existing != null && (existing.getDelFlag() == null || existing.getDelFlag() == 0)) {
            throw new ServiceException("不可重复领取");
        }
        AccountCoupon accountCoupon = new AccountCoupon();
        accountCoupon.setAccountCouponId(IdUtil.getSnowflakeNextId());
        accountCoupon.setCouponId(couponId);
        accountCoupon.setAccountId(accountId);
        accountCoupon.setCouponName(coupon.getCouponName());
        accountCoupon.setThresholdAmount(coupon.getThresholdAmount());
        accountCoupon.setDiscountAmount(coupon.getDiscountAmount());
        accountCoupon.setValidStartTime(coupon.getValidStartTime());
        accountCoupon.setValidEndTime(coupon.getValidEndTime());
        accountCoupon.setCouponStatus(0);
        accountCoupon.setReceiveTime(now);
        accountCoupon.setOrderId(0L);
        accountCoupon.setDelFlag(0);
        accountCoupon.setCreateTime(now);
        accountCoupon.setUpdateTime(now);
        accountCouponMapper.insertAccountCoupon(accountCoupon);
        couponMapper.incrementReceiveCount(couponId);
        return accountCouponMapper.selectAccountCouponApiResult(accountCoupon.getAccountCouponId());
    }
}
