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
 * 客户端下单明细 ApiParams
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderSubmitItemApiParams implements Serializable {

    /**
     * SKU ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long skuId;

    /**
     * 购买数量
     */
    private Integer quantity;
}
