package com.restaurant.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.CourierMapper;
import com.restaurant.system.domain.Courier;
import com.restaurant.system.service.ICourierService;

/**
 * 配送员Service业务层处理
 * 
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class CourierServiceImpl implements ICourierService 
{
    @Autowired
    private CourierMapper courierMapper;

    /**
     * 查询配送员
     * 
     * @param courierId 配送员主键
     * @return 配送员
     */
    @Override
    public Courier selectCourierByCourierId(String courierId)
    {
        return courierMapper.selectCourierByCourierId(courierId);
    }

    /**
     * 查询配送员列表
     * 
     * @param courier 配送员
     * @return 配送员
     */
    @Override
    public List<Courier> selectCourierList(Courier courier)
    {
        return courierMapper.selectCourierList(courier);
    }

    /**
     * 新增配送员
     * 
     * @param courier 配送员
     * @return 结果
     */
    @Override
    public int insertCourier(Courier courier)
    {
        return courierMapper.insertCourier(courier);
    }

    /**
     * 修改配送员
     * 
     * @param courier 配送员
     * @return 结果
     */
    @Override
    public int updateCourier(Courier courier)
    {
        return courierMapper.updateCourier(courier);
    }

    /**
     * 批量删除配送员
     * 
     * @param courierIds 需要删除的配送员主键
     * @return 结果
     */
    @Override
    public int deleteCourierByCourierIds(String[] courierIds)
    {
        return courierMapper.deleteCourierByCourierIds(courierIds);
    }

    /**
     * 删除配送员信息
     * 
     * @param courierId 配送员主键
     * @return 结果
     */
    @Override
    public int deleteCourierByCourierId(String courierId)
    {
        return courierMapper.deleteCourierByCourierId(courierId);
    }
}
