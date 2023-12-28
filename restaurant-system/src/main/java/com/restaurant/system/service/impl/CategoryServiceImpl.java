package com.restaurant.system.service.impl;

import java.util.List;

import com.restaurant.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.CategoryMapper;
import com.restaurant.system.domain.Category;
import com.restaurant.system.service.ICategoryService;

/**
 * 类别Service业务层处理
 * 
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class CategoryServiceImpl implements ICategoryService 
{
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询类别
     * 
     * @param categoryId 类别主键
     * @return 类别
     */
    @Override
    public Category selectCategoryByCategoryId(String categoryId)
    {
        return categoryMapper.selectCategoryByCategoryId(categoryId);
    }

    /**
     * 查询类别列表
     * 
     * @param category 类别
     * @return 类别
     */
    @Override
    public List<Category> selectCategoryList(Category category)
    {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增类别
     * 
     * @param category 类别
     * @return 结果
     */
    @Override
    public int insertCategory(Category category)
    {
        category.setCategoryId(UUID.fastUUID().toString(true));
        return categoryMapper.insertCategory(category);
    }

    /**
     * 修改类别
     * 
     * @param category 类别
     * @return 结果
     */
    @Override
    public int updateCategory(Category category)
    {
        return categoryMapper.updateCategory(category);
    }

    /**
     * 批量删除类别
     * 
     * @param categoryIds 需要删除的类别主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByCategoryIds(String[] categoryIds)
    {
        return categoryMapper.deleteCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除类别信息
     * 
     * @param categoryId 类别主键
     * @return 结果
     */
    @Override
    public int deleteCategoryByCategoryId(String categoryId)
    {
        return categoryMapper.deleteCategoryByCategoryId(categoryId);
    }
}
