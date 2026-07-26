package com.demo.account.apiParams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serial;
import java.io.Serializable;

/**
 * 账号余额流水 ApiParams 对象（客户端）
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountBalanceRecordApiParams")
public class AccountBalanceRecordApiParams implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号ID
     */
    @JsonIgnore
    private Long accountId;

    /**
     * 流水类型（1充值 2赠送 3订单支付 4订单退款）
     */
    private Integer recordType;

    /**
     * 变动方向（1收入 2支出）
     */
    private Integer changeType;
}
