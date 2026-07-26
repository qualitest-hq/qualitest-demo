package com.demo.coupon.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 优惠券 Result 对象
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
@Alias("CouponResult")
public class CouponResult implements Serializable {

    /**
     * 优惠券ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long couponId;

    /**
     * 优惠券名称
     */
    @Excel(name = "优惠券名称")
    private String couponName;

    /**
     * 满金额
     */
    @Excel(name = "满金额")
    private BigDecimal thresholdAmount;

    /**
     * 减金额
     */
    @Excel(name = "减金额")
    private BigDecimal discountAmount;

    /**
     * 发放总量
     */
    @Excel(name = "发放总量")
    private Integer totalCount;

    /**
     * 已领取数量
     */
    @Excel(name = "已领取数量")
    private Integer receiveCount;

    /**
     * 已使用数量
     */
    @Excel(name = "已使用数量")
    private Integer usedCount;

    /**
     * 有效开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "有效开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date validStartTime;

    /**
     * 有效结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "有效结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date validEndTime;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private Integer status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


}
