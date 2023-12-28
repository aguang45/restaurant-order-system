package com.restaurant.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

/**
 * 购物车对象 res_shopping_cart
 * 
 * @author aguang
 * @date 2023-12-28
 */
public class ShoppingCart extends BaseEntity
{
    private static final long serialVersionUID = 1L;

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

    /** 逻辑删除 */
    private Integer isDeleted;

    public void setShoppingCartId(String shoppingCartId) 
    {
        this.shoppingCartId = shoppingCartId;
    }

    public String getShoppingCartId() 
    {
        return shoppingCartId;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setDishId(String dishId) 
    {
        this.dishId = dishId;
    }

    public String getDishId() 
    {
        return dishId;
    }
    public void setNumber(Long number) 
    {
        this.number = number;
    }

    public Long getNumber() 
    {
        return number;
    }
    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }
    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("shoppingCartId", getShoppingCartId())
            .append("userId", getUserId())
            .append("dishId", getDishId())
            .append("number", getNumber())
            .append("amount", getAmount())
            .append("createTime", getCreateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
