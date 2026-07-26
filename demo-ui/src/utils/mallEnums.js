/** 商城业务枚举（硬编码，不接入 sys_dict） */

export const ORDER_STATUS_OPTIONS = [
  { label: '待付款', value: 0, elTagType: 'warning' },
  { label: '待发货', value: 1, elTagType: 'primary' },
  { label: '待收货', value: 2, elTagType: 'primary' },
  { label: '已完成', value: 3, elTagType: 'success' },
  { label: '已取消', value: 4, elTagType: 'info' },
  { label: '退款中', value: 5, elTagType: 'danger' }
]

export const PAY_STATUS_OPTIONS = [
  { label: '待支付', value: 0, elTagType: 'warning' },
  { label: '已支付', value: 1, elTagType: 'success' },
  { label: '已关闭', value: 2, elTagType: 'info' }
]

export const PAY_TYPE_OPTIONS = [
  { label: '未选', value: 0, elTagType: 'info' },
  { label: '微信', value: 1, elTagType: 'success' },
  { label: '支付宝', value: 2, elTagType: 'primary' },
  { label: '余额', value: 3, elTagType: 'warning' }
]

/** 订单表 refund_status */
export const ORDER_REFUND_STATUS_OPTIONS = [
  { label: '无退款', value: 0, elTagType: 'info' },
  { label: '部分退款', value: 1, elTagType: 'warning' },
  { label: '全额退款', value: 2, elTagType: 'danger' },
  { label: '退款处理中', value: 3, elTagType: 'danger' }
]

/** 退款单 refund_status */
export const REFUND_STATUS_OPTIONS = [
  { label: '待审核', value: 0, elTagType: 'warning' },
  { label: '已通过', value: 1, elTagType: 'primary' },
  { label: '已拒绝', value: 2, elTagType: 'info' },
  { label: '退款中', value: 3, elTagType: 'warning' },
  { label: '已完成', value: 4, elTagType: 'success' },
  { label: '已取消', value: 5, elTagType: 'info' }
]

export const REFUND_TYPE_OPTIONS = [
  { label: '仅退款', value: 1, elTagType: 'warning' },
  { label: '退货退款', value: 2, elTagType: 'danger' }
]

export const SHELF_STATUS_OPTIONS = [
  { label: '下架', value: 0, elTagType: 'info' },
  { label: '上架', value: 1, elTagType: 'success' }
]

export const NORMAL_DISABLE_OPTIONS = [
  { label: '正常', value: 0, elTagType: 'success' },
  { label: '停用', value: 1, elTagType: 'danger' }
]

export const COUPON_STATUS_OPTIONS = [
  { label: '未使用', value: 0, elTagType: 'primary' },
  { label: '已使用', value: 1, elTagType: 'success' },
  { label: '已过期', value: 2, elTagType: 'info' }
]

export const GENDER_OPTIONS = [
  { label: '男', value: 0 },
  { label: '女', value: 1 },
  { label: '未知', value: 2 }
]

export function getEnumLabel(options, value) {
  if (value === null || value === undefined || value === '') {
    return ''
  }
  const item = options.find(o => o.value == value)
  return item ? item.label : String(value)
}
