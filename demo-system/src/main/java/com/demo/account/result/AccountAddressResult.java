package com.demo.account.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.math.BigDecimal;
import com.demo.common.annotation.Excel;

import java.io.Serializable;

/**
 * 账号收货地址 Result 对象
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
@Alias("AccountAddressResult")
public class AccountAddressResult implements Serializable {

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
     * 账号昵称
     */
    @Excel(name = "账号昵称")
    private String accountNickName;

    /**
     * 账号手机号
     */
    @Excel(name = "账号手机号")
    private String accountMobile;

    /**
     * 收货人姓名
     */
    @Excel(name = "收货人姓名")
    private String receiverName;

    /**
     * 收货人手机
     */
    @Excel(name = "收货人手机")
    private String receiverPhone;

    /**
     * 省
     */
    @Excel(name = "省")
    private String province;

    /**
     * 市
     */
    @Excel(name = "市")
    private String city;

    /**
     * 区/县
     */
    @Excel(name = "区/县")
    private String district;

    /**
     * 详细地址
     */
    @Excel(name = "详细地址")
    private String detailAddress;

    /**
     * 邮政编码
     */
    @Excel(name = "邮政编码")
    private String postalCode;

    /**
     * 是否默认（0否 1是）
     */
    @Excel(name = "是否默认", readConverterExp = "0=否,1=是")
    private Integer isDefault;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;


}
