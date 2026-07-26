package com.demo.coupon.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 优惠券 ApiResult 对象（客户端）
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
@Alias("CouponApiResult")
public class CouponApiResult implements Serializable {

    /**
     * 优惠券ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long couponId;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 满金额
     */
    private BigDecimal thresholdAmount;

    /**
     * 减金额
     */
    private BigDecimal discountAmount;

    /**
     * 发放总量
     */
    private Integer totalCount;

    /**
     * 已领取数量
     */
    private Integer receiveCount;

    /**
     * 已使用数量
     */
    private Integer usedCount;

    /**
     * 有效开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;

    /**
     * 有效结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;


}
