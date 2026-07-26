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
 * 客户端账号密码修改 ApiParams
 *
 * @author demo
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountPasswordUpdateApiParams")
public class AccountPasswordUpdateApiParams implements Serializable {

    /** 旧密码 */
    private String oldPassword;

    /** 新密码 */
    private String newPassword;
}
