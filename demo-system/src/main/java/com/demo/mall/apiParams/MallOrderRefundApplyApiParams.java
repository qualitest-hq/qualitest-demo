package com.demo.mall.apiParams;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 退款预览/申请 ApiParams
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MallOrderRefundApplyApiParams implements Serializable {

    /**
     * 订单 ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 退款类型（1仅退款 2退货退款）
     */
    private Integer refundType;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款明细（preview 可选，apply 必填）
     */
    private List<MallOrderRefundApplyItemApiParams> items;
}
