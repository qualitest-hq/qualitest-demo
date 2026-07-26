import request from '@/utils/request'

// 查询商城商品SPU列表
export function listMallProduct(query) {
  return request({
    url: '/web/mall/mallProduct/list',
    method: 'get',
    params: query
  })
}

// 查询商城商品SPU详细
export function getMallProduct(productId) {
  return request({
    url: '/web/mall/mallProduct/' + productId,
    method: 'get'
  })
}

// 新增商城商品SPU
export function addMallProduct(data) {
  return request({
    url: '/web/mall/mallProduct',
    method: 'post',
    data: data
  })
}

// 修改商城商品SPU
export function updateMallProduct(data) {
  return request({
    url: '/web/mall/mallProduct',
    method: 'put',
    data: data
  })
}

// 删除商城商品SPU
export function delMallProduct(productId) {
  return request({
    url: '/web/mall/mallProduct/' + productId,
    method: 'delete'
  })
}

// 上架/下架
export function changeShelfStatus(data) {
  return request({
    url: '/web/mall/mallProduct/changeShelfStatus',
    method: 'put',
    data: data
  })
}
