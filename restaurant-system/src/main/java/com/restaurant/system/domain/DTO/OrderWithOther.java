package com.restaurant.system.domain.DTO;

import com.restaurant.system.domain.Order;

public class OrderWithOther extends Order {
    private String userName;
    private String name;
    private String detailAddress;
    private String phone;
    private String courierName;
    private String courierPhone;

    @Override
    public String toString() {
        return "OrderWithOther{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", detailAddress='" + detailAddress + '\'' +
                ", phone='" + phone + '\'' +
                ", courierName='" + courierName + '\'' +
                ", courierPhone='" + courierPhone + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
