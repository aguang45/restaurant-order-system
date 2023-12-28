package com.restaurant.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

/**
 * 菜品对象 res_dish
 * 
 * @author agunag
 * @date 2023-12-28
 */
public class Dish extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 菜品id */
    private String dishId;

    /** 菜品名称 */
    @Excel(name = "菜品名称")
    private String dishName;

    /** 菜品分类 */
    @Excel(name = "菜品分类")
    private String dishType;

    /** 菜品价格 */
    @Excel(name = "菜品价格")
    private BigDecimal dishPrice;

    /** 菜品描述 */
    @Excel(name = "菜品描述")
    private String dishDescription;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String dishImage;

    /** 逻辑删除 */
    private Integer isDeleted;

    public void setDishId(String dishId) 
    {
        this.dishId = dishId;
    }

    public String getDishId() 
    {
        return dishId;
    }
    public void setDishName(String dishName) 
    {
        this.dishName = dishName;
    }

    public String getDishName() 
    {
        return dishName;
    }
    public void setDishType(String dishType) 
    {
        this.dishType = dishType;
    }

    public String getDishType() 
    {
        return dishType;
    }
    public void setDishPrice(BigDecimal dishPrice) 
    {
        this.dishPrice = dishPrice;
    }

    public BigDecimal getDishPrice() 
    {
        return dishPrice;
    }
    public void setDishDescription(String dishDescription) 
    {
        this.dishDescription = dishDescription;
    }

    public String getDishDescription() 
    {
        return dishDescription;
    }
    public void setDishImage(String dishImage) 
    {
        this.dishImage = dishImage;
    }

    public String getDishImage() 
    {
        return dishImage;
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
            .append("dishId", getDishId())
            .append("dishName", getDishName())
            .append("dishType", getDishType())
            .append("dishPrice", getDishPrice())
            .append("dishDescription", getDishDescription())
            .append("dishImage", getDishImage())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
