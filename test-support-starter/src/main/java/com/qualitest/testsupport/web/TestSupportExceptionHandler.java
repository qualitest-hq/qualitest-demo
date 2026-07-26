package com.qualitest.testsupport.web;

import com.qualitest.testsupport.exception.TestSupportException;
import com.qualitest.testsupport.model.TestSupportErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * test-support 端点统一异常处理，将业务异常映射为 JSON 错误体与 HTTP 状态码。
 */
@RestControllerAdvice(basePackageClasses = TestSupportController.class)
public class TestSupportExceptionHandler {

    @ExceptionHandler(TestSupportException.class)
    public ResponseEntity<TestSupportErrorResponse> handleTestSupportException(TestSupportException ex) {
        HttpStatus status = mapStatus(ex.getCode());
        return ResponseEntity.status(status).body(TestSupportErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build());
    }

    /**
     * 未预期的异常统一返回 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<TestSupportErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TestSupportErrorResponse.builder()
                .code("INTERNAL_ERROR")
                .message(ex.getMessage() != null ? ex.getMessage() : "unexpected error")
                .build());
    }

    private HttpStatus mapStatus(String code) {
        return switch (code) {
            case "UNAUTHORIZED" -> HttpStatus.UNAUTHORIZED;
            case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "BAD_REQUEST" -> HttpStatus.BAD_REQUEST;
            case "TIMEOUT" -> HttpStatus.GATEWAY_TIMEOUT;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
