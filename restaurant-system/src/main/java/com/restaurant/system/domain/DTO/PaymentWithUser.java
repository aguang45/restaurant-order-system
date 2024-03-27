package com.restaurant.system.domain.DTO;

import com.restaurant.common.annotation.Excel;
import com.restaurant.system.domain.Payment;

/**
 * @author aguang
 */
public class PaymentWithUser extends Payment {
    @Excel(name = "用户名")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
