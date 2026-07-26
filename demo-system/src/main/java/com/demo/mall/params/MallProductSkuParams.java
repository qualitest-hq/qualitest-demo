package com.demo.mall.params;

import java.math.BigDecimal;
import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城商品SKU Params 对象
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
@Alias("MallProductSkuParams")
public class MallProductSkuParams extends BaseEntity implements Serializable {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称（模糊，关联SPU）
     */
    private String productName;

    /**
     * SKU名称
     */
    private String skuName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

}
