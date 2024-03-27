package com.restaurant.system.domain.DTO;

import java.math.BigDecimal;

public class CategoryWithDishChildren {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private BigDecimal price;
    private String description;
    private String logo;
    private String goodNum;
    private String monthlySales;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(String goodNum) {
        this.goodNum = goodNum;
    }

    public String getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(String monthlySales) {
        this.monthlySales = monthlySales;
    }

}
