package com.demo.mall.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 商城订单 Result 对象
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
@Alias("MallOrderResult")
public class MallOrderResult implements Serializable {

    /**
     * 订单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderNo;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 账号昵称
     */
    @Excel(name = "账号昵称")
    private String accountNickName;

    /**
     * 账号手机号
     */
    @Excel(name = "账号手机号")
    private String accountMobile;

    /**
     * 订单状态（0待付款 1待发货 2待收货 3已完成 4已取消 5退款中）
     */
    @Excel(name = "订单状态", readConverterExp = "0=待付款,1=待发货,2=待收货,3=已完成,4=已取消,5=退款中")
    private Integer orderStatus;

    /**
     * 商品总金额
     */
    @Excel(name = "商品总金额")
    private BigDecimal totalAmount;

    /**
     * 优惠金额
     */
    @Excel(name = "优惠金额")
    private BigDecimal discountAmount;

    /**
     * 优惠券ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long couponId;

    /**
     * 券名称
     */
    @Excel(name = "券名称")
    private String couponName;

    /**
     * 满减门槛
     */
    @Excel(name = "满减门槛")
    private BigDecimal couponThresholdAmount;

    /**
     * 满减面额
     */
    @Excel(name = "满减面额")
    private BigDecimal couponDiscountAmount;

    /**
     * 实际抵扣金额
     */
    @Excel(name = "实际抵扣金额")
    private BigDecimal couponAmount;

    /**
     * 运费
     */
    @Excel(name = "运费")
    private BigDecimal freightAmount;

    /**
     * 应付金额
     */
    @Excel(name = "应付金额")
    private BigDecimal payAmount;

    /**
     * 使用的账号优惠券ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountCouponId;

    /**
     * 收货人姓名
     */
    @Excel(name = "收货人姓名")
    private String receiverName;

    /**
     * 收货人手机
     */
    @Excel(name = "收货人手机")
    private String receiverPhone;

    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    private String receiverAddress;

    /**
     * 买家留言
     */
    @Excel(name = "买家留言")
    private String buyerRemark;

    /**
     * 取消原因
     */
    @Excel(name = "取消原因")
    private String cancelReason;

    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发货时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date deliveryTime;

    /**
     * 收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "收货时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date finishTime;

    /**
     * 支付流水号
     */
    @Excel(name = "支付流水号")
    private String paymentNo;

    /**
     * 支付方式（0未选 1微信 2支付宝 3余额）
     */
    @Excel(name = "支付方式", readConverterExp = "0=未选,1=微信,2=支付宝,3=余额")
    private Integer payType;

    /**
     * 支付状态（0待支付 1已支付 2已关闭）
     */
    @Excel(name = "支付状态", readConverterExp = "0=待支付,1=已支付,2=已关闭")
    private Integer payStatus;

    /**
     * 第三方交易号
     */
    @Excel(name = "第三方交易号")
    private String transactionNo;

    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /**
     * 累计已退金额
     */
    @Excel(name = "累计已退金额")
    private BigDecimal refundAmount;

    /**
     * 退款状态（0无退款 1部分退款 2全额退款 3退款处理中）
     */
    @Excel(name = "退款状态", readConverterExp = "0=无退款,1=部分退款,2=全额退款,3=退款处理中")
    private Integer refundStatus;

    /**
     * 退款次数
     */
    @Excel(name = "退款次数")
    private Integer refundCount;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 订单明细列表
     */
    private List<MallOrderItemResult> items;

}
