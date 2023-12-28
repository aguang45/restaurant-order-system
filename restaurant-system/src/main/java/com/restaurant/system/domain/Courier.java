package com.restaurant.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

/**
 * 配送员对象 res_courier
 * 
 * @author aguang
 * @date 2023-12-28
 */
public class Courier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配送员id */
    private String courierId;

    /** 配送员姓名 */
    @Excel(name = "配送员姓名")
    private String courierName;

    /** 配送员手机号 */
    @Excel(name = "配送员手机号")
    private String courierPhone;

    /** 逻辑删除 */
    private Integer isDeleted;

    public void setCourierId(String courierId) 
    {
        this.courierId = courierId;
    }

    public String getCourierId() 
    {
        return courierId;
    }
    public void setCourierName(String courierName) 
    {
        this.courierName = courierName;
    }

    public String getCourierName() 
    {
        return courierName;
    }
    public void setCourierPhone(String courierPhone) 
    {
        this.courierPhone = courierPhone;
    }

    public String getCourierPhone() 
    {
        return courierPhone;
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
            .append("courierId", getCourierId())
            .append("courierName", getCourierName())
            .append("courierPhone", getCourierPhone())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
