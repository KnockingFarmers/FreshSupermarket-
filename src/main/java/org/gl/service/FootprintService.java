package org.gl.service;

import org.gl.entity.Footprint;
import com.baomidou.mybatisplus.extension.service.IService;
import org.gl.entity.Goods;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-13
 */
public interface FootprintService extends IService<Footprint> {

    /**
     * 添加用户足迹
     * @param token token
     * @param goodsId 商品主键id
     * @return 是否添加成功
     */
    Integer addFootprint(String token,Long goodsId);

    /**
     * 删除用户足迹
     * @param token token
     * @param footprintId 足迹主键id
     * @return 删除结果集
     */
    Map<String,Object> removeFootprint(String token,Long... footprintId);

    /**
     * 获取用户足迹列表
     * @param token token
     * @return 收藏对象列表
     */
    List<Footprint> getUserFootprint(String token);

}
