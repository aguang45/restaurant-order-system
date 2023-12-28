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
import com.restaurant.system.domain.ResPayment;
import com.restaurant.system.service.IResPaymentService;
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
public class ResPaymentController extends BaseController
{
    @Autowired
    private IResPaymentService resPaymentService;

    /**
     * 查询支付信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:payment:list')")
    @GetMapping("/list")
    public TableDataInfo list(ResPayment resPayment)
    {
        startPage();
        List<ResPayment> list = resPaymentService.selectResPaymentList(resPayment);
        return getDataTable(list);
    }

    /**
     * 导出支付信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:payment:export')")
    @Log(title = "支付信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ResPayment resPayment)
    {
        List<ResPayment> list = resPaymentService.selectResPaymentList(resPayment);
        ExcelUtil<ResPayment> util = new ExcelUtil<ResPayment>(ResPayment.class);
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
    @PreAuthorize("@ss.hasPermi('system:payment:add')")
    @Log(title = "支付信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ResPayment resPayment)
    {
        return toAjax(resPaymentService.insertResPayment(resPayment));
    }

    /**
     * 修改支付信息
     */
    @PreAuthorize("@ss.hasPermi('system:payment:edit')")
    @Log(title = "支付信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ResPayment resPayment)
    {
        return toAjax(resPaymentService.updateResPayment(resPayment));
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
}
