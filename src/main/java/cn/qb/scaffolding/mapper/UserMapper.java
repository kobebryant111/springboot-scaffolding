package cn.qb.scaffolding.mapper;

import cn.qb.scaffolding.model.UserPO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 用户 mapper
 * @author: 秦博
 * @date: 2025/4/8 17:19
 */
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {


}
