package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.gl.entity.Goods;
import org.gl.entity.SearchRecord;
import org.gl.entity.User;
import org.gl.mapper.GoodsMapper;
import org.gl.mapper.SearchRecordMapper;
import org.gl.service.SearchRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.util.JwtUtil;
import org.gl.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-16
 */
@Service
public class SearchRecordServiceImpl extends ServiceImpl<SearchRecordMapper, SearchRecord> implements SearchRecordService {

    @Autowired
    private SearchRecordMapper searchRecordMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Integer addSearchRecord(String searchText,Long userId) {
        SearchRecord searchRecord = new SearchRecord();
        searchRecord.setSearchText(searchText);
        searchRecord.setUserId(userId);
        searchRecord.setGmtCreate(new Date());
        int insertDB = searchRecordMapper.insert(searchRecord);
        long insertRedis = redisUtil.sSet(userId.toString(), searchText);
        return 1;
    }

    @Override
    public List<Goods> searchGoods(String keyword, String token) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("goods_name", keyword);
        wrapper.or();
        wrapper.like("goods_first_class", keyword);
        wrapper.or();
        wrapper.like("goods_two_class", keyword);
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        addSearchRecord(keyword, (Long) map.get("userId"));

        return goodsMapper.selectList(wrapper);
    }

    @Override
    public List getUserSearchRecord(String token) {
        List searchRecordList = null;
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        Set<Object> searchRecordSet = redisUtil.sGet(map.get("userId").toString());
        if (searchRecordSet != null) {
            searchRecordList = new ArrayList(searchRecordSet);
        }else {
            QueryWrapper wrapper=new QueryWrapper();
            wrapper.eq("user_id",map.get("userId"));
            wrapper.select("DISTINCT search_text");
            searchRecordList=searchRecordMapper.selectList(wrapper);
        }

        return searchRecordList;
    }

    @Override
    public List getHotSearch() {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.select("search_text , count(*) AS count")
                .groupBy("search_text");
        wrapper.orderByDesc("count");
        return searchRecordMapper.selectList(wrapper);
    }
}
