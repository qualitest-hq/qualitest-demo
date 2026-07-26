package com.demo.coupon.params;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 优惠券 Params 对象
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
@Alias("CouponParams")
public class CouponParams extends BaseEntity implements Serializable {

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

}
