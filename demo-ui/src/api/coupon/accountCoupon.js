import request from '@/utils/request'

// 查询账号优惠券列表
export function listAccountCoupon(query) {
  return request({
    url: '/web/coupon/accountCoupon/list',
    method: 'get',
    params: query
  })
}

// 查询账号优惠券详细
export function getAccountCoupon(accountCouponId) {
  return request({
    url: '/web/coupon/accountCoupon/' + accountCouponId,
    method: 'get'
  })
}

// 新增账号优惠券
export function addAccountCoupon(data) {
  return request({
    url: '/web/coupon/accountCoupon',
    method: 'post',
    data: data
  })
}

// 修改账号优惠券
export function updateAccountCoupon(data) {
  return request({
    url: '/web/coupon/accountCoupon',
    method: 'put',
    data: data
  })
}

// 删除账号优惠券
export function delAccountCoupon(accountCouponId) {
  return request({
    url: '/web/coupon/accountCoupon/' + accountCouponId,
    method: 'delete'
  })
}
