package com.demo.mall.params;

import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城商品分类 Params 对象
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
@Alias("MallCategoryParams")
public class MallCategoryParams extends BaseEntity implements Serializable {

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

}
