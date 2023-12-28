package com.restaurant.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

/**
 * 订单详情对象 res_order_detail
 * 
 * @author aguang
 * @date 2023-12-28
 */
public class OrderDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单详情id */
    private String orderDetailId;

    /** 对应的订单id */
    @Excel(name = "对应的订单id")
    private String orderId;

    /** 对应的菜品id */
    @Excel(name = "对应的菜品id")
    private String dishId;

    /** 菜品数量 */
    @Excel(name = "菜品数量")
    private Long dishQuantity;

    /** 小计金额 */
    @Excel(name = "小计金额")
    private BigDecimal subtotal;

    /** 逻辑删除，0未删除 */
    private Integer isDeleted;

    public void setOrderDetailId(String orderDetailId) 
    {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderDetailId() 
    {
        return orderDetailId;
    }
    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setDishId(String dishId) 
    {
        this.dishId = dishId;
    }

    public String getDishId() 
    {
        return dishId;
    }
    public void setDishQuantity(Long dishQuantity) 
    {
        this.dishQuantity = dishQuantity;
    }

    public Long getDishQuantity() 
    {
        return dishQuantity;
    }
    public void setSubtotal(BigDecimal subtotal) 
    {
        this.subtotal = subtotal;
    }

    public BigDecimal getSubtotal() 
    {
        return subtotal;
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
            .append("orderDetailId", getOrderDetailId())
            .append("orderId", getOrderId())
            .append("dishId", getDishId())
            .append("dishQuantity", getDishQuantity())
            .append("subtotal", getSubtotal())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
