package cn.qb.scaffolding.common;

/**
 * 业务异常
 * @author: 秦博
 * @date: 2025/3/26 15:37
 */
public enum BusinessErrCode implements IErrCode {

    ID_ERROR(1000, "Id上送有误"),
    
    // 登录相关错误码
    USERNAME_OR_PASSWORD_EMPTY(1001, "用户名或密码不能为空"),
    USER_NOT_FOUND(1002, "用户名不存在"),
    PASSWORD_ERROR(1003, "密码错误"),
    USER_DISABLED(1004, "账户已被禁用"),
    
    // 注册相关错误码
    USERNAME_ALREADY_EXISTS(1005, "用户名已存在"),
    PASSWORD_NOT_MATCH(1006, "两次输入的密码不一致"),
    EMAIL_ALREADY_EXISTS(1007, "邮箱已被注册"),
    INVALID_EMAIL_FORMAT(1008, "邮箱格式不正确"),
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
