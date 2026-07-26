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
 * 商城商品SKU对象 mall_product_sku
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
@Alias("MallProductSku")
public class MallProductSku extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * SKU ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long skuId;

    /**
     * 商品ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long productId;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 销售价
     */
    private BigDecimal salePrice;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 销量
     */
    private Integer salesCount;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
