package com.restaurant.system.domain.DTO;

import com.restaurant.common.annotation.Excel;

/**
 * 热门菜品对象 dish
 */
public class DishTop {
    /** 菜品id */
    private String dishId;
    private String categoryName;
    private String dishName;
    private String dishPrice;
    private int count;
    private String dishDescription;

    /** 图片地址 */
    private String dishImage;

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
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

    @Override
    public String toString() {
        return "DishTop{" +
                "categoryName='" + categoryName + '\'' +
                ", dishName='" + dishName + '\'' +
                ", dishPrice='" + dishPrice + '\'' +
                ", count=" + count +
                '}';
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
