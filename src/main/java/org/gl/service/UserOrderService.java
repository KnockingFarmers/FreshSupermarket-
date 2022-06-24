package org.gl.service;

import org.gl.entity.Goods;
import org.gl.entity.UserOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gl.entity.ShoppingCart;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-21
 */
public interface UserOrderService extends IService<UserOrder> {


    /**
     * 生成订单号
     * @param userId 用户ID
     * @param shoppingCartListLength 购买商品的长度
     * @return 订单号
     */
    Long createOrderNumber(Long userId,Integer shoppingCartListLength);

    /**
     * 生成流水号
     * @param goodsType 购买商品的类型
     * @return 流水号
     */
    String crateSerialNumber(List goodsType);

    /**
     * 生成订单数据
     * @param token token
     * @param shoppingCartIdStrArray 购物车ID数组
     * @param payPrice 付款金额
     * @param patented 是否确认付款
     * @param shoppingAddressId 收货地址id
     * @param goodsId 商品ID
     * @param goodsNum 商品数量
     * @return 支付成功的数据
     */
    Map<String,Object> insertOrder(String token,
                                   BigDecimal payPrice,
                                   Long shoppingAddressId,
                                   Integer patented,
                                   String[] shoppingCartIdStrArray,
                                   Long goodsId,
                                   Integer goodsNum);

    /**
     * 获取用户订单
     * @param token token
     * @param orderState 获取用户哪种状态的订单
     * @return 订单集合
     */
    List<UserOrder> getUserOrderList(String token, String orderState);

    /**
     * 付款
     * @param token token
     * @param orderId 订单对象
     * @return 支付成功数据
     */
    Map<String, Object>  payment(String token,Long orderId);

    /**
     * 获取用户订单剩余处理数量
     * @param token token
     * @return 4个状态的数据
     */
    Map<String, Object> getUserAllOrderStateNumber(String token );

    /**
     * 确认收货
     * @param orderId 订单ID
     * @return 是否确认收货成功
     */
    Integer confirmReceiptOfGoods(Long orderId);

    /**
     * 获取一个订单的详情数据
     * @param orderId 订单ID
     * @return 订单对象
     */
    UserOrder getUserOrder(Long orderId);

}
