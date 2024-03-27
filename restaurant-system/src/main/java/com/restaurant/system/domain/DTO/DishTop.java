package com.restaurant.system.domain.DTO;

/**
 * 热门菜品对象 dish
 */
public class DishTop {
    private String categoryName;
    private String dishName;
    private String dishPrice;
    private int count;

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
