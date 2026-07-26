<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="72px">
      <el-form-item label="账号" prop="accountId">
        <RemoteSelect v-model="queryParams.accountId" :remote="accountRemote" placeholder="昵称/手机号搜索" />
      </el-form-item>
      <el-form-item label="商品" prop="productId">
        <RemoteSelect v-model="queryParams.productId" :remote="productRemote" placeholder="商品名称搜索" />
      </el-form-item>
      <el-form-item label="SKU" prop="skuId">
        <RemoteSelect v-model="queryParams.skuId" :remote="skuRemote" placeholder="SKU名称搜索" width="240px" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['mall:mallCart:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mallCartList">
      <el-table-column label="账号" align="center" key="accountNickName" prop="accountNickName" min-width="110" show-overflow-tooltip v-if="columnVisible['accountNickName']" />
      <el-table-column label="商品" align="center" key="productName" prop="productName" min-width="120" show-overflow-tooltip v-if="columnVisible['productName']" />
      <el-table-column label="SKU" align="center" key="skuName" prop="skuName" min-width="120" show-overflow-tooltip v-if="columnVisible['skuName']" />
      <el-table-column label="数量" align="center" key="quantity" prop="quantity" width="80" v-if="columnVisible['quantity']" />
      <el-table-column label="购物车ID" align="center" key="cartId" prop="cartId" width="100" v-if="columnVisible['cartId']" />
      <el-table-column label="账号ID" align="center" key="accountId" prop="accountId" width="100" v-if="columnVisible['accountId']" />
      <el-table-column label="商品ID" align="center" key="productId" prop="productId" width="100" v-if="columnVisible['productId']" />
      <el-table-column label="SKU ID" align="center" key="skuId" prop="skuId" width="100" v-if="columnVisible['skuId']" />
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script setup name="MallCart">
import { listMallCart } from "@/api/mall/mallCart"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useAccountRemoteSelect, useProductRemoteSelect, useSkuRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()

const accountRemote = useAccountRemoteSelect()
const productRemote = useProductRemoteSelect()

const mallCartList = ref([])
const loading = ref(true)
const showSearch = ref(true)
const total = ref(0)

const data = reactive({
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    accountId: undefined,
    productId: undefined,
    skuId: undefined,
  }
})

const { queryParams } = toRefs(data)
const skuRemote = useSkuRemoteSelect(computed(() => ({ productId: queryParams.value.productId })))

const columns = ref([
  { key: 'accountNickName', label: '账号', visible: true },
  { key: 'productName', label: '商品', visible: true },
  { key: 'skuName', label: 'SKU', visible: true },
  { key: 'quantity', label: '数量', visible: true },
  { key: 'cartId', label: '购物车ID', visible: false },
  { key: 'accountId', label: '账号ID', visible: false },
  { key: 'productId', label: '商品ID', visible: false },
  { key: 'skuId', label: 'SKU ID', visible: false },
])

const columnVisible = computed(() => {
  const result = {}
  columns.value.forEach(col => {
    result[col.key] = col.visible
  })
  return result
})

function getList() {
  loading.value = true
  listMallCart(queryParams.value).then(response => {
    mallCartList.value = response.rows
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

function handleExport() {
  proxy.download('web/mall/mallCart/export', { ...queryParams.value }, `mallCart_${new Date().getTime()}.xlsx`)
}

getList()
</script>
