package cn.qb.scaffolding.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description: 用户PO
 * @author: 秦博
 * @date: 2025/4/8 17:18
 */
@Data
@TableName("users")
public class UserPO {

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String name;

    private Integer age;
}
