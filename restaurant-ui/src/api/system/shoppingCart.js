import request from '@/utils/request'

// 查询购物车列表
export function listShoppingCart(query) {
  return request({
    url: '/system/shoppingCart/list',
    method: 'get',
    params: query
  })
}

// 查询购物车详细
export function getShoppingCart(shoppingCartId) {
  return request({
    url: '/system/shoppingCart/' + shoppingCartId,
    method: 'get'
  })
}

// 新增购物车
export function addShoppingCart(data) {
  return request({
    url: '/system/shoppingCart',
    method: 'post',
    data: data
  })
}

// 修改购物车
export function updateShoppingCart(data) {
  return request({
    url: '/system/shoppingCart',
    method: 'put',
    data: data
  })
}

// 删除购物车
export function delShoppingCart(shoppingCartId) {
  return request({
    url: '/system/shoppingCart/' + shoppingCartId,
    method: 'delete'
  })
}
