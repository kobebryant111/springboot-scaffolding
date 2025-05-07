package cn.qb.scaffolding.service;

import cn.qb.scaffolding.api.request.LoginRequest;
import cn.qb.scaffolding.api.request.RegisterRequest;
import cn.qb.scaffolding.api.request.UpdateUserRequest;
import cn.qb.scaffolding.api.response.GetUserResponse;
import cn.qb.scaffolding.api.response.LoginResponse;

/**
 * @Description: 用户服务接口
 * @author: 秦博
 * @date: 2025/4/8 17:09
 */

public interface UserService {

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    GetUserResponse getUser(String userId);

    /**
     * 更新用户信息
     *
     * @param request 更新用户请求
     * @return 更新后的用户信息
     */
    GetUserResponse updateUser(UpdateUserRequest request);

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @return 登录响应
     */
    LoginResponse login(LoginRequest request);

    /**
     * 用户注册
     *
     * @param request 注册请求
     * @return 注册成功后的用户信息
     */
    GetUserResponse register(RegisterRequest request);
}
