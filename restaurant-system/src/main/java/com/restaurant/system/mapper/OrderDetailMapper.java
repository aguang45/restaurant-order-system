package com.restaurant.system.mapper;

import java.util.List;
import com.restaurant.system.domain.OrderDetail;

/**
 * 订单详情Mapper接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface OrderDetailMapper 
{
    /**
     * 查询订单详情
     * 
     * @param orderDetailId 订单详情主键
     * @return 订单详情
     */
    public OrderDetail selectOrderDetailByOrderDetailId(String orderDetailId);

    /**
     * 查询订单详情列表
     * 
     * @param orderDetail 订单详情
     * @return 订单详情集合
     */
    public List<OrderDetail> selectOrderDetailList(OrderDetail orderDetail);

    /**
     * 新增订单详情
     * 
     * @param orderDetail 订单详情
     * @return 结果
     */
    public int insertOrderDetail(OrderDetail orderDetail);

    /**
     * 修改订单详情
     * 
     * @param orderDetail 订单详情
     * @return 结果
     */
    public int updateOrderDetail(OrderDetail orderDetail);

    /**
     * 删除订单详情
     * 
     * @param orderDetailId 订单详情主键
     * @return 结果
     */
    public int deleteOrderDetailByOrderDetailId(String orderDetailId);

    /**
     * 批量删除订单详情
     * 
     * @param orderDetailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderDetailByOrderDetailIds(String[] orderDetailIds);
}
