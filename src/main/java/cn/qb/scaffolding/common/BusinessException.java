package cn.qb.scaffolding.common;

import java.io.Serial;

/**
 * @Description: 业务异常
 */
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected Integer code;
    /**
     * 错误信息
     */
    protected String message;

    public BusinessException() {
        super();
    }

    public BusinessException(IErrCode errorInfoInterface) {
        super(errorInfoInterface.getMessage());
        this.code = errorInfoInterface.getCode();
        this.message = errorInfoInterface.getMessage();
    }

    public BusinessException(IErrCode errorInfoInterface, String message) {
        super(errorInfoInterface.getMessage());
        this.code = errorInfoInterface.getCode();
        this.message = errorInfoInterface.getMessage() + message;
    }

    public BusinessException(IErrCode errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getMessage(), cause);
        this.code = errorInfoInterface.getCode();
        this.message = errorInfoInterface.getMessage();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
