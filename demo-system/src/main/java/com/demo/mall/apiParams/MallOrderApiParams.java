package com.demo.mall.apiParams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城订单 ApiParams 对象（客户端）
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
@Alias("MallOrderApiParams")
public class MallOrderApiParams implements Serializable {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 账号ID
     */
    @JsonIgnore
    private Long accountId;

    /**
     * 订单状态（0待付款 1待发货 2待收货 3已完成 4已取消 5退款中）
     */
    private Integer orderStatus;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 使用的账号优惠券ID
     */
    private Long accountCouponId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人手机
     */
    private String receiverPhone;

    /**
     * 支付方式（0未选 1微信 2支付宝 3余额）
     */
    private Integer payType;

    /**
     * 支付状态（0待支付 1已支付 2已关闭）
     */
    private Integer payStatus;

    /**
     * 退款状态（0无退款 1部分退款 2全额退款 3退款处理中）
     */
    private Integer refundStatus;

}
