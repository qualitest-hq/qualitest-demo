package com.qualitest.testsupport.model;

import lombok.Builder;
import lombok.Value;

/**
 * test-support 端点错误响应体
 */
@Value
@Builder
public class TestSupportErrorResponse {

    /**
     * 错误码，如 UNAUTHORIZED、NOT_FOUND、TIMEOUT
     */
    String code;

    /**
     * 可读错误说明
     */
    String message;
}
