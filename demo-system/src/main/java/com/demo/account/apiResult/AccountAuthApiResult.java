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

/**
 * 客户端账号认证响应，用于注册、登录接口返回。
 *
 * @author demo
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountAuthApiResult")
public class AccountAuthApiResult implements Serializable {

    /**
     * 账号主键
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long accountId;

    /**
     * JWT 访问令牌，后续请求通过 Authorization: Bearer 携带
     */
    private String token;

    /**
     * 账号昵称
     */
    private String nickName;
}
