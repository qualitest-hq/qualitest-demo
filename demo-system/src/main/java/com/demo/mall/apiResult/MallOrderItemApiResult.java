package com.demo.mall.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城订单明细 ApiResult 对象（客户端）
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
@Alias("MallOrderItemApiResult")
public class MallOrderItemApiResult implements Serializable {

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
    private String productName;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 成交单价
     */
    private BigDecimal salePrice;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 小计金额
     */
    private BigDecimal totalAmount;

    /**
     * 累计已退数量
     */
    private Integer refundedQuantity;

    /**
     * 累计已退金额
     */
    private BigDecimal refundedAmount;


}
