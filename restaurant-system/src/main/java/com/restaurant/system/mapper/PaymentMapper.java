package com.restaurant.system.mapper;

import java.util.List;

import com.restaurant.system.domain.DTO.CategoryNameAndSubtotal;
import com.restaurant.system.domain.Payment;

/**
 * 支付信息Mapper接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface PaymentMapper
{
    /**
     * 查询支付信息
     * 
     * @param paymentId 支付信息主键
     * @return 支付信息
     */
    public Payment selectResPaymentByPaymentId(String paymentId);    /**
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
    public List<Payment> selectResPaymentList(Payment payment);

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
     * 删除支付信息
     * 
     * @param paymentId 支付信息主键
     * @return 结果
     */
    public int deleteResPaymentByPaymentId(String paymentId);

    /**
     * 批量删除支付信息
     * 
     * @param paymentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteResPaymentByPaymentIds(String[] paymentIds);

    /**
     * 获取今日支付总额
     * @return
     */
    public Integer getTodayPaymentCount();

    /**
     * 获取本月支付总额
     * @return
     */
    public Integer getThisMonthPaymentCount();

    /**
     * 获取今日支付菜品分类总额
     * @return
     */
    public List<CategoryNameAndSubtotal> getTodayPaymentSubtotal();

    /**
     * 获取昨日支付菜品分类总额
     * @return
     */
    public List<CategoryNameAndSubtotal> getYesterdayPaymentSubtotal();
}
