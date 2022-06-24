package org.gl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gl.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
