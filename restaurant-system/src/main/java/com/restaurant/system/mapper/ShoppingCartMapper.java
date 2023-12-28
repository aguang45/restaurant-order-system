package com.restaurant.system.mapper;

import java.util.List;
import com.restaurant.system.domain.ShoppingCart;

/**
 * 购物车Mapper接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface ShoppingCartMapper 
{
    /**
     * 查询购物车
     * 
     * @param shoppingCartId 购物车主键
     * @return 购物车
     */
    public ShoppingCart selectShoppingCartByShoppingCartId(String shoppingCartId);

    /**
     * 查询购物车列表
     * 
     * @param shoppingCart 购物车
     * @return 购物车集合
     */
    public List<ShoppingCart> selectShoppingCartList(ShoppingCart shoppingCart);

    /**
     * 新增购物车
     * 
     * @param shoppingCart 购物车
     * @return 结果
     */
    public int insertShoppingCart(ShoppingCart shoppingCart);

    /**
     * 修改购物车
     * 
     * @param shoppingCart 购物车
     * @return 结果
     */
    public int updateShoppingCart(ShoppingCart shoppingCart);

    /**
     * 删除购物车
     * 
     * @param shoppingCartId 购物车主键
     * @return 结果
     */
    public int deleteShoppingCartByShoppingCartId(String shoppingCartId);

    /**
     * 批量删除购物车
     * 
     * @param shoppingCartIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShoppingCartByShoppingCartIds(String[] shoppingCartIds);
}
