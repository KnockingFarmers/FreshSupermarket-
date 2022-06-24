package org.gl.service;

import org.gl.entity.Sales;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 甘龙
 * @since 2022-04-12
 */
public interface SalesService extends IService<Sales> {

    /**
     * 想销售表插入数据
     * @param goodsId 商品主键id
     * @param purchaseQuantity 购买数量
     * @param goodsPrice 商品价格
     * @return
     */
    Integer insertSale(Long goodsId,Double goodsPrice,Integer purchaseQuantity);
}
