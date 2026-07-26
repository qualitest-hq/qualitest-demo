package com.demo.mall.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 商城订单明细 Result 对象
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
@Alias("MallOrderItemResult")
public class MallOrderItemResult implements Serializable {

    /**
     * 订单明细ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderItemId;

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
     * 商品ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long productId;

    /**
     * SKU ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long skuId;

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
     * 成交单价
     */
    @Excel(name = "成交单价")
    private BigDecimal salePrice;

    /**
     * 购买数量
     */
    @Excel(name = "购买数量")
    private Integer quantity;

    /**
     * 小计金额
     */
    @Excel(name = "小计金额")
    private BigDecimal totalAmount;

    /**
     * 累计已退数量
     */
    @Excel(name = "累计已退数量")
    private Integer refundedQuantity;

    /**
     * 累计已退金额
     */
    @Excel(name = "累计已退金额")
    private BigDecimal refundedAmount;


}
