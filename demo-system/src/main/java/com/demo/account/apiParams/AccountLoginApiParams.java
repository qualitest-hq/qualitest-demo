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
 * 客户端账号登录 ApiParams
 *
 * @author demo
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountLoginApiParams")
public class AccountLoginApiParams implements Serializable {

    /** 手机号 */
    private String mobile;

    /** 密码 */
    private String password;
}
