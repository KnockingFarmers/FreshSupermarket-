package org.gl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.gl.entity.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gl.entity.Goods;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-21
 */
@Mapper
public interface CollectMapper extends BaseMapper<Collect> {

    /**
     * 获取用户收藏
     * @param userId 用户ID
     * @return 收藏对象列表
     */
    List<Collect> selectUserCollect(@Param("userId") Long userId);
}
