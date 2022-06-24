package org.gl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 甘龙
 * @since 2022-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "order_goods_id", type = IdType.ID_WORKER)
    private Long orderGoodsId;

    private Long orderId;

    private Long goodsId;

    private Integer num;

    @TableField(exist = false)
    private Goods goods;


}
