package com.restaurant.system.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.restaurant.common.core.domain.entity.SysUser;
import com.restaurant.common.utils.DateUtils;
import com.restaurant.common.utils.SecurityUtils;
import com.restaurant.common.utils.uuid.UUID;
import com.restaurant.system.domain.DTO.DishWithCategory;
import com.restaurant.system.domain.DTO.ShoppingCartDTO;
import com.restaurant.system.domain.DTO.ShoppingCartWithUserAndDish;
import com.restaurant.system.mapper.DishMapper;
import com.restaurant.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.ShoppingCartMapper;
import com.restaurant.system.domain.ShoppingCart;
import com.restaurant.system.service.IShoppingCartService;

/**
 * 购物车Service业务层处理
 *
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询购物车
     *
     * @param shoppingCartId 购物车主键
     * @return 购物车
     */
    @Override
    public ShoppingCart selectShoppingCartByShoppingCartId(String shoppingCartId) {
        return shoppingCartMapper.selectShoppingCartByShoppingCartId(shoppingCartId);
    }

    /**
     * 查询购物车列表
     *
     * @param shoppingCart 购物车
     * @return 购物车
     */
    @Override
    public List<ShoppingCartWithUserAndDish> selectShoppingCartList(ShoppingCart shoppingCart) {
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectShoppingCartList(shoppingCart);
        List<ShoppingCartWithUserAndDish> shoppingCartWithUserAndDishes = new ArrayList<>();
        for (ShoppingCart shoppingCart1 : shoppingCarts) {
            DishWithCategory dishWithCategory = dishMapper.selectDishByDishId(shoppingCart1.getDishId());
            ShoppingCartWithUserAndDish shoppingCartWithUserAndDish = new ShoppingCartWithUserAndDish();
            shoppingCartWithUserAndDish.setShoppingCartId(shoppingCart1.getShoppingCartId());
            shoppingCartWithUserAndDish.setUserId(shoppingCart1.getUserId());
            shoppingCartWithUserAndDish.setDishId(shoppingCart1.getDishId());
            shoppingCartWithUserAndDish.setNumber(shoppingCart1.getNumber());
            shoppingCartWithUserAndDish.setAmount(shoppingCart1.getAmount());
            shoppingCartWithUserAndDish.setDishName(dishWithCategory.getDishName());
            SysUser sysUser = sysUserMapper.selectUserById(Long.valueOf(shoppingCart1.getUserId()));
            shoppingCartWithUserAndDish.setNickName(sysUser.getNickName());
            shoppingCartWithUserAndDish.setCreateTime(shoppingCart1.getCreateTime());
            shoppingCartWithUserAndDish.setIsDeleted(shoppingCart1.getIsDeleted());
            shoppingCartWithUserAndDishes.add(shoppingCartWithUserAndDish);
        }
        return shoppingCartWithUserAndDishes;
    }

    /**
     * 新增购物车
     *
     * @param shoppingCart 购物车
     * @return 结果
     */
    @Override
    public int insertShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setCreateTime(DateUtils.getNowDate());
        return shoppingCartMapper.insertShoppingCart(shoppingCart);
    }

    /**
     * 修改购物车
     *
     * @param
     * @return 结果
     */
    @Override
    public int updateShoppingCart(List<ShoppingCartDTO> list) {
        int i = 0;
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setUserId(String.valueOf(SecurityUtils.getUserId()));
        shoppingCartMapper.deleteShoppingCartByShoppingCart(shoppingCart1);
        for (ShoppingCartDTO shoppingCartDTO : list) {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(String.valueOf(SecurityUtils.getUserId()));
            shoppingCart.setDishId(shoppingCartDTO.getId());
            shoppingCart.setShoppingCartId(UUID.fastUUID().toString(true));
            shoppingCart.setNumber(Long.valueOf(shoppingCartDTO.getGoodNum()));
            shoppingCart.setCreateTime(DateUtils.getNowDate());
            shoppingCart.setIsDeleted(0);
            DishWithCategory dishWithCategory = dishMapper.selectDishByDishId(shoppingCartDTO.getId());
            shoppingCart.setAmount(dishWithCategory.getDishPrice().multiply(BigDecimal.valueOf(shoppingCart.getNumber())));
//            ShoppingCart shoppingCart1 = new ShoppingCart();
//            shoppingCart1.setUserId(String.valueOf(SecurityUtils.getUserId()));
//            shoppingCart1.setDishId(shoppingCartDTO.getId());
//            List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectShoppingCartList(shoppingCart1);
//            if (shoppingCarts.size() > 0) {
//                shoppingCart.setShoppingCartId(shoppingCarts.get(0).getShoppingCartId());
//                shoppingCartMapper.updateShoppingCart(shoppingCart);
//            } else {
            shoppingCartMapper.insertShoppingCart(shoppingCart);
//            }
            i++;
        }
        if (list.size() == i) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * 批量删除购物车
     *
     * @param shoppingCartIds 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteShoppingCartByShoppingCartIds(String[] shoppingCartIds) {
        return shoppingCartMapper.deleteShoppingCartByShoppingCartIds(shoppingCartIds);
    }

    /**
     * 删除购物车信息
     *
     * @param shoppingCartId 购物车主键
     * @return 结果
     */
    @Override
    public int deleteShoppingCartByShoppingCartId(String shoppingCartId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setShoppingCartId(shoppingCartId);
        return shoppingCartMapper.deleteShoppingCartByShoppingCart(shoppingCart);
    }
}
