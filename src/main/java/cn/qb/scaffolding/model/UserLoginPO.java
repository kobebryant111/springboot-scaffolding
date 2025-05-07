package cn.qb.scaffolding.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 用户登录PO
 * @author: 秦博
 * @date: 2025/4/8 17:18
 */
@Data
@TableName("user_login")
public class UserLoginPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码哈希
     */
    private String passwordHash;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLogin;

    /**
     * 账户是否激活
     */
    private Boolean isActive;
} 