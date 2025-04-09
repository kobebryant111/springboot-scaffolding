package cn.qb.scaffolding.common;

/**
 * @Description: 基础错误码枚举
 * @author: 秦博
 * @date: 2025/3/26 15:35
 */
public enum BaseErrCode implements IErrCode {

    SUCCESS(0, "请求成功"),
    USER_NOT_LOGIN(305, "用户未登录"),
    BODY_NOT_MATCH(400, "请求的数据格式不符"),
    SIGNATURE_NOT_MATCH(401, "请求的数字签名不匹配"),
    NOT_FOUND(404, "数据不存在"),
    STATE_ERROR(412, "状态错误"),
    PARAM_ERROR(414, "参数错误"),
    DATA_EXIST(417, "数据已存在"),
    NOT_ALLOW(495, "没有权限操作"),
    INTERNAL_SERVER_ERROR(500, "未知错误"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    SENTINEL_LIMIT(429, "触发限流，请稍后重试！");

    private final Integer code;
    private final String message;

    BaseErrCode(Integer code, String message) {
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
