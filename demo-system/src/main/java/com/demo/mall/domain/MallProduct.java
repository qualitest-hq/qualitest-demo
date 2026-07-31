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
 * 商城商品SPU对象 mall_product
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
@Alias("MallProduct")
public class MallProduct extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 封面图 URL
     */
    private String coverImage;

    /**
     * 详情图 URL（逗号分隔）
     */
    private String detailImages;

    /**
     * 视频 URL
     */
    private String videoUrl;

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
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
