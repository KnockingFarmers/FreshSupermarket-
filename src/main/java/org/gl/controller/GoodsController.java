package org.gl.controller;


import org.gl.entity.Comment;
import org.gl.entity.CommentImages;
import org.gl.entity.Goods;
import org.gl.entity.User;
import org.gl.mapper.CommentImagesMapper;
import org.gl.mapper.CommentMapper;
import org.gl.mapper.GoodsMapper;
import org.gl.mapper.UserMapper;
import org.gl.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-13
 */
@RestController
@CrossOrigin
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @GetMapping("/getHomeGoods")
    public List<Goods> getHomeGoods(){
        return goodsService.getHomeGoods();
    }


    @GetMapping("/banner")
    public List banner(){
        return goodsService.getBanner();
    }

    @GetMapping("/goodsClassification")
    public List<Goods> goodsClassification(String goodsFirstClass,String goodsTwoClass){
        return goodsService.goodsClassification(goodsFirstClass,goodsTwoClass);
    }


}

