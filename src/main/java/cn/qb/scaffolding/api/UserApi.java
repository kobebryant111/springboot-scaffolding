package cn.qb.scaffolding.api;

import cn.qb.scaffolding.api.response.GetUserResponse;
import cn.qb.scaffolding.common.Response;
import cn.qb.scaffolding.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public Response<GetUserResponse> getUserInfo(@PathVariable String userId){

        return Response.success(userService.getUser(userId));
    }

}
