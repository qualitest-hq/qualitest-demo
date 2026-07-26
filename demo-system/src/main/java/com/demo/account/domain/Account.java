package com.demo.account.domain;

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
import java.io.Serial;

/**
 * 用户账号对象 account
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
@Alias("Account")
public class Account extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 性别（0男 1女 2未知）
     */
    private Integer gender;

    /**
     * 注册来源
     */
    private String registerSource;

    /**
     * 账号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 删除标志（0未删除 1已删除）
     */
    private Integer delFlag;

}
