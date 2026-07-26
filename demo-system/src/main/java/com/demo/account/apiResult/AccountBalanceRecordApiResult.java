package com.demo.account.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 余额流水客户端 ApiResult
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountBalanceRecordApiResult")
public class AccountBalanceRecordApiResult implements Serializable {

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

    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
