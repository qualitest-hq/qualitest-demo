package com.demo.mall.apiParams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 客户端取消订单 ApiParams
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderCancelApiParams implements Serializable {

    /**
     * 取消原因
     */
    private String cancelReason;
}
