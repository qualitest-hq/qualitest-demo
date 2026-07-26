package com.demo.mall.apiParams;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城商品分类 ApiParams 对象（客户端）
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
@Alias("MallCategoryApiParams")
public class MallCategoryApiParams implements Serializable {

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

}
