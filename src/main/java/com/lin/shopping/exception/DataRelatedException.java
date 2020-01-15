package com.lin.shopping.exception;

public class DataRelatedException extends BusinessException {
    private static final long serialVersionUID = 2328912758348709L;

    public DataRelatedException(String code, Object... parameters) {
        super(code, BusinessError.DATA_RELATED, parameters);
    }

    public DataRelatedException(String code, Throwable throwable, Object... parameters) {
        super(code, BusinessError.DATA_RELATED, throwable, parameters);
    }
}
