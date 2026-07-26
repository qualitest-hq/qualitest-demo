package com.demo.coupon.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import com.demo.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serial;

/**
 * 账号优惠券对象 account_coupon
 * 
 * @author demo
 * @date 2026-06-20
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountCoupon")
public class AccountCoupon extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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

    /**
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
