package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.gl.entity.Comment;
import org.gl.entity.CommentImages;
import org.gl.entity.User;
import org.gl.entity.UserOrder;
import org.gl.mapper.CommentImagesMapper;
import org.gl.mapper.CommentMapper;
import org.gl.mapper.OrderMapper;
import org.gl.service.CommentImagesService;
import org.gl.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gl.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-06-01
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private static final String LOCATION = "http://localhost:8081/static";

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentImagesMapper imagesMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public List<Comment> getCommentList(Long goodsId) {
        return commentMapper.selectCommentByGoodsId(goodsId);
    }

    @Override
    public Map<String, Object> getGoodsCommentNumAndGoodsFirstComment(Long goodsId) {
        Map<String, Object> resultMap = new HashMap<>(2);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("goods_id", goodsId);
        Integer goodsCommentNum = commentMapper.selectCount(wrapper);
        Comment comment = commentMapper.selectOneComment(goodsId);
        resultMap.put("goodsCommentNum", goodsCommentNum);
        resultMap.put("comment", comment);
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = IOException.class)
    public Integer userSubmitComment(String token, Long goodsId,Long orderId,String commentText,Integer score, MultipartFile... files) throws IOException {
        Map<String, Object> map = jwtUtil.analyzeToken(token);
        Comment comment=new Comment();
        comment.setCommentText(commentText);
        comment.setScore(score);
        comment.setGoodsId(goodsId);
        comment.setUserId((Long) map.get("userId"));
        comment.setGmtCreate(new Date());
        int commentInsert = commentMapper.insert(comment);
        Long commentId = comment.getCommentId();

        //修改订单状态
        UserOrder userOrder = orderMapper.selectById(orderId);
        userOrder.setOrderStatus("已完成");
        orderMapper.updateById(userOrder);

        int commentImageInsert=0;
        if (files!=null&&files.length>0) {
            for (int i = 0; i < files.length; i++) {
                String originalFilename = files[i].getOriginalFilename();
                //临时路径
                String tempPath = Thread.currentThread().getContextClassLoader().getResource("static").getPath();

                //新文件名
                String newFileName = UUID.randomUUID().toString();
                //后缀
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //路径
                String path = tempPath + "/commentImages/" + newFileName + suffix;
                //存入文件
                files[i].transferTo(new File(path));

                CommentImages commentImage = new CommentImages();
                commentImage.setCommentImages(LOCATION + "/commentImages/" + newFileName + suffix);
                commentImage.setCommentId(commentId);
                commentImageInsert+=imagesMapper.insert(commentImage);
                //删除原来的文件
                File oldUserAvatar = new File(path + originalFilename);
                oldUserAvatar.deleteOnExit();
            }
        }

        if (commentInsert==1){
            return 1;
        }else {
            return 0;
        }

    }
}
