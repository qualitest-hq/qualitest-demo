package com.demo.mall.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商城购物车 ApiResult 对象（客户端）
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
@Alias("MallCartApiResult")
public class MallCartApiResult implements Serializable {

    /**
     * 购物车ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cartId;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

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
     * 数量
     */
    private Integer quantity;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * SKU 名称
     */
    private String skuName;

    /**
     * 销售单价
     */
    private BigDecimal salePrice;

    /**
     * 行小计金额
     */
    private BigDecimal subtotal;

}
