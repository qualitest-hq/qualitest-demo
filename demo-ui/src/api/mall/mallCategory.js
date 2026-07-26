import request from '@/utils/request'

// 查询商城商品分类列表
export function listMallCategory(query) {
  return request({
    url: '/web/mall/mallCategory/list',
    method: 'get',
    params: query
  })
}

// 查询商城商品分类详细
export function getMallCategory(categoryId) {
  return request({
    url: '/web/mall/mallCategory/' + categoryId,
    method: 'get'
  })
}

// 新增商城商品分类
export function addMallCategory(data) {
  return request({
    url: '/web/mall/mallCategory',
    method: 'post',
    data: data
  })
}

// 修改商城商品分类
export function updateMallCategory(data) {
  return request({
    url: '/web/mall/mallCategory',
    method: 'put',
    data: data
  })
}

// 删除商城商品分类
export function delMallCategory(categoryId) {
  return request({
    url: '/web/mall/mallCategory/' + categoryId,
    method: 'delete'
  })
}
