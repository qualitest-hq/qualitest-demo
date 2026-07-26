import request from '@/utils/request'

// 查询账号收货地址列表
export function listAccountAddress(query) {
  return request({
    url: '/web/account/accountAddress/list',
    method: 'get',
    params: query
  })
}

// 查询账号收货地址详细
export function getAccountAddress(addressId) {
  return request({
    url: '/web/account/accountAddress/' + addressId,
    method: 'get'
  })
}

// 新增账号收货地址
export function addAccountAddress(data) {
  return request({
    url: '/web/account/accountAddress',
    method: 'post',
    data: data
  })
}

// 修改账号收货地址
export function updateAccountAddress(data) {
  return request({
    url: '/web/account/accountAddress',
    method: 'put',
    data: data
  })
}

// 删除账号收货地址
export function delAccountAddress(addressId) {
  return request({
    url: '/web/account/accountAddress/' + addressId,
    method: 'delete'
  })
}
