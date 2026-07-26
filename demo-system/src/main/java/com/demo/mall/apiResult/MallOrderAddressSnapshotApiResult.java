package com.demo.mall.apiResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 订单收货地址快照 ApiResult
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderAddressSnapshotApiResult implements Serializable {

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人手机
     */
    private String receiverPhone;

    /**
     * 完整收货地址
     */
    private String receiverAddress;
}
