package com.demo.mall.domain;

import java.math.BigDecimal;
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
 * 商城订单明细对象 mall_order_item
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
@Alias("MallOrderItem")
public class MallOrderItem extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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

    /**
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
