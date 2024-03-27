package com.restaurant.system.service;

import java.util.List;

import com.restaurant.system.domain.DTO.TableWithOrderDTO;
import com.restaurant.system.domain.Table;

/**
 * 桌位Service接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface ITableService 
{
    /**
     * 查询桌位
     * 
     * @param tableId 桌位主键
     * @return 桌位
     */
    public Table selectTableByTableId(String tableId);

    /**
     * 查询桌位列表
     * 
     * @param table 桌位
     * @return 桌位集合
     */
    public List<Table> selectTableList(Table table);

    /**
     * 新增桌位
     * 
     * @param table 桌位
     * @return 结果
     */
    public int insertTable(Table table);

    /**
     * 修改桌位
     * 
     * @param table 桌位
     * @return 结果
     */
    public int updateTable(Table table);

    /**
     * 批量删除桌位
     * 
     * @param tableIds 需要删除的桌位主键集合
     * @return 结果
     */
    public int deleteTableByTableIds(String[] tableIds);

    /**
     * 删除桌位信息
     * 
     * @param tableId 桌位主键
     * @return 结果
     */
    public int deleteTableByTableId(String tableId);

    /**
     * 获取桌位和订单信息
     * @param tableWithOrderDTO
     * @return
     */
    public List<TableWithOrderDTO> getTableWithOrder(TableWithOrderDTO tableWithOrderDTO);

    public boolean setTableFree(String tableId, String orderId);
}
