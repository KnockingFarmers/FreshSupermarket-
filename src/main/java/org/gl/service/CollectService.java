package org.gl.service;

import org.gl.entity.Collect;
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
 * @since 2022-03-21
 */
public interface CollectService extends IService<Collect> {

    /**
     * 添加收藏
     * @param token token
     * @param goodsId 商品主键id
     * @return 是否添加成功
     */
    Integer addCollect(String token,Long goodsId);

    /**
     * 删除用户收藏商品
     * @param token token
     * @param collectId 收藏主键id
     * @return 删除结果集
     */
    Map<String,Object> removeCollect(String token,Long... collectId);

    /**
     * 获取用户收藏列表
     * @param token token
     * @return 收藏对象列表
     */
    List<Collect> getCollectList(String token);

    /**
     * 查看用户是否已收藏
     * @param token token
     * @param goodsId 商品id
     * @return 收藏ID字符串
     */
    String userCollected(String token,Long goodsId);

}
