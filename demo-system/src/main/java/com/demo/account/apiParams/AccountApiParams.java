package com.demo.account.apiParams;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 用户账号 ApiParams 对象（客户端）
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
@Alias("AccountApiParams")
public class AccountApiParams implements Serializable {

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String mobile;

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

}
