package com.demo.account.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 用户账号 Result 对象
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
@Alias("AccountResult")
public class AccountResult implements Serializable {

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 昵称
     */
    @Excel(name = "昵称")
    private String nickName;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String mobile;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;

    /**
     * 账户余额
     */
    @Excel(name = "账户余额")
    private BigDecimal balance;

    /**
     * 性别（0男 1女 2未知）
     */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private Integer gender;

    /**
     * 注册来源
     */
    @Excel(name = "注册来源")
    private String registerSource;

    /**
     * 账号状态（0正常 1停用）
     */
    @Excel(name = "账号状态", readConverterExp = "0=正常,1=停用")
    private Integer status;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP")
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


}
