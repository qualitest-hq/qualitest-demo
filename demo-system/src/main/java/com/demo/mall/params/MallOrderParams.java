package com.demo.mall.params;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城订单 Params 对象
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
@Alias("MallOrderParams")
public class MallOrderParams extends BaseEntity implements Serializable {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 账号ID
     */
    private Long accountId;

    /**
     * 账号昵称（模糊）
     */
    private String accountNickName;

    /**
     * 账号手机号（模糊）
     */
    private String accountMobile;

    /**
     * 券名称（模糊）
     */
    private String couponName;

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
