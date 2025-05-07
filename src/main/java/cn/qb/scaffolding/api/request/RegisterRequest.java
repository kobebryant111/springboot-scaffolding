package cn.qb.scaffolding.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户注册请求")
public class RegisterRequest {
    
    @Schema(description = "用户名", required = true)
    private String username;
    
    @Schema(description = "密码", required = true)
    private String password;
    
    @Schema(description = "确认密码", required = true)
    private String confirmPassword;
    
    @Schema(description = "邮箱", required = true)
    private String email;
    
    @Schema(description = "用户年龄")
    private Integer age;
} 