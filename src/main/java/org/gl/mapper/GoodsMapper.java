package org.gl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gl.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-13
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}
