package com.restaurant.system.service.impl;

import java.util.List;

import com.restaurant.common.utils.uuid.UUID;
import com.restaurant.system.domain.DTO.DishWithCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.DishMapper;
import com.restaurant.system.domain.Dish;
import com.restaurant.system.service.IDishService;

/**
 * 菜品Service业务层处理
 * 
 * @author agunag
 * @date 2023-12-28
 */
@Service
public class DishServiceImpl implements IDishService 
{
    @Autowired
    private DishMapper dishMapper;

    /**
     * 查询菜品
     * 
     * @param dishId 菜品主键
     * @return 菜品
     */
    @Override
    public DishWithCategory selectDishByDishId(String dishId)
    {
        return dishMapper.selectDishByDishId(dishId);
    }

    /**
     * 查询菜品列表
     * 
     * @param dish 菜品
     * @return 菜品
     */
    @Override
    public List<DishWithCategory> selectDishList(Dish dish)
    {
        return dishMapper.selectDishList(dish);
    }

    /**
     * 新增菜品
     * 
     * @param dish 菜品
     * @return 结果
     */
    @Override
    public int insertDish(Dish dish)
    {
        dish.setDishId(UUID.fastUUID().toString(true));
        return dishMapper.insertDish(dish);
    }

    /**
     * 修改菜品
     * 
     * @param dish 菜品
     * @return 结果
     */
    @Override
    public int updateDish(Dish dish)
    {
        return dishMapper.updateDish(dish);
    }

    /**
     * 批量删除菜品
     * 
     * @param dishIds 需要删除的菜品主键
     * @return 结果
     */
    @Override
    public int deleteDishByDishIds(String[] dishIds)
    {
        return dishMapper.deleteDishByDishIds(dishIds);
    }

    /**
     * 删除菜品信息
     * 
     * @param dishId 菜品主键
     * @return 结果
     */
    @Override
    public int deleteDishByDishId(String dishId)
    {
        return dishMapper.deleteDishByDishId(dishId);
    }
}
