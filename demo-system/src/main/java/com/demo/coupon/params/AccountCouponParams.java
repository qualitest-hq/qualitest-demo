package com.demo.coupon.params;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 账号优惠券 Params 对象
 *
 * @author demo
 * @since 2026-06-20
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountCouponParams")
public class AccountCouponParams extends BaseEntity implements Serializable {

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 账号ID
     */
    private Long accountId;

    /**
     * 账号昵称（模糊）
     */
    private String accountNickName;

    /**
     * 账号手机号（模糊）
     */
    private String accountMobile;

    /**
     * 券名称
     */
    private String couponName;

    /**
     * 订单编号（模糊）
     */
    private String orderNo;

    /**
     * 状态（0未使用 1已使用 2已过期）
     */
    private Integer couponStatus;

}
