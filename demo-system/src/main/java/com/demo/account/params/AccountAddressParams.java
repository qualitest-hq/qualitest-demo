package com.demo.account.params;

import com.demo.common.core.domain.BaseEntity;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 账号收货地址 Params 对象
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
@Alias("AccountAddressParams")
public class AccountAddressParams extends BaseEntity implements Serializable {

    /**
     * 账号ID
     */
    private Long accountId;

    /**
     * 账号昵称（模糊）
     */
    private String accountNickName;

    /**
     * 账号手机号（模糊）
     */
    private String accountMobile;

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
