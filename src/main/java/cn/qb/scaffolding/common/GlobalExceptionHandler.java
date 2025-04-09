package cn.qb.scaffolding.common;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;

/**
 * 全局异常过滤器
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常，返回自定义的业务码，码值1000以上
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Response<Void> bizExceptionHandler(HttpServletRequest req, BusinessException e) {
        log.error("发生业务异常！原因是：", e);
        return Response.error(e.getCode() == null ? -1 : e.getCode(), e.getMessage());
    }

    /**
     * dto参数校验，针对的是使用@Valid或@Validated注解，返回400
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Response<Void> methodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String defaultMessage = Objects.requireNonNull(fieldError).getDefaultMessage();
            log.error("参数校验失败！原因是:", ex);
            return Response.error(BaseErrCode.BODY_NOT_MATCH.getCode(), fieldError.getField() + ":" + defaultMessage);
        } else {
            log.error("参数校验失败！原因是:", ex);
            return Response.error(BaseErrCode.BODY_NOT_MATCH.getCode(), BaseErrCode.BODY_NOT_MATCH.getMessage());
        }
    }

    /**
     * @RequestParam 请求参数未传递时的抛出该错，返回400
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    @ResponseBody
    public Response<Void> exceptionHandler(HttpServletRequest req, MissingServletRequestParameterException e) {
        log.error("参数异常！原因是:", e);
        return Response.error(BaseErrCode.BODY_NOT_MATCH.getCode(), e.getParameterName() + "未传入");
    }

    /**
     * 限流
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = UndeclaredThrowableException.class)
    @ResponseBody
    public Response<Void> exceptionHandler(HttpServletRequest req, UndeclaredThrowableException e){

        log.error("触发限流");
        return Response.error(BaseErrCode.SENTINEL_LIMIT.getCode(), BaseErrCode.SENTINEL_LIMIT.getMessage());
    }

    /**
     * 兜底异常，返回500
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response<Void> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("未知异常！原因是:", e);
        return Response.error(BaseErrCode.INTERNAL_SERVER_ERROR.getCode(), BaseErrCode.INTERNAL_SERVER_ERROR.getMessage());
    }
}