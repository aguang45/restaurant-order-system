package com.restaurant.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.ResPaymentMapper;
import com.restaurant.system.domain.ResPayment;
import com.restaurant.system.service.IResPaymentService;

/**
 * 支付信息Service业务层处理
 * 
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class ResPaymentServiceImpl implements IResPaymentService 
{
    @Autowired
    private ResPaymentMapper resPaymentMapper;

    /**
     * 查询支付信息
     * 
     * @param paymentId 支付信息主键
     * @return 支付信息
     */
    @Override
    public ResPayment selectResPaymentByPaymentId(String paymentId)
    {
        return resPaymentMapper.selectResPaymentByPaymentId(paymentId);
    }

    /**
     * 查询支付信息列表
     * 
     * @param resPayment 支付信息
     * @return 支付信息
     */
    @Override
    public List<ResPayment> selectResPaymentList(ResPayment resPayment)
    {
        return resPaymentMapper.selectResPaymentList(resPayment);
    }

    /**
     * 新增支付信息
     * 
     * @param resPayment 支付信息
     * @return 结果
     */
    @Override
    public int insertResPayment(ResPayment resPayment)
    {
        return resPaymentMapper.insertResPayment(resPayment);
    }

    /**
     * 修改支付信息
     * 
     * @param resPayment 支付信息
     * @return 结果
     */
    @Override
    public int updateResPayment(ResPayment resPayment)
    {
        return resPaymentMapper.updateResPayment(resPayment);
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
        return resPaymentMapper.deleteResPaymentByPaymentIds(paymentIds);
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
        return resPaymentMapper.deleteResPaymentByPaymentId(paymentId);
    }
}
