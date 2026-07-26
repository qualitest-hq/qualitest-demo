import request from '@/utils/request'

// 查询商城退款单列表
export function listMallOrderRefund(query) {
  return request({
    url: '/web/mall/mallOrderRefund/list',
    method: 'get',
    params: query
  })
}

// 查询商城退款单详细
export function getMallOrderRefund(refundId) {
  return request({
    url: '/web/mall/mallOrderRefund/' + refundId,
    method: 'get'
  })
}

// 新增商城退款单
export function addMallOrderRefund(data) {
  return request({
    url: '/web/mall/mallOrderRefund',
    method: 'post',
    data: data
  })
}

// 修改商城退款单
export function updateMallOrderRefund(data) {
  return request({
    url: '/web/mall/mallOrderRefund',
    method: 'put',
    data: data
  })
}

// 删除商城退款单
export function delMallOrderRefund(refundId) {
  return request({
    url: '/web/mall/mallOrderRefund/' + refundId,
    method: 'delete'
  })
}

// 查询商城退款单详情（含退款明细）
export function getMallOrderRefundDetail(refundId) {
  return request({
    url: '/web/mall/mallOrderRefund/' + refundId + '/detail',
    method: 'get'
  })
}

// 审核通过
export function approveMallOrderRefund(refundId) {
  return request({
    url: '/web/mall/mallOrderRefund/' + refundId + '/approve',
    method: 'post'
  })
}

// 审核拒绝
export function rejectMallOrderRefund(refundId, data) {
  return request({
    url: '/web/mall/mallOrderRefund/' + refundId + '/reject',
    method: 'post',
    data: data
  })
}

// 确认退款完成
export function completeMallOrderRefund(refundId) {
  return request({
    url: '/web/mall/mallOrderRefund/' + refundId + '/complete',
    method: 'post'
  })
}
