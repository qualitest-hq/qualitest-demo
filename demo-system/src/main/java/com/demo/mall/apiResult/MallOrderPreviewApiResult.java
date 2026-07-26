package com.demo.mall.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 订单预览/创建响应 ApiResult
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderPreviewApiResult implements Serializable {

    /**
     * 订单 ID（仅 create 返回）
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 订单编号（仅 create 返回）
     */
    private String orderNo;

    /**
     * 商品总金额
     */
    private BigDecimal totalAmount;

    /**
     * 券抵扣金额
     */
    private BigDecimal couponAmount;

    /**
     * 订单总优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 运费
     */
    private BigDecimal freightAmount;

    /**
     * 应付金额
     */
    private BigDecimal payAmount;

    /**
     * 订单明细
     */
    private List<MallOrderPreviewItemApiResult> items;

    /**
     * 收货地址快照
     */
    private MallOrderAddressSnapshotApiResult address;
}
