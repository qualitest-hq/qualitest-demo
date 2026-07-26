import request from '@/utils/request'

// 查询商城商品SKU列表
export function listMallProductSku(query) {
  return request({
    url: '/web/mall/mallProductSku/list',
    method: 'get',
    params: query
  })
}

// 查询商城商品SKU详细
export function getMallProductSku(skuId) {
  return request({
    url: '/web/mall/mallProductSku/' + skuId,
    method: 'get'
  })
}

// 新增商城商品SKU
export function addMallProductSku(data) {
  return request({
    url: '/web/mall/mallProductSku',
    method: 'post',
    data: data
  })
}

// 修改商城商品SKU
export function updateMallProductSku(data) {
  return request({
    url: '/web/mall/mallProductSku',
    method: 'put',
    data: data
  })
}

// 删除商城商品SKU
export function delMallProductSku(skuId) {
  return request({
    url: '/web/mall/mallProductSku/' + skuId,
    method: 'delete'
  })
}
