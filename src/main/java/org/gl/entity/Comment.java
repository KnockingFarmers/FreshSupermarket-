package org.gl.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 甘龙
 * @since 2022-06-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.ID_WORKER)
    private Long commentId;

    private String commentText;

    private Long goodsId;

    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date gmtCreate;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    private Integer score;

    @TableField(exist = false)
    private String commentIdStr;

    @TableField(exist = false)
    private List<CommentImages> commentImagesList;

    @TableField(exist = false)
    private User user;

    public String getCommentIdStr() {
        return this.commentId+"";
    }
}
