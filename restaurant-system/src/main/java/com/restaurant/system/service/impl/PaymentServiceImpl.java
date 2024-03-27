package com.restaurant.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.restaurant.common.core.domain.entity.SysUser;
import com.restaurant.common.core.redis.RedisCache;
import com.restaurant.common.utils.bean.BeanUtils;
import com.restaurant.system.domain.Category;
import com.restaurant.system.domain.DTO.CategoryNameAndSubtotal;
import com.restaurant.system.domain.DTO.PaymentWithUser;
import com.restaurant.system.domain.Order;
import com.restaurant.system.mapper.CategoryMapper;
import com.restaurant.system.mapper.OrderMapper;
import com.restaurant.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.PaymentMapper;
import com.restaurant.system.domain.Payment;
import com.restaurant.system.service.IPaymentService;

/**
 * 支付信息Service业务层处理
 * 
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class PaymentServiceImpl implements IPaymentService
{
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询支付信息
     * 
     * @param paymentId 支付信息主键
     * @return 支付信息
     */
    @Override
    public Payment selectResPaymentByPaymentId(String paymentId)
    {
        return paymentMapper.selectResPaymentByPaymentId(paymentId);
    }

    /**
     * 查询支付信息
     *
     * @param orderId 订单主键
     * @return 支付信息
     */
    @Override
    public Payment selectResPaymentByOrderId(String orderId)
    {
        return paymentMapper.selectResPaymentByOrderId(orderId);
    }

    /**
     * 查询支付信息列表
     * 
     * @param payment 支付信息
     * @return 支付信息
     */
    @Override
    public List<PaymentWithUser> selectResPaymentList(Payment payment)
    {
        List<Payment> payments = paymentMapper.selectResPaymentList(payment);
        List<PaymentWithUser> paymentWithUsers = new ArrayList<>();
        for (Payment payment1 : payments) {
            PaymentWithUser paymentWithUser = new PaymentWithUser();
            BeanUtils.copyBeanProp(paymentWithUser, payment1);
            Order order = orderMapper.selectOrderByOrderId(payment1.getOrderId());
            SysUser sysUser = userMapper.selectUserById(Long.valueOf(order.getUserId()));
            paymentWithUser.setUserName(sysUser.getUserName());
            paymentWithUsers.add(paymentWithUser);
        }
        return paymentWithUsers;
    }

    /**
     * 新增支付信息
     * 
     * @param payment 支付信息
     * @return 结果
     */
    @Override
    public int insertResPayment(Payment payment)
    {
        int insertResPayment = paymentMapper.insertResPayment(payment);
        if (insertResPayment > 0) {
            Order order = orderMapper.selectOrderByOrderId(payment.getOrderId());
            String orderId = "order:" + order.getUserId() + ":" + payment.getOrderId();
            redisCache.deleteObject(orderId);
        }
        return insertResPayment;
    }

    /**
     * 修改支付信息
     * 
     * @param payment 支付信息
     * @return 结果
     */
    @Override
    public int updateResPayment(Payment payment)
    {
        return paymentMapper.updateResPayment(payment);
    }

    /**
     * 批量删除支付信息
     * 
     * @param paymentIds 需要删除的支付信息主键
     * @return 结果
     */
    @Override
    public int deleteResPaymentByPaymentIds(String[] paymentIds)
    {
        return paymentMapper.deleteResPaymentByPaymentIds(paymentIds);
    }

    /**
     * 删除支付信息信息
     * 
     * @param paymentId 支付信息主键
     * @return 结果
     */
    @Override
    public int deleteResPaymentByPaymentId(String paymentId)
    {
        return paymentMapper.deleteResPaymentByPaymentId(paymentId);
    }

    /**
     * 获取今日菜品分类支付额
     * @return
     */
    @Override
    public List<CategoryNameAndSubtotal> selectTodayCategoryNameAndSubtotal() {
        List<Category> categories = categoryMapper.selectCategoryList(new Category());
        List<CategoryNameAndSubtotal> categoryNameAndSubtotalList = paymentMapper.getTodayPaymentSubtotal();
        List<CategoryNameAndSubtotal> categoryNameAndSubtotalList1 = new ArrayList<>();
        for (Category category : categories) {
            boolean flag = false;
            for (CategoryNameAndSubtotal categoryNameAndSubtotal : categoryNameAndSubtotalList) {
                if (category.getCategoryName().equals(categoryNameAndSubtotal.getCategoryName())) {
                    flag = true;
                    categoryNameAndSubtotalList1.add(categoryNameAndSubtotal);
                    break;
                }
            }
            if (!flag) {
                CategoryNameAndSubtotal categoryNameAndSubtotal = new CategoryNameAndSubtotal();
                categoryNameAndSubtotal.setCategoryName(category.getCategoryName());
                categoryNameAndSubtotal.setSubtotal("0");
                categoryNameAndSubtotalList1.add(categoryNameAndSubtotal);
            }
        }
        return categoryNameAndSubtotalList1;
    }

    @Override
    public List<CategoryNameAndSubtotal> selectYesterdayCategoryNameAndSubtotal() {
        List<Category> categories = categoryMapper.selectCategoryList(new Category());
        List<CategoryNameAndSubtotal> categoryNameAndSubtotalList = paymentMapper.getYesterdayPaymentSubtotal();
        List<CategoryNameAndSubtotal> categoryNameAndSubtotalList1 = new ArrayList<>();
        for (Category category : categories) {
            boolean flag = false;
            for (CategoryNameAndSubtotal categoryNameAndSubtotal : categoryNameAndSubtotalList) {
                if (category.getCategoryName().equals(categoryNameAndSubtotal.getCategoryName())) {
                    flag = true;
                    categoryNameAndSubtotalList1.add(categoryNameAndSubtotal);
                    break;
                }
            }
            if (!flag) {
                CategoryNameAndSubtotal categoryNameAndSubtotal = new CategoryNameAndSubtotal();
                categoryNameAndSubtotal.setCategoryName(category.getCategoryName());
                categoryNameAndSubtotal.setSubtotal("0");
                categoryNameAndSubtotalList1.add(categoryNameAndSubtotal);
            }
        }
        return categoryNameAndSubtotalList1;
    }


}
