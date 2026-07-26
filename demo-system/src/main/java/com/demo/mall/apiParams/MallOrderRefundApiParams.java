package com.demo.mall.apiParams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城退款单 ApiParams 对象（客户端）
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
@Alias("MallOrderRefundApiParams")
public class MallOrderRefundApiParams implements Serializable {

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 账号ID
     */
    @JsonIgnore
    private Long accountId;

    /**
     * 退款类型（1仅退款 2退货退款）
     */
    private Integer refundType;

    /**
     * 退款状态（0待审核 1已通过 2已拒绝 3退款中 4已完成 5已取消）
     */
    private Integer refundStatus;

}
