package com.demo.mall.apiParams;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 客户端修改购物车数量 ApiParams
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallCartQuantityApiParams implements Serializable {

    /**
     * 购物车 ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long cartId;

    /**
     * 购买数量
     */
    private Integer quantity;
}
