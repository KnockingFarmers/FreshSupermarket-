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
 * @since 2022-03-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "shopping_address_id", type = IdType.ID_WORKER)
    private Long shoppingAddressId;

    private Long userId;

    private String consigneePhone;

    private String shoppingAddress;

    private String consignee;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    private Integer defaulted;

    /**
     * JS支持最长数字为16位会导致long类型精度丢失问题，需要把id转为字符串userIdStr
     * 让该属性不参与sql语句
     */
    @TableField(exist = false)
    private String shoppingAddressIdStr;

    public String getShoppingAddressIdStr() {
        return this.shoppingAddressId+"";
    }

    public void setShoppingAddressIdStr(String shoppingAddressIdStr) {
        this.shoppingAddressIdStr = shoppingAddressIdStr;
    }
}
