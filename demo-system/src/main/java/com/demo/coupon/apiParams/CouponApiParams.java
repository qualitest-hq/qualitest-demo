package com.demo.coupon.apiParams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 优惠券 ApiParams 对象（客户端）
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
@Alias("CouponApiParams")
public class CouponApiParams implements Serializable {

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 账号ID（排除已领取）
     */
    @JsonIgnore
    private Long accountId;

}
