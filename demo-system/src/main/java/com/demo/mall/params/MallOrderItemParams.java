package com.demo.mall.params;

import java.math.BigDecimal;
import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城订单明细 Params 对象
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
@Alias("MallOrderItemParams")
public class MallOrderItemParams extends BaseEntity implements Serializable {

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号（模糊）
     */
    private String orderNo;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * SKU名称
     */
    private String skuName;

}
