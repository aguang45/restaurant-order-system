import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/system/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单列表
export function listOrderForList(query) {
  return request({
    url: '/system/order/getOrderWithOtherForList',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(orderId) {
  return request({
    url: '/system/order/' + orderId,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/system/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/system/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(orderId) {
  return request({
    url: '/system/order/' + orderId,
    method: 'delete'
  })
}

// 获取热门数据
export function getTopData() {
  return request({
    url: '/system/order/getTopData',
    method: 'get'
  })
}

// 查询外卖订单列表
export function listOrderWithOther(query) {
  return request({
    url: '/system/order/getOrderWithOther',
    method: 'get',
    data: query
  })
}
