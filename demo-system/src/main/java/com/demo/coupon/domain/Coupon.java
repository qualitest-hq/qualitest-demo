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
 * 优惠券对象 coupon
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
@Alias("Coupon")
public class Coupon extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
