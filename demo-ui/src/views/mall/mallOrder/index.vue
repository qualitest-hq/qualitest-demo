<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input v-model="queryParams.orderNo" placeholder="请输入订单编号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="账号" prop="accountId">
        <RemoteSelect v-model="queryParams.accountId" :remote="accountRemote" placeholder="昵称/手机号搜索" />
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="全部" clearable style="width: 120px">
          <el-option v-for="item in ORDER_STATUS_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="支付状态" prop="payStatus">
        <el-select v-model="queryParams.payStatus" placeholder="全部" clearable style="width: 120px">
          <el-option v-for="item in PAY_STATUS_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="退款状态" prop="refundStatus">
        <el-select v-model="queryParams.refundStatus" placeholder="全部" clearable style="width: 130px">
          <el-option v-for="item in ORDER_REFUND_STATUS_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="收货人" prop="receiverName">
        <el-input v-model="queryParams.receiverName" placeholder="收货人姓名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="收货手机" prop="receiverPhone">
        <el-input v-model="queryParams.receiverPhone" placeholder="收货人手机" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['mall:mallOrder:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mallOrderList">
      <el-table-column label="订单编号" align="center" key="orderNo" prop="orderNo" min-width="160" show-overflow-tooltip v-if="columnVisible['orderNo']" />
      <el-table-column label="账号" align="center" key="accountNickName" prop="accountNickName" min-width="120" show-overflow-tooltip v-if="columnVisible['accountNickName']" />
      <el-table-column label="账号ID" align="center" key="accountId" prop="accountId" width="100" v-if="columnVisible['accountId']" />
      <el-table-column label="订单状态" align="center" key="orderStatus" prop="orderStatus" width="100" v-if="columnVisible['orderStatus']">
        <template #default="scope">
          <dict-tag :options="ORDER_STATUS_OPTIONS" :value="scope.row.orderStatus" />
        </template>
      </el-table-column>
      <el-table-column label="应付金额" align="center" key="payAmount" prop="payAmount" width="100" v-if="columnVisible['payAmount']" />
      <el-table-column label="支付方式" align="center" key="payType" prop="payType" width="90" v-if="columnVisible['payType']">
        <template #default="scope">
          <dict-tag :options="PAY_TYPE_OPTIONS" :value="scope.row.payType" />
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center" key="payStatus" prop="payStatus" width="90" v-if="columnVisible['payStatus']">
        <template #default="scope">
          <dict-tag :options="PAY_STATUS_OPTIONS" :value="scope.row.payStatus" />
        </template>
      </el-table-column>
      <el-table-column label="退款状态" align="center" key="refundStatus" prop="refundStatus" width="110" v-if="columnVisible['refundStatus']">
        <template #default="scope">
          <dict-tag :options="ORDER_REFUND_STATUS_OPTIONS" :value="scope.row.refundStatus" />
        </template>
      </el-table-column>
      <el-table-column label="收货人" align="center" key="receiverName" prop="receiverName" width="90" v-if="columnVisible['receiverName']" />
      <el-table-column label="收货手机" align="center" key="receiverPhone" prop="receiverPhone" width="120" v-if="columnVisible['receiverPhone']" />
      <el-table-column label="支付时间" align="center" key="payTime" prop="payTime" width="160" v-if="columnVisible['payTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.payTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)">详情</el-button>
          <el-button link type="primary" @click="goOrderItems(scope.row)">明细</el-button>
          <el-button link type="success" v-if="scope.row.orderStatus === 1" @click="handleDeliver(scope.row)" v-hasPermi="['mall:mallOrder:deliver']">发货</el-button>
          <el-button link type="warning" v-if="scope.row.orderStatus === 0 && scope.row.payStatus === 0" @click="handleClose(scope.row)" v-hasPermi="['mall:mallOrder:close']">关单</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-drawer v-model="detailOpen" title="订单详情" size="60%" destroy-on-close>
      <template v-if="detailData">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="订单编号">{{ detailData.orderNo }}</el-descriptions-item>
          <el-descriptions-item label="账号ID">{{ detailData.accountId }}</el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <dict-tag :options="ORDER_STATUS_OPTIONS" :value="detailData.orderStatus" />
          </el-descriptions-item>
          <el-descriptions-item label="支付状态">
            <dict-tag :options="PAY_STATUS_OPTIONS" :value="detailData.payStatus" />
          </el-descriptions-item>
          <el-descriptions-item label="商品总额">{{ detailData.totalAmount }}</el-descriptions-item>
          <el-descriptions-item label="优惠金额">{{ detailData.discountAmount }}</el-descriptions-item>
          <el-descriptions-item label="运费">{{ detailData.freightAmount }}</el-descriptions-item>
          <el-descriptions-item label="应付金额">{{ detailData.payAmount }}</el-descriptions-item>
          <el-descriptions-item label="支付方式">
            <dict-tag :options="PAY_TYPE_OPTIONS" :value="detailData.payType" />
          </el-descriptions-item>
          <el-descriptions-item label="退款状态">
            <dict-tag :options="ORDER_REFUND_STATUS_OPTIONS" :value="detailData.refundStatus" />
          </el-descriptions-item>
          <el-descriptions-item label="收货人">{{ detailData.receiverName }}</el-descriptions-item>
          <el-descriptions-item label="收货手机">{{ detailData.receiverPhone }}</el-descriptions-item>
          <el-descriptions-item label="收货地址" :span="2">{{ detailData.receiverAddress }}</el-descriptions-item>
          <el-descriptions-item label="买家留言" :span="2">{{ detailData.buyerRemark }}</el-descriptions-item>
        </el-descriptions>
        <div class="mt16">
          <div class="mb8" style="font-weight: bold">订单明细</div>
          <el-table :data="detailData.items || []" border size="small">
            <el-table-column label="商品名称" prop="productName" min-width="120" />
            <el-table-column label="SKU名称" prop="skuName" min-width="100" />
            <el-table-column label="单价" prop="salePrice" width="90" />
            <el-table-column label="数量" prop="quantity" width="70" />
            <el-table-column label="小计" prop="totalAmount" width="90" />
            <el-table-column label="已退数量" prop="refundedQuantity" width="90" />
            <el-table-column label="已退金额" prop="refundedAmount" width="90" />
          </el-table>
        </div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup name="MallOrder">
import { listMallOrder, getMallOrderDetail, deliverMallOrder, closeMallOrder } from "@/api/mall/mallOrder"
import { ORDER_STATUS_OPTIONS, PAY_STATUS_OPTIONS, PAY_TYPE_OPTIONS, ORDER_REFUND_STATUS_OPTIONS } from "@/utils/mallEnums"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useAccountRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()
const router = useRouter()
const accountRemote = useAccountRemoteSelect()

const mallOrderList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const detailOpen = ref(false)
const detailData = ref(null)

const columns = ref([
  { key: 'orderNo', label: '订单编号', visible: true },
  { key: 'accountNickName', label: '账号', visible: true },
  { key: 'accountId', label: '账号ID', visible: false },
  { key: 'orderStatus', label: '订单状态', visible: true },
  { key: 'payAmount', label: '应付金额', visible: true },
  { key: 'payType', label: '支付方式', visible: true },
  { key: 'payStatus', label: '支付状态', visible: true },
  { key: 'refundStatus', label: '退款状态', visible: true },
  { key: 'receiverName', label: '收货人', visible: true },
  { key: 'receiverPhone', label: '收货手机', visible: true },
  { key: 'payTime', label: '支付时间', visible: true },
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
    orderNo: undefined,
    accountId: undefined,
    orderStatus: undefined,
    payStatus: undefined,
    refundStatus: undefined,
    receiverName: undefined,
    receiverPhone: undefined,
  }
})

const { queryParams } = toRefs(data)

function getList() {
  loading.value = true
  listMallOrder(queryParams.value).then(response => {
    mallOrderList.value = response.rows
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
  handleQuery()
}

function handleDetail(row) {
  getMallOrderDetail(row.orderId).then(response => {
    detailData.value = response.data
    detailOpen.value = true
  })
}

function goOrderItems(row) {
  router.push({ path: '/mallManages/mallOrderItem', query: { orderId: row.orderId } })
}

function handleDeliver(row) {
  proxy.$modal.confirm('确认对订单「' + row.orderNo + '」发货？').then(() => {
    return deliverMallOrder(row.orderId)
  }).then(() => {
    proxy.$modal.msgSuccess("发货成功")
    getList()
  }).catch(() => {})
}

function handleClose(row) {
  proxy.$modal.confirm('确认关闭未支付订单「' + row.orderNo + '」？').then(() => {
    return closeMallOrder(row.orderId, { cancelReason: '管理员关闭' })
  }).then(() => {
    proxy.$modal.msgSuccess("关单成功")
    getList()
  }).catch(() => {})
}

function handleExport() {
  proxy.download('web/mall/mallOrder/export', { ...queryParams.value }, `mallOrder_${new Date().getTime()}.xlsx`)
}

getList()
</script>

<style scoped>
.mt16 { margin-top: 16px; }
.mb8 { margin-bottom: 8px; }
</style>
