package com.lin.shopping.exception;

public class DataDulplicateException extends BusinessException {
    private static final long serialVersionUID = 2348912758318709L;

    public DataDulplicateException(String code, Object... parameters) {
        super(code, BusinessError.DATA_DUPLICATE, parameters);
    }

    public DataDulplicateException(String code, Throwable throwable, Object... parameters) {
        super(code, BusinessError.DATA_DUPLICATE, throwable, parameters);
    }
}