package com.demo.mall.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城退款明细 ApiResult 对象（客户端）
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
@Alias("MallOrderRefundItemApiResult")
public class MallOrderRefundItemApiResult implements Serializable {

    /**
     * 退款明细ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long refundItemId;

    /**
     * 退款单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long refundId;

    /**
     * 订单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 订单明细ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderItemId;

    /**
     * 本次退款数量
     */
    private Integer refundQuantity;

    /**
     * 本次退款金额
     */
    private BigDecimal refundAmount;


}
