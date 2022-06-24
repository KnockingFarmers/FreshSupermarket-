package org.gl.controller;


import org.gl.entity.Goods;
import org.gl.entity.ShoppingCart;
import org.gl.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-18
 */
@CrossOrigin
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    @PostMapping("/addGoodsToShoppingCart")
    public Integer addGoodsToShoppingCart(String token, String goodsIdStr,Integer goodsNum) {
        return cartService.addGoodsToShoppingCart(token, Long.valueOf(goodsIdStr),goodsNum);
    }

    @PostMapping("/removeUserShoppingCartGoods")
    public Map<String, Object> removeUserShoppingCartGoods(String token,String... shoppingCartIdStr) {

        Long[] shoppingCartId=new Long[shoppingCartIdStr.length];
        for (int i = 0; i < shoppingCartIdStr.length; i++) {
            shoppingCartId[i]=Long.valueOf(shoppingCartIdStr[i]);
        }
        return cartService.removeUserShoppingCartGoods(token,shoppingCartId);
    }

    @GetMapping("/getUserShoppingCart")
    public List<ShoppingCart> getUserShoppingCart(String token){
        return cartService.getUserShoppingCart(token);
    }


}

