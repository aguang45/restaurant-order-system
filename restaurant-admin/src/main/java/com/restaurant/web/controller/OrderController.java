package com.restaurant.web.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.common.utils.SecurityUtils;
import com.restaurant.common.utils.uuid.UUID;
import com.restaurant.system.domain.*;
import com.restaurant.system.domain.DTO.*;
import com.restaurant.system.service.IDishService;
import com.restaurant.system.service.IOrderDetailService;
import com.restaurant.system.service.ITableService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.restaurant.system.service.IOrderService;
import com.restaurant.common.utils.poi.ExcelUtil;
import com.restaurant.common.core.page.TableDataInfo;

/**
 * 订单Controller
 * 
 * @author aguang
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/system/order")
public class OrderController extends BaseController
{
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private ITableService tableService;

    @Autowired
    private IDishService dishService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order order)
    {
        startPage();
        List<OrderWithUser> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/getOrderWithOtherForList")
    public TableDataInfo getOrderWithOtherForList(Order order)
    {
        startPage();
        List<OrderWithOtherForList> list = orderService.getOrderWithOtherForList(order);
        return getDataTable(list);
    }

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @PostMapping("/appList")
    public TableDataInfo appList(@RequestBody Order order)
    {
        startPage();
        List<OrderWithUser> list = orderService.selectOrderAppList(order);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order order)
    {
        List<OrderWithUser> list = orderService.selectOrderList(order);
        ExcelUtil<OrderWithUser> util = new ExcelUtil<OrderWithUser>(OrderWithUser.class);
        util.exportExcel(response, list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") String orderId)
    {
        return success(orderService.selectOrderByOrderId(orderId));
    }

    /**
     * 新增订单
     */
//    @PreAuthorize("@ss.hasPermi('system:order:add')")
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    @Transactional
    public AjaxResult add(@RequestBody ShoppingCartReq shoppingCartReq)
    {
        // 获取当前的用户
        String userId = String.valueOf(SecurityUtils.getUserId());
        LocalDateTime now = LocalDateTime.now();
        Order order = new Order();
        String orderId = UUID.fastUUID().toString(true);
        order.setOrderId(orderId);
        order.setUserId(userId);
        order.setTableId(shoppingCartReq.getTableId());
        order.setAddressId(shoppingCartReq.getAddressId());
        order.setOrderStatus("0");
        order.setOrderTime(now);
        order.setIsDeleted(0);
        orderService.insertOrder(order);

        // 插入订单详情
        for(ShoppingCartDTO shoppingCart : shoppingCartReq.getShoppingCartList()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setOrderDetailId(UUID.fastUUID().toString(true));
            orderDetail.setDishId(shoppingCart.getId());
            DishWithCategory dishWithCategory = dishService.selectDishByDishId(shoppingCart.getId());
            int price = dishWithCategory.getDishPrice().intValue();
            int goodNum = shoppingCart.getGoodNum();
            BigDecimal subtotal = new BigDecimal(price * goodNum);
            orderDetail.setDishQuantity(Long.valueOf(goodNum));
            orderDetail.setSubtotal(subtotal);

            orderDetail.setIsDeleted(0);
            orderDetailService.insertOrderDetail(orderDetail);
        }

        // 修改桌子状态
        Table table = new Table();
        table.setTableId(shoppingCartReq.getTableId());
        table.setIsFree(0);
        tableService.updateTable(table);
        return success(orderId);
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Order order)
    {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable String[] orderIds)
    {
        return toAjax(orderService.deleteOrderByOrderIds(orderIds));
    }

    /**
     * 获取主页顶部卡片数据
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/getTopData")
    public AjaxResult getTopData() {
        return AjaxResult.success(orderService.getTopData());
    }

    /**
     * 获取外卖订单详情
     * @param orderWithOther
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/getOrderWithOther")
    public AjaxResult getOrderWithOtherList(OrderWithOther orderWithOther) {
        return AjaxResult.success(orderService.getOrderWithOtherList(orderWithOther));
    }
}
