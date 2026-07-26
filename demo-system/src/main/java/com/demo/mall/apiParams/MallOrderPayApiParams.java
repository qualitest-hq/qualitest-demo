package com.demo.mall.apiParams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 客户端订单支付 ApiParams
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderPayApiParams implements Serializable {

    /**
     * 支付方式（1微信 2支付宝 3余额）
     */
    private Integer payType;
}
