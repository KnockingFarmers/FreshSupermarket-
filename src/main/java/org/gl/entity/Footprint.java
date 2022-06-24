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
 * @since 2022-03-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Footprint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "footprint_id", type = IdType.ID_WORKER)
    private Long footprintId;

    private Long goodsId;

    @TableLogic
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;

    @Version
    private Integer version;

    private Long userId;

    @TableField(exist = false)
    private Goods goods;

    /**
     * JS支持最长数字为16位会导致long类型精度丢失问题，需要把id转为字符串userIdStr
     * 让该属性不参与sql语句
     */
    @TableField(exist = false)
    private String footprintIdStr;

    public String getFootprintIdStr() {
        return this.footprintId+"";
    }

    public void setFootprintIdStr(String footprintIdStr) {
        this.footprintIdStr = footprintIdStr;
    }
}
