package com.restaurant.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.system.domain.DTO.CategoryWithDish;
import com.restaurant.system.domain.DTO.DishTop;
import com.restaurant.system.domain.DTO.DishWithCategory;
import com.restaurant.system.domain.Dish;

/**
 * 菜品Service接口
 * 
 * @author agunag
 * @date 2023-12-28
 */
public interface IDishService extends IService<Dish>
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
     * 批量删除菜品
     * 
     * @param dishIds 需要删除的菜品主键集合
     * @return 结果
     */
    public int deleteDishByDishIds(String[] dishIds);

    /**
     * 删除菜品信息
     * 
     * @param dishId 菜品主键
     * @return 结果
     */
    public int deleteDishByDishId(String dishId);

    List<CategoryWithDish> selectCategoryWithDishList();

    List<Dish> selectDishNameList(Dish dish);

    List<DishTop> selectDishTopList(Integer limit);

    String importDish(List<Dish> dishList, Boolean isUpdateSupport, String operName);
}
