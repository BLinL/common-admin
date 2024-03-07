package com.example.spst.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlbExHandler extends ResponseEntityExceptionHandler {


    /**
     * Spring Security 核心异常（如 AuthenticationException 和 AccessDeniedException）属于运行时异常。
     * 由于这些异常是由 DispatcherServlet 后面的 Authentication Filter 在调用 Controller 方法之前抛出的，
     * 因此 @ControllerAdvice 无法捕获这些异常。
     * @param authenticationException
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Map<String, Object>> handleException(Exception authenticationException) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("code", 1,
                        "message", authenticationException.getLocalizedMessage()));
    }
}
