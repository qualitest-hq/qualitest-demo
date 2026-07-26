package com.demo.mall.apiParams;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 客户端下单/预览 ApiParams
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderSubmitApiParams implements Serializable {

    /**
     * 购物车 ID 列表（与 items 二选一）
     */
    private List<Long> cartIds;

    /**
     * 直接购买明细（与 cartIds 二选一）
     */
    private List<MallOrderSubmitItemApiParams> items;

    /**
     * 收货地址 ID（create 必填，preview 可选）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long addressId;

    /**
     * 账号优惠券 ID（不使用传 0 或不传）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountCouponId;

    /**
     * 买家留言
     */
    private String buyerRemark;
}
