package com.demo.mall.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城退款单 ApiResult 对象（客户端）
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
@Alias("MallOrderRefundApiResult")
public class MallOrderRefundApiResult implements Serializable {

    /**
     * 退款单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long refundId;

    /**
     * 退款单号
     */
    private String refundNo;

    /**
     * 订单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 退款类型（1仅退款 2退货退款）
     */
    private Integer refundType;

    /**
     * 本次退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款状态（0待审核 1已通过 2已拒绝 3退款中 4已完成 5已取消）
     */
    private Integer refundStatus;

    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 退款流水号
     */
    private String transactionNo;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 退款完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /**
     * 处理备注
     */
    private String handleRemark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 退款明细
     */
    private List<MallOrderRefundItemApiResult> items;

}
