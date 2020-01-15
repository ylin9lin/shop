package com.lin.shopping.exception;

public class DataNotFoundException extends BusinessException {
    private static final long serialVersionUID = 2348912758348609L;

    public DataNotFoundException(String code, Object... parameters) {
        super(code, BusinessError.DATA_NOT_FOUND, parameters);
    }

    public DataNotFoundException(String code, Throwable throwable, Object... parameters) {
        super(code, BusinessError.DATA_NOT_FOUND, throwable, parameters);
    }
}
