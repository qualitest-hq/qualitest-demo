import { debounce } from '@/utils'
import { listAccount } from '@/api/account/account'
import { listCoupon } from '@/api/coupon/coupon'
import { listMallCategory } from '@/api/mall/mallCategory'
import { listMallProduct } from '@/api/mall/mallProduct'
import { listMallProductSku } from '@/api/mall/mallProductSku'
import { listMallOrder } from '@/api/mall/mallOrder'
import { listMallOrderRefund } from '@/api/mall/mallOrderRefund'

const PAGE_SIZE = 20

/**
 * 通用远程下拉搜索（分页 + 防抖，避免一次加载全量）
 */
export function useRemoteSelect(fetchFn, config = {}) {
  const {
    valueKey,
    labelFn,
    buildQuery = (keyword) => ({}),
    debounceMs = 300,
    pageSize = PAGE_SIZE,
  } = config

  const loading = ref(false)
  const options = ref([])

  const remoteSearch = debounce(async (keyword) => {
    loading.value = true
    try {
      const res = await fetchFn({
        pageNum: 1,
        pageSize,
        ...buildQuery(keyword || ''),
      })
      options.value = (res.rows || []).map(row => ({
        value: row[valueKey],
        label: labelFn(row),
        raw: row,
      }))
    } finally {
      loading.value = false
    }
  }, debounceMs)

  /** 路由预填或编辑回显时补全选项 label */
  async function ensureOption(value, getFn, rowLabelFn) {
    if (value == null || value === '') return
    if (options.value.some(o => String(o.value) === String(value))) return
    try {
      const res = await getFn(value)
      const row = res.data
      if (row) {
        options.value = [{ value: row[valueKey], label: rowLabelFn(row), raw: row }, ...options.value]
      }
    } catch (_) { /* ignore */ }
  }

  return reactive({ loading, options, remoteSearch, ensureOption })
}

export function useAccountRemoteSelect() {
  return useRemoteSelect(listAccount, {
    valueKey: 'accountId',
    labelFn: (row) => {
      const parts = [row.nickName, row.mobile].filter(Boolean)
      return parts.length ? parts.join(' / ') : `账号${row.accountId}`
    },
    buildQuery: (keyword) => {
      if (!keyword) return {}
      if (/^\d+$/.test(keyword)) return { mobile: keyword }
      return { nickName: keyword }
    },
  })
}

export function useCouponRemoteSelect() {
  return useRemoteSelect(listCoupon, {
    valueKey: 'couponId',
    labelFn: (row) => row.couponName || `优惠券${row.couponId}`,
    buildQuery: (keyword) => (keyword ? { couponName: keyword } : {}),
  })
}

export function useCategoryRemoteSelect() {
  return useRemoteSelect(listMallCategory, {
    valueKey: 'categoryId',
    labelFn: (row) => row.categoryName || `分类${row.categoryId}`,
    buildQuery: (keyword) => (keyword ? { categoryName: keyword } : {}),
  })
}

export function useProductRemoteSelect() {
  return useRemoteSelect(listMallProduct, {
    valueKey: 'productId',
    labelFn: (row) => row.productName || `商品${row.productId}`,
    buildQuery: (keyword) => (keyword ? { productName: keyword } : {}),
  })
}

export function useSkuRemoteSelect(extraQueryRef) {
  return useRemoteSelect(listMallProductSku, {
    valueKey: 'skuId',
    labelFn: (row) => {
      const name = row.skuName || `SKU${row.skuId}`
      return row.productName ? `${row.productName} - ${name}` : name
    },
    buildQuery: (keyword) => {
      const q = keyword ? { skuName: keyword } : {}
      const extra = extraQueryRef?.value || extraQueryRef
      if (extra?.productId) q.productId = extra.productId
      return q
    },
  })
}

export function useOrderRemoteSelect() {
  return useRemoteSelect(listMallOrder, {
    valueKey: 'orderId',
    labelFn: (row) => row.orderNo || `订单${row.orderId}`,
    buildQuery: (keyword) => (keyword ? { orderNo: keyword } : {}),
  })
}

export function useRefundRemoteSelect() {
  return useRemoteSelect(listMallOrderRefund, {
    valueKey: 'refundId',
    labelFn: (row) => row.refundNo || `退款${row.refundId}`,
    buildQuery: (keyword) => (keyword ? { refundNo: keyword } : {}),
  })
}
