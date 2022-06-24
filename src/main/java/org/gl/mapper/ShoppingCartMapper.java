package org.gl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.gl.entity.Goods;
import org.gl.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-18
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    /**
     * 查询用户购物车商品
     * @param userId 用户主键
     * @return 购物车对象列表
     */
     List<ShoppingCart> selectUserShoppingCart(@Param("userId") Long userId);

    /**
     * 查询用户在购物车提交的商品
     * @param shoppingCartId 购物车主键id
     * @return 购物车对象
     */
     ShoppingCart selectUserSubmitGoods(@Param("shoppingCartId") Long shoppingCartId);
}
