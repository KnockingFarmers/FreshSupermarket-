package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.entity.Collect;
import org.gl.entity.Goods;
import org.gl.entity.User;
import org.gl.mapper.CollectMapper;
import org.gl.mapper.GoodsMapper;
import org.gl.service.CollectService;
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
 * @since 2022-03-21
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Integer addCollect(String token, Long goodsId) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", map.get("userId"));
        wrapper.eq("goods_id", goodsId);
        if (collectMapper.selectCount(wrapper)==0) {
            Collect collect = new Collect();
            collect.setGoodsId(goodsId);
            collect.setUserId((Long) map.get("userId"));
            collect.setGmtCreate(new Date());
            return collectMapper.insert(collect);
        }
        return 0;
    }

    @Override
    public Map<String, Object> removeCollect(String token, Long... collectId) {
        int result = 0;
        for (int i = 0; i < collectId.length; i++) {
            int delete = collectMapper.deleteById(collectId[i]);
            if (delete == 1) {
                result++;
            }
        }
        Map<String, Object> userMap = jwtUtil.analyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", userMap.get("userId"));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("deleteNum", result);
        resultMap.put("collectList", collectMapper.selectList(wrapper));

        return resultMap;
    }

    @Override
    public List<Collect> getCollectList(String token) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        return collectMapper.selectUserCollect((Long) map.get("userId"));
    }

    @Override
    public String userCollected(String token, Long goodsId) {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", map.get("userId"));
        wrapper.eq("goods_id", goodsId);
        Collect collect = collectMapper.selectOne(wrapper);

        return collect == null ? "" : collect.getCollectIdStr();
    }
}
