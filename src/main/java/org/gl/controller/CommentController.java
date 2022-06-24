package org.gl.controller;


import org.gl.entity.Comment;
import org.gl.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 甘龙
 * @since 2022-06-01
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/getGoodsCommentList")
    public List<Comment> getGoodsCommentList(String goodsIdStr){

        return commentService.getCommentList(Long.valueOf(goodsIdStr));
    }

    @GetMapping("/getGoodsCommentNumAndFirstComment")
    public Map<String, Object> getGoodsCommentNumAndFirstComment(String goodsIdStr){

        return commentService.getGoodsCommentNumAndGoodsFirstComment(Long.valueOf(goodsIdStr));
    }

    @PostMapping("/userSubmitComment")
    public Integer userSubmitComment(String token,
                                     String goodsIdStr,
                                     String orderIdStr,
                                     String commentText,
                                     Integer score,
                                     @RequestParam(value = "files",required = false) MultipartFile... files) throws IOException {

            return commentService.userSubmitComment(token,
                Long.valueOf(goodsIdStr),
                Long.valueOf(orderIdStr),
                commentText,
                score,
                files);
    }

}

