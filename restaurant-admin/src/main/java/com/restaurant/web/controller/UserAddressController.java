package com.restaurant.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.system.domain.DTO.UserAddressWithUser;
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
import com.restaurant.system.domain.UserAddress;
import com.restaurant.system.service.IUserAddressService;
import com.restaurant.common.utils.poi.ExcelUtil;
import com.restaurant.common.core.page.TableDataInfo;

/**
 * 收货地址Controller
 * 
 * @author aguang
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/system/address")
public class UserAddressController extends BaseController
{
    @Autowired
    private IUserAddressService userAddressService;

    /**
     * 查询收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserAddress userAddress)
    {
        startPage();
        List<UserAddressWithUser> list = userAddressService.selectUserAddressList(userAddress);
        return getDataTable(list);
    }

    /**
     * 查询收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:query')")
    @GetMapping("/appList")
    public TableDataInfo appList(String addressId)
    {
        startPage();
        List<UserAddressWithUser> list = userAddressService.selectUserAddressAppList(addressId);
        return getDataTable(list);
    }

    /**
     * 导出收货地址列表
     */
    @PreAuthorize("@ss.hasPermi('system:address:export')")
    @Log(title = "收货地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserAddress userAddress)
    {
        List<UserAddressWithUser> list = userAddressService.selectUserAddressList(userAddress);
        ExcelUtil<UserAddressWithUser> util = new ExcelUtil<UserAddressWithUser>(UserAddressWithUser.class);
        util.exportExcel(response, list, "收货地址数据");
    }

    /**
     * 获取收货地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:address:query')")
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") String addressId)
    {
        return success(userAddressService.selectUserAddressByAddressId(addressId));
    }

    /**
     * 新增收货地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:add')")
    @Log(title = "收货地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserAddress userAddress)
    {
        return toAjax(userAddressService.insertUserAddress(userAddress));
    }

    /**
     * 修改收货地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:edit')")
    @Log(title = "收货地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserAddress userAddress)
    {
        return toAjax(userAddressService.updateUserAddress(userAddress));
    }

    /**
     * 删除收货地址
     */
    @PreAuthorize("@ss.hasPermi('system:address:remove')")
    @Log(title = "收货地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable String[] addressIds)
    {
        return toAjax(userAddressService.deleteUserAddressByAddressIds(addressIds));
    }
}
