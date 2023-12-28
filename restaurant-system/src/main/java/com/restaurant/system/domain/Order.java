package com.restaurant.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

/**
 * 订单对象 res_order
 * 
 * @author aguang
 * @date 2023-12-28
 */
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单id */
    private String orderId;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 订单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderTime;

    /** 订单状态 */
    @Excel(name = "订单状态")
    private String orderStatus;

    /** 座位id */
    @Excel(name = "座位id")
    private String tableId;

    /** 配送地址id */
    @Excel(name = "配送地址id")
    private String addressId;

    /** 配送状态 */
    @Excel(name = "配送状态")
    private String deliveryStatus;

    /** 配送员id */
    @Excel(name = "配送员id")
    private String courierId;

    /** 逻辑删除 */
    private Integer isDeleted;

    public void setOrderId(String orderId) 
    {
        this.orderId = orderId;
    }

    public String getOrderId() 
    {
        return orderId;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setOrderTime(Date orderTime) 
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() 
    {
        return orderTime;
    }
    public void setOrderStatus(String orderStatus) 
    {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() 
    {
        return orderStatus;
    }
    public void setTableId(String tableId) 
    {
        this.tableId = tableId;
    }

    public String getTableId() 
    {
        return tableId;
    }
    public void setAddressId(String addressId) 
    {
        this.addressId = addressId;
    }

    public String getAddressId() 
    {
        return addressId;
    }
    public void setDeliveryStatus(String deliveryStatus) 
    {
        this.deliveryStatus = deliveryStatus;
    }

    public String getDeliveryStatus() 
    {
        return deliveryStatus;
    }
    public void setCourierId(String courierId) 
    {
        this.courierId = courierId;
    }

    public String getCourierId() 
    {
        return courierId;
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
            .append("orderId", getOrderId())
            .append("userId", getUserId())
            .append("orderTime", getOrderTime())
            .append("orderStatus", getOrderStatus())
            .append("tableId", getTableId())
            .append("addressId", getAddressId())
            .append("deliveryStatus", getDeliveryStatus())
            .append("courierId", getCourierId())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
