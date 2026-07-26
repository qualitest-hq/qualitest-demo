package com.demo.mall.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 商城商品SPU Result 对象
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
@Alias("MallProductResult")
public class MallProductResult implements Serializable {

    /**
     * 商品ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long productId;

    /**
     * 分类ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long categoryId;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String categoryName;

    /**
     * 商品名称
     */
    @Excel(name = "商品名称")
    private String productName;

    /**
     * 计量单位
     */
    @Excel(name = "计量单位")
    private String unitName;

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
     * 固定运费
     */
    @Excel(name = "固定运费")
    private BigDecimal freightAmount;

    /**
     * 总库存
     */
    @Excel(name = "总库存")
    private Integer stockTotal;

    /**
     * 累计销量
     */
    @Excel(name = "累计销量")
    private Integer salesCount;

    /**
     * 上架状态（0下架 1上架）
     */
    @Excel(name = "上架状态", readConverterExp = "0=下架,1=上架")
    private Integer shelfStatus;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sortNum;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


}
