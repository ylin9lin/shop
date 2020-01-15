package com.lin.shopping.exception;

import org.springframework.http.HttpStatus;

/**
 * 业务异常枚举类，若无必要无需增加
 */
public enum BusinessError {
    /**
     * 客户端通用错误
     */
    GENERAL_CLIENT(400, HttpStatus.BAD_REQUEST),

    /**
     * 尚有关联数据错误
     */
    DATA_RELATED(400, HttpStatus.BAD_REQUEST),

    /**
     * 数据缺失错误
     */
    DATA_NOT_FOUND(400, HttpStatus.BAD_REQUEST),

    /**
     * 数据库记录部分属性冲突错误
     */
    DATA_DUPLICATE(400, HttpStatus.BAD_REQUEST),

    /**
     * 数据参数校验错误
     */
    DATA_VALIDATION_FAILED(400, HttpStatus.BAD_REQUEST),

    /**
     * 登录错误
     */
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED),

    FORBIDDEN(403, HttpStatus.FORBIDDEN),
    /**
     * 未定义错误
     */
    UNDEFINED_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR);


    /**
     * 错误编号
     */
    private long code;

    /**
     * 错误状态码
     */
    private HttpStatus httpStatus;

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    BusinessError(long code, HttpStatus httpStatus) {
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
