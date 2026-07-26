package com.demo.mall.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import com.demo.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serial;

/**
 * 商城退款明细对象 mall_order_refund_item
 * 
 * @author demo
 * @date 2026-06-20
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("MallOrderRefundItem")
public class MallOrderRefundItem extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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

    /**
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
