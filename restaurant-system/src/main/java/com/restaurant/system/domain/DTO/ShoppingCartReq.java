package com.restaurant.system.domain.DTO;

import com.restaurant.system.domain.Dish;
import com.restaurant.system.domain.ShoppingCart;

import java.util.List;

public class ShoppingCartReq {
    private String tableId;
    private String addressId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    private List<ShoppingCartDTO> shoppingCartList;

    @Override
    public String toString() {
        return "ShoppingCartReq{" +
                "tableId='" + tableId + '\'' +
                ", shoppingCartList=" + shoppingCartList +
                '}';
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public List<ShoppingCartDTO> getShoppingCartList() {
        return shoppingCartList;
    }

    public void setShoppingCartList(List<ShoppingCartDTO> shoppingCartList) {
        this.shoppingCartList = shoppingCartList;
    }
}
