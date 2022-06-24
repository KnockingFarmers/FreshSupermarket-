package org.gl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.gl.entity.Comment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 甘龙
 * @since 2022-06-01
 */
public interface CommentService extends IService<Comment> {

    /**
     * 获取商品评论列表
     * @param goodsId 商品ID
     * @return 评论集合
     */
    List<Comment> getCommentList(Long goodsId);

    /**
     * 获取商品评论数量和第一条评论
     * @param goodsId 商品ID
     * @return 商品的评论数量和第一条评论
     */
    Map<String,Object> getGoodsCommentNumAndGoodsFirstComment(Long goodsId);

    /**
     * 用户提交评论
     * @param token token
     * @param goodsId 商品ID
     * @param files 评论图片
     * @param orderId 订单ID
     * @param commentText 评论内容
     * @param score 平分数 1-5
     * @return 是否评论成功
     * @throws IOException 保存图片可能发生
     */
    Integer userSubmitComment(String token, Long goodsId,Long orderId,String commentText,Integer score, MultipartFile... files) throws IOException;

}
