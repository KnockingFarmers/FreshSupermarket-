package org.gl.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
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
@ToString
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "goods_id", type = IdType.ID_WORKER)
    private Long goodsId;

    private String goodsName;

    private Double goodsPrice;

    private String goodsImage;

    private String goodsFirstClass;

    private String goodsTwoClass;

    private Integer goodsInventory;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @Version
    private Integer version;



    /**
     * JS支持最长数字为16位会导致long类型精度丢失问题，需要把id转为字符串userIdStr
     * 让该属性不参与sql语句
     */
    @TableField(exist = false)
    private String goodsIdStr;

    public String getGoodsIdStr() {
        return this.goodsId+"";
    }

    public void setGoodsIdStr(String goodsIdStr) {
        this.goodsIdStr = goodsIdStr;
    }
}
