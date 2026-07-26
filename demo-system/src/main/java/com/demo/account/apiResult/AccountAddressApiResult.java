package com.demo.account.apiResult;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * 账号收货地址 ApiResult 对象（客户端）
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
@Alias("AccountAddressApiResult")
public class AccountAddressApiResult implements Serializable {

    /**
     * 地址ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long addressId;

    /**
     * 账号ID
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 邮政编码
     */
    private String postalCode;

    /**
     * 是否默认（0否 1是）
     */
    private Integer isDefault;

    /**
     * 备注
     */
    private String remark;


}
