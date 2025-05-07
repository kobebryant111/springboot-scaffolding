package cn.qb.scaffolding.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户登录响应")
public class LoginResponse {
    
    @Schema(description = "用户ID")
    private String userId;
    
    @Schema(description = "用户名")
    private String username;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "访问令牌")
    private String token;
} 