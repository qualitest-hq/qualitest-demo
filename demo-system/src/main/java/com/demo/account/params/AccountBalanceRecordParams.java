package com.demo.account.params;

import com.demo.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.io.Serial;
import java.io.Serializable;

/**
 * 余额流水管理端查询 Params
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("AccountBalanceRecordParams")
public class AccountBalanceRecordParams extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long accountId;

    private String accountNickName;

    private String accountMobile;

    private Integer recordType;

    private Integer changeType;

    private String bizNo;
}
