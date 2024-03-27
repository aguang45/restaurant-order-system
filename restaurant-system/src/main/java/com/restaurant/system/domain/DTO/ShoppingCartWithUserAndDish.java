package com.restaurant.system.domain.DTO;

import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

import java.math.BigDecimal;

public class ShoppingCartWithUserAndDish extends BaseEntity {

    /** 主键 */
    private String shoppingCartId;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 菜品id */
    @Excel(name = "菜品id")
    private String dishId;

    /** 数量 */
    @Excel(name = "数量")
    private Long number;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;


    /** 菜品名称 */
    @Excel(name = "菜品名称")
    private String dishName;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String nickName;

    /** 逻辑删除 */
    private Integer isDeleted;

    @Override
    public String toString() {
        return "ShoppingCartWithUserAndDish{" +
                "shoppingCartId='" + shoppingCartId + '\'' +
                ", userId='" + userId + '\'' +
                ", dishId='" + dishId + '\'' +
                ", number=" + number +
                ", amount=" + amount +
                ", dishName='" + dishName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
