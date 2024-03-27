package com.restaurant.system.service;

import java.util.List;

import com.restaurant.system.domain.DTO.UserAddressWithUser;
import com.restaurant.system.domain.UserAddress;

/**
 * 收货地址Service接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface IUserAddressService 
{
    /**
     * 查询收货地址
     * 
     * @param addressId 收货地址主键
     * @return 收货地址
     */
    public UserAddress selectUserAddressByAddressId(String addressId);

    /**
     * 查询收货地址列表
     * 
     * @param userAddress 收货地址
     * @return 收货地址集合
     */
    public List<UserAddressWithUser> selectUserAddressList(UserAddress userAddress);

    /**
     * 查询收货地址列表
     *
     * @param userId 用户ID
     * @return 收货地址集合
     */
    public List<UserAddressWithUser> selectUserAddressAppList(String addressId);

    /**
     * 新增收货地址
     * 
     * @param userAddress 收货地址
     * @return 结果
     */
    public int insertUserAddress(UserAddress userAddress);

    /**
     * 修改收货地址
     * 
     * @param userAddress 收货地址
     * @return 结果
     */
    public int updateUserAddress(UserAddress userAddress);

    /**
     * 批量删除收货地址
     * 
     * @param addressIds 需要删除的收货地址主键集合
     * @return 结果
     */
    public int deleteUserAddressByAddressIds(String[] addressIds);

    /**
     * 删除收货地址信息
     * 
     * @param addressId 收货地址主键
     * @return 结果
     */
    public int deleteUserAddressByAddressId(String addressId);
}
