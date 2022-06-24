package org.gl.service;

import org.gl.entity.Goods;
import org.gl.entity.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-18
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    /**
     * 添加商品到购物车
     * @param token token
     * @param goodsId 商品主键id
     * @param goodsNum 商品数量
     * @return 是否添加成功
     */
    Integer addGoodsToShoppingCart(String token,Long goodsId,Integer goodsNum);

    /**
     * 移除用户在购物车的商品
     * @param token token
     * @param shoppingCartId 购物车主键id
     * @param shoppingCartId 商品主键id
     * @return 删除结果集
     */
    Map<String,Object> removeUserShoppingCartGoods(String token, Long... shoppingCartId);

    /**
     * 获取用户购物车商品
     * @param token token
     * @return 购物车对象列表
     */
    List<ShoppingCart> getUserShoppingCart(String token);

}
