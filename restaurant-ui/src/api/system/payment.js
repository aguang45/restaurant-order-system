import request from '@/utils/request'

// 查询支付信息列表
export function listPayment(query) {
  return request({
    url: '/system/payment/list',
    method: 'get',
    params: query
  })
}

// 查询支付信息详细
export function getPayment(paymentId) {
  return request({
    url: '/system/payment/' + paymentId,
    method: 'get'
  })
}

// 新增支付信息
export function addPayment(data) {
  return request({
    url: '/system/payment',
    method: 'post',
    data: data
  })
}

// 修改支付信息
export function updatePayment(data) {
  return request({
    url: '/system/payment',
    method: 'put',
    data: data
  })
}

// 删除支付信息
export function delPayment(paymentId) {
  return request({
    url: '/system/payment/' + paymentId,
    method: 'delete'
  })
}
