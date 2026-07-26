<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="86px">
      <el-form-item label="账号" prop="accountId">
        <RemoteSelect v-model="queryParams.accountId" :remote="accountRemote" placeholder="昵称/手机号搜索" />
      </el-form-item>
      <el-form-item label="收货人" prop="receiverName">
        <el-input v-model="queryParams.receiverName" placeholder="收货人姓名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="手机号" prop="receiverPhone">
        <el-input v-model="queryParams.receiverPhone" placeholder="收货人手机" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="是否默认" prop="isDefault">
        <el-select v-model="queryParams.isDefault" placeholder="全部" clearable style="width: 100px">
          <el-option label="是" :value="1" />
          <el-option label="否" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['account:accountAddress:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountAddressList">
      <el-table-column label="地址ID" align="center" key="addressId" prop="addressId" width="100" v-if="columnVisible['addressId']" />
      <el-table-column label="账号" align="center" key="accountNickName" prop="accountNickName" min-width="110" show-overflow-tooltip v-if="columnVisible['accountNickName']" />
      <el-table-column label="账号ID" align="center" key="accountId" prop="accountId" width="100" v-if="columnVisible['accountId']" />
      <el-table-column label="收货人" align="center" key="receiverName" prop="receiverName" width="90" v-if="columnVisible['receiverName']" />
      <el-table-column label="手机" align="center" key="receiverPhone" prop="receiverPhone" width="120" v-if="columnVisible['receiverPhone']" />
      <el-table-column label="省" align="center" key="province" prop="province" width="80" v-if="columnVisible['province']" />
      <el-table-column label="市" align="center" key="city" prop="city" width="80" v-if="columnVisible['city']" />
      <el-table-column label="区/县" align="center" key="district" prop="district" width="80" v-if="columnVisible['district']" />
      <el-table-column label="详细地址" align="center" key="detailAddress" prop="detailAddress" min-width="160" show-overflow-tooltip v-if="columnVisible['detailAddress']" />
      <el-table-column label="邮编" align="center" key="postalCode" prop="postalCode" width="80" v-if="columnVisible['postalCode']" />
      <el-table-column label="默认" align="center" key="isDefault" prop="isDefault" width="70" v-if="columnVisible['isDefault']">
        <template #default="scope">
          <el-tag :type="scope.row.isDefault === 1 ? 'success' : 'info'">{{ scope.row.isDefault === 1 ? '是' : '否' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="80" fixed="right">
        <template #default="scope">
          <el-button link type="primary" @click="handleView(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="收货地址详情" v-model="open" width="500px" append-to-body>
      <el-descriptions :column="1" border v-if="form.addressId">
        <el-descriptions-item label="地址ID">{{ form.addressId }}</el-descriptions-item>
        <el-descriptions-item label="账号ID">{{ form.accountId }}</el-descriptions-item>
        <el-descriptions-item label="收货人">{{ form.receiverName }}</el-descriptions-item>
        <el-descriptions-item label="手机">{{ form.receiverPhone }}</el-descriptions-item>
        <el-descriptions-item label="省">{{ form.province }}</el-descriptions-item>
        <el-descriptions-item label="市">{{ form.city }}</el-descriptions-item>
        <el-descriptions-item label="区/县">{{ form.district }}</el-descriptions-item>
        <el-descriptions-item label="详细地址">{{ form.detailAddress }}</el-descriptions-item>
        <el-descriptions-item label="邮政编码">{{ form.postalCode }}</el-descriptions-item>
        <el-descriptions-item label="是否默认">{{ form.isDefault === 1 ? '是' : '否' }}</el-descriptions-item>
        <el-descriptions-item label="备注">{{ form.remark }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script setup name="AccountAddress">
import { listAccountAddress, getAccountAddress } from "@/api/account/accountAddress"
import { getAccount } from "@/api/account/account"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useAccountRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()
const route = useRoute()
const accountRemote = useAccountRemoteSelect()

const accountAddressList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)
const form = ref({})

const columns = ref([
  { key: 'addressId', label: '地址ID', visible: false },
  { key: 'accountNickName', label: '账号', visible: true },
  { key: 'accountId', label: '账号ID', visible: false },
  { key: 'receiverName', label: '收货人', visible: true },
  { key: 'receiverPhone', label: '手机', visible: true },
  { key: 'province', label: '省', visible: true },
  { key: 'city', label: '市', visible: true },
  { key: 'district', label: '区/县', visible: true },
  { key: 'detailAddress', label: '详细地址', visible: true },
  { key: 'postalCode', label: '邮编', visible: true },
  { key: 'isDefault', label: '默认', visible: true },
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
    receiverName: undefined,
    receiverPhone: undefined,
    isDefault: undefined,
  }
})

const { queryParams } = toRefs(data)

function getList() {
  loading.value = true
  listAccountAddress(queryParams.value).then(response => {
    accountAddressList.value = response.rows
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
  getAccountAddress(row.addressId).then(response => {
    form.value = response.data
    open.value = true
  })
}

function handleExport() {
  proxy.download('web/account/accountAddress/export', { ...queryParams.value }, `accountAddress_${new Date().getTime()}.xlsx`)
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
