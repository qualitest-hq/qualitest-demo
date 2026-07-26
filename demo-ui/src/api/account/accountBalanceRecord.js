import request from '@/utils/request'

// 查询余额流水列表
export function listAccountBalanceRecord(query) {
  return request({
    url: '/web/account/accountBalanceRecord/list',
    method: 'get',
    params: query
  })
}

// 查询余额流水详细
export function getAccountBalanceRecord(balanceRecordId) {
  return request({
    url: '/web/account/accountBalanceRecord/' + balanceRecordId,
    method: 'get'
  })
}
