package org.gl.service;

import org.gl.entity.Goods;
import org.gl.entity.SearchRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-16
 */
public interface SearchRecordService extends IService<SearchRecord> {

    /**
     * 添加搜索记录
     * @param searchText 搜索文本
     * @param userId 用户主键id
     * @return 是否添加成功
     */
    Integer addSearchRecord(String searchText,Long userId);

    /**
     * 搜索商品
     * @param keyword 关键词
     * @param token 用户主键id
     * @return 商品列表
     */
    List<Goods> searchGoods(String keyword,String token);

    /**
     * 获取用户搜索记录
     * @param token 用户主键id
     * @return 搜索记录列表
     */
    List getUserSearchRecord(String token);

    /**
     * 获取热搜
     * @return 热搜列表
     */
    List getHotSearch();
}
