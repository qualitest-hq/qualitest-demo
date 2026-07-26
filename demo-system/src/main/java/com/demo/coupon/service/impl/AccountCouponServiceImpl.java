package com.demo.coupon.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.demo.common.utils.SecurityUtils;
import java.util.List;
import java.util.Objects;
import com.demo.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.coupon.mapper.AccountCouponMapper;
import com.demo.coupon.domain.AccountCoupon;
import com.demo.coupon.params.AccountCouponParams;
import com.demo.coupon.apiParams.AccountCouponApiParams;
import com.demo.coupon.result.AccountCouponResult;
import com.demo.coupon.apiResult.AccountCouponApiResult;
import com.demo.coupon.service.IAccountCouponService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 账号优惠券Service业务层处理
 * 
 * @author demo
 * @date 2026-06-20
 */
@Service
public class AccountCouponServiceImpl implements IAccountCouponService {
    @Autowired
    private AccountCouponMapper accountCouponMapper;

    /**
     * 查询账号优惠券列表
     *
     * @param accountCoupon 账号优惠券
     * @return 账号优惠券
     */
    @Override
    public List<AccountCoupon> selectAccountCouponList(AccountCoupon accountCoupon) {
        return accountCouponMapper.selectAccountCouponList(accountCoupon);
    }

    /**
     * 查询账号优惠券
     *
     * @param accountCouponId 账号优惠券主键
     * @return 账号优惠券
     */
    @Override
    public AccountCoupon selectAccountCouponById(Long accountCouponId) {
        return accountCouponMapper.selectAccountCouponById(accountCouponId);
    }

    /**
     * 查询账号优惠券Result列表
     *
     * @param params 账号优惠券Params
     * @return 账号优惠券Result集合
     */
    @Override
    public List<AccountCouponResult> selectAccountCouponResultList(AccountCouponParams params) {
        return accountCouponMapper.selectAccountCouponResultList(params);
    }

    /**
     * 获取账号优惠券详细信息
     *
     * @param accountCouponId 账号优惠券主键
     * @return 账号优惠券Result
     */
    @Override
    public AccountCouponResult selectAccountCouponResult(Long accountCouponId) {
        return accountCouponMapper.selectAccountCouponResult(accountCouponId);
    }

    /**
     * 查询账号优惠券ApiResult列表（客户端）
     */
    @Override
    public List<AccountCouponApiResult> selectAccountCouponApiResultList(AccountCouponApiParams params) {
        return accountCouponMapper.selectAccountCouponApiResultList(params);
    }

    /**
     * 获取账号优惠券ApiResult详细信息（客户端）
     */
    @Override
    public AccountCouponApiResult selectAccountCouponApiResult(Long accountCouponId) {
        return accountCouponMapper.selectAccountCouponApiResult(accountCouponId);
    }

    /**
     * 新增账号优惠券
     *
     * @param accountCoupon 账号优惠券
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertAccountCoupon(AccountCoupon accountCoupon) {
        if (Objects.isNull(accountCoupon.getAccountCouponId())) {
            accountCoupon.setAccountCouponId(IdUtil.getSnowflakeNextId());
        }
        accountCoupon.setCreateTime(DateUtils.getNowDate());
        return accountCouponMapper.insertAccountCoupon(accountCoupon);
    }

    /**
     * 修改账号优惠券
     *
     * @param accountCoupon 账号优惠券
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateAccountCoupon(AccountCoupon accountCoupon) {
        accountCoupon.setUpdateTime(DateUtils.getNowDate());
        return accountCouponMapper.updateAccountCoupon(accountCoupon);
    }

    /**
     * 批量删除账号优惠券
     * 
     * @param accountCouponIdList 需要删除的账号优惠券主键集合
     * @return 结果
     */
    @Override
    public int deleteAccountCouponByIdList(List<Long> accountCouponIdList) {
        return accountCouponMapper.deleteAccountCouponByIdList(accountCouponIdList);
    }

    /**
     * 删除账号优惠券信息
     * 
     * @param accountCouponId 账号优惠券主键
     * @return 结果
     */
    @Override
    public int deleteAccountCouponById(Long accountCouponId) {
        return accountCouponMapper.deleteAccountCouponById(accountCouponId);
    }

    /**
     * 逻辑删除账号优惠券信息
     * 
     * @param accountCouponId 账号优惠券主键
     * @return 结果
     */
    @Override
    public int logicDeleteAccountCouponById(Long accountCouponId) {
        return accountCouponMapper.logicDeleteAccountCouponById(accountCouponId);
    }

    /**
     * 批量逻辑删除账号优惠券信息
     * 
     * @param accountCouponIdList 账号优惠券主键集合
     * @return 结果
     */
    @Override
    public int logicDeleteAccountCouponByIdList(List<Long> accountCouponIdList) {
        return accountCouponMapper.logicDeleteAccountCouponByIdList(accountCouponIdList);
    }

    /**
     * 查询账号优惠券数量
     *
     * @param params 账号优惠券Params
     * @return 数量
     */
    @Override
    public int selectAccountCouponCount(AccountCouponParams params) {
        return accountCouponMapper.selectAccountCouponCount(params);
    }

    /**
     * 按条件查询单条账号优惠券
     *
     * @param params 账号优惠券Params
     * @return 账号优惠券
     */
    @Override
    public AccountCoupon selectAccountCouponOne(AccountCouponParams params) {
        return accountCouponMapper.selectAccountCouponOne(params);
    }
}
