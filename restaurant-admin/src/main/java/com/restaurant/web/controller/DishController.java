package com.restaurant.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.common.annotation.Anonymous;
import com.restaurant.common.core.domain.model.LoginUser;
import com.restaurant.common.utils.ServletUtils;
import com.restaurant.framework.web.service.TokenService;
import com.restaurant.system.domain.DTO.CategoryWithDish;
import com.restaurant.system.domain.DTO.DishTop;
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
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private TokenService tokenService;

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
     * 客户端查询菜品列表
     */
    @Anonymous
    @GetMapping("/appList")
    public List<CategoryWithDish> listAll()
    {
        return dishService.selectCategoryWithDishList();
    }

    /**
     * 查询菜品列表
     *
     * @param dish
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:dish:query')")
    @GetMapping("/dishNameList")
    public List<Dish> dishNameList(Dish dish)
    {
        List<Dish> list = dishService.selectDishNameList(dish);
        return list;
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

    /**
     * 获取菜品排行榜
     */
    @PreAuthorize("@ss.hasPermi('system:dish:add')")
    @GetMapping("/getDishTop")
    public AjaxResult getDishTop()
    {
        return success(dishService.selectDishTopList(null));
    }

    /**
     * 导入菜品数据
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "菜品数据", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Dish> util = new ExcelUtil<Dish>(Dish.class);
        List<Dish> list = util.importExcel("菜品信息",file.getInputStream(),1);
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        String operName = loginUser.getUsername();
        String message = dishService.importDish(list, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 导出菜品模板
     * @param response
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Dish> util = new ExcelUtil<Dish>(Dish.class);
        util.importTemplateExcel(response,"菜品信息","菜品信息");
    }
}
