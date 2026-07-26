<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="86px">
      <el-form-item label="账号" prop="accountId">
        <RemoteSelect v-model="queryParams.accountId" :remote="accountRemote" placeholder="昵称/手机号搜索" />
      </el-form-item>
      <el-form-item label="流水类型" prop="recordType">
        <el-select v-model="queryParams.recordType" placeholder="全部" clearable style="width: 120px">
          <el-option label="充值" :value="1" />
          <el-option label="赠送" :value="2" />
          <el-option label="订单支付" :value="3" />
          <el-option label="订单退款" :value="4" />
        </el-select>
      </el-form-item>
      <el-form-item label="变动方向" prop="changeType">
        <el-select v-model="queryParams.changeType" placeholder="全部" clearable style="width: 100px">
          <el-option label="收入" :value="1" />
          <el-option label="支出" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item label="业务单号" prop="bizNo">
        <el-input v-model="queryParams.bizNo" placeholder="业务单号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['account:accountBalanceRecord:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList">
      <el-table-column label="流水ID" align="center" key="balanceRecordId" prop="balanceRecordId" width="100" v-if="columnVisible['balanceRecordId']" />
      <el-table-column label="账号" align="center" key="accountNickName" prop="accountNickName" min-width="110" show-overflow-tooltip v-if="columnVisible['accountNickName']" />
      <el-table-column label="账号ID" align="center" key="accountId" prop="accountId" width="100" v-if="columnVisible['accountId']" />
      <el-table-column label="流水类型" align="center" key="recordType" prop="recordType" width="100" v-if="columnVisible['recordType']">
        <template #default="scope">
          {{ recordTypeLabel(scope.row.recordType) }}
        </template>
      </el-table-column>
      <el-table-column label="方向" align="center" key="changeType" prop="changeType" width="70" v-if="columnVisible['changeType']">
        <template #default="scope">
          <el-tag :type="scope.row.changeType === 1 ? 'success' : 'danger'">
            {{ scope.row.changeType === 1 ? '收入' : '支出' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="变动金额" align="center" key="changeAmount" prop="changeAmount" width="100" v-if="columnVisible['changeAmount']" />
      <el-table-column label="变动后余额" align="center" key="balanceAfter" prop="balanceAfter" width="110" v-if="columnVisible['balanceAfter']" />
      <el-table-column label="业务单号" align="center" key="bizNo" prop="bizNo" min-width="140" show-overflow-tooltip v-if="columnVisible['bizNo']" />
      <el-table-column label="创建时间" align="center" key="createTime" prop="createTime" width="160" v-if="columnVisible['createTime']" />
      <el-table-column label="操作" align="center" width="80" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="余额流水详情" v-model="open" width="520px" append-to-body>
      <el-descriptions :column="1" border v-if="form.balanceRecordId">
        <el-descriptions-item label="流水ID">{{ form.balanceRecordId }}</el-descriptions-item>
        <el-descriptions-item label="账号">{{ form.accountNickName }}（{{ form.accountId }}）</el-descriptions-item>
        <el-descriptions-item label="流水类型">{{ recordTypeLabel(form.recordType) }}</el-descriptions-item>
        <el-descriptions-item label="变动方向">{{ form.changeType === 1 ? '收入' : '支出' }}</el-descriptions-item>
        <el-descriptions-item label="变动金额">{{ form.changeAmount }}</el-descriptions-item>
        <el-descriptions-item label="变动前余额">{{ form.balanceBefore }}</el-descriptions-item>
        <el-descriptions-item label="变动后余额">{{ form.balanceAfter }}</el-descriptions-item>
        <el-descriptions-item label="业务ID">{{ form.bizId }}</el-descriptions-item>
        <el-descriptions-item label="业务单号">{{ form.bizNo }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ form.createTime }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{ form.remark }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup name="AccountBalanceRecord">
import { listAccountBalanceRecord, getAccountBalanceRecord } from "@/api/account/accountBalanceRecord"
import { getAccount } from "@/api/account/account"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useAccountRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()
const route = useRoute()
const accountRemote = useAccountRemoteSelect()

const recordList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const form = ref({})

const columns = ref([
  { key: 'balanceRecordId', label: '流水ID', visible: false },
  { key: 'accountNickName', label: '账号', visible: true },
  { key: 'accountId', label: '账号ID', visible: false },
  { key: 'recordType', label: '流水类型', visible: true },
  { key: 'changeType', label: '方向', visible: true },
  { key: 'changeAmount', label: '变动金额', visible: true },
  { key: 'balanceAfter', label: '变动后余额', visible: true },
  { key: 'bizNo', label: '业务单号', visible: true },
  { key: 'createTime', label: '创建时间', visible: true },
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
    accountId: undefined,
    recordType: undefined,
    changeType: undefined,
    bizNo: undefined,
  }
})

const { queryParams } = toRefs(data)

function recordTypeLabel(type) {
  const map = { 1: '充值', 2: '赠送', 3: '订单支付', 4: '订单退款' }
  return map[type] || type
}

function getList() {
  loading.value = true
  listAccountBalanceRecord(queryParams.value).then(response => {
    recordList.value = response.rows
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
  if (route.query.accountId) {
    queryParams.value.accountId = route.query.accountId
  }
  handleQuery()
}

function handleView(row) {
  getAccountBalanceRecord(row.balanceRecordId).then(response => {
    form.value = response.data
    open.value = true
  })
}

function handleExport() {
  proxy.download('web/account/accountBalanceRecord/export', { ...queryParams.value }, `accountBalanceRecord_${new Date().getTime()}.xlsx`)
}

onMounted(() => {
  if (route.query.accountId) {
    queryParams.value.accountId = route.query.accountId
    accountRemote.ensureOption(route.query.accountId, getAccount, (row) => {
      const parts = [row.nickName, row.mobile].filter(Boolean)
      return parts.length ? parts.join(' / ') : `账号${row.accountId}`
    })
  }
  getList()
})
</script>

<style scoped>
.mb8 { margin-bottom: 8px; }
</style>
