package com.restaurant.system.domain.DTO;

import com.restaurant.common.core.domain.BaseEntity;

public class ShoppingCartDTO extends BaseEntity {
    private String id;
    private String name;
    private int price;
    private String description;
    private String logo;
    private int goodNum;
    private String monthlySales;

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", logo='" + logo + '\'' +
                ", goodNum=" + goodNum +
                ", monthlySales='" + monthlySales + '\'' +
                '}';
    }

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public int getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }

    public String getMonthlySales() {
        return monthlySales;
    }

    public void setMonthlySales(String monthlySales) {
        this.monthlySales = monthlySales;
    }
}
