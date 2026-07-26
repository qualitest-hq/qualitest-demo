package com.demo.account.apiParams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户端模拟充值 ApiParams
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountBalanceRechargeApiParams implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;

    private String remark;
}
