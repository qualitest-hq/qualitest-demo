package com.demo.mall.params;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品上架/下架参数
 */
@Getter
@Setter
public class ChangeShelfStatusParams {

    /**
     * 商品 ID
     */
    /**
     * 商品 ID
     */
    private Long productId;

    /** 0下架 1上架 */
    private Integer shelfStatus;
}
