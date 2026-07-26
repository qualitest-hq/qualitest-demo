package com.demo.account.apiParams;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 客户端账号注册 ApiParams
 *
 * @author demo
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountRegisterApiParams")
public class AccountRegisterApiParams implements Serializable {

    /** 手机号 */
    private String mobile;

    /** 密码 */
    private String password;

    /** 昵称（可选） */
    private String nickName;
}
