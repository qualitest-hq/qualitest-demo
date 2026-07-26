package com.demo.common.enums;

/**
 * 余额流水类型
 */
public enum AccountBalanceRecordType {

    RECHARGE(1, "充值"),
    GIFT(2, "赠送"),
    ORDER_PAY(3, "订单支付"),
    ORDER_REFUND(4, "订单退款");

    private final int code;
    private final String label;

    AccountBalanceRecordType(int code, String label) {
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
