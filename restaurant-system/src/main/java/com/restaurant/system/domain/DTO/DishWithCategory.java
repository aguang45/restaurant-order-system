package com.restaurant.system.domain.DTO;

import com.restaurant.common.annotation.Excel;

import java.math.BigDecimal;

public class DishWithCategory {

    private static final long serialVersionUID = 1L;

    /** 菜品id */
    private String dishId;

    /** 菜品名称 */
    @Excel(name = "菜品名称")
    private String dishName;

    @Override
    public String toString() {
        return "DishWithCategory{" +
                "dishId='" + dishId + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishType='" + dishType + '\'' +
                ", dishPrice=" + dishPrice +
                ", dishDescription='" + dishDescription + '\'' +
                ", dishImage='" + dishImage + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categorySort=" + categorySort +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public BigDecimal getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(BigDecimal dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategorySort() {
        return categorySort;
    }

    public void setCategorySort(Long categorySort) {
        this.categorySort = categorySort;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

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


}
