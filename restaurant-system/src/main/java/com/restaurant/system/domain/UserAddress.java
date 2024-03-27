package com.restaurant.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

/**
 * 收货地址对象 res_user_address
 * 
 * @author aguang
 * @date 2023-12-28
 */
public class UserAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地址id */
    private String addressId;

    /** 用户id */
    @Excel(name = "用户id")
    private String userId;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 收货人 */
    @Excel(name = "收货人")
    private String name;

    /** 收货人性别 */
    @Excel(name = "收货人性别")
    private String sex;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String detailAddress;

    /** 是否默认地址 */
    @Excel(name = "是否默认地址")
    private Integer isDefault;

    /** 逻辑删除 */
    private Integer isDeleted;

    public void setAddressId(String addressId) 
    {
        this.addressId = addressId;
    }

    public String getAddressId() 
    {
        return addressId;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setDetailAddress(String detailAddress) 
    {
        this.detailAddress = detailAddress;
    }

    public String getDetailAddress() 
    {
        return detailAddress;
    }
    public void setIsDefault(Integer isDefault) 
    {
        this.isDefault = isDefault;
    }

    public Integer getIsDefault() 
    {
        return isDefault;
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
            .append("addressId", getAddressId())
            .append("userId", getUserId())
            .append("phone", getPhone())
            .append("name", getName())
            .append("sex", getSex())
            .append("detailAddress", getDetailAddress())
            .append("isDefault", getIsDefault())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
