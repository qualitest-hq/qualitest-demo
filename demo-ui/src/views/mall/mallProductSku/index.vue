<template>

  <div class="app-container">

    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="90px">

      <el-form-item label="所属SPU" prop="productId">

        <RemoteSelect v-model="queryParams.productId" :remote="productRemote" placeholder="商品名称搜索" />

      </el-form-item>

      <el-form-item label="SKU名称" prop="skuName">

        <el-input v-model="queryParams.skuName" placeholder="请输入SKU名称" clearable @keyup.enter="handleQuery" />

      </el-form-item>

      <el-form-item label="状态" prop="status">

        <el-select v-model="queryParams.status" placeholder="全部" clearable style="width: 100px">

          <el-option v-for="item in NORMAL_DISABLE_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />

        </el-select>

      </el-form-item>

      <el-form-item>

        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>

        <el-button icon="Refresh" @click="resetQuery">重置</el-button>

      </el-form-item>

    </el-form>



    <el-row :gutter="10" class="mb8">

      <el-col :span="1.5">

        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['mall:mallProductSku:add']">新增</el-button>

      </el-col>

      <el-col :span="1.5">

        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['mall:mallProductSku:edit']">修改</el-button>

      </el-col>

      <el-col :span="1.5">

        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['mall:mallProductSku:remove']">删除</el-button>

      </el-col>

      <el-col :span="1.5">

        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['mall:mallProductSku:export']">导出</el-button>

      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>

    </el-row>



    <el-table v-loading="loading" :data="mallProductSkuList" @selection-change="handleSelectionChange">

      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="SKU ID" align="center" key="skuId" prop="skuId" width="100" v-if="columnVisible['skuId']" />

      <el-table-column label="所属SPU" align="center" key="productName" prop="productName" min-width="120" show-overflow-tooltip v-if="columnVisible['productName']" />

      <el-table-column label="商品ID" align="center" key="productId" prop="productId" width="100" v-if="columnVisible['productId']" />

      <el-table-column label="SKU名称" align="center" key="skuName" prop="skuName" min-width="120" v-if="columnVisible['skuName']" />

      <el-table-column label="销售价" align="center" key="salePrice" prop="salePrice" width="90" v-if="columnVisible['salePrice']" />

      <el-table-column label="库存" align="center" key="stock" prop="stock" width="80" v-if="columnVisible['stock']" />

      <el-table-column label="销量" align="center" key="salesCount" prop="salesCount" width="80" v-if="columnVisible['salesCount']" />

      <el-table-column label="状态" align="center" key="status" prop="status" width="80" v-if="columnVisible['status']">

        <template #default="scope">

          <dict-tag :options="NORMAL_DISABLE_OPTIONS" :value="scope.row.status" />

        </template>

      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="140">

        <template #default="scope">

          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['mall:mallProductSku:edit']">修改</el-button>

          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['mall:mallProductSku:remove']">删除</el-button>

        </template>

      </el-table-column>

    </el-table>



    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />



    <el-dialog :title="title" v-model="open" width="500px" append-to-body>

      <el-form ref="mallProductSkuRef" :model="form" :rules="rules" label-width="100px">

        <el-form-item label="所属SPU" prop="productId">

          <RemoteSelect v-model="form.productId" :remote="productRemote" placeholder="商品名称搜索" width="100%" />

        </el-form-item>

        <el-form-item label="SKU名称" prop="skuName">

          <el-input v-model="form.skuName" placeholder="请输入SKU名称" />

        </el-form-item>

        <el-form-item label="市场价" prop="marketPrice">

          <el-input v-model="form.marketPrice" placeholder="请输入市场价" />

        </el-form-item>

        <el-form-item label="销售价" prop="salePrice">

          <el-input v-model="form.salePrice" placeholder="请输入销售价（≥0）" />

        </el-form-item>

        <el-form-item label="库存" prop="stock">

          <el-input v-model="form.stock" placeholder="请输入库存（≥0）" />

        </el-form-item>

        <el-form-item label="状态" prop="status">

          <el-select v-model="form.status" placeholder="请选择" style="width: 100%">

            <el-option v-for="item in NORMAL_DISABLE_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />

          </el-select>

        </el-form-item>

        <el-form-item label="备注" prop="remark">

          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />

        </el-form-item>

      </el-form>

      <template #footer>

        <el-button type="primary" @click="submitForm">确 定</el-button>

        <el-button @click="cancel">取 消</el-button>

      </template>

    </el-dialog>

  </div>

</template>



<script setup name="MallProductSku">

import { listMallProductSku, getMallProductSku, delMallProductSku, addMallProductSku, updateMallProductSku } from "@/api/mall/mallProductSku"

import { getMallProduct } from "@/api/mall/mallProduct"

import { NORMAL_DISABLE_OPTIONS } from "@/utils/mallEnums"

import RemoteSelect from "@/components/RemoteSelect/index.vue"

import { useProductRemoteSelect } from "@/utils/useRemoteSelect"



const { proxy } = getCurrentInstance()

const route = useRoute()

const productRemote = useProductRemoteSelect()



const mallProductSkuList = ref([])

const open = ref(false)

const loading = ref(true)

const showSearch = ref(true)

const ids = ref([])

const single = ref(true)

const multiple = ref(true)

const total = ref(0)

const title = ref("")



const columns = ref([

  { key: 'skuId', label: 'SKU ID', visible: false },

  { key: 'productName', label: '所属SPU', visible: true },

  { key: 'productId', label: '商品ID', visible: false },

  { key: 'skuName', label: 'SKU名称', visible: true },

  { key: 'salePrice', label: '销售价', visible: true },

  { key: 'stock', label: '库存', visible: true },

  { key: 'salesCount', label: '销量', visible: true },

  { key: 'status', label: '状态', visible: true },

])



const columnVisible = computed(() => {

  const result = {}

  columns.value.forEach(col => {

    result[col.key] = col.visible

  })

  return result

})



const validateNonNegative = (rule, value, callback) => {

  if (value === null || value === undefined || value === '') {

    callback()

    return

  }

  const num = Number(value)

  if (isNaN(num) || num < 0) {

    callback(new Error('不能为负数'))

  } else {

    callback()

  }

}



const data = reactive({

  form: {},

  queryParams: {

    pageNum: 1,

    pageSize: 10,

    productId: undefined,

    skuName: undefined,

    status: undefined,

  },

  rules: {

    productId: [{ required: true, message: "请选择所属SPU", trigger: "change" }],

    skuName: [{ required: true, message: "SKU名称不能为空", trigger: "blur" }],

    salePrice: [{ validator: validateNonNegative, trigger: "blur" }],

    stock: [{ validator: validateNonNegative, trigger: "blur" }],

  }

})



const { queryParams, form, rules } = toRefs(data)



function getList() {

  loading.value = true

  listMallProductSku(queryParams.value).then(response => {

    mallProductSkuList.value = response.rows

    total.value = response.total

    loading.value = false

  })

}



function cancel() {

  open.value = false

  reset()

}



function reset() {

  form.value = {

    skuId: null,

    productId: queryParams.value.productId || null,

    skuName: null,

    marketPrice: null,

    salePrice: null,

    stock: null,

    salesCount: null,

    status: 0,

    remark: null

  }

  proxy.resetForm("mallProductSkuRef")

}



function handleQuery() {

  queryParams.value.pageNum = 1

  getList()

}



function resetQuery() {

  proxy.resetForm("queryRef")

  if (route.query.productId) {

    queryParams.value.productId = route.query.productId

  }

  handleQuery()

}



function handleSelectionChange(selection) {

  ids.value = selection.map(item => item.skuId)

  single.value = selection.length != 1

  multiple.value = !selection.length

}



function handleAdd() {

  reset()

  open.value = true

  title.value = "添加商城商品SKU"

}



function handleUpdate(row) {

  reset()

  const _skuId = row.skuId || ids.value

  getMallProductSku(_skuId).then(response => {

    form.value = response.data

    productRemote.ensureOption(form.value.productId, getMallProduct, (row) => row.productName || `商品${row.productId}`)

    open.value = true

    title.value = "修改商城商品SKU"

  })

}



function submitForm() {

  proxy.$refs["mallProductSkuRef"].validate(valid => {

    if (valid) {

      const req = form.value.skuId != null ? updateMallProductSku(form.value) : addMallProductSku(form.value)

      req.then(() => {

        proxy.$modal.msgSuccess(form.value.skuId != null ? "修改成功" : "新增成功")

        open.value = false

        getList()

      })

    }

  })

}



function handleDelete(row) {

  const _skuIds = row.skuId || ids.value

  proxy.$modal.confirm('是否确认删除商城商品SKU编号为"' + _skuIds + '"的数据项？').then(function() {

    return delMallProductSku(_skuIds)

  }).then(() => {

    getList()

    proxy.$modal.msgSuccess("删除成功")

  }).catch(() => {})

}



function handleExport() {

  proxy.download('web/mall/mallProductSku/export', { ...queryParams.value }, `mallProductSku_${new Date().getTime()}.xlsx`)

}



onMounted(() => {

  if (route.query.productId) {

    queryParams.value.productId = route.query.productId

    productRemote.ensureOption(route.query.productId, getMallProduct, (row) => row.productName || `商品${row.productId}`)

  }

  getList()

})

</script>

