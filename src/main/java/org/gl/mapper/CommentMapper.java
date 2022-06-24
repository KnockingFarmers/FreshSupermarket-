package org.gl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gl.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 甘龙
 * @since 2022-06-01
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询商品的评论
     * @param goodsId 商品ID
     * @return 评论对象列表
     */
    List<Comment> selectCommentByGoodsId(@Param("goodsId") Long goodsId);

    /**
     * 获取商品的最新一条评论
     * @param goodsId 用户ID
     * @return 评论对象
     */
    Comment selectOneComment(@Param("goodsId") Long goodsId);
}
