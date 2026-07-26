import request from '@/utils/request'

// 查询商城购物车列表
export function listMallCart(query) {
  return request({
    url: '/web/mall/mallCart/list',
    method: 'get',
    params: query
  })
}

// 查询商城购物车详细
export function getMallCart(cartId) {
  return request({
    url: '/web/mall/mallCart/' + cartId,
    method: 'get'
  })
}

// 新增商城购物车
export function addMallCart(data) {
  return request({
    url: '/web/mall/mallCart',
    method: 'post',
    data: data
  })
}

// 修改商城购物车
export function updateMallCart(data) {
  return request({
    url: '/web/mall/mallCart',
    method: 'put',
    data: data
  })
}

// 删除商城购物车
export function delMallCart(cartId) {
  return request({
    url: '/web/mall/mallCart/' + cartId,
    method: 'delete'
  })
}
