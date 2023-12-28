package com.restaurant.system.service.impl;

import java.util.List;
import com.restaurant.common.utils.DateUtils;
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
public class ShoppingCartServiceImpl implements IShoppingCartService 
{
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    /**
     * 查询购物车
     * 
     * @param shoppingCartId 购物车主键
     * @return 购物车
     */
    @Override
    public ShoppingCart selectShoppingCartByShoppingCartId(String shoppingCartId)
    {
        return shoppingCartMapper.selectShoppingCartByShoppingCartId(shoppingCartId);
    }

    /**
     * 查询购物车列表
     * 
     * @param shoppingCart 购物车
     * @return 购物车
     */
    @Override
    public List<ShoppingCart> selectShoppingCartList(ShoppingCart shoppingCart)
    {
        return shoppingCartMapper.selectShoppingCartList(shoppingCart);
    }

    /**
     * 新增购物车
     * 
     * @param shoppingCart 购物车
     * @return 结果
     */
    @Override
    public int insertShoppingCart(ShoppingCart shoppingCart)
    {
        shoppingCart.setCreateTime(DateUtils.getNowDate());
        return shoppingCartMapper.insertShoppingCart(shoppingCart);
    }

    /**
     * 修改购物车
     * 
     * @param shoppingCart 购物车
     * @return 结果
     */
    @Override
    public int updateShoppingCart(ShoppingCart shoppingCart)
    {
        return shoppingCartMapper.updateShoppingCart(shoppingCart);
    }

    /**
     * 批量删除购物车
     * 
     * @param shoppingCartIds 需要删除的购物车主键
     * @return 结果
     */
    @Override
    public int deleteShoppingCartByShoppingCartIds(String[] shoppingCartIds)
    {
        return shoppingCartMapper.deleteShoppingCartByShoppingCartIds(shoppingCartIds);
    }

    /**
     * 删除购物车信息
     * 
     * @param shoppingCartId 购物车主键
     * @return 结果
     */
    @Override
    public int deleteShoppingCartByShoppingCartId(String shoppingCartId)
    {
        return shoppingCartMapper.deleteShoppingCartByShoppingCartId(shoppingCartId);
    }
}
