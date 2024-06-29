package com.restaurant.system.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.common.exception.ServiceException;
import com.restaurant.common.utils.SecurityUtils;
import com.restaurant.common.utils.StringUtils;
import com.restaurant.common.utils.uuid.UUID;
import com.restaurant.system.domain.Category;
import com.restaurant.system.domain.DTO.CategoryWithDish;
import com.restaurant.system.domain.DTO.CategoryWithDishChildren;
import com.restaurant.system.domain.DTO.DishTop;
import com.restaurant.system.domain.DTO.DishWithCategory;
import com.restaurant.system.mapper.CategoryMapper;
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
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 查询菜品
     *
     * @param dishId 菜品主键
     * @return 菜品
     */
    @Override
    public DishWithCategory selectDishByDishId(String dishId) {
        return dishMapper.selectDishByDishId(dishId);
    }

    /**
     * 查询菜品列表
     *
     * @param dish 菜品
     * @return 菜品
     */
    @Override
    public List<DishWithCategory> selectDishList(Dish dish) {
        return dishMapper.selectDishList(dish);
    }

    /**
     * 新增菜品
     *
     * @param dish 菜品
     * @return 结果
     */
    @Override
    public int insertDish(Dish dish) {
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
    public int updateDish(Dish dish) {
        return dishMapper.updateDish(dish);
    }

    /**
     * 批量删除菜品
     *
     * @param dishIds 需要删除的菜品主键
     * @return 结果
     */
    @Override
    public int deleteDishByDishIds(String[] dishIds) {
        return dishMapper.deleteDishByDishIds(dishIds);
    }

    /**
     * 删除菜品信息
     *
     * @param dishId 菜品主键
     * @return 结果
     */
    @Override
    public int deleteDishByDishId(String dishId) {
        return dishMapper.deleteDishByDishId(dishId);
    }

    /**
     * 查询菜品列表,返回菜品分类及子级菜品
     *
     * @return
     */
    @Override
    public List<CategoryWithDish> selectCategoryWithDishList() {
        List<CategoryWithDish> categoryWithDishes = new ArrayList<>();
        CategoryWithDish categoryWithDish1 = new CategoryWithDish();
        categoryWithDish1.setId("1");
        categoryWithDish1.setName("推荐");
        List<CategoryWithDishChildren> childrens1 = new ArrayList<>();
//        获取热门菜品
        List<DishTop> dishTop = dishMapper.getDishTop(3);
//        获取用户最近购买过次数排行前三的菜品
        List<DishTop> dishsByUserId = dishMapper.getDishsByUserId(String.valueOf(SecurityUtils.getUserId()));
        if (!dishsByUserId.isEmpty()) {
            for (int i = 0; i < dishsByUserId.size(); i++) {
                DishTop dish = dishsByUserId.get(i);
                CategoryWithDishChildren categoryWithDishChildren = new CategoryWithDishChildren();
                categoryWithDishChildren.setId(dish.getDishId());
                categoryWithDishChildren.setName(dish.getDishName());
                categoryWithDishChildren.setPrice(new BigDecimal(dish.getDishPrice()));
                categoryWithDishChildren.setLogo(dish.getDishImage());
                categoryWithDishChildren.setDescription("最近两个月购买过 " + dish.getCount() + " 次");
                childrens1.add(categoryWithDishChildren);
            }
        }

        for (int i = 0; i < dishTop.size(); i++) {
            DishTop dish = dishTop.get(i);
            CategoryWithDishChildren categoryWithDishChildren = new CategoryWithDishChildren();
            categoryWithDishChildren.setId(dish.getDishId());
            categoryWithDishChildren.setName(dish.getDishName());
            if (!dish.getDishPrice().isEmpty()) {
                categoryWithDishChildren.setPrice(new BigDecimal(dish.getDishPrice()));
            }
            categoryWithDishChildren.setLogo(dish.getDishImage());
            categoryWithDishChildren.setDescription("HOT!!! 热卖第" + (i + 1) + "名");
            childrens1.add(categoryWithDishChildren);
        }
        categoryWithDish1.setChildren(childrens1);


        categoryWithDishes.add(categoryWithDish1);


        List<Category> categories = categoryMapper.selectCategoryList(null);
        for (Category category : categories) {
            CategoryWithDish categoryWithDish = new CategoryWithDish();
            categoryWithDish.setId(category.getCategoryId());
            categoryWithDish.setName(category.getCategoryName());

            List<DishWithCategory> dishes = dishMapper.selectDishList(new Dish(category.getCategoryId()));
            List<CategoryWithDishChildren> childrens = new ArrayList<>();
            for (DishWithCategory dish : dishes) {
                CategoryWithDishChildren categoryWithDishChildren = new CategoryWithDishChildren();
                categoryWithDishChildren.setId(dish.getDishId());
                categoryWithDishChildren.setName(dish.getDishName());
                categoryWithDishChildren.setPrice(dish.getDishPrice());
                categoryWithDishChildren.setLogo(dish.getDishImage());
                categoryWithDishChildren.setDescription(dish.getDishDescription());
                if (Objects.equals(dish.getDishType(), categoryWithDish.getId())) {
                    childrens.add(categoryWithDishChildren);
                }
            }
            categoryWithDish.setChildren(childrens);

            categoryWithDishes.add(categoryWithDish);
        }

        return categoryWithDishes;
    }

    @Override
    public List<Dish> selectDishNameList(Dish dish) {
        return dishMapper.selectDishNameList(dish);
    }

    @Override
    public List<DishTop> selectDishTopList(Integer limit) {
        return dishMapper.getDishTop(limit);
    }

    /**
     * 导入用户数据
     *
     * @param dishList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importDish(List<Dish> dishList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(dishList) || dishList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Dish dish : dishList) {
            try {
//                BeanValidators.validateWithException(validator, user);
                dish.setCreateBy(operName);
                this.insertDish(dish);
                successNum++;
                successMsg.append("<br/>" + successNum + "、账号 " + dish.getDishName() + " 导入成功");

            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + dish.getDishName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
