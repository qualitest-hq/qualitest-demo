package com.demo.mall.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 商城退款单 Result 对象
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
@Alias("MallOrderRefundResult")
public class MallOrderRefundResult implements Serializable {

    /**
     * 退款单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long refundId;

    /**
     * 退款单号
     */
    @Excel(name = "退款单号")
    private String refundNo;

    /**
     * 订单ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderId;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderNo;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 账号昵称
     */
    @Excel(name = "账号昵称")
    private String accountNickName;

    /**
     * 账号手机号
     */
    @Excel(name = "账号手机号")
    private String accountMobile;

    /**
     * 退款类型（1仅退款 2退货退款）
     */
    @Excel(name = "退款类型", readConverterExp = "1=仅退款,2=退货退款")
    private Integer refundType;

    /**
     * 本次退款金额
     */
    @Excel(name = "本次退款金额")
    private BigDecimal refundAmount;

    /**
     * 退款状态（0待审核 1已通过 2已拒绝 3退款中 4已完成 5已取消）
     */
    @Excel(name = "退款状态", readConverterExp = "0=待审核,1=已通过,2=已拒绝,3=退款中,4=已完成,5=已取消")
    private Integer refundStatus;

    /**
     * 退款原因
     */
    @Excel(name = "退款原因")
    private String refundReason;

    /**
     * 退款流水号
     */
    @Excel(name = "退款流水号")
    private String transactionNo;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date applyTime;

    /**
     * 退款完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "退款完成时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /**
     * 处理备注
     */
    @Excel(name = "处理备注")
    private String handleRemark;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 退款明细
     */
    private List<MallOrderRefundItemResult> refundItems;

}
