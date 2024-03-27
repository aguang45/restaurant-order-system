package com.restaurant.system.domain.DTO;

import com.restaurant.system.domain.Order;

public class OrderWithOtherForList extends Order {
    private String userName;
    private String userPhone;
    private String phoneNumber;
    private String userSex;
    private String courierName;
    private String courierPhone;
    private String tableName;
    private String tableSize;
    private String userAddress;

    @Override
    public String toString() {
        return "OrderWithOtherForList{" +
                "userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userSex='" + userSex + '\'' +
                ", courierName='" + courierName + '\'' +
                ", courierPhone='" + courierPhone + '\'' +
                ", tableName='" + tableName + '\'' +
                ", tableSize='" + tableSize + '\'' +
                ", userAddress='" + userAddress + '\'' +
                '}';
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
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

    public String getTableSize() {
        return tableSize;
    }

    public void setTableSize(String tableSize) {
        this.tableSize = tableSize;
    }
}
