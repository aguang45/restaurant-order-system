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
import com.restaurant.system.domain.OrderDetail;
import com.restaurant.system.service.IOrderDetailService;
import com.restaurant.common.utils.poi.ExcelUtil;
import com.restaurant.common.core.page.TableDataInfo;

/**
 * 订单详情Controller
 * 
 * @author aguang
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/system/detail")
public class OrderDetailController extends BaseController
{
    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 查询订单详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderDetail orderDetail)
    {
        startPage();
        List<OrderDetail> list = orderDetailService.selectOrderDetailList(orderDetail);
        return getDataTable(list);
    }

    /**
     * 导出订单详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:detail:export')")
    @Log(title = "订单详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderDetail orderDetail)
    {
        List<OrderDetail> list = orderDetailService.selectOrderDetailList(orderDetail);
        ExcelUtil<OrderDetail> util = new ExcelUtil<OrderDetail>(OrderDetail.class);
        util.exportExcel(response, list, "订单详情数据");
    }

    /**
     * 获取订单详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:detail:query')")
    @GetMapping(value = "/{orderDetailId}")
    public AjaxResult getInfo(@PathVariable("orderDetailId") String orderDetailId)
    {
        return success(orderDetailService.selectOrderDetailByOrderDetailId(orderDetailId));
    }

    /**
     * 获取订单详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:detail:query')")
    @GetMapping(value = "/getInfoByOrderId/{orderId}")
    public AjaxResult getInfoByOrderId(@PathVariable("orderId") String orderId)
    {
        return success(orderDetailService.selectOrderDetailByOrderId(orderId));
    }

    /**
     * 新增订单详情
     */
    @PreAuthorize("@ss.hasPermi('system:detail:add')")
    @Log(title = "订单详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderDetail orderDetail)
    {
        return toAjax(orderDetailService.insertOrderDetail(orderDetail));
    }

    /**
     * 修改订单详情
     */
    @PreAuthorize("@ss.hasPermi('system:detail:edit')")
    @Log(title = "订单详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderDetail orderDetail)
    {
        return toAjax(orderDetailService.updateOrderDetail(orderDetail));
    }

    /**
     * 删除订单详情
     */
    @PreAuthorize("@ss.hasPermi('system:detail:remove')")
    @Log(title = "订单详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderDetailIds}")
    public AjaxResult remove(@PathVariable String[] orderDetailIds)
    {
        return toAjax(orderDetailService.deleteOrderDetailByOrderDetailIds(orderDetailIds));
    }
}
