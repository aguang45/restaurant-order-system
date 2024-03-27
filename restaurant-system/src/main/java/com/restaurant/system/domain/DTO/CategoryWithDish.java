package com.restaurant.system.domain.DTO;

import com.restaurant.common.annotation.Excel;
import com.restaurant.system.domain.Dish;

import java.util.List;

public class CategoryWithDish {
    private static final long serialVersionUID = 1L;

    /** 类别id */
    private String id;

    /** 类别名称 */
    private String name;

    /** 菜品列表 */
    private List<CategoryWithDishChildren> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryWithDishChildren> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryWithDishChildren> children) {
        this.children = children;
    }
}


