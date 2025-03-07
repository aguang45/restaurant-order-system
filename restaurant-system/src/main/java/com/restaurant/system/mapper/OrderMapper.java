package com.restaurant.system.mapper;

import java.util.List;

import com.restaurant.system.domain.DTO.OrderWithOther;
import com.restaurant.system.domain.DTO.OrderWithOtherForList;
import com.restaurant.system.domain.Order;

/**
 * 订单Mapper接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface OrderMapper 
{
    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    public Order selectOrderByOrderId(String orderId);

    /**
     * 查询订单列表
     * 
     * @param order 订单
     * @return 订单集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单
     * 
     * @param order 订单
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 删除订单
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(String orderId);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(String[] orderIds);

    /**
     * 获取本月取消订单数
     * @return
     */
    public Integer getCancelOrderCount();

    /**
     * 获取外卖订单详情
     * @param orderWithOther
     * @return
     */
    public List<OrderWithOther> getOrderWithOtherList(OrderWithOther orderWithOther);

    /**
     * 获取订单详情
     * @param orderWithOtherForList
     * @return
     */
    public List<OrderWithOtherForList> getOrderWithOtherForList(Order order);

}
