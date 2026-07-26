package com.demo.mall.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城商品分类 ApiResult 对象（客户端）
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
@Alias("MallCategoryApiResult")
public class MallCategoryApiResult implements Serializable {

    /**
     * 分类ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 显示顺序
     */
    private Integer orderNum;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;


}
