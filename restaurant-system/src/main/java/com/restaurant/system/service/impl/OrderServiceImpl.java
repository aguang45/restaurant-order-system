package com.restaurant.system.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.restaurant.common.core.redis.RedisCache;
import com.restaurant.common.utils.SecurityUtils;
import com.restaurant.common.utils.bean.BeanUtils;
import com.restaurant.system.domain.*;
import com.restaurant.system.domain.DTO.*;
import com.restaurant.system.mapper.PaymentMapper;
import com.restaurant.system.mapper.SysUserMapper;
import com.restaurant.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.OrderMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单Service业务层处理
 *
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private IUserAddressService userAddressService;
    @Autowired
    private ITableService tableService;
    @Autowired
    private ICourierService courierService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Autowired
    private IDishService dishService;
    private List<OrderDetailWithDish> orderDetailsWithDishes;


    /**
     * 查询订单
     *
     * @param orderId 订单主键
     * @return 订单
     */
    @Override
    public Order selectOrderByOrderId(String orderId) {
        return orderMapper.selectOrderByOrderId(orderId);
    }

    /**
     * 查询订单列表
     *
     * @param order 订单
     * @return 订单
     */
    @Override
    public List<OrderWithUser>  selectOrderList(Order order) {
        List<OrderWithUser> list = new ArrayList<>();
        List<Order> orderList = orderMapper.selectOrderList(order);

        for (Order order1 : orderList) {
            OrderWithUser orderWithUser = new OrderWithUser();
            BeanUtils.copyProperties(order1, orderWithUser);
//            设置用户姓名
            orderWithUser.setUserName(userMapper.selectUserById(Long.valueOf(order1.getUserId())).getNickName());
//            设置配送地址信息
            if (order1.getAddressId() != null) {
                UserAddress userAddress = userAddressService.selectUserAddressByAddressId(order1.getAddressId());
                orderWithUser.setUserAddress(userAddress.getDetailAddress());
                orderWithUser.setUserPhone(userAddress.getPhone());
                orderWithUser.setUserSex(userAddress.getSex());
            }
            if (order1.getCourierId() != null) {
//            设置配送员信息
                Courier courier = courierService.selectCourierByCourierId(order1.getCourierId());
                orderWithUser.setCourierName(courier.getCourierName());
                orderWithUser.setCourierPhone(courier.getCourierPhone());
            }
            if (order1.getTableId() != null) {
//            设置座位信息
                orderWithUser.setTableName(tableService.selectTableByTableId(order1.getTableId()).getTableName());
            }
//            设置订单详情信息
            /*List<OrderDetail> orderDetails = orderDetailService.selectOrderDetailByOrderId(order1.getOrderId());
            List<OrderDetialWithDish> orderDetialWithDishes = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetails) {
                OrderDetialWithDish orderDetialWithDish = new OrderDetialWithDish();
                BeanUtils.copyProperties(orderDetail, orderDetialWithDish);
                DishWithCategory dishWithCategory = dishService.selectDishByDishId(orderDetail.getDishId());
                orderDetialWithDish.setDishName(dishWithCategory.getDishName());
                orderDetialWithDish.setDishPrice(String.valueOf(dishWithCategory.getDishPrice()));
                orderDetialWithDish.setDishImg(dishWithCategory.getDishImage());
                orderDetialWithDish.setCategoryName(dishWithCategory.getCategoryName());
                orderDetialWithDishes.add(orderDetialWithDish);
            }

            orderWithUser.setOrderDetails(orderDetialWithDishes);*/

            list.add(orderWithUser);
        }

        return list;
    }

    @Override
    public List<OrderWithUser> selectOrderAppList(Order order) {
//        设置用户id
        order.setUserId(String.valueOf(SecurityUtils.getUserId()));
        List<OrderWithUser> list = new ArrayList<>();
        List<Order> orderList = orderMapper.selectOrderList(order);

        for (Order order1 : orderList) {
            OrderWithUser orderWithUser = new OrderWithUser();
            BeanUtils.copyProperties(order1, orderWithUser);
//            设置用户姓名
            orderWithUser.setUserName(userMapper.selectUserById(Long.valueOf(order1.getUserId())).getNickName());
//            设置配送地址信息
            if (order1.getAddressId() != null) {
                UserAddress userAddress = userAddressService.selectUserAddressByAddressId(order1.getAddressId());
                orderWithUser.setUserAddress(userAddress.getDetailAddress());
                orderWithUser.setUserPhone(userAddress.getPhone());
                orderWithUser.setUserSex(userAddress.getSex());
            }
            if (order1.getCourierId() != null) {
//            设置配送员信息
                Courier courier = courierService.selectCourierByCourierId(order1.getCourierId());
                orderWithUser.setCourierName(courier.getCourierName());
                orderWithUser.setCourierPhone(courier.getCourierPhone());
            }
            if (order1.getTableId() != null ) {
//            设置座位信息
                orderWithUser.setTableName(tableService.selectTableByTableId(order1.getTableId()).getTableName());
            }
//            设置订单详情信息
            List<OrderDetailWithDish> orderDetails = orderDetailService.selectOrderDetailByOrderId(order1.getOrderId());
            List<OrderDetailWithDish> orderDetailsWithDishes = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetails) {
                OrderDetailWithDish orderDetailWithDish = new OrderDetailWithDish();
                BeanUtils.copyProperties(orderDetail, orderDetailWithDish);
                DishWithCategory dishWithCategory = dishService.selectDishByDishId(orderDetail.getDishId());
                orderDetailWithDish.setDishName(dishWithCategory.getDishName());
                orderDetailWithDish.setDishPrice(String.valueOf(dishWithCategory.getDishPrice()));
                orderDetailWithDish.setDishImg(dishWithCategory.getDishImage());
                orderDetailWithDish.setCategoryName(dishWithCategory.getCategoryName());
                orderDetailsWithDishes.add(orderDetailWithDish);
            }
            orderWithUser.setOrderDetails(orderDetailsWithDishes);

            list.add(orderWithUser);
        }

        return list;
    }

    /**
     * 新增订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int insertOrder(Order order) {
        String orderId = "order:" + order.getUserId() + ":" + order.getOrderId();
        redisCache.setCacheObject(orderId, System.currentTimeMillis(), 60 * 30, TimeUnit.SECONDS);
        return orderMapper.insertOrder(order);
    }

    /**
     * 检查订单是否超时
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    @Transactional
    public void checkOrderTime() {
        Order order1 = new Order();
        order1.setOrderStatus("0");
        List<Order> orders = orderMapper.selectOrderList(order1);
        for (Order order : orders) {
            String orderId = "order:" + order.getUserId() + ":" + order.getOrderId();
            Payment payment = paymentService.selectResPaymentByOrderId(order.getOrderId());
            if ("0".equals(order.getOrderStatus()) && payment == null) {
                if (redisCache.getCacheObject(orderId) == null) {
                    // 订单超时
                    outTimeOrder(order);
                    // 不需要再次删除Redis中的键，因为它已经因为过期而不存在
                }
            }
        }
    }

    /*public void checkOrderTime() {
        Order order1 = new Order();
        order1.setOrderStatus("0");
        List<Order> orders = orderMapper.selectOrderList(order1);
        for (Order order : orders) {
            String orderId = "order:" + order.getUserId() + ":" + order.getOrderId();
            Payment payment = paymentService.selectResPaymentByOrderId(order.getOrderId());
            if ("0".equals(order.getOrderStatus()) && payment == null) {
                if (redisCache.getCacheObject(orderId) == null) {
                    outTimeOrder(order);
                    redisCache.deleteObject(orderId);
                } else if (System.currentTimeMillis() - (long) redisCache.getCacheObject(orderId) > 60 * 30 * 1000) {
                    outTimeOrder(order);
                    redisCache.deleteObject(orderId);
                }
            }
        }
    }*/

    /**
     * 超时订单处理
     * @param order
     */
    private void outTimeOrder(Order order) {
        order.setOrderStatus("2");
//        order.setIsDeleted(1);
        orderMapper.updateOrder(order);
//        释放座位
        if (order.getTableId() != null) {
            Table table = tableService.selectTableByTableId(order.getTableId());
            table.setIsFree(1);
            tableService.updateTable(table);
        }
    }


    /**
     * 修改订单
     *
     * @param order 订单
     * @return 结果
     */
    @Override
    public int updateOrder(Order order) {
        if ("2".equals(order.getDeliveryStatus())) {
            order.setOrderStatus("3");
        }
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单
     *
     * @param orderIds 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderIds(String[] orderIds) {
        return orderMapper.deleteOrderByOrderIds(orderIds);
    }

    /**
     * 删除订单信息
     *
     * @param orderId 订单主键
     * @return 结果
     */
    @Override
    public int deleteOrderByOrderId(String orderId) {
        return orderMapper.deleteOrderByOrderId(orderId);
    }

    @Override
    public List<Integer> getTopData() {
        List<Integer> list = new ArrayList<>();
        list.add(paymentMapper.getTodayPaymentCount());
        list.add(paymentMapper.getThisMonthPaymentCount());
        list.add(orderMapper.getCancelOrderCount());
        return list;
    }

    @Override
    public List<OrderWithOther> getOrderWithOtherList(OrderWithOther orderWithOther) {
        return orderMapper.getOrderWithOtherList(orderWithOther);
    }

    @Override
    public List<OrderWithOtherForList> getOrderWithOtherForList(Order order) {
        return orderMapper.getOrderWithOtherForList(order);
    }
}
