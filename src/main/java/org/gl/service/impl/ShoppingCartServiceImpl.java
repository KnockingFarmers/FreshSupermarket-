package org.gl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.entity.ShoppingCart;
import org.gl.mapper.GoodsMapper;
import org.gl.mapper.ShoppingCartMapper;
import org.gl.service.ShoppingCartService;
import org.gl.util.JwtUtil;
import org.gl.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-18
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Integer addGoodsToShoppingCart(String token, Long goodsId, Integer goodsNum) {

        Map<String, Object> map = jwtUtil.analyzeToken(token);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setGoodsId(goodsId);
        shoppingCart.setUserId((Long) map.get("userId"));
        shoppingCart.setGoodsNum(goodsNum);
        shoppingCart.setGmtCreate(new Date());
        int result = shoppingCartMapper.insert(shoppingCart);
        return result;
    }

    @Override
    public Map<String, Object> removeUserShoppingCartGoods(String token, Long... shoppingCartId) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        int result = 0;
        for (int i = 0; i < shoppingCartId.length; i++) {
            int delete = shoppingCartMapper.deleteById(shoppingCartId[i]);
            if (delete == 1) {
                result++;
            }
        }
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("successfulNumber", result);
        resultMap.put("shoppingCartList",
                shoppingCartMapper.selectUserShoppingCart((Long) map.get("userId")));
        return resultMap;
    }

    @Override
    public List<ShoppingCart> getUserShoppingCart(String token) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        return shoppingCartMapper.selectUserShoppingCart((Long) map.get("userId"));
    }
}
