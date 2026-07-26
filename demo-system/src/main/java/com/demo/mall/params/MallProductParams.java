package com.demo.mall.params;

import java.math.BigDecimal;
import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城商品SPU Params 对象
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
@Alias("MallProductParams")
public class MallProductParams extends BaseEntity implements Serializable {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称（模糊）
     */
    private String categoryName;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 上架状态（0下架 1上架）
     */
    private Integer shelfStatus;

}
