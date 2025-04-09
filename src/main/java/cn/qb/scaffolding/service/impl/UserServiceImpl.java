package cn.qb.scaffolding.service.impl;

import cn.qb.scaffolding.api.response.GetUserResponse;
import cn.qb.scaffolding.convert.UserConvert;
import cn.qb.scaffolding.mapper.UserMapper;
import cn.qb.scaffolding.model.UserPO;
import cn.qb.scaffolding.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 用户服务实现类
 * @author: 秦博
 * @date: 2025/4/8 17:09
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public GetUserResponse getUser(String userId){

        //数据库查询
        UserPO userPO = userMapper.selectById(userId);
        //转换并返回
        return UserConvert.INSTANCE.po2Response(userPO);
    }

}
