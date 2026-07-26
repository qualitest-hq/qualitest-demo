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
 * 订单预览明细 ApiResult
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderPreviewItemApiResult implements Serializable {

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
     * 行小计金额
     */
    private BigDecimal totalAmount;
}
