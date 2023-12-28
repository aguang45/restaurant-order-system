package com.restaurant.system.mapper;

import java.util.List;

import com.restaurant.system.domain.DTO.DishWithCategory;
import com.restaurant.system.domain.Dish;

/**
 * 菜品Mapper接口
 * 
 * @author agunag
 * @date 2023-12-28
 */
public interface DishMapper 
{
    /**
     * 查询菜品
     * 
     * @param dishId 菜品主键
     * @return 菜品
     */
    public DishWithCategory selectDishByDishId(String dishId);

    /**
     * 查询菜品列表
     * 
     * @param dish 菜品
     * @return 菜品集合
     */
    public List<DishWithCategory> selectDishList(Dish dish);

    /**
     * 新增菜品
     * 
     * @param dish 菜品
     * @return 结果
     */
    public int insertDish(Dish dish);

    /**
     * 修改菜品
     * 
     * @param dish 菜品
     * @return 结果
     */
    public int updateDish(Dish dish);

    /**
     * 删除菜品
     * 
     * @param dishId 菜品主键
     * @return 结果
     */
    public int deleteDishByDishId(String dishId);

    /**
     * 批量删除菜品
     * 
     * @param dishIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDishByDishIds(String[] dishIds);
}
