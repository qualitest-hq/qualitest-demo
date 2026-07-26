package com.demo.mall.apiParams;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城商品SKU ApiParams 对象（客户端）
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
@Alias("MallProductSkuApiParams")
public class MallProductSkuApiParams implements Serializable {

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU名称
     */
    private String skuName;

}
