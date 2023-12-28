package com.restaurant.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.UserAddressMapper;
import com.restaurant.system.domain.UserAddress;
import com.restaurant.system.service.IUserAddressService;

/**
 * 收货地址Service业务层处理
 * 
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class UserAddressServiceImpl implements IUserAddressService 
{
    @Autowired
    private UserAddressMapper userAddressMapper;

    /**
     * 查询收货地址
     * 
     * @param addressId 收货地址主键
     * @return 收货地址
     */
    @Override
    public UserAddress selectUserAddressByAddressId(String addressId)
    {
        return userAddressMapper.selectUserAddressByAddressId(addressId);
    }

    /**
     * 查询收货地址列表
     * 
     * @param userAddress 收货地址
     * @return 收货地址
     */
    @Override
    public List<UserAddress> selectUserAddressList(UserAddress userAddress)
    {
        return userAddressMapper.selectUserAddressList(userAddress);
    }

    /**
     * 新增收货地址
     * 
     * @param userAddress 收货地址
     * @return 结果
     */
    @Override
    public int insertUserAddress(UserAddress userAddress)
    {
        return userAddressMapper.insertUserAddress(userAddress);
    }

    /**
     * 修改收货地址
     * 
     * @param userAddress 收货地址
     * @return 结果
     */
    @Override
    public int updateUserAddress(UserAddress userAddress)
    {
        return userAddressMapper.updateUserAddress(userAddress);
    }

    /**
     * 批量删除收货地址
     * 
     * @param addressIds 需要删除的收货地址主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressByAddressIds(String[] addressIds)
    {
        return userAddressMapper.deleteUserAddressByAddressIds(addressIds);
    }

    /**
     * 删除收货地址信息
     * 
     * @param addressId 收货地址主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressByAddressId(String addressId)
    {
        return userAddressMapper.deleteUserAddressByAddressId(addressId);
    }
}
