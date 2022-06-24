package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.entity.OrderGoods;
import org.gl.entity.ShoppingCart;
import org.gl.entity.User;
import org.gl.entity.UserOrder;
import org.gl.mapper.OrderGoodsMapper;
import org.gl.mapper.OrderMapper;
import org.gl.mapper.ShoppingCartMapper;
import org.gl.mapper.UserMapper;
import org.gl.service.UserOrderService;
import org.gl.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-21
 */
@Service
public class UserOrderServiceImpl extends ServiceImpl<OrderMapper, UserOrder> implements UserOrderService {

    private static final String FRUITS_VEGETABLES = "水果蔬菜";
    private static final String MEAT_RGG = "肉禽蛋品";
    private static final String SEAFOOD = "海鲜水产";
    private static final String FROZEN = "速食冷冻";
    private static final String ORN = "油粮米面";
    private static final String X = "X";

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderGoodsMapper orderGoodsMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public Long createOrderNumber(Long userId, Integer shoppingCartListLength) {
        Long timestamp = Long.valueOf(System.currentTimeMillis());
        Long orderNumber = timestamp + userId + shoppingCartListLength;
        return orderNumber;
    }

    @Override
    public String crateSerialNumber(List goodsType) {
        String serialNumber = "";
        if (goodsType!=null){
            if (goodsType.contains(FRUITS_VEGETABLES)) {
                serialNumber += "S";
            } else if (goodsType.contains(MEAT_RGG)) {
                serialNumber += "R";
            } else if (goodsType.contains(SEAFOOD)) {
                serialNumber += "H";
            } else if (goodsType.contains(FROZEN)) {
                serialNumber += "K";
            } else if (goodsType.contains(ORN)) {
                serialNumber += "Y";
            } else if (goodsType.contains(X)) {
                serialNumber += "X";
            }
        }else {
            serialNumber += "X";
        }
        serialNumber += System.currentTimeMillis() + 1;

        return serialNumber;
    }

    @Override
    public Map<String, Object> insertOrder(String token,
                                           BigDecimal payPrice,
                                           Long shoppingAddressId,
                                           Integer patented,
                                           String[] shoppingCartIdStrArray,
                                           Long goodsId,
                                           Integer goodsNum) {
        Map<String, Object> resultMap = new HashMap<>(5);
        List<ShoppingCart> shoppingCartList =null;
        if (shoppingCartIdStrArray!=null&&shoppingCartIdStrArray.length>0){
            shoppingCartList =new ArrayList<>();
            for (int i = 0; i < shoppingCartIdStrArray.length; i++) {
                shoppingCartList.add(shoppingCartMapper.selectUserSubmitGoods(Long.valueOf(shoppingCartIdStrArray[i])));
            }
        }

        //生成订单对象
        Map<String, Object> userMap = jwtUtil.analyzeToken(token);
        UserOrder order = new UserOrder();
        Long orderNumber ;
        if (shoppingCartList!=null){
            orderNumber = createOrderNumber((Long) userMap.get("userId"), shoppingCartList.size());
        }else {
            orderNumber = createOrderNumber((Long) userMap.get("userId"), goodsNum);
        }
        order.setGmtCreate(new Date());
        order.setOrderNumber(orderNumber);
        order.setUserId((Long) userMap.get("userId"));
        order.setPaymentMethod("生鲜钱包");
        order.setShoppingAddressId(shoppingAddressId);

        if (shoppingCartList!=null){
            List<String> goodsType = new ArrayList();
            shoppingCartList.forEach(shoppingCart -> {
                goodsType.add(shoppingCart.getGoods().getGoodsFirstClass() == "" ?
                        "X" : shoppingCart.getGoods().getGoodsFirstClass());
            });
            order.setSerialNumber(crateSerialNumber(goodsType));
        }else {
            order.setSerialNumber(crateSerialNumber(null));
        }

        //判断用户是否支付
        User user = userMapper.selectById((Long) userMap.get("userId"));
        if (patented != 1) {
            order.setOrderStatus("待支付");
        } else if (user.getUserBalance().subtract(payPrice).compareTo(BigDecimal.ZERO) >= 0) {
            user.setUserBalance(user.getUserBalance().subtract(payPrice));
            int update = userMapper.updateById(user);

            if (update == 1) {
                resultMap.put("payment", true);
            } else {
                resultMap.put("error", "服务器异常,请重试");
            }
            order.setOrderStatus("待发货");
        } else {
            order.setOrderStatus("待支付");
            resultMap.put("error", "余额不足");
        }
        order.setOrderAmount(payPrice);
        int result = orderMapper.insert(order);
        resultMap.put("insert", result);

        //向订单商品表插入该订单所对应的商品并删除购物车提交的商品
        UserOrder userOrder = orderMapper.selectById(order.getOrderId());
        if (shoppingCartList!=null) {
            shoppingCartList.forEach(shoppingCart -> {
                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setGoodsId(Long.valueOf(shoppingCart.getGoods().getGoodsIdStr()));
                orderGoods.setOrderId(userOrder.getOrderId());
                orderGoods.setNum(shoppingCart.getGoodsNum());
                orderGoodsMapper.insert(orderGoods);

                shoppingCartMapper.deleteById(Long.valueOf(shoppingCart.getShoppingCartIdStr()));

            });
        } else {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setGoodsId(goodsId);
            orderGoods.setOrderId(userOrder.getOrderId());
            orderGoods.setNum(goodsNum);
            orderGoodsMapper.insert(orderGoods);
        }

        resultMap.put("order",userOrder);

        return resultMap;

    }

    @Override
    public List<UserOrder> getUserOrderList(String token, String orderState) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);

        return orderMapper.selectAllByUserIdAfter((Long) map.get("userId"), orderState == null ? "" : orderState);
    }

    @Override
    public Map<String, Object> payment(String token, Long orderId) {
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> userMap = jwtUtil.analyzeToken(token);
        User user = userMapper.selectById((Long) userMap.get("userId"));
        UserOrder userOrder = orderMapper.selectById(orderId);
        try {
            if (user.getUserBalance().subtract(userOrder.getOrderAmount()).compareTo(BigDecimal.ZERO) >= 0) {
                user.setUserBalance(user.getUserBalance().subtract(userOrder.getOrderAmount()));
                userMapper.updateById(user);
                userOrder.setOrderStatus("待发货");
                orderMapper.updateById(userOrder);
                resultMap.put("paymentSuccessful", true);
            }
        } catch (Exception e) {
            resultMap.put("error", "服务器异常");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getUserAllOrderStateNumber(String token) {
        String[] orderStatus = {
                "待支付",
                "待发货",
                "待收货",
                "待评价",
        };

        String[] orderStatusVariable = {
                "toBePaid",
                "toBeDelivered",
                "toBeReceived",
                "toBeEvaluated",
        };
        Map<String, Object> resultMap = new HashMap<>(orderStatus.length);
        Map<String, Object> userMap = jwtUtil.analyzeToken(token);

        for (int i = 0; i < orderStatus.length; i++) {
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("user_id", userMap.get("userId"));
            wrapper.eq("order_status", orderStatus[i]);
            Integer integer = orderMapper.selectCount(wrapper);
            resultMap.put(orderStatusVariable[i], integer);
        }

        return resultMap;
    }

    @Override
    public Integer confirmReceiptOfGoods(Long orderId) {
        UserOrder userOrder = orderMapper.selectById(orderId);
            userOrder.setOrderStatus("待评价");

        return orderMapper.updateById(userOrder);
    }

    @Override
    public UserOrder getUserOrder(Long orderId) {
        return orderMapper.selectByOrderId(orderId);
    }


}
