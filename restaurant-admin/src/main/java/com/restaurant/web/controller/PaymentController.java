package com.restaurant.web.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.common.annotation.Anonymous;
import com.restaurant.common.utils.uuid.UUID;
import com.restaurant.system.domain.DTO.PaymentWithUser;
import com.restaurant.system.domain.Order;
import com.restaurant.system.domain.OrderDetail;
import com.restaurant.system.service.IOrderDetailService;
import com.restaurant.system.service.IOrderService;
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
import com.restaurant.system.domain.Payment;
import com.restaurant.system.service.IPaymentService;
import com.restaurant.common.utils.poi.ExcelUtil;
import com.restaurant.common.core.page.TableDataInfo;

/**
 * 支付信息Controller
 * 
 * @author aguang
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/system/payment")
public class PaymentController extends BaseController
{
    @Autowired
    private IPaymentService resPaymentService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IOrderDetailService orderDetailService;

    /**
     * 查询支付信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:payment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Payment payment)
    {
        startPage();
        List<PaymentWithUser> list = resPaymentService.selectResPaymentList(payment);
        return getDataTable(list);
    }

    /**
     * 导出支付信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:payment:export')")
    @Log(title = "支付信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Payment payment)
    {
        List<PaymentWithUser> list = resPaymentService.selectResPaymentList(payment);
        ExcelUtil<PaymentWithUser> util = new ExcelUtil<PaymentWithUser>(PaymentWithUser.class);
        util.exportExcel(response, list, "支付信息数据");
    }

    /**
     * 获取支付信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:payment:query')")
    @GetMapping(value = "/{paymentId}")
    public AjaxResult getInfo(@PathVariable("paymentId") String paymentId)
    {
        return success(resPaymentService.selectResPaymentByPaymentId(paymentId));
    }

    /**
     * 新增支付信息
     */
//    @PreAuthorize("@ss.hasPermi('system:payment:add')")
    @Anonymous
    @Log(title = "支付信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Payment payment)
    {
        payment.setPaymentId(UUID.fastUUID().toString(true));
        String orderId = payment.getOrderId();
        Order order = orderService.selectOrderByOrderId(orderId);
        order.setOrderId(orderId);
        order.setOrderStatus("1");
        payment.setOrderId(orderId);
        payment.setPaymentTime(LocalDateTime.now());
//        计算总价
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(orderId);
        List<OrderDetail> orderDetailList = orderDetailService.selectOrderDetailList(orderDetail);
        BigDecimal amount = new BigDecimal(0);
        for (OrderDetail orderDetail1 : orderDetailList) {
            amount = amount.add(orderDetail1.getSubtotal());
        }
        payment.setAmount(amount);
        payment.setPaymentMethod("1");

        orderService.updateOrder(order);
        resPaymentService.insertResPayment(payment);
        return success(payment);
    }

    /**
     * 修改支付信息
     */
    @PreAuthorize("@ss.hasPermi('system:payment:edit')")
    @Log(title = "支付信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Payment payment)
    {
        return toAjax(resPaymentService.updateResPayment(payment));
    }

    /**
     * 删除支付信息
     */
    @PreAuthorize("@ss.hasPermi('system:payment:remove')")
    @Log(title = "支付信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{paymentIds}")
    public AjaxResult remove(@PathVariable String[] paymentIds)
    {
        return toAjax(resPaymentService.deleteResPaymentByPaymentIds(paymentIds));
    }

    /**
     * 获取今日各分类销售额
     * @return
     */
    @Anonymous
    @GetMapping("/todayCategoryNameAndSubtotal")
    public AjaxResult todayCategoryNameAndSubtotal() {
        return AjaxResult.success(resPaymentService.selectTodayCategoryNameAndSubtotal());
    }

    /**
     * 获取昨日各分类销售额
     * @return
     */
    @Anonymous
    @GetMapping("/yesterdayCategoryNameAndSubtotal")
    public AjaxResult yesterdayCategoryNameAndSubtotal() {
        return AjaxResult.success(resPaymentService.selectYesterdayCategoryNameAndSubtotal());
    }
}
