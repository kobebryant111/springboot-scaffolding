package cn.qb.scaffolding.api;

import cn.qb.scaffolding.api.request.LoginRequest;
import cn.qb.scaffolding.api.request.RegisterRequest;
import cn.qb.scaffolding.api.request.UpdateUserRequest;
import cn.qb.scaffolding.api.response.GetUserResponse;
import cn.qb.scaffolding.api.response.LoginResponse;
import cn.qb.scaffolding.common.Response;
import cn.qb.scaffolding.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 用户api
 * @author: 秦博
 * @date: 2025/4/8 17:01
 */

@RestController
@Tag(name = "用户openapi")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "获取用户信息")
    public Response<GetUserResponse> getUserInfo(@PathVariable String userId){
        return Response.success(userService.getUser(userId));
    }

    @PutMapping("/user")
    @Operation(summary = "更新用户信息")
    public Response<GetUserResponse> updateUser(@RequestBody UpdateUserRequest request) {
        return Response.success(userService.updateUser(request));
    }

    @PostMapping("/user/login")
    @Operation(summary = "用户登录")
    public Response<LoginResponse> login(@RequestBody LoginRequest request) {
        return Response.success(userService.login(request));
    }

    @PostMapping("/user/register")
    @Operation(summary = "用户注册")
    public Response<GetUserResponse> register(@RequestBody RegisterRequest request) {
        return Response.success(userService.register(request));
    }
}
