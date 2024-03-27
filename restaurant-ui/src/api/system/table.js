import request from '@/utils/request'

// 查询桌位列表
export function listTable(query) {
  return request({
    url: '/system/table/list',
    method: 'get',
    params: query
  })
}

// 查询带订单信息桌位列表
export function listTableWithOrder(query) {
  return request({
    url: '/system/table/withOrderList',
    method: 'get',
    params: query
  })
}

// 查询带订单信息桌位列表
export function setTableFree(query) {
  return request({
    url: '/system/table/setTableFree',
    method: 'put',
    params: query
  })
}

// 查询桌位详细
export function getTable(tableId) {
  return request({
    url: '/system/table/' + tableId,
    method: 'get'
  })
}

// 新增桌位
export function addTable(data) {
  return request({
    url: '/system/table',
    method: 'post',
    data: data
  })
}

// 修改桌位
export function updateTable(data) {
  return request({
    url: '/system/table',
    method: 'put',
    data: data
  })
}

// 删除桌位
export function delTable(tableId) {
  return request({
    url: '/system/table/' + tableId,
    method: 'delete'
  })
}
