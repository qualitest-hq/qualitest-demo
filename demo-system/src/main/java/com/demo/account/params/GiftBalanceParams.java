package com.demo.account.params;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * 赠送账户余额参数
 */
@Getter
@Setter
public class GiftBalanceParams {

    /**
     * 目标账号 ID
     */
    private Long accountId;

    /** 赠送金额（须大于 0） */
    private BigDecimal amount;

    /** 备注（可选） */
    private String remark;
}
