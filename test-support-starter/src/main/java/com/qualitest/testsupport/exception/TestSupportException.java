package com.qualitest.testsupport.exception;

/**
 * test-support 业务异常，携带机器可读错误码
 */
public class TestSupportException extends RuntimeException {

    private final String code;

    public TestSupportException(String code, String message) {
        super(message);
        this.code = code;
    }

    public TestSupportException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
