package com.restaurant.system.domain.DTO;

import com.restaurant.common.annotation.Excel;
import com.restaurant.system.domain.Order;
import com.restaurant.system.domain.OrderDetail;

import java.util.List;

/**
 * @author aguang
 */
public class OrderWithUser extends Order {
    @Excel(name = "用户姓名")
    private String userName;
    @Excel(name = "用户电话")
    private String userPhone;
    @Excel(name = "收货地址")
    private String userAddress;
    @Excel(name = "收货人性别")
    private String userSex;
    @Excel(name = "配送员姓名")
    private String courierName;
    @Excel(name = "配送员电话")
    private String courierPhone;
    @Excel(name = "桌号")
    private String tableName;
    private List<OrderDetailWithDish> orderDetails;

    public List<OrderDetailWithDish> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailWithDish> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierPhone() {
        return courierPhone;
    }

    public void setCourierPhone(String courierPhone) {
        this.courierPhone = courierPhone;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getCustomerName() {
        return courierName;
    }

    public void setCustomerName(String customerName) {
        this.courierName = customerName;
    }

    public String getCustomerPhone() {
        return courierPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.courierPhone = customerPhone;
    }
}
