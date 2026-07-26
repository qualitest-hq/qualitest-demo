import request from '@/utils/request'

/** 测试场景列表 */
export function listScenarios() {
  return request({
    url: '/web/test/scenario/list',
    method: 'get'
  })
}

/** 当前已加载场景 */
export function getCurrentScenario() {
  return request({
    url: '/web/test/scenario/current',
    method: 'get'
  })
}

/** 重置基线 seed */
export function resetBaseline() {
  return request({
    url: '/web/test/scenario/reset',
    method: 'post'
  })
}

/** 加载测试场景（默认 reset + load） */
export function loadScenario(id, noReset = false) {
  return request({
    url: '/web/test/scenario/load/' + id,
    method: 'post',
    params: { noReset }
  })
}
