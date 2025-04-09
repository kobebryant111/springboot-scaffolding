package cn.qb.scaffolding.service;

import cn.qb.scaffolding.api.response.GetUserResponse;

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
}
