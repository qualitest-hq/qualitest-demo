<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
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
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['mall:mallOrderItem:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mallOrderItemList">
      <el-table-column label="明细ID" align="center" key="orderItemId" prop="orderItemId" width="100" v-if="columnVisible['orderItemId']" />
      <el-table-column label="订单编号" align="center" key="orderNo" prop="orderNo" min-width="150" show-overflow-tooltip v-if="columnVisible['orderNo']" />
      <el-table-column label="订单ID" align="center" key="orderId" prop="orderId" width="100" v-if="columnVisible['orderId']" />
      <el-table-column label="商品名称" align="center" key="productName" prop="productName" min-width="120" v-if="columnVisible['productName']" />
      <el-table-column label="SKU名称" align="center" key="skuName" prop="skuName" min-width="100" v-if="columnVisible['skuName']" />
      <el-table-column label="单价" align="center" key="salePrice" prop="salePrice" width="90" v-if="columnVisible['salePrice']" />
      <el-table-column label="数量" align="center" key="quantity" prop="quantity" width="70" v-if="columnVisible['quantity']" />
      <el-table-column label="小计" align="center" key="totalAmount" prop="totalAmount" width="90" v-if="columnVisible['totalAmount']" />
      <el-table-column label="已退数量" align="center" key="refundedQuantity" prop="refundedQuantity" width="90" v-if="columnVisible['refundedQuantity']" />
      <el-table-column label="已退金额" align="center" key="refundedAmount" prop="refundedAmount" width="90" v-if="columnVisible['refundedAmount']" />
      <el-table-column label="操作" align="center" width="80">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="订单明细详情" v-model="open" width="500px" append-to-body>
      <el-descriptions :column="1" border v-if="form.orderItemId">
        <el-descriptions-item label="明细ID">{{ form.orderItemId }}</el-descriptions-item>
        <el-descriptions-item label="订单ID">{{ form.orderId }}</el-descriptions-item>
        <el-descriptions-item label="商品ID">{{ form.productId }}</el-descriptions-item>
        <el-descriptions-item label="SKU ID">{{ form.skuId }}</el-descriptions-item>
        <el-descriptions-item label="商品名称">{{ form.productName }}</el-descriptions-item>
        <el-descriptions-item label="SKU名称">{{ form.skuName }}</el-descriptions-item>
        <el-descriptions-item label="成交单价">{{ form.salePrice }}</el-descriptions-item>
        <el-descriptions-item label="购买数量">{{ form.quantity }}</el-descriptions-item>
        <el-descriptions-item label="小计金额">{{ form.totalAmount }}</el-descriptions-item>
        <el-descriptions-item label="累计已退数量">{{ form.refundedQuantity }}</el-descriptions-item>
        <el-descriptions-item label="累计已退金额">{{ form.refundedAmount }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup name="MallOrderItem">
import { listMallOrderItem, getMallOrderItem } from "@/api/mall/mallOrderItem"
import { getMallOrder } from "@/api/mall/mallOrder"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useOrderRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()
const route = useRoute()
const orderRemote = useOrderRemoteSelect()

const mallOrderItemList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const form = ref({})

const columns = ref([
  { key: 'orderItemId', label: '明细ID', visible: false },
  { key: 'orderNo', label: '订单编号', visible: true },
  { key: 'orderId', label: '订单ID', visible: false },
  { key: 'productName', label: '商品名称', visible: true },
  { key: 'skuName', label: 'SKU名称', visible: true },
  { key: 'salePrice', label: '单价', visible: true },
  { key: 'quantity', label: '数量', visible: true },
  { key: 'totalAmount', label: '小计', visible: true },
  { key: 'refundedQuantity', label: '已退数量', visible: true },
  { key: 'refundedAmount', label: '已退金额', visible: true },
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
    orderId: undefined,
    productName: undefined,
  }
})

const { queryParams } = toRefs(data)

function getList() {
  loading.value = true
  listMallOrderItem(queryParams.value).then(response => {
    mallOrderItemList.value = response.rows
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
  if (route.query.orderId) {
    queryParams.value.orderId = route.query.orderId
  }
  handleQuery()
}

function handleView(row) {
  getMallOrderItem(row.orderItemId).then(response => {
    form.value = response.data
    open.value = true
  })
}

function handleExport() {
  proxy.download('web/mall/mallOrderItem/export', { ...queryParams.value }, `mallOrderItem_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
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
