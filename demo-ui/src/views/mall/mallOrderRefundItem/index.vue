<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="退款单" prop="refundId">
        <RemoteSelect v-model="queryParams.refundId" :remote="refundRemote" placeholder="退款单号搜索" />
      </el-form-item>
      <el-form-item label="订单" prop="orderId">
        <RemoteSelect v-model="queryParams.orderId" :remote="orderRemote" placeholder="订单编号搜索" />
      </el-form-item>
      <el-form-item label="商品名称" prop="productName">
        <el-input v-model="queryParams.productName" placeholder="请输入商品名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['mall:mallOrderRefundItem:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mallOrderRefundItemList">
      <el-table-column label="退款明细ID" align="center" key="refundItemId" prop="refundItemId" width="110" v-if="columnVisible['refundItemId']" />
      <el-table-column label="退款单号" align="center" key="refundNo" prop="refundNo" min-width="150" show-overflow-tooltip v-if="columnVisible['refundNo']" />
      <el-table-column label="退款单ID" align="center" key="refundId" prop="refundId" width="100" v-if="columnVisible['refundId']" />
      <el-table-column label="订单ID" align="center" key="orderId" prop="orderId" width="100" v-if="columnVisible['orderId']" />
      <el-table-column label="商品名称" align="center" key="productName" prop="productName" min-width="120" show-overflow-tooltip v-if="columnVisible['productName']" />
      <el-table-column label="SKU名称" align="center" key="skuName" prop="skuName" min-width="100" show-overflow-tooltip v-if="columnVisible['skuName']" />
      <el-table-column label="订单明细ID" align="center" key="orderItemId" prop="orderItemId" width="110" v-if="columnVisible['orderItemId']" />
      <el-table-column label="退款数量" align="center" key="refundQuantity" prop="refundQuantity" width="90" v-if="columnVisible['refundQuantity']" />
      <el-table-column label="退款金额" align="center" key="refundAmount" prop="refundAmount" width="90" v-if="columnVisible['refundAmount']" />
      <el-table-column label="操作" align="center" width="80">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="退款明细详情" v-model="open" width="450px" append-to-body>
      <el-descriptions :column="1" border v-if="form.refundItemId">
        <el-descriptions-item label="退款明细ID">{{ form.refundItemId }}</el-descriptions-item>
        <el-descriptions-item label="退款单ID">{{ form.refundId }}</el-descriptions-item>
        <el-descriptions-item label="订单ID">{{ form.orderId }}</el-descriptions-item>
        <el-descriptions-item label="订单明细ID">{{ form.orderItemId }}</el-descriptions-item>
        <el-descriptions-item label="退款数量">{{ form.refundQuantity }}</el-descriptions-item>
        <el-descriptions-item label="退款金额">{{ form.refundAmount }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup name="MallOrderRefundItem">
import { listMallOrderRefundItem, getMallOrderRefundItem } from "@/api/mall/mallOrderRefundItem"
import { getMallOrderRefund } from "@/api/mall/mallOrderRefund"
import { getMallOrder } from "@/api/mall/mallOrder"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useOrderRemoteSelect, useRefundRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()
const route = useRoute()
const refundRemote = useRefundRemoteSelect()
const orderRemote = useOrderRemoteSelect()

const mallOrderRefundItemList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const form = ref({})

const columns = ref([
  { key: 'refundItemId', label: '退款明细ID', visible: false },
  { key: 'refundNo', label: '退款单号', visible: true },
  { key: 'refundId', label: '退款单ID', visible: false },
  { key: 'orderId', label: '订单ID', visible: false },
  { key: 'productName', label: '商品名称', visible: true },
  { key: 'skuName', label: 'SKU名称', visible: true },
  { key: 'orderItemId', label: '订单明细ID', visible: false },
  { key: 'refundQuantity', label: '退款数量', visible: true },
  { key: 'refundAmount', label: '退款金额', visible: true },
])

const columnVisible = computed(() => {
  const result = {}
  columns.value.forEach(col => {
    result[col.key] = col.visible
  })
  return result
})

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    refundId: undefined,
    orderId: undefined,
    productName: undefined,
  }
})

const { queryParams } = toRefs(data)

function getList() {
  loading.value = true
  listMallOrderRefundItem(queryParams.value).then(response => {
    mallOrderRefundItemList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  if (route.query.refundId) {
    queryParams.value.refundId = route.query.refundId
  }
  if (route.query.orderId) {
    queryParams.value.orderId = route.query.orderId
  }
  handleQuery()
}

function handleView(row) {
  getMallOrderRefundItem(row.refundItemId).then(response => {
    form.value = response.data
    open.value = true
  })
}

function handleExport() {
  proxy.download('web/mall/mallOrderRefundItem/export', { ...queryParams.value }, `mallOrderRefundItem_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  if (route.query.refundId) {
    queryParams.value.refundId = route.query.refundId
    refundRemote.ensureOption(route.query.refundId, getMallOrderRefund, (row) => row.refundNo || `退款${row.refundId}`)
  }
  if (route.query.orderId) {
    queryParams.value.orderId = route.query.orderId
    orderRemote.ensureOption(route.query.orderId, getMallOrder, (row) => row.orderNo || `订单${row.orderId}`)
  }
  getList()
})
</script>

<style scoped>
.mb8 { margin-bottom: 8px; }
</style>
