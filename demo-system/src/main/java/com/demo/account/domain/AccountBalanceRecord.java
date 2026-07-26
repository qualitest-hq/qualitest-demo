package com.demo.account.domain;

import com.demo.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 账号余额流水对象 account_balance_record
 *
 * @author demo
 * @date 2026-06-28
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountBalanceRecord")
public class AccountBalanceRecord extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long balanceRecordId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 流水类型（1充值 2赠送 3订单支付 4订单退款）
     */
    private Integer recordType;

    /**
     * 变动方向（1收入 2支出）
     */
    private Integer changeType;

    private BigDecimal changeAmount;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long bizId;

    private String bizNo;

    private Integer delFlag;
}
