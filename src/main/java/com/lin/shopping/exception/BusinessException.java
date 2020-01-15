package com.lin.shopping.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 2348912758348709L;
    private final transient Object[] parameters;

    private String messageCode;

    private BusinessError error;

    public BusinessException(String messageCode, Object... parameters) {
        super(messageCode);
        this.parameters = parameters;
        this.messageCode = messageCode;
        this.error = BusinessError.UNDEFINED_ERROR;
    }

    public BusinessException(String messageCode, BusinessError error, Object... parameters) {
        super(messageCode);
        this.parameters = parameters;
        this.messageCode = messageCode;
        this.error = error;
    }

    public BusinessException(String messageCode, Throwable cause, Object... parameters) {
        this(messageCode, cause, BusinessError.UNDEFINED_ERROR, parameters);
    }

    public BusinessException(String messageCode, Throwable cause, BusinessError error, Object... parameters) {
        super(messageCode, cause);
        this.parameters = parameters;
        this.messageCode = messageCode;
        this.error = error;
    }

    public BusinessException(Throwable cause) {
        this(cause.getMessage(), cause, BusinessError.UNDEFINED_ERROR);
    }

    public Object[] getParameters() {
        return parameters;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public BusinessError getError() {
        return error;
    }

    public String getTrace() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        this.printStackTrace(ps);
        ps.flush();
        return new String(baos.toByteArray());
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("messageCode", messageCode);
        map.put("message", super.getMessage());
        return map;
    }
}
