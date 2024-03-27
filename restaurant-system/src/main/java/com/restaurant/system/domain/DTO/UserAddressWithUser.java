package com.restaurant.system.domain.DTO;

import com.restaurant.common.annotation.Excel;
import com.restaurant.system.domain.UserAddress;

public class UserAddressWithUser extends UserAddress {
    @Excel(name = "用户名称")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
