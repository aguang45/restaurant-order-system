package com.restaurant.system.service.impl;

import java.util.List;

import com.restaurant.common.utils.uuid.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.restaurant.system.mapper.TableMapper;
import com.restaurant.system.domain.Table;
import com.restaurant.system.service.ITableService;

/**
 * 桌位Service业务层处理
 * 
 * @author aguang
 * @date 2023-12-28
 */
@Service
public class TableServiceImpl implements ITableService 
{
    @Autowired
    private TableMapper tableMapper;

    /**
     * 查询桌位
     * 
     * @param tableId 桌位主键
     * @return 桌位
     */
    @Override
    public Table selectTableByTableId(String tableId)
    {
        return tableMapper.selectTableByTableId(tableId);
    }

    /**
     * 查询桌位列表
     * 
     * @param table 桌位
     * @return 桌位
     */
    @Override
    public List<Table> selectTableList(Table table)
    {
        return tableMapper.selectTableList(table);
    }

    /**
     * 新增桌位
     * 
     * @param table 桌位
     * @return 结果
     */
    @Override
    public int insertTable(Table table)
    {
        table.setTableId(UUID.fastUUID().toString(true));
        return tableMapper.insertTable(table);
    }

    /**
     * 修改桌位
     * 
     * @param table 桌位
     * @return 结果
     */
    @Override
    public int updateTable(Table table)
    {
        return tableMapper.updateTable(table);
    }

    /**
     * 批量删除桌位
     * 
     * @param tableIds 需要删除的桌位主键
     * @return 结果
     */
    @Override
    public int deleteTableByTableIds(String[] tableIds)
    {
        return tableMapper.deleteTableByTableIds(tableIds);
    }

    /**
     * 删除桌位信息
     * 
     * @param tableId 桌位主键
     * @return 结果
     */
    @Override
    public int deleteTableByTableId(String tableId)
    {
        return tableMapper.deleteTableByTableId(tableId);
    }
}
