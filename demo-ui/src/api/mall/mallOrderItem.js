import request from '@/utils/request'

// 查询商城订单明细列表
export function listMallOrderItem(query) {
  return request({
    url: '/web/mall/mallOrderItem/list',
    method: 'get',
    params: query
  })
}

// 查询商城订单明细详细
export function getMallOrderItem(orderItemId) {
  return request({
    url: '/web/mall/mallOrderItem/' + orderItemId,
    method: 'get'
  })
}

// 新增商城订单明细
export function addMallOrderItem(data) {
  return request({
    url: '/web/mall/mallOrderItem',
    method: 'post',
    data: data
  })
}

// 修改商城订单明细
export function updateMallOrderItem(data) {
  return request({
    url: '/web/mall/mallOrderItem',
    method: 'put',
    data: data
  })
}

// 删除商城订单明细
export function delMallOrderItem(orderItemId) {
  return request({
    url: '/web/mall/mallOrderItem/' + orderItemId,
    method: 'delete'
  })
}
