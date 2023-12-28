package com.restaurant.system.mapper;

import java.util.List;
import com.restaurant.system.domain.Courier;

/**
 * 配送员Mapper接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface CourierMapper 
{
    /**
     * 查询配送员
     * 
     * @param courierId 配送员主键
     * @return 配送员
     */
    public Courier selectCourierByCourierId(String courierId);

    /**
     * 查询配送员列表
     * 
     * @param courier 配送员
     * @return 配送员集合
     */
    public List<Courier> selectCourierList(Courier courier);

    /**
     * 新增配送员
     * 
     * @param courier 配送员
     * @return 结果
     */
    public int insertCourier(Courier courier);

    /**
     * 修改配送员
     * 
     * @param courier 配送员
     * @return 结果
     */
    public int updateCourier(Courier courier);

    /**
     * 删除配送员
     * 
     * @param courierId 配送员主键
     * @return 结果
     */
    public int deleteCourierByCourierId(String courierId);

    /**
     * 批量删除配送员
     * 
     * @param courierIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourierByCourierIds(String[] courierIds);
}
