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
 * 客户端账号资料修改 ApiParams
 *
 * @author demo
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountProfileUpdateApiParams")
public class AccountProfileUpdateApiParams implements Serializable {

    /** 昵称 */
    private String nickName;

    /** 性别（0男 1女 2未知） */
    private Integer gender;
}
