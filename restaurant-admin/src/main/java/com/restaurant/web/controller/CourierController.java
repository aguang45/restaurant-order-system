package com.restaurant.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.restaurant.system.domain.Courier;
import com.restaurant.system.service.ICourierService;
import com.restaurant.common.utils.poi.ExcelUtil;
import com.restaurant.common.core.page.TableDataInfo;

/**
 * 配送员Controller
 * 
 * @author aguang
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/system/courier")
public class CourierController extends BaseController
{
    @Autowired
    private ICourierService courierService;

    /**
     * 查询配送员列表
     */
    @PreAuthorize("@ss.hasPermi('system:courier:list')")
    @GetMapping("/list")
    public TableDataInfo list(Courier courier)
    {
        startPage();
        List<Courier> list = courierService.selectCourierList(courier);
        return getDataTable(list);
    }

    /**
     * 导出配送员列表
     */
    @PreAuthorize("@ss.hasPermi('system:courier:export')")
    @Log(title = "配送员", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Courier courier)
    {
        List<Courier> list = courierService.selectCourierList(courier);
        ExcelUtil<Courier> util = new ExcelUtil<Courier>(Courier.class);
        util.exportExcel(response, list, "配送员数据");
    }

    /**
     * 获取配送员详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:courier:query')")
    @GetMapping(value = "/{courierId}")
    public AjaxResult getInfo(@PathVariable("courierId") String courierId)
    {
        return success(courierService.selectCourierByCourierId(courierId));
    }

    /**
     * 新增配送员
     */
    @PreAuthorize("@ss.hasPermi('system:courier:add')")
    @Log(title = "配送员", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Courier courier)
    {
        return toAjax(courierService.insertCourier(courier));
    }

    /**
     * 修改配送员
     */
    @PreAuthorize("@ss.hasPermi('system:courier:edit')")
    @Log(title = "配送员", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Courier courier)
    {
        return toAjax(courierService.updateCourier(courier));
    }

    /**
     * 删除配送员
     */
    @PreAuthorize("@ss.hasPermi('system:courier:remove')")
    @Log(title = "配送员", businessType = BusinessType.DELETE)
	@DeleteMapping("/{courierIds}")
    public AjaxResult remove(@PathVariable String[] courierIds)
    {
        return toAjax(courierService.deleteCourierByCourierIds(courierIds));
    }
}
