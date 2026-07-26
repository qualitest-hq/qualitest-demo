package com.demo.mall.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 商城退款单对象 mall_order_refund
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
@Alias("MallOrderRefund")
public class MallOrderRefund extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
