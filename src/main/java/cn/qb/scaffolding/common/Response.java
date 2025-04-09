package cn.qb.scaffolding.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用返回
 */
@Data
@NoArgsConstructor
public class Response<T> implements Serializable {

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "响应状态码")
    private Integer code;

    @Schema(description = "响应状态信息")
    private String message;

    public Response(Integer code, String message, T data) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public static <T> Response<T> errCode(IErrCode errCode) {
        return new Response<>(errCode.getCode(), errCode.getMessage(), null);
    }

    public static <T> Response<T> success() {
        return new Response<>(0, "ok", null);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(0, "ok", data);
    }

    public static <T> Response<T> success(String message) {
        return new Response<>(0, message, null);
    }

    public static <T> Response<T> success(String message, T data) {
        return new Response<>(0, message, data);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(500, message, null);
    }

    public static <T> Response<T> error(IErrCode errCode) {
        return new Response<>(errCode.getCode(), errCode.getMessage(), null);
    }

    public static <T> Response<T> error(Integer code, String message) {
        return new Response<>(code, message, null);
    }

    public static <T> Response<T> error(Integer code) {
        return new Response<>(code, "error", null);
    }

}
