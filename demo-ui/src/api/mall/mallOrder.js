import request from '@/utils/request'

// 查询商城订单列表
export function listMallOrder(query) {
  return request({
    url: '/web/mall/mallOrder/list',
    method: 'get',
    params: query
  })
}

// 查询商城订单详细
export function getMallOrder(orderId) {
  return request({
    url: '/web/mall/mallOrder/' + orderId,
    method: 'get'
  })
}

// 新增商城订单
export function addMallOrder(data) {
  return request({
    url: '/web/mall/mallOrder',
    method: 'post',
    data: data
  })
}

// 修改商城订单
export function updateMallOrder(data) {
  return request({
    url: '/web/mall/mallOrder',
    method: 'put',
    data: data
  })
}

// 删除商城订单
export function delMallOrder(orderId) {
  return request({
    url: '/web/mall/mallOrder/' + orderId,
    method: 'delete'
  })
}

// 查询商城订单详情（含明细）
export function getMallOrderDetail(orderId) {
  return request({
    url: '/web/mall/mallOrder/' + orderId + '/detail',
    method: 'get'
  })
}

// 发货
export function deliverMallOrder(orderId) {
  return request({
    url: '/web/mall/mallOrder/' + orderId + '/deliver',
    method: 'post'
  })
}

// 关闭订单
export function closeMallOrder(orderId, data) {
  return request({
    url: '/web/mall/mallOrder/' + orderId + '/close',
    method: 'post',
    data: data
  })
}
