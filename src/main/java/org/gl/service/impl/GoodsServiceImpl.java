package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.gl.entity.Goods;
import org.gl.mapper.GoodsMapper;
import org.gl.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.service.SearchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-13
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    SearchRecordService searchRecordService;

    @Override
    public List<Goods> getHomeGoods() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.last("ORDER BY RAND() ");
        goodsMapper.selectList(wrapper);
        return goodsMapper.selectList(wrapper);
    }

    @Override
    public List getBanner() {
        List list = new ArrayList();
        list.add("http://182.92.160.192:620/banner/01.webp");
        list.add("http://182.92.160.192:620/banner/02.webp");
        list.add("http://182.92.160.192:620/banner/03.webp");
        list.add("http://182.92.160.192:620/banner/04.webp");
        return list;
    }

    @Override
    public List<Goods> goodsClassification(String goodsFirstClass, String goodsTwoClass) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("goods_first_class", goodsFirstClass == null ? "水果蔬菜" : goodsFirstClass);
        if (goodsTwoClass!=null){
            wrapper.eq("goods_two_class",goodsTwoClass);
        }
        return goodsMapper.selectList(wrapper);
    }


}
