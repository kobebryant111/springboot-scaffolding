package cn.qb.scaffolding.convert;

import cn.qb.scaffolding.api.response.GetUserResponse;
import cn.qb.scaffolding.model.UserPO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Description: 用户转换类
 * @author: 秦博
 * @date: 2025/4/8 17:26
 */

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * 用户PO转用户响应
     *
     * @param userPO
     * @return
     */
    GetUserResponse po2Response(UserPO userPO);

}
