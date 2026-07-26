package com.demo.mall.params;

import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城购物车 Params 对象
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
@Alias("MallCartParams")
public class MallCartParams extends BaseEntity implements Serializable {

    /**
     * 账号ID
     */
    private Long accountId;

    /**
     * 账号昵称（模糊）
     */
    private String accountNickName;

    /**
     * 账号手机号（模糊）
     */
    private String accountMobile;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 商品名称（模糊）
     */
    private String productName;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * SKU名称（模糊）
     */
    private String skuName;

}
