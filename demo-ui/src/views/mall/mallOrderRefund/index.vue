<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="退款单号" prop="refundNo">
        <el-input v-model="queryParams.refundNo" placeholder="请输入退款单号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="订单" prop="orderId">
        <RemoteSelect v-model="queryParams.orderId" :remote="orderRemote" placeholder="订单编号搜索" />
      </el-form-item>
      <el-form-item label="账号" prop="accountId">
        <RemoteSelect v-model="queryParams.accountId" :remote="accountRemote" placeholder="昵称/手机号搜索" />
      </el-form-item>
      <el-form-item label="退款原因" prop="refundReason">
        <el-input v-model="queryParams.refundReason" placeholder="请输入退款原因" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="退款类型" prop="refundType">
        <el-select v-model="queryParams.refundType" placeholder="全部" clearable style="width: 120px">
          <el-option v-for="item in REFUND_TYPE_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="退款状态" prop="refundStatus">
        <el-select v-model="queryParams.refundStatus" placeholder="全部" clearable style="width: 120px">
          <el-option v-for="item in REFUND_STATUS_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['mall:mallOrderRefund:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mallOrderRefundList">
      <el-table-column label="退款单号" align="center" key="refundNo" prop="refundNo" min-width="160" show-overflow-tooltip v-if="columnVisible['refundNo']" />
      <el-table-column label="订单编号" align="center" key="orderNo" prop="orderNo" min-width="150" show-overflow-tooltip v-if="columnVisible['orderNo']" />
      <el-table-column label="订单ID" align="center" key="orderId" prop="orderId" width="100" v-if="columnVisible['orderId']" />
      <el-table-column label="账号" align="center" key="accountNickName" prop="accountNickName" min-width="110" show-overflow-tooltip v-if="columnVisible['accountNickName']" />
      <el-table-column label="账号ID" align="center" key="accountId" prop="accountId" width="100" v-if="columnVisible['accountId']" />
      <el-table-column label="退款类型" align="center" key="refundType" prop="refundType" width="100" v-if="columnVisible['refundType']">
        <template #default="scope">
          <dict-tag :options="REFUND_TYPE_OPTIONS" :value="scope.row.refundType" />
        </template>
      </el-table-column>
      <el-table-column label="退款金额" align="center" key="refundAmount" prop="refundAmount" width="100" v-if="columnVisible['refundAmount']" />
      <el-table-column label="退款状态" align="center" key="refundStatus" prop="refundStatus" width="100" v-if="columnVisible['refundStatus']">
        <template #default="scope">
          <dict-tag :options="REFUND_STATUS_OPTIONS" :value="scope.row.refundStatus" />
        </template>
      </el-table-column>
      <el-table-column label="退款原因" align="center" key="refundReason" prop="refundReason" min-width="120" show-overflow-tooltip v-if="columnVisible['refundReason']" />
      <el-table-column label="申请时间" align="center" key="applyTime" prop="applyTime" width="160" v-if="columnVisible['applyTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.applyTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="240" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="handleDetail(scope.row)">详情</el-button>
          <el-button link type="success" v-if="scope.row.refundStatus === 0" @click="handleApprove(scope.row)" v-hasPermi="['mall:mallOrderRefund:approve']">通过</el-button>
          <el-button link type="danger" v-if="scope.row.refundStatus === 0" @click="handleReject(scope.row)" v-hasPermi="['mall:mallOrderRefund:reject']">拒绝</el-button>
          <el-button link type="warning" v-if="scope.row.refundStatus === 1" @click="handleComplete(scope.row)" v-hasPermi="['mall:mallOrderRefund:complete']">完成退款</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-drawer v-model="detailOpen" title="退款单详情" size="55%" destroy-on-close>
      <template v-if="detailData">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="退款单号">{{ detailData.refundNo }}</el-descriptions-item>
          <el-descriptions-item label="订单ID">{{ detailData.orderId }}</el-descriptions-item>
          <el-descriptions-item label="账号ID">{{ detailData.accountId }}</el-descriptions-item>
          <el-descriptions-item label="退款类型">
            <dict-tag :options="REFUND_TYPE_OPTIONS" :value="detailData.refundType" />
          </el-descriptions-item>
          <el-descriptions-item label="退款金额">{{ detailData.refundAmount }}</el-descriptions-item>
          <el-descriptions-item label="退款状态">
            <dict-tag :options="REFUND_STATUS_OPTIONS" :value="detailData.refundStatus" />
          </el-descriptions-item>
          <el-descriptions-item label="退款原因" :span="2">{{ detailData.refundReason }}</el-descriptions-item>
          <el-descriptions-item label="处理备注" :span="2">{{ detailData.handleRemark }}</el-descriptions-item>
        </el-descriptions>
        <div class="mt16">
          <div class="mb8" style="font-weight: bold">退款明细</div>
          <el-table :data="detailData.refundItems || []" border size="small">
            <el-table-column label="订单明细ID" prop="orderItemId" width="120" />
            <el-table-column label="退款数量" prop="refundQuantity" width="100" />
            <el-table-column label="退款金额" prop="refundAmount" width="100" />
          </el-table>
        </div>
      </template>
    </el-drawer>

    <el-dialog title="拒绝退款" v-model="rejectOpen" width="450px" append-to-body>
      <el-form ref="rejectRef" :model="rejectForm" label-width="80px">
        <el-form-item label="处理备注" prop="handleRemark">
          <el-input v-model="rejectForm.handleRemark" type="textarea" placeholder="请输入拒绝原因" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="rejectOpen = false">取消</el-button>
        <el-button type="primary" @click="submitReject">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="MallOrderRefund">
import {
  listMallOrderRefund, getMallOrderRefundDetail,
  approveMallOrderRefund, rejectMallOrderRefund, completeMallOrderRefund
} from "@/api/mall/mallOrderRefund"
import { REFUND_STATUS_OPTIONS, REFUND_TYPE_OPTIONS } from "@/utils/mallEnums"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useAccountRemoteSelect, useOrderRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()
const accountRemote = useAccountRemoteSelect()
const orderRemote = useOrderRemoteSelect()

const mallOrderRefundList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const detailOpen = ref(false)
const detailData = ref(null)
const rejectOpen = ref(false)
const rejectForm = ref({ refundId: null, handleRemark: '' })

const columns = ref([
  { key: 'refundNo', label: '退款单号', visible: true },
  { key: 'orderNo', label: '订单编号', visible: true },
  { key: 'orderId', label: '订单ID', visible: false },
  { key: 'accountNickName', label: '账号', visible: true },
  { key: 'accountId', label: '账号ID', visible: false },
  { key: 'refundType', label: '退款类型', visible: true },
  { key: 'refundAmount', label: '退款金额', visible: true },
  { key: 'refundStatus', label: '退款状态', visible: true },
  { key: 'refundReason', label: '退款原因', visible: true },
  { key: 'applyTime', label: '申请时间', visible: true },
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
    refundNo: undefined,
    orderId: undefined,
    accountId: undefined,
    refundReason: undefined,
    refundType: undefined,
    refundStatus: undefined,
  }
})

const { queryParams } = toRefs(data)

function getList() {
  loading.value = true
  listMallOrderRefund(queryParams.value).then(response => {
    mallOrderRefundList.value = response.rows
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
  getMallOrderRefundDetail(row.refundId).then(response => {
    detailData.value = response.data
    detailOpen.value = true
  })
}

function handleApprove(row) {
  proxy.$modal.confirm('确认通过退款单「' + row.refundNo + '」？').then(() => {
    return approveMallOrderRefund(row.refundId)
  }).then(() => {
    proxy.$modal.msgSuccess("审核通过")
    getList()
  }).catch(() => {})
}

function handleReject(row) {
  rejectForm.value = { refundId: row.refundId, handleRemark: '' }
  rejectOpen.value = true
}

function submitReject() {
  rejectMallOrderRefund(rejectForm.value.refundId, { handleRemark: rejectForm.value.handleRemark }).then(() => {
    proxy.$modal.msgSuccess("已拒绝")
    rejectOpen.value = false
    getList()
  })
}

function handleComplete(row) {
  proxy.$modal.confirm('确认完成退款「' + row.refundNo + '」？将执行到账与库存处理。').then(() => {
    return completeMallOrderRefund(row.refundId)
  }).then(() => {
    proxy.$modal.msgSuccess("退款完成")
    getList()
  }).catch(() => {})
}

function handleExport() {
  proxy.download('web/mall/mallOrderRefund/export', { ...queryParams.value }, `mallOrderRefund_${new Date().getTime()}.xlsx`)
}

getList()
</script>

<style scoped>
.mt16 { margin-top: 16px; }
.mb8 { margin-bottom: 8px; }
</style>
