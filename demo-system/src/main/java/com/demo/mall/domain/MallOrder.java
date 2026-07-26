package com.demo.mall.domain;

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
 * 商城订单对象 mall_order
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
@Alias("MallOrder")
public class MallOrder extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 订单状态（0待付款 1待发货 2待收货 3已完成 4已取消 5退款中）
     */
    private Integer orderStatus;

    /**
     * 商品总金额
     */
    private BigDecimal totalAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 优惠券ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long couponId;

    /**
     * 券名称
     */
    private String couponName;

    /**
     * 满减门槛
     */
    private BigDecimal couponThresholdAmount;

    /**
     * 满减面额
     */
    private BigDecimal couponDiscountAmount;

    /**
     * 实际抵扣金额
     */
    private BigDecimal couponAmount;

    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 应付金额
     */
    private BigDecimal payAmount;

    /**
     * 使用的账号优惠券ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
     * 收货地址
     */
    private String receiverAddress;

    /**
     * 买家留言
     */
    private String buyerRemark;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    /**
     * 收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 支付流水号
     */
    private String paymentNo;

    /**
     * 支付方式（0未选 1微信 2支付宝 3余额）
     */
    private Integer payType;

    /**
     * 支付状态（0待支付 1已支付 2已关闭）
     */
    private Integer payStatus;

    /**
     * 第三方交易号
     */
    private String transactionNo;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 累计已退金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款状态（0无退款 1部分退款 2全额退款 3退款处理中）
     */
    private Integer refundStatus;

    /**
     * 退款次数
     */
    private Integer refundCount;

    /**
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
