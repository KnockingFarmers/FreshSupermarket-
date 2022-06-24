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
 * @since 2022-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "shopping_cart_id", type = IdType.ID_WORKER)
    private Long shoppingCartId;

    private Long userId;

    private Long goodsId;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private Integer goodsNum;

    @TableField(exist = false)
    private Goods goods;

    /**
     * JS支持最长数字为16位会导致long类型精度丢失问题，需要把id转为字符串userIdStr
     * 让该属性不参与sql
     */
    @TableField(exist = false)
    private String shoppingCartIdStr;

    public String getShoppingCartIdStr() {
        return this.shoppingCartId+"";
    }

    public void setShoppingCartIdStr(String shoppingCartIdStr) {
        this.shoppingCartIdStr = shoppingCartIdStr;
    }

}
