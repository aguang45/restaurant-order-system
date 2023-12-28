import request from '@/utils/request'

// 查询配送员列表
export function listCourier(query) {
  return request({
    url: '/system/courier/list',
    method: 'get',
    params: query
  })
}

// 查询配送员详细
export function getCourier(courierId) {
  return request({
    url: '/system/courier/' + courierId,
    method: 'get'
  })
}

// 新增配送员
export function addCourier(data) {
  return request({
    url: '/system/courier',
    method: 'post',
    data: data
  })
}

// 修改配送员
export function updateCourier(data) {
  return request({
    url: '/system/courier',
    method: 'put',
    data: data
  })
}

// 删除配送员
export function delCourier(courierId) {
  return request({
    url: '/system/courier/' + courierId,
    method: 'delete'
  })
}
