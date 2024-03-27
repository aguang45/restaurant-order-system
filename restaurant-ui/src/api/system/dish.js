import request from '@/utils/request'

// 查询菜品列表
export function listDish(query) {
  return request({
    url: '/system/dish/list',
    method: 'get',
    params: query
  })
}

// 查询菜系及菜品列表
export function listCategoryWithDish(query) {
  return request({
    url: '/system/dish/appList',
    method: 'get',
    params: query
  })
}

// 查询菜品名字列表
export function listDishName(query) {
  return request({
    url: '/system/dish/dishNameList',
    method: 'get',
    params: query
  })
}

// 查询菜品详细
export function getDish(dishId) {
  return request({
    url: '/system/dish/' + dishId,
    method: 'get'
  })
}

// 新增菜品
export function addDish(data) {
  return request({
    url: '/system/dish',
    method: 'post',
    data: data
  })
}

// 修改菜品
export function updateDish(data) {
  return request({
    url: '/system/dish',
    method: 'put',
    data: data
  })
}

// 删除菜品
export function delDish(dishId) {
  return request({
    url: '/system/dish/' + dishId,
    method: 'delete'
  })
}

// 获取菜品排行榜
export function getDishTop() {
  return request({
    url: '/system/dish/getDishTop',
    method: 'get'
  })
}
