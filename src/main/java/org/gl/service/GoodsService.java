package org.gl.service;

import org.gl.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-13
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 首页商品列表
     * @return 商品对象列表
     */
    List<Goods> getHomeGoods();

    /**
     * 轮播
     * @return 轮播图集合
     */
    List getBanner();

    /**
     * 商品分类
     * @param goodsFirstClass 一级分类
     * @param goodsTwoClass 二级分类
     * @return 商品对象集合
     */
    List<Goods> goodsClassification(String goodsFirstClass,String goodsTwoClass);
}
