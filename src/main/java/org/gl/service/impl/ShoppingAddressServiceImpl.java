package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.entity.ShoppingAddress;
import org.gl.mapper.ShoppingAddressMapper;
import org.gl.service.ShoppingAddressService;
import org.gl.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-21
 */
@Service
public class ShoppingAddressServiceImpl extends ServiceImpl<ShoppingAddressMapper, ShoppingAddress> implements ShoppingAddressService {

    @Autowired
    private ShoppingAddressMapper shoppingAddressMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Integer addShoppingAddress(String token,
                                      String consigneePhone,
                                      String shoppingAddress,
                                      String consignee,
                                      Integer defaulted) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        ShoppingAddress shoppingAddressObj = new ShoppingAddress();
        shoppingAddressObj.setUserId((Long) map.get("userId"));
        shoppingAddressObj.setConsigneePhone(consigneePhone);
        shoppingAddressObj.setShoppingAddress(shoppingAddress);
        shoppingAddressObj.setConsignee(consignee);
        shoppingAddressObj.setGmtCreate(new Date());
        if (defaulted==1){
            QueryWrapper wrapper=new QueryWrapper();
            wrapper.eq("user_id",map.get("userId"));
            List<ShoppingAddress> list = shoppingAddressMapper.selectList(wrapper);
            list.forEach(address->{
                address.setDefaulted(0);
                shoppingAddressMapper.updateById(address);
            });
            shoppingAddressObj.setDefaulted(defaulted);
        }

        return shoppingAddressMapper.insert(shoppingAddressObj);
    }

    @Override
    public Integer modifyShoppingAddress(ShoppingAddress shoppingAddress) {
        return shoppingAddressMapper.updateById(shoppingAddress);
    }

    @Override
    public Map<String,Object> removeShoppingAddress(String token,Long... shoppingAddressId) {
        int result=0;
        for (int i = 0; i < shoppingAddressId.length; i++) {
            int delete = shoppingAddressMapper.deleteById(shoppingAddressId[i]);
            if (delete==1) {
                result++;
            }
        }
        Map<String, Object> userMap = jwtUtil.analyzeToken(token);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",userMap.get("userId"));
        Map<String,Object> resultMap=new HashMap<>(2);
        resultMap.put("deleteNum",result);
        resultMap.put("shoppingAddressList",shoppingAddressMapper.selectList(wrapper));
        return resultMap;
    }

    @Override
    public List<ShoppingAddress> getUserShoppingAddress(String token,Integer defaulted) {
        Map<String,Object> userMap=jwtUtil.analyzeToken(token);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id", userMap.get("userId"));
        wrapper.orderByDesc("defaulted");
        if (defaulted==1){
            wrapper.eq("defaulted",1);
        }
        return shoppingAddressMapper.selectList(wrapper);
    }

    @Override
    public ShoppingAddress getShoppingAddressInfoById(Long shoppingAddressId) {
        return shoppingAddressMapper.selectById(shoppingAddressId);
    }
}
