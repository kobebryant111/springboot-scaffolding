package cn.qb.scaffolding.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "更新用户请求")
public class UpdateUserRequest {
    
    @Schema(description = "用户ID")
    private String userId;
    
    @Schema(description = "用户名称")
    private String name;
    
    @Schema(description = "用户年龄")
    private Integer age;
} 