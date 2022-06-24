package org.gl.service;

import org.gl.entity.ShoppingAddress;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface ShoppingAddressService extends IService<ShoppingAddress> {

    /**
     *添加收货地址
     * @param token
     * @param consigneePhone
     * @param shoppingAddress
     * @param consignee
     * @param defaulted
     * @return 是否添加成功
     */
    Integer addShoppingAddress(String token,
                               String consigneePhone,
                               String shoppingAddress,
                               String consignee,
                               Integer defaulted);

    /**
     * 修改收货地址
     * @param shoppingAddress 收货地址对象
     * @return 是否修改成功
     */
    Integer modifyShoppingAddress(ShoppingAddress shoppingAddress);

    /**
     * 删除收货地址
     * @param token token
     * @param shoppingAddressId 收货地址主键id
     * @return 删除结果集
     */
    Map<String,Object> removeShoppingAddress(String token,Long... shoppingAddressId);

    /**
     * 获取用户收货地址
     * @param token token
     * @param defaulted 是否获取默认地址
     * @return 收货地址列表
     */
    List<ShoppingAddress> getUserShoppingAddress(String token,Integer defaulted);

    /**
     * 获取单个收货地址
     * @param shoppingAddressId 收货地址主键id
     * @return 收货地址对象
     */
    ShoppingAddress getShoppingAddressInfoById(Long shoppingAddressId);

}
