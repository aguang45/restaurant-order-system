package com.restaurant.system.mapper;

import java.util.List;
import com.restaurant.system.domain.Table;

/**
 * 桌位Mapper接口
 * 
 * @author aguang
 * @date 2023-12-28
 */
public interface TableMapper 
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
     * 删除桌位
     * 
     * @param tableId 桌位主键
     * @return 结果
     */
    public int deleteTableByTableId(String tableId);

    /**
     * 批量删除桌位
     * 
     * @param tableIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTableByTableIds(String[] tableIds);
}
