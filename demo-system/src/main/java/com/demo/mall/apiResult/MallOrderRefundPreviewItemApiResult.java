package com.demo.mall.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 退款预览明细 ApiResult
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderRefundPreviewItemApiResult implements Serializable {

    /**
     * 订单明细 ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderItemId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * SKU 名称
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
     * 累计已退数量
     */
    private Integer refundedQuantity;

    /**
     * 可退数量
     */
    private Integer refundableQuantity;

    /**
     * 本次拟退数量
     */
    private Integer refundQuantity;

    /**
     * 本次拟退金额
     */
    private BigDecimal refundAmount;
}
