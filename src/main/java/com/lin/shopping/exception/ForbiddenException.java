package com.lin.shopping.exception;

public class ForbiddenException extends BusinessException {
    private static final long serialVersionUID = 2341333583318709L;

    public ForbiddenException() {
        this("权限不足，请联系管理员");
    }

    public ForbiddenException(String code, Object... parameters) {
        super(code, BusinessError.FORBIDDEN, parameters);
    }

    public ForbiddenException(String code, Throwable throwable, Object... parameters) {
        super(code, BusinessError.FORBIDDEN, throwable, parameters);
    }
}