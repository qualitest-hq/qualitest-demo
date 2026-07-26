package com.demo.mall.params;

import java.math.BigDecimal;
import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城退款明细 Params 对象
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
@Alias("MallOrderRefundItemParams")
public class MallOrderRefundItemParams extends BaseEntity implements Serializable {

    /**
     * 退款单ID
     */
    private Long refundId;

    /**
     * 退款单号（模糊）
     */
    private String refundNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 订单编号（模糊）
     */
    private String orderNo;

    /**
     * 订单明细ID
     */
    private Long orderItemId;

    /**
     * 商品名称（模糊）
     */
    private String productName;

}
