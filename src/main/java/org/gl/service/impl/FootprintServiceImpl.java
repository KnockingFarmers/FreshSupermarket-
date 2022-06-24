package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.gl.entity.Footprint;
import org.gl.entity.Goods;
import org.gl.entity.User;
import org.gl.mapper.FootprintMapper;
import org.gl.mapper.GoodsMapper;
import org.gl.service.FootprintService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-13
 */
@Service
public class FootprintServiceImpl extends ServiceImpl<FootprintMapper, Footprint> implements FootprintService {

    @Autowired
    private FootprintMapper footprintMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Integer addFootprint(String token, Long goodsId) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",map.get("userId"));
        wrapper.eq("goods_id",goodsId);
        if (footprintMapper.selectCount(wrapper)==0){

            Footprint footprint = new Footprint();
            footprint.setGmtCreate(new Date());
            footprint.setUserId((Long) map.get("userId"));
            footprint.setGoodsId(goodsId);
            return footprintMapper.insert(footprint);
        }
        return 0;

    }

    @Override
    public Map<String, Object>  removeFootprint(String token,Long... footprintId) {
        int result = 0;
        for (int i = 0; i < footprintId.length; i++) {
            int delete = footprintMapper.deleteById(footprintId[i]);
            if (delete == 1) {
                result++;
            }
        }
        Map<String, Object> userMap = jwtUtil.analyzeToken(token);
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("user_id",userMap.get("userId"));

        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("deleteNum",result);
        resultMap.put("footprintList",footprintMapper.selectList(wrapper));

        return resultMap;
    }

    @Override
    public List<Footprint> getUserFootprint(String token) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        return footprintMapper.selectUserFootprint((Long) map.get("userId"));
    }
}
