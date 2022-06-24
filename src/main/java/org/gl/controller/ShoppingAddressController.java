package org.gl.controller;


import org.gl.entity.ShoppingAddress;
import org.gl.service.ShoppingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
@CrossOrigin
@RequestMapping("/shoppingAddress")
public class ShoppingAddressController {

    @Autowired
    private ShoppingAddressService service;

    @GetMapping("/getUserShoppingAddress")
    public List<ShoppingAddress> getUserShoppingAddress(String token,Integer defaulted){
        return service.getUserShoppingAddress(token,defaulted);
    }

    @PostMapping("/addUserShoppingAddress")
    public Integer addUserShoppingAddress(String token,
                                          String consigneePhone,
                                          String shoppingAddress,
                                          String consignee,
                                          Integer defaulted) throws UnsupportedEncodingException {

        return service.addShoppingAddress(token,consigneePhone,URLDecoder.decode(shoppingAddress, "utf-8"),URLDecoder.decode(consignee,"utf-8"),defaulted);
    }

    @PostMapping("/removeUserShoppingAddress")
    public Map<String, Object> removeUserShoppingAddress(String token, String... shoppingAddressIdStr){
        Long[] shoppingAddressId=new Long[shoppingAddressIdStr.length];
        for (int i = 0; i < shoppingAddressIdStr.length; i++) {
            shoppingAddressId[i]=Long.valueOf(shoppingAddressIdStr[i]);
        }
        return service.removeShoppingAddress(token,shoppingAddressId);
    }

    @PostMapping("/modifyUserShoppingAddress")
    public Integer getUserShoppingAddress(ShoppingAddress shoppingAddress,String shoppingAddressIdStr){
        shoppingAddress.setShoppingAddressId(Long.valueOf(shoppingAddressIdStr));
        return service.modifyShoppingAddress(shoppingAddress);
    }

    @GetMapping("/getShoppingAddressInfo")
    public ShoppingAddress getShoppingAddressInfo(String shoppingAddressIdStr){
        return service.getShoppingAddressInfoById(Long.valueOf(shoppingAddressIdStr));
    }

}

