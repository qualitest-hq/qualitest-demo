package com.demo.mall.apiParams;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城商品SPU ApiParams 对象（客户端）
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
@Alias("MallProductApiParams")
public class MallProductApiParams implements Serializable {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 上架状态（0下架 1上架）
     */
    private Integer shelfStatus;

}
