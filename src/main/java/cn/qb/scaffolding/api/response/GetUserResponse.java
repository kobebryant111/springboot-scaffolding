package cn.qb.scaffolding.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description: 获取用户信息返回体
 * @author: 秦博
 * @date: 2025/4/8 17:06
 */

@Data
public class GetUserResponse {

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "用户名")
    private String name;

    @Schema(description = "年龄")
    private Integer age;
}
