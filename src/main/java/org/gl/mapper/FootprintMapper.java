package org.gl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.gl.entity.Footprint;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.gl.entity.Goods;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-13
 */
@Mapper
public interface FootprintMapper extends BaseMapper<Footprint> {


    /**
     * 查询用户足迹
     * @param userId 用户ID
     * @return 足迹列表
     */
    List<Footprint> selectUserFootprint(@Param("userId")Long userId);
}
