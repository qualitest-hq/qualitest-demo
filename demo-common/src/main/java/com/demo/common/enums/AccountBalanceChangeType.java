package com.demo.common.enums;

/**
 * 余额变动方向
 */
public enum AccountBalanceChangeType {

    INCOME(1, "收入"),
    EXPENSE(2, "支出");

    private final int code;
    private final String label;

    AccountBalanceChangeType(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }
}
