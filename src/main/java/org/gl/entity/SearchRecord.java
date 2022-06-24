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

/**
 * <p>
 * 
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "search_record_id", type = IdType.ID_WORKER)
    private Long searchRecordId;

    private Long userId;

    private String searchText;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;

    /**
     * JS支持最长数字为16位会导致long类型精度丢失问题，需要把id转为字符串userIdStr
     * 让该属性不参与sql语句
     */
    @TableField(exist = false)
    private String searchRecordIdStr;

    public String getSearchRecordIdStr() {
        return this.searchRecordId+"";
    }

    public void setSearchRecordIdStr(String searchRecordIdStr) {
        this.searchRecordIdStr = searchRecordIdStr;
    }
}
