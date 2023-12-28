package com.restaurant.system.service;

import java.util.List;
import com.restaurant.system.domain.ResPayment;

/**
 * 支付信息Service接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface IResPaymentService 
{
    /**
     * 查询支付信息
     * 
     * @param paymentId 支付信息主键
     * @return 支付信息
     */
    public ResPayment selectResPaymentByPaymentId(String paymentId);

    /**
     * 查询支付信息列表
     * 
     * @param resPayment 支付信息
     * @return 支付信息集合
     */
    public List<ResPayment> selectResPaymentList(ResPayment resPayment);

    /**
     * 新增支付信息
     * 
     * @param resPayment 支付信息
     * @return 结果
     */
    public int insertResPayment(ResPayment resPayment);

    /**
     * 修改支付信息
     * 
     * @param resPayment 支付信息
     * @return 结果
     */
    public int updateResPayment(ResPayment resPayment);

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
}
