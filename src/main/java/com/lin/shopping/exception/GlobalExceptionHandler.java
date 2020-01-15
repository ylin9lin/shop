package com.lin.shopping.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import java.util.List;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> process(Throwable exception) {
        LOGGER.error("exception process", exception);
        return ErrorResponse.generateResponse(
                new BusinessException("系统异常", BusinessError.UNDEFINED_ERROR, exception));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> process(BusinessException exception) {
        LOGGER.error("exception process", exception);
        return ErrorResponse.generateResponse(exception);
    }

    private ResponseEntity<ErrorResponse> generateValidResponse(BindingResult bindingResult, Exception exception) {
        if (bindingResult.getErrorCount() > 0) {
            List<ObjectError> list = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError tmp : list) {
                if (tmp instanceof FieldError) {
                    FieldError fieldError = (FieldError) tmp;
                    sb.append(fieldError.getField())
                            .append(": ")
                            .append(fieldError.getDefaultMessage())
                            .append("\n");
                }
            }
            return ErrorResponse.generateResponse(sb.toString(), BusinessError.GENERAL_CLIENT);
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.error(exception.toString());
        }
        return ErrorResponse.generateResponse(new ClientParamException("请求的数据格式错误"));

    }

    @Override
    protected ResponseEntity<Object>
    handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        ErrorResponse baseResponse = ErrorResponse.of(status.value(), ex.getMessage());
        return super.handleExceptionInternal(ex, baseResponse, headers, status, request);
    }
}


