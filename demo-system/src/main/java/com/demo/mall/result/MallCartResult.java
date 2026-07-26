package com.demo.mall.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 商城购物车 Result 对象
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
@Alias("MallCartResult")
public class MallCartResult implements Serializable {

    /**
     * 购物车ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cartId;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 账号昵称
     */
    @Excel(name = "账号昵称")
    private String accountNickName;

    /**
     * 账号手机号
     */
    @Excel(name = "账号手机号")
    private String accountMobile;

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
     * SKU ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long skuId;

    /**
     * SKU名称
     */
    @Excel(name = "SKU名称")
    private String skuName;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Integer quantity;


}
