package com.demo.account.service.impl;

import com.demo.account.domain.Account;
import com.demo.account.domain.AccountBalanceRecord;
import com.demo.account.mapper.AccountBalanceRecordMapper;
import com.demo.account.mapper.AccountMapper;
import com.demo.common.enums.AccountBalanceChangeType;
import com.demo.common.enums.AccountBalanceRecordType;
import com.demo.common.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountBalanceRecordServiceImplTest {

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AccountBalanceRecordMapper accountBalanceRecordMapper;

    @InjectMocks
    private AccountBalanceRecordServiceImpl accountBalanceRecordService;

    @Test
    void changeBalance_recharge_increasesBalanceAndWritesRecord() {
        Long accountId = 30001L;
        Account account = Account.builder()
                .accountId(accountId)
                .balance(new BigDecimal("100.00"))
                .delFlag(0)
                .build();
        when(accountMapper.selectAccountByIdForUpdate(accountId)).thenReturn(account);
        when(accountMapper.addAccountBalance(eq(accountId), any())).thenReturn(1);

        AccountBalanceRecord record = accountBalanceRecordService.changeBalance(
                accountId,
                new BigDecimal("50.00"),
                AccountBalanceRecordType.RECHARGE,
                0L,
                "RC001",
                "模拟充值");

        assertEquals(AccountBalanceRecordType.RECHARGE.getCode(), record.getRecordType());
        assertEquals(AccountBalanceChangeType.INCOME.getCode(), record.getChangeType());
        assertEquals(0, new BigDecimal("100.00").compareTo(record.getBalanceBefore()));
        assertEquals(0, new BigDecimal("150.00").compareTo(record.getBalanceAfter()));
        assertEquals(0, new BigDecimal("50.00").compareTo(record.getChangeAmount()));

        ArgumentCaptor<AccountBalanceRecord> captor = ArgumentCaptor.forClass(AccountBalanceRecord.class);
        verify(accountBalanceRecordMapper).insertAccountBalanceRecord(captor.capture());
        assertEquals(accountId, captor.getValue().getAccountId());
        verify(accountMapper).addAccountBalance(accountId, new BigDecimal("50.00"));
    }

    @Test
    void changeBalance_expenseInsufficientBalance_throws() {
        Long accountId = 30002L;
        Account account = Account.builder()
                .accountId(accountId)
                .balance(new BigDecimal("100.00"))
                .delFlag(0)
                .build();
        when(accountMapper.selectAccountByIdForUpdate(accountId)).thenReturn(account);

        ServiceException ex = assertThrows(ServiceException.class, () ->
                accountBalanceRecordService.changeBalance(
                        accountId,
                        new BigDecimal("-210.00"),
                        AccountBalanceRecordType.ORDER_PAY,
                        7008L,
                        "MO001",
                        "订单余额支付"));
        assertEquals("余额不足", ex.getMessage());
    }
}
