package cn.qb.scaffolding.common;

/**
 * 业务异常
 * @author: 秦博
 * @date: 2025/3/26 15:37
 */
public enum BusinessErrCode implements IErrCode {

    ID_ERROR(1000, "Id上送有误"),
    ;

    private final Integer code;
    private final String message;

    BusinessErrCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
