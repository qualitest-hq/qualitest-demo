package com.demo.mall.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

/**
 * 商城商品SPU ApiResult 对象（客户端）
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
@Alias("MallProductApiResult")
public class MallProductApiResult implements Serializable {

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
     * 商品名称
     */
    private String productName;

    /**
     * 计量单位
     */
    private String unitName;

    /**
     * 市场价
     */
    private BigDecimal marketPrice;

    /**
     * 销售价
     */
    private BigDecimal salePrice;

    /**
     * 固定运费
     */
    private BigDecimal freightAmount;

    /**
     * 总库存
     */
    private Integer stockTotal;

    /**
     * 累计销量
     */
    private Integer salesCount;

    /**
     * 上架状态（0下架 1上架）
     */
    private Integer shelfStatus;

    /**
     * 排序
     */
    private Integer sortNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * SKU 列表（客户端详情）
     */
    private List<MallProductSkuApiResult> skus;

}
