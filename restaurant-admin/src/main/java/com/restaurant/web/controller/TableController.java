package com.restaurant.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.restaurant.system.domain.DTO.TableWithOrderDTO;
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
import com.restaurant.system.domain.Table;
import com.restaurant.system.service.ITableService;
import com.restaurant.common.utils.poi.ExcelUtil;
import com.restaurant.common.core.page.TableDataInfo;

/**
 * 桌位Controller
 * 
 * @author aguang
 * @date 2023-12-28
 */
@RestController
@RequestMapping("/system/table")
public class TableController extends BaseController
{
    @Autowired
    private ITableService tableService;

    /**
     * 查询桌位列表
     */
    @PreAuthorize("@ss.hasPermi('system:table:query')")
    @GetMapping("/list")
    public TableDataInfo list(Table table)
    {
        startPage();
        List<Table> list = tableService.selectTableList(table);
        return getDataTable(list);
    }

    /**
     * 导出桌位列表
     */
    @PreAuthorize("@ss.hasPermi('system:table:export')")
    @Log(title = "桌位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Table table)
    {
        List<Table> list = tableService.selectTableList(table);
        ExcelUtil<Table> util = new ExcelUtil<Table>(Table.class);
        util.exportExcel(response, list, "桌位数据");
    }

    /**
     * 获取桌位详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:table:query')")
    @GetMapping(value = "/{tableId}")
    public AjaxResult getInfo(@PathVariable("tableId") String tableId)
    {
        return success(tableService.selectTableByTableId(tableId));
    }

    /**
     * 新增桌位
     */
    @PreAuthorize("@ss.hasPermi('system:table:add')")
    @Log(title = "桌位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Table table)
    {
        return toAjax(tableService.insertTable(table));
    }

    /**
     * 修改桌位
     */
    @PreAuthorize("@ss.hasPermi('system:table:edit')")
    @Log(title = "桌位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Table table)
    {
        return toAjax(tableService.updateTable(table));
    }

    /**
     * 删除桌位
     */
    @PreAuthorize("@ss.hasPermi('system:table:remove')")
    @Log(title = "桌位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tableIds}")
    public AjaxResult remove(@PathVariable String[] tableIds)
    {
        return toAjax(tableService.deleteTableByTableIds(tableIds));
    }

    /**
     * 查询桌位列表
     * @param tableWithOrderDTO
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:table:query')")
    @GetMapping("/withOrderList")
    public TableDataInfo tableWithOrderList(TableWithOrderDTO tableWithOrderDTO)
    {
        startPage();
        List<TableWithOrderDTO> list = tableService.getTableWithOrder(tableWithOrderDTO);
        return getDataTable(list);
    }

    @Transactional
    @PreAuthorize("@ss.hasPermi('system:table:edit')")
    @PutMapping("/setTableFree")
    public AjaxResult setTableFree(String tableId, String orderId) {
        tableService.setTableFree(tableId, orderId);
        return success();
    }
}
