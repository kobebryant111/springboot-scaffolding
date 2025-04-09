package cn.qb.scaffolding.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description: 获取用户信息请求体
 * @author: 秦博
 * @date: 2025/4/8 17:07
 */

@Data
public class GetUserRequest {

    @Schema(description = "用户ID")
    private String userId;
}
