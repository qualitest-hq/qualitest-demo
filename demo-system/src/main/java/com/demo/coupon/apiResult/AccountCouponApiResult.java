package com.demo.coupon.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 账号优惠券 ApiResult 对象（客户端）
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
@Alias("AccountCouponApiResult")
public class AccountCouponApiResult implements Serializable {

    /**
     * 账号优惠券ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountCouponId;

    /**
     * 优惠券ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long couponId;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 券名称
     */
    private String couponName;

    /**
     * 满减门槛
     */
    private BigDecimal thresholdAmount;

    /**
     * 满减金额
     */
    private BigDecimal discountAmount;

    /**
     * 有效开始
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;

    /**
     * 有效结束
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;

    /**
     * 状态（0未使用 1已使用 2已过期）
     */
    private Integer couponStatus;

    /**
     * 领取时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /**
     * 使用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useTime;

    /**
     * 使用订单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;


}
