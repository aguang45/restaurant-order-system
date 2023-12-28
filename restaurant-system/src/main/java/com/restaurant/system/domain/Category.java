package com.restaurant.system.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

/**
 * 类别对象 res_category
 * 
 * @author aguang
 * @date 2023-12-28
 */
public class Category extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类别id */
    private String categoryId;

    /** 菜品类别 */
    @Excel(name = "菜品类别")
    private String categoryName;

    /** 排序 */
    @Excel(name = "排序")
    private Long categorySort;


    /** 逻辑删除 */
    private Integer isDeleted;

    public void setCategoryId(String categoryId) 
    {
        this.categoryId = categoryId;
    }

    public String getCategoryId() 
    {
        return categoryId;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setCategorySort(Long categorySort) 
    {
        this.categorySort = categorySort;
    }

    public Long getCategorySort() 
    {
        return categorySort;
    }
    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("categoryName", getCategoryName())
            .append("categorySort", getCategorySort())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
