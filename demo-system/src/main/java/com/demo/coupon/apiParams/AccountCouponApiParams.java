package com.demo.coupon.apiParams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账号优惠券 ApiParams 对象（客户端）
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
@Alias("AccountCouponApiParams")
public class AccountCouponApiParams implements Serializable {

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 账号ID
     */
    @JsonIgnore
    private Long accountId;

    /**
     * 券名称
     */
    private String couponName;

    /**
     * 状态（0未使用 1已使用 2已过期）
     */
    private Integer couponStatus;

    /**
     * 订单金额（筛选可用券）
     */
    private BigDecimal orderAmount;

}
