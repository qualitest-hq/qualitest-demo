package com.demo.mall.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 商城退款明细 Result 对象
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
@Alias("MallOrderRefundItemResult")
public class MallOrderRefundItemResult implements Serializable {

    /**
     * 退款明细ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long refundItemId;

    /**
     * 退款单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long refundId;

    /**
     * 退款单号
     */
    @Excel(name = "退款单号")
    private String refundNo;

    /**
     * 订单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 订单明细ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderItemId;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String productName;

    /**
     * SKU名称
     */
    @Excel(name = "SKU名称")
    private String skuName;

    /**
     * 本次退款数量
     */
    @Excel(name = "本次退款数量")
    private Integer refundQuantity;

    /**
     * 本次退款金额
     */
    @Excel(name = "本次退款金额")
    private BigDecimal refundAmount;


}
