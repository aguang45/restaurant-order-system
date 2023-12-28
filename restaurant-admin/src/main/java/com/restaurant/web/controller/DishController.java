package com.restaurant.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.system.domain.DTO.DishWithCategory;
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
import com.restaurant.system.domain.Dish;
import com.restaurant.system.service.IDishService;
import com.restaurant.common.utils.poi.ExcelUtil;
import com.restaurant.common.core.page.TableDataInfo;

/**
 * 菜品Controller
 * 
 * @author agunag
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/system/dish")
public class DishController extends BaseController
{
    @Autowired
    private IDishService dishService;

    /**
     * 查询菜品列表
     */
    @PreAuthorize("@ss.hasPermi('system:dish:list')")
    @GetMapping("/list")
    public TableDataInfo list(Dish dish)
    {
        startPage();
        List<DishWithCategory> list = dishService.selectDishList(dish);
        return getDataTable(list);
    }

    /**
     * 导出菜品列表
     */
    @PreAuthorize("@ss.hasPermi('system:dish:export')")
    @Log(title = "菜品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Dish dish)
    {
        List<DishWithCategory> list = dishService.selectDishList(dish);
        ExcelUtil<DishWithCategory> util = new ExcelUtil<DishWithCategory>(DishWithCategory.class);
        util.exportExcel(response, list, "菜品数据");
    }

    /**
     * 获取菜品详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dish:query')")
    @GetMapping(value = "/{dishId}")
    public AjaxResult getInfo(@PathVariable("dishId") String dishId)
    {
        return success(dishService.selectDishByDishId(dishId));
    }

    /**
     * 新增菜品
     */
    @PreAuthorize("@ss.hasPermi('system:dish:add')")
    @Log(title = "菜品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Dish dish)
    {
        return toAjax(dishService.insertDish(dish));
    }

    /**
     * 修改菜品
     */
    @PreAuthorize("@ss.hasPermi('system:dish:edit')")
    @Log(title = "菜品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Dish dish)
    {
        return toAjax(dishService.updateDish(dish));
    }

    /**
     * 删除菜品
     */
    @PreAuthorize("@ss.hasPermi('system:dish:remove')")
    @Log(title = "菜品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dishIds}")
    public AjaxResult remove(@PathVariable String[] dishIds)
    {
        return toAjax(dishService.deleteDishByDishIds(dishIds));
    }
}
