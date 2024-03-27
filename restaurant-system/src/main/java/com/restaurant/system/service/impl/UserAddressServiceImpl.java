package com.restaurant.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.restaurant.common.core.domain.entity.SysUser;
import com.restaurant.common.utils.SecurityUtils;
import com.restaurant.common.utils.bean.BeanUtils;
import com.restaurant.common.utils.uuid.UUID;
import com.restaurant.system.domain.DTO.UserAddressWithUser;
import com.restaurant.system.mapper.SysUserMapper;
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
public class UserAddressServiceImpl implements IUserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询收货地址
     *
     * @param addressId 收货地址主键
     * @return 收货地址
     */
    @Override
    public UserAddress selectUserAddressByAddressId(String addressId) {
        return userAddressMapper.selectUserAddressByAddressId(addressId);
    }

    /**
     * 查询收货地址列表
     *
     * @param userAddress 收货地址
     * @return 收货地址
     */
    @Override
    public List<UserAddressWithUser> selectUserAddressList(UserAddress userAddress) {
//        List<UserAddressWithUser> list = new ArrayList<>();
        List<UserAddress> userAddresses = userAddressMapper.selectUserAddressList(userAddress);
//        UserAddressWithUser userAddressWithUser = new UserAddressWithUser();
//        userAddresses.forEach(address -> {
//            SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(address.getUserId()));
//            userAddressWithUser.setUserName(sysUser.getUserName());
//            BeanUtils.copyBeanProp(userAddressWithUser, address);
//            list.add(userAddressWithUser);
//        });

        return userAddresses.stream()
                .map(address -> {
                    UserAddressWithUser userAddressWithUser = new UserAddressWithUser();
                    SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(address.getUserId()));
                    userAddressWithUser.setUserName(sysUser.getUserName());
                    BeanUtils.copyBeanProp(userAddressWithUser, address);
                    return userAddressWithUser;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserAddressWithUser> selectUserAddressAppList(String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(String.valueOf(SecurityUtils.getUserId()));
        userAddress.setAddressId(addressId);
        List<UserAddress> userAddresses = userAddressMapper.selectUserAddressList(userAddress);

        return userAddresses.stream()
                .map(address -> {
                    UserAddressWithUser userAddressWithUser = new UserAddressWithUser();
                    SysUser sysUser = sysUserMapper.selectUserById(Long.parseLong(address.getUserId()));
                    userAddressWithUser.setUserName(sysUser.getUserName());
                    BeanUtils.copyBeanProp(userAddressWithUser, address);
                    return userAddressWithUser;
                })
                .collect(Collectors.toList());
    }

    /**
     * 新增收货地址
     *
     * @param userAddress 收货地址
     * @return 结果
     */
    @Override
    public int insertUserAddress(UserAddress userAddress) {
        userAddress.setCreateBy(SecurityUtils.getUsername());
        userAddress.setUserId(String.valueOf(SecurityUtils.getUserId()));
        userAddress.setAddressId(UUID.randomUUID().toString(true));
        if (userAddress.getIsDefault() == 1) {
            userAddressMapper.updateDefaultAddress(userAddress);
        }
        return userAddressMapper.insertUserAddress(userAddress);
    }

    /**
     * 修改收货地址
     *
     * @param userAddress 收货地址
     * @return 结果
     */
    @Override
    public int updateUserAddress(UserAddress userAddress) {
        userAddress.setCreateBy(SecurityUtils.getUsername());
        userAddress.setUserId(String.valueOf(SecurityUtils.getUserId()));
        if (userAddress.getIsDefault() == 1) {
            userAddressMapper.updateDefaultAddress(userAddress);
        }
        return userAddressMapper.updateUserAddress(userAddress);
    }

    /**
     * 批量删除收货地址
     *
     * @param addressIds 需要删除的收货地址主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressByAddressIds(String[] addressIds) {
        return userAddressMapper.deleteUserAddressByAddressIds(addressIds);
    }

    /**
     * 删除收货地址信息
     *
     * @param addressId 收货地址主键
     * @return 结果
     */
    @Override
    public int deleteUserAddressByAddressId(String addressId) {
        return userAddressMapper.deleteUserAddressByAddressId(addressId);
    }
}
