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
 * 退款预览 ApiResult
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderRefundPreviewApiResult implements Serializable {

    /**
     * 是否允许退款
     */
    private Boolean canRefund;

    /**
     * 不可退款时的原因
     */
    private String refuseReason;

    /**
     * 订单 ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 订单应付金额
     */
    private BigDecimal payAmount;

    /**
     * 订单累计已退金额
     */
    private BigDecimal refundedAmount;

    /**
     * 本次拟退总额
     */
    private BigDecimal refundAmount;

    /**
     * 明细可退/拟退信息
     */
    private List<MallOrderRefundPreviewItemApiResult> items;
}
