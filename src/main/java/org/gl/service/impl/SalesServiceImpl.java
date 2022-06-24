package org.gl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.gl.entity.Sales;
import org.gl.mapper.SalesMapper;
import org.gl.service.SalesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 甘龙
 * @since 2022-04-12
 */
@Service
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

    @Autowired
    private SalesMapper salesMapper;

    @Override
    public Integer insertSale(Long goodsId,Double goodsPrice, Integer purchaseQuantity) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("goods_id",goodsId);
        Sales sales = salesMapper.selectOne(wrapper);

        sales.setSalesNumber(sales.getSalesNumber()+purchaseQuantity);
        Double salePrice=(goodsPrice*purchaseQuantity)+sales.getTotalSalesPrice();
        sales.setTotalSalesPrice(salePrice);
        return salesMapper.updateById(sales);
    }
}
