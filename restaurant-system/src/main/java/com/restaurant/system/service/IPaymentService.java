package com.restaurant.system.service;

import java.util.List;

import com.restaurant.system.domain.DTO.CategoryNameAndSubtotal;
import com.restaurant.system.domain.DTO.PaymentWithUser;
import com.restaurant.system.domain.Payment;

/**
 * 支付信息Service接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface IPaymentService
{
    /**
     * 查询支付信息
     * 
     * @param paymentId 支付信息主键
     * @return 支付信息
     */
    public Payment selectResPaymentByPaymentId(String paymentId);

    /**
     * 查询支付信息
     *
     * @param orderId 订单主键
     * @return 支付信息
     */
    public Payment selectResPaymentByOrderId(String orderId);

    /**
     * 查询支付信息列表
     * 
     * @param payment 支付信息
     * @return 支付信息集合
     */
    public List<PaymentWithUser> selectResPaymentList(Payment payment);

    /**
     * 新增支付信息
     * 
     * @param payment 支付信息
     * @return 结果
     */
    public int insertResPayment(Payment payment);

    /**
     * 修改支付信息
     * 
     * @param payment 支付信息
     * @return 结果
     */
    public int updateResPayment(Payment payment);

    /**
     * 批量删除支付信息
     * 
     * @param paymentIds 需要删除的支付信息主键集合
     * @return 结果
     */
    public int deleteResPaymentByPaymentIds(String[] paymentIds);

    /**
     * 删除支付信息信息
     * 
     * @param paymentId 支付信息主键
     * @return 结果
     */
    public int deleteResPaymentByPaymentId(String paymentId);

    /**
     * 获取今日菜品分类支付额
     * @return
     */
    public List<CategoryNameAndSubtotal> selectTodayCategoryNameAndSubtotal();

    /**
     * 获取昨日菜品分类支付额
     * @return
     */
    public List<CategoryNameAndSubtotal> selectYesterdayCategoryNameAndSubtotal();
}
