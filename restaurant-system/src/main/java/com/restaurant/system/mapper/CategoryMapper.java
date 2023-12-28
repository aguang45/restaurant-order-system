package com.restaurant.system.mapper;

import java.util.List;
import com.restaurant.system.domain.Category;

/**
 * 类别Mapper接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface CategoryMapper 
{
    /**
     * 查询类别
     * 
     * @param categoryId 类别主键
     * @return 类别
     */
    public Category selectCategoryByCategoryId(String categoryId);

    /**
     * 查询类别列表
     * 
     * @param category 类别
     * @return 类别集合
     */
    public List<Category> selectCategoryList(Category category);

    /**
     * 新增类别
     * 
     * @param category 类别
     * @return 结果
     */
    public int insertCategory(Category category);

    /**
     * 修改类别
     * 
     * @param category 类别
     * @return 结果
     */
    public int updateCategory(Category category);

    /**
     * 删除类别
     * 
     * @param categoryId 类别主键
     * @return 结果
     */
    public int deleteCategoryByCategoryId(String categoryId);

    /**
     * 批量删除类别
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCategoryByCategoryIds(String[] categoryIds);
}
