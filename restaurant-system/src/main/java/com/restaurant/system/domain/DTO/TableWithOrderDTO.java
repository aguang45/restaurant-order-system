package com.restaurant.system.domain.DTO;

/**
 * @author aguang
 */
public class TableWithOrderDTO {
    private String tableId;
    private String orderId;
    private String orderStatus;
    private String orderTime;
    private String tableName;
    private Integer isFree;
    private Integer tableSize;

    @Override
    public String toString() {
        return "TableWithOrderDTO{" +
                "tableId='" + tableId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", tableName='" + tableName + '\'' +
                ", isFree='" + isFree + '\'' +
                ", tableSize=" + tableSize +
                '}';
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getTableSize() {
        return tableSize;
    }

    public void setTableSize(Integer tableSize) {
        this.tableSize = tableSize;
    }
}
