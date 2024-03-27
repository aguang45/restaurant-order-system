package com.restaurant.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.system.domain.DTO.ShoppingCartDTO;
import com.restaurant.system.domain.DTO.ShoppingCartWithUserAndDish;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.restaurant.common.annotation.Log;
import com.restaurant.common.core.controller.BaseController;
import com.restaurant.common.core.domain.AjaxResult;
import com.restaurant.common.enums.BusinessType;
import com.restaurant.system.domain.ShoppingCart;
import com.restaurant.system.service.IShoppingCartService;
import com.restaurant.common.utils.poi.ExcelUtil;
import com.restaurant.common.core.page.TableDataInfo;

/**
 * 购物车Controller
 * 
 * @author aguang
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/system/shoppingCart")
public class ShoppingCartController extends BaseController
{
    @Autowired
    private IShoppingCartService shoppingCartService;

    /**
     * 查询购物车列表
     */
    @PreAuthorize("@ss.hasPermi('system:shoppingCart:list')")
//    @PreAuthorize("@ss.hasAnyRoles('admin, common')")
    @GetMapping("/list")
    public TableDataInfo list(ShoppingCart shoppingCart)
    {
        startPage();
        List<ShoppingCartWithUserAndDish> list = shoppingCartService.selectShoppingCartList(shoppingCart);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @PreAuthorize("@ss.hasPermi('system:shoppingCart:export')")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShoppingCart shoppingCart)
    {
        List<ShoppingCartWithUserAndDish> list = shoppingCartService.selectShoppingCartList(shoppingCart);
        ExcelUtil<ShoppingCartWithUserAndDish> util = new ExcelUtil<ShoppingCartWithUserAndDish>(ShoppingCartWithUserAndDish.class);
        util.exportExcel(response, list, "购物车数据");
    }

    /**
     * 获取购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:shoppingCart:query')")
//    @PreAuthorize("@ss.hasAnyRoles('admin, common')")
    @GetMapping(value = "/{shoppingCartId}")
    public AjaxResult getInfo(@PathVariable("shoppingCartId") String shoppingCartId)
    {
        return success(shoppingCartService.selectShoppingCartByShoppingCartId(shoppingCartId));
    }

    /**
     * 新增购物车
     */
    @PreAuthorize("@ss.hasPermi('system:shoppingCart:add')")
//    @PreAuthorize("@ss.hasAnyRoles('admin, common')")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShoppingCart shoppingCart)
    {
        return toAjax(shoppingCartService.insertShoppingCart(shoppingCart));
    }

    /**
     * 修改购物车
     */
    @PreAuthorize("@ss.hasPermi('system:shoppingCart:edit')")
//    @PreAuthorize("@ss.hasAnyRoles('admin, common')")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody List<ShoppingCartDTO> list)
    {
        return toAjax(shoppingCartService.updateShoppingCart(list));
    }

    /**
     * 删除购物车
     */
    @PreAuthorize("@ss.hasPermi('system:shoppingCart:remove')")
//    @PreAuthorize("@ss.hasAnyRoles('admin, common')")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
	@DeleteMapping("/{shoppingCartIds}")
    public AjaxResult remove(@PathVariable String[] shoppingCartIds)
    {
        return toAjax(shoppingCartService.deleteShoppingCartByShoppingCartIds(shoppingCartIds));
    }
}
