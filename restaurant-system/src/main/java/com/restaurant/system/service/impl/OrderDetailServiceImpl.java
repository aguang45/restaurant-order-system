package com.restaurant.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.restaurant.common.utils.bean.BeanUtils;
import com.restaurant.system.domain.DTO.DishWithCategory;
import com.restaurant.system.domain.DTO.OrderDetailWithDish;
import com.restaurant.system.service.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.OrderDetailMapper;
import com.restaurant.system.domain.OrderDetail;
import com.restaurant.system.service.IOrderDetailService;

/**
 * 订单详情Service业务层处理
 * 
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class OrderDetailServiceImpl implements IOrderDetailService 
{
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private IDishService dishService;

    /**
     * 查询订单详情
     * 
     * @param orderDetailId 订单详情主键
     * @return 订单详情
     */
    @Override
    public List<OrderDetail> selectOrderDetailByOrderDetailId(String orderDetailId)
    {
        return orderDetailMapper.selectOrderDetailByOrderId(orderDetailId);
    }

    /**
     * 查询订单详情列表
     * @param orderId 订单详情主键
     * @return
     */
    @Override
    public List<OrderDetailWithDish> selectOrderDetailByOrderId(String orderId)
    {
        List<OrderDetailWithDish> orderDetailWithDishes = new ArrayList<>();
        List<OrderDetail> orderDetails = orderDetailMapper.selectOrderDetailByOrderId(orderId);
        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailWithDish orderDetailWithDish = new OrderDetailWithDish();
            DishWithCategory dishWithCategory = dishService.selectDishByDishId(orderDetail.getDishId());
            BeanUtils.copyBeanProp(orderDetailWithDish, orderDetail);
            orderDetailWithDish.setDishName(dishWithCategory.getDishName());
            orderDetailWithDish.setCategoryName(dishWithCategory.getCategoryName());
            orderDetailWithDish.setDishPrice(String.valueOf(dishWithCategory.getDishPrice()));
            orderDetailWithDish.setDishImg(dishWithCategory.getDishImage());

            orderDetailWithDishes.add(orderDetailWithDish);
        }
        return orderDetailWithDishes;
    }

    /**
     * 查询订单详情列表
     * 
     * @param orderDetail 订单详情
     * @return 订单详情
     */
    @Override
    public List<OrderDetail> selectOrderDetailList(OrderDetail orderDetail)
    {
        return orderDetailMapper.selectOrderDetailList(orderDetail);
    }

    /**
     * 新增订单详情
     * 
     * @param orderDetail 订单详情
     * @return 结果
     */
    @Override
    public int insertOrderDetail(OrderDetail orderDetail)
    {
        return orderDetailMapper.insertOrderDetail(orderDetail);
    }

    /**
     * 修改订单详情
     * 
     * @param orderDetail 订单详情
     * @return 结果
     */
    @Override
    public int updateOrderDetail(OrderDetail orderDetail)
    {
        return orderDetailMapper.updateOrderDetail(orderDetail);
    }

    /**
     * 批量删除订单详情
     * 
     * @param orderDetailIds 需要删除的订单详情主键
     * @return 结果
     */
    @Override
    public int deleteOrderDetailByOrderDetailIds(String[] orderDetailIds)
    {
        return orderDetailMapper.deleteOrderDetailByOrderDetailIds(orderDetailIds);
    }

    /**
     * 删除订单详情信息
     * 
     * @param orderDetailId 订单详情主键
     * @return 结果
     */
    @Override
    public int deleteOrderDetailByOrderDetailId(String orderDetailId)
    {
        return orderDetailMapper.deleteOrderDetailByOrderDetailId(orderDetailId);
    }
}
