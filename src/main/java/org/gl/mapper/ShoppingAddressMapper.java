package org.gl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gl.entity.ShoppingAddress;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
public interface ShoppingAddressMapper extends BaseMapper<ShoppingAddress> {

}
