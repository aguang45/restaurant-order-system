package com.restaurant.system.domain.DTO;

/**
 * @author aguang
 */
public class CategoryNameAndSubtotal {
    private String categoryName;
    private String subtotal;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }
}
