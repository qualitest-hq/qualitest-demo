package com.demo.account.apiParams;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 账号收货地址 ApiParams 对象（客户端）
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
@Alias("AccountAddressApiParams")
public class AccountAddressApiParams implements Serializable {

    /**
     * 账号ID
     */
    @JsonIgnore
    private Long accountId;

    /**
     * 收货人姓名
     */
    private String receiverName;

    /**
     * 收货人手机
     */
    private String receiverPhone;

    /**
     * 是否默认（0否 1是）
     */
    private Integer isDefault;

}
