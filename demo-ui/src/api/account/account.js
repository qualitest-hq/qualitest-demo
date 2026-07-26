import request from '@/utils/request'

// 查询用户账号列表
export function listAccount(query) {
  return request({
    url: '/web/account/account/list',
    method: 'get',
    params: query
  })
}

// 查询用户账号详细
export function getAccount(accountId) {
  return request({
    url: '/web/account/account/' + accountId,
    method: 'get'
  })
}

// 新增用户账号
export function addAccount(data) {
  return request({
    url: '/web/account/account',
    method: 'post',
    data: data
  })
}

// 修改用户账号
export function updateAccount(data) {
  return request({
    url: '/web/account/account',
    method: 'put',
    data: data
  })
}

// 赠送账户余额
export function giftBalance(data) {
  return request({
    url: '/web/account/account/giftBalance',
    method: 'put',
    data: data
  })
}

// 删除用户账号
export function delAccount(accountId) {
  return request({
    url: '/web/account/account/' + accountId,
    method: 'delete'
  })
}
