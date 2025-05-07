package cn.qb.scaffolding.service.impl;

import cn.qb.scaffolding.api.request.LoginRequest;
import cn.qb.scaffolding.api.request.RegisterRequest;
import cn.qb.scaffolding.api.request.UpdateUserRequest;
import cn.qb.scaffolding.api.response.GetUserResponse;
import cn.qb.scaffolding.api.response.LoginResponse;
import cn.qb.scaffolding.common.BusinessErrCode;
import cn.qb.scaffolding.common.BusinessException;
import cn.qb.scaffolding.convert.UserConvert;
import cn.qb.scaffolding.mapper.UserLoginMapper;
import cn.qb.scaffolding.mapper.UserMapper;
import cn.qb.scaffolding.mapper.UserTokenMapper;
import cn.qb.scaffolding.model.UserLoginPO;
import cn.qb.scaffolding.model.UserPO;
import cn.qb.scaffolding.model.UserTokenPO;
import cn.qb.scaffolding.service.UserService;
import cn.qb.scaffolding.utils.JwtUtil;
import cn.qb.scaffolding.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @Description: 用户服务实现类
 * @author: 秦博
 * @date: 2025/4/8 17:09
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserLoginMapper userLoginMapper;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public GetUserResponse getUser(String userId){
        //数据库查询
        UserPO userPO = userMapper.selectById(userId);
        //转换并返回
        return UserConvert.INSTANCE.po2Response(userPO);
    }

    @Override
    public GetUserResponse updateUser(UpdateUserRequest request) {
        // 查询用户是否存在
        UserPO userPO = userMapper.selectById(request.getUserId());
        if (userPO == null) {
            throw new BusinessException(BusinessErrCode.ID_ERROR);
        }

        // 更新用户信息
        userPO.setName(request.getName());
        userPO.setAge(request.getAge());
        userMapper.updateById(userPO);

        // 返回更新后的用户信息
        return UserConvert.INSTANCE.po2Response(userPO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginResponse login(LoginRequest request) {
        // 参数校验
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new BusinessException(BusinessErrCode.USERNAME_OR_PASSWORD_EMPTY);
        }

        // 查询用户
        LambdaQueryWrapper<UserLoginPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserLoginPO::getUsername, request.getUsername());
        UserLoginPO userLoginPO = userLoginMapper.selectOne(queryWrapper);

        // 用户不存在
        if (userLoginPO == null) {
            throw new BusinessException(BusinessErrCode.USER_NOT_FOUND);
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), userLoginPO.getPasswordHash())) {
            throw new BusinessException(BusinessErrCode.PASSWORD_ERROR);
        }

        // 检查用户状态
        if (!userLoginPO.getIsActive()) {
            throw new BusinessException(BusinessErrCode.USER_DISABLED);
        }

        // 更新最后登录时间
        userLoginPO.setLastLogin(LocalDateTime.now());
        userLoginMapper.updateById(userLoginPO);

        // 生成token
        String token = jwtUtil.generateToken(userLoginPO.getUsername());
        LocalDateTime expireTime = LocalDateTime.now().plusHours(24); // token 24小时后过期

        // 清理过期的token
        LambdaQueryWrapper<UserTokenPO> expireQuery = new LambdaQueryWrapper<>();
        expireQuery.lt(UserTokenPO::getExpireTime, LocalDateTime.now());
        userTokenMapper.delete(expireQuery);

        // 保存新token到数据库
        UserTokenPO userTokenPO = new UserTokenPO();
        userTokenPO.setUserId(userLoginPO.getId());
        userTokenPO.setUsername(userLoginPO.getUsername());
        userTokenPO.setToken(token);
        userTokenPO.setExpireTime(expireTime);
        userTokenPO.setCreateTime(LocalDateTime.now());
        userTokenPO.setUpdateTime(LocalDateTime.now());
        userTokenMapper.insert(userTokenPO);

        // 保存token到Redis
        redisUtil.storeToken(token, userLoginPO.getId());

        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setUserId(userLoginPO.getId().toString());
        response.setUsername(userLoginPO.getUsername());
        response.setEmail(userLoginPO.getEmail());
        response.setToken(token);

        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GetUserResponse register(RegisterRequest request) {
        // 参数校验
        if (request.getUsername() == null || request.getPassword() == null || request.getConfirmPassword() == null) {
            throw new BusinessException(BusinessErrCode.USERNAME_OR_PASSWORD_EMPTY);
        }

        // 验证两次密码是否一致
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException(BusinessErrCode.PASSWORD_NOT_MATCH);
        }

        // 验证邮箱格式
        if (!Pattern.matches(EMAIL_PATTERN, request.getEmail())) {
            throw new BusinessException(BusinessErrCode.INVALID_EMAIL_FORMAT);
        }

        // 检查用户名是否已存在
        LambdaQueryWrapper<UserLoginPO> usernameQuery = new LambdaQueryWrapper<>();
        usernameQuery.eq(UserLoginPO::getUsername, request.getUsername());
        if (userLoginMapper.selectCount(usernameQuery) > 0) {
            throw new BusinessException(BusinessErrCode.USERNAME_ALREADY_EXISTS);
        }

        // 检查邮箱是否已被注册
        LambdaQueryWrapper<UserLoginPO> emailQuery = new LambdaQueryWrapper<>();
        emailQuery.eq(UserLoginPO::getEmail, request.getEmail());
        if (userLoginMapper.selectCount(emailQuery) > 0) {
            throw new BusinessException(BusinessErrCode.EMAIL_ALREADY_EXISTS);
        }

        // 创建用户基本信息
        UserPO userPO = new UserPO();
        userPO.setName(request.getUsername());
        userPO.setAge(request.getAge());
        userMapper.insert(userPO);

        // 创建用户登录信息
        UserLoginPO userLoginPO = new UserLoginPO();
        userLoginPO.setUsername(request.getUsername());
        userLoginPO.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userLoginPO.setEmail(request.getEmail());
        userLoginPO.setIsActive(true);
        userLoginPO.setLastLogin(LocalDateTime.now());
        userLoginMapper.insert(userLoginPO);

        // 返回用户信息
        return UserConvert.INSTANCE.po2Response(userPO);
    }
}
