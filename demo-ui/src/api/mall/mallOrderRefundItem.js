import request from '@/utils/request'

// 查询商城退款明细列表
export function listMallOrderRefundItem(query) {
  return request({
    url: '/web/mall/mallOrderRefundItem/list',
    method: 'get',
    params: query
  })
}

// 查询商城退款明细详细
export function getMallOrderRefundItem(refundItemId) {
  return request({
    url: '/web/mall/mallOrderRefundItem/' + refundItemId,
    method: 'get'
  })
}

// 新增商城退款明细
export function addMallOrderRefundItem(data) {
  return request({
    url: '/web/mall/mallOrderRefundItem',
    method: 'post',
    data: data
  })
}

// 修改商城退款明细
export function updateMallOrderRefundItem(data) {
  return request({
    url: '/web/mall/mallOrderRefundItem',
    method: 'put',
    data: data
  })
}

// 删除商城退款明细
export function delMallOrderRefundItem(refundItemId) {
  return request({
    url: '/web/mall/mallOrderRefundItem/' + refundItemId,
    method: 'delete'
  })
}
