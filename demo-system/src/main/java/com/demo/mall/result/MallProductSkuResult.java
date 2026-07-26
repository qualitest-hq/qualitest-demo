package com.demo.mall.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 商城商品SKU Result 对象
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
@Alias("MallProductSkuResult")
public class MallProductSkuResult implements Serializable {

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
     * 市场价
     */
    @Excel(name = "市场价")
    private BigDecimal marketPrice;

    /**
     * 销售价
     */
    @Excel(name = "销售价")
    private BigDecimal salePrice;

    /**
     * 库存
     */
    @Excel(name = "库存")
    private Integer stock;

    /**
     * 销量
     */
    @Excel(name = "销量")
    private Integer salesCount;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private Integer status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


}
