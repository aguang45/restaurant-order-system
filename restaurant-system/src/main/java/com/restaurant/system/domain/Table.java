package com.restaurant.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.restaurant.common.annotation.Excel;
import com.restaurant.common.core.domain.BaseEntity;

/**
 * 桌位对象 res_table
 * 
 * @author aguang
 * @date 2023-12-28
 */
public class Table extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 桌位id */
    private String tableId;

    /** 桌位名称 */
    @Excel(name = "桌位名称")
    private String tableName;

    /** 桌位大小 */
    @Excel(name = "桌位大小")
    private Long tableSize;

    /** 桌位是否空闲，0为空闲 */
    @Excel(name = "桌位是否空闲，0为空闲")
    private Integer isFree;

    /** 逻辑删除，0未删除 */
    private Integer isDeleted;

    public void setTableId(String tableId) 
    {
        this.tableId = tableId;
    }

    public String getTableId() 
    {
        return tableId;
    }
    public void setTableName(String tableName) 
    {
        this.tableName = tableName;
    }

    public String getTableName() 
    {
        return tableName;
    }
    public void setTableSize(Long tableSize) 
    {
        this.tableSize = tableSize;
    }

    public Long getTableSize() 
    {
        return tableSize;
    }
    public void setIsFree(Integer isFree) 
    {
        this.isFree = isFree;
    }

    public Integer getIsFree() 
    {
        return isFree;
    }
    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tableId", getTableId())
            .append("tableName", getTableName())
            .append("tableSize", getTableSize())
            .append("isFree", getIsFree())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
