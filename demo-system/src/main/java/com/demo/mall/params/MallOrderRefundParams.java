package com.demo.mall.params;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 商城退款单 Params 对象
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
@Alias("MallOrderRefundParams")
public class MallOrderRefundParams extends BaseEntity implements Serializable {

    /**
     * 退款单号
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
     * 账号ID
     */
    private Long accountId;

    /**
     * 账号昵称（模糊）
     */
    private String accountNickName;

    /**
     * 账号手机号（模糊）
     */
    private String accountMobile;

    /**
     * 退款类型（1仅退款 2退货退款）
     */
    private Integer refundType;

    /**
     * 退款状态（0待审核 1已通过 2已拒绝 3退款中 4已完成 5已取消）
     */
    private Integer refundStatus;

    /**
     * 退款原因（模糊）
     */
    private String refundReason;

}
