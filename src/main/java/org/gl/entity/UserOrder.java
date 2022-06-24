package org.gl.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_id", type = IdType.ID_WORKER)
    private Long orderId;

    private Long userId;

    private Long orderNumber;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @Version
    private Integer version;

    private String orderStatus;

    private String paymentMethod;

    private String serialNumber;

    private BigDecimal orderAmount;

    private Long shoppingAddressId;

    @TableField(exist = false)
    private String orderIdStr;

    @TableField(exist = false)
    private List<OrderGoods> orderGoodsList;

    @TableField(exist = false)
    private Goods goods;

    @TableField(exist = false)
    private String shoppingAddressIdStr;

    public String getOrderIdStr() {
        return this.orderId+"";
    }

    public String getShoppingAddressIdStr() {
        return this.shoppingAddressId+"";
    }

    public void setShoppingAddressIdStr(String shoppingAddressIdStr) {
        this.shoppingAddressIdStr = shoppingAddressIdStr;
    }
}
