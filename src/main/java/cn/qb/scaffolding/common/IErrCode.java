package cn.qb.scaffolding.common;

/**
 * @Description: 错误码接口
 * @author: 秦博
 * @date: 2025/3/26 15:03
 */
public interface IErrCode {

    /**
     * 错误码
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * 错误信息
     *
     * @return String
     */
    String getMessage();
}
