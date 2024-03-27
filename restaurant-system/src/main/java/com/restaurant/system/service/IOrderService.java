package com.restaurant.system.service;

import java.util.List;

import com.restaurant.system.domain.DTO.OrderWithOther;
import com.restaurant.system.domain.DTO.OrderWithOtherForList;
import com.restaurant.system.domain.DTO.OrderWithUser;
import com.restaurant.system.domain.Order;

/**
 * 订单Service接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface IOrderService 
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
    public List<OrderWithUser> selectOrderList(Order order);

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单集合
     */
    public List<OrderWithUser> selectOrderAppList(Order order);

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
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteOrderByOrderIds(String[] orderIds);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteOrderByOrderId(String orderId);

    /**
     * 获取热门数据
     * @return
     */
    public List<Integer> getTopData();

    /**
     * 获取外卖订单信息
     * @param orderWithOther
     * @return
     */
    public List<OrderWithOther> getOrderWithOtherList(OrderWithOther orderWithOther);

    /**
     * 获取订单信息
     * @param order
     * @return
     */
    public List<OrderWithOtherForList> getOrderWithOtherForList(Order order);
}
