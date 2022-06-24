package org.gl.controller;


import org.gl.entity.Goods;
import org.gl.entity.ShoppingCart;
import org.gl.entity.UserOrder;
import org.gl.service.UserOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-21
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class UserOrderController {

    @Autowired
    private UserOrderService orderService;

    @PostMapping("/submitOrder")
    public Map<String, Object> submitOrder(String token,
                                           BigDecimal payPrice,
                                           String shoppingAddressIdStr,
                                           Integer patented,
                                           @RequestParam(value = "shoppingCartIdStrArray",required = false) String[] shoppingCartIdStrArray,
                                           @RequestParam(value = "goodsIdStr",required = false) String goodsIdStr,
                                           @RequestParam(value = "goodsNum",required = false)Integer goodsNum){
        return orderService.insertOrder(token,
                payPrice,
                Long.valueOf(shoppingAddressIdStr),
                patented,shoppingCartIdStrArray,
                goodsIdStr==null?null:Long.valueOf(goodsIdStr),
                goodsNum);
    }

    @GetMapping("/getUserOrderList")
    public List<UserOrder> getUserOrderList(String token,String orderState){
        return orderService.getUserOrderList(token,orderState);
    }

    @PostMapping("/payment")
    public Map<String, Object> payment(String token,String orderIdStr){
        return orderService.payment(token,Long.valueOf(orderIdStr));
    }

    @GetMapping("/getUserAllOrderStateNumber")
    public Map<String, Object> payment(String token){
        return orderService.getUserAllOrderStateNumber(token);
    }

    @PostMapping("/confirmReceiptOfGoods")
    public Integer confirmReceiptOfGoods(String orderIdStr){
        return orderService.confirmReceiptOfGoods(Long.valueOf(orderIdStr));
    }

    @GetMapping("/getUserOrderInfo")
    public UserOrder getUserOrderInfo(String orderIdStr){
        return orderService.getUserOrder(Long.valueOf(orderIdStr));
    }

}

