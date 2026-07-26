package com.demo.account.result;

import com.demo.common.annotation.Excel;
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
 * 余额流水管理端 Result
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountBalanceRecordResult")
public class AccountBalanceRecordResult implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Excel(name = "流水ID")
    private Long balanceRecordId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Excel(name = "账号ID")
    private Long accountId;

    @Excel(name = "账号昵称")
    private String accountNickName;

    @Excel(name = "账号手机号")
    private String accountMobile;

    @Excel(name = "流水类型", readConverterExp = "1=充值,2=赠送,3=订单支付,4=订单退款")
    private Integer recordType;

    @Excel(name = "变动方向", readConverterExp = "1=收入,2=支出")
    private Integer changeType;

    @Excel(name = "变动金额")
    private BigDecimal changeAmount;

    @Excel(name = "变动前余额")
    private BigDecimal balanceBefore;

    @Excel(name = "变动后余额")
    private BigDecimal balanceAfter;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Excel(name = "业务ID")
    private Long bizId;

    @Excel(name = "业务单号")
    private String bizNo;

    @Excel(name = "备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
