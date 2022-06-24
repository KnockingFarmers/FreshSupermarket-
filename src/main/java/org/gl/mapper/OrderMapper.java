package org.gl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gl.entity.UserOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 甘龙
 * @since 2022-03-21
 */
@Mapper
public interface OrderMapper extends BaseMapper<UserOrder> {

    /**
     * 获取用户订单
     * @param userId 用户主键
     * @param orderState 订单状态
     * @return 订单对象列表
     */
    List<UserOrder> selectAllByUserIdAfter(@Param("userId") Long userId, @Param("orderState")String orderState);

    UserOrder selectByOrderId(@Param("orderId")Long orderId);
}
