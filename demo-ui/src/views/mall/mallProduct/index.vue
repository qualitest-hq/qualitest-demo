<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="86px">
      <el-form-item label="分类" prop="categoryId">
        <RemoteSelect v-model="queryParams.categoryId" :remote="categoryRemote" placeholder="分类名称搜索" />
      </el-form-item>
      <el-form-item label="商品名称" prop="productName">
        <el-input v-model="queryParams.productName" placeholder="请输入商品名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="上架状态" prop="shelfStatus">
        <el-select v-model="queryParams.shelfStatus" placeholder="全部" clearable style="width: 100px">
          <el-option v-for="item in SHELF_STATUS_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['mall:mallProduct:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate" v-hasPermi="['mall:mallProduct:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['mall:mallProduct:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['mall:mallProduct:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mallProductList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="商品ID" align="center" key="productId" prop="productId" width="100" v-if="columnVisible['productId']" />
      <el-table-column label="分类" align="center" key="categoryName" prop="categoryName" min-width="120" show-overflow-tooltip v-if="columnVisible['categoryName']" />
      <el-table-column label="商品名称" align="center" key="productName" prop="productName" min-width="140" show-overflow-tooltip v-if="columnVisible['productName']" />
      <el-table-column label="封面" align="center" key="coverImage" prop="coverImage" width="80" v-if="columnVisible['coverImage']">
        <template #default="scope">
          <image-preview v-if="scope.row.coverImage" :src="scope.row.coverImage" :width="40" :height="40" />
        </template>
      </el-table-column>
      <el-table-column label="销售价" align="center" key="salePrice" prop="salePrice" width="90" v-if="columnVisible['salePrice']" />
      <el-table-column label="固定运费" align="center" key="freightAmount" prop="freightAmount" width="90" v-if="columnVisible['freightAmount']" />
      <el-table-column label="总库存" align="center" key="stockTotal" prop="stockTotal" width="80" v-if="columnVisible['stockTotal']" />
      <el-table-column label="上架状态" align="center" key="shelfStatus" prop="shelfStatus" width="90" v-if="columnVisible['shelfStatus']">
        <template #default="scope">
          <dict-tag :options="SHELF_STATUS_OPTIONS" :value="scope.row.shelfStatus" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['mall:mallProduct:edit']">修改</el-button>
          <el-button link type="primary" @click="goSkuManage(scope.row)">SKU管理</el-button>
          <el-button link :type="scope.row.shelfStatus === 1 ? 'warning' : 'success'" @click="handleToggleShelf(scope.row)" v-hasPermi="['mall:mallProduct:edit']">
            {{ scope.row.shelfStatus === 1 ? '下架' : '上架' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" v-model="open" width="640px" append-to-body>
      <el-form ref="mallProductRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="分类" prop="categoryId">
          <RemoteSelect v-model="form.categoryId" :remote="categoryRemote" placeholder="分类名称搜索" width="100%" />
        </el-form-item>
        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="计量单位" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入计量单位" />
        </el-form-item>
        <el-form-item label="市场价" prop="marketPrice">
          <el-input v-model="form.marketPrice" placeholder="请输入市场价" />
        </el-form-item>
        <el-form-item label="销售价" prop="salePrice">
          <el-input v-model="form.salePrice" placeholder="请输入销售价" />
        </el-form-item>
        <el-form-item label="封面图" prop="coverImage">
          <image-upload v-model="form.coverImage" :limit="1" />
        </el-form-item>
        <el-form-item label="详情图" prop="detailImages">
          <image-upload v-model="form.detailImages" :limit="5" />
        </el-form-item>
        <el-form-item label="视频" prop="videoUrl">
          <file-upload v-model="form.videoUrl" :limit="1" :file-size="20" :file-type="['mp4', 'avi', 'rmvb']" />
        </el-form-item>
        <el-form-item label="固定运费" prop="freightAmount">
          <el-input v-model="form.freightAmount" placeholder="请输入固定运费" />
        </el-form-item>
        <el-form-item label="上架状态" prop="shelfStatus">
          <el-select v-model="form.shelfStatus" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in SHELF_STATUS_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sortNum">
          <el-input v-model="form.sortNum" placeholder="请输入排序" />
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

<script setup name="MallProduct">
import { listMallProduct, getMallProduct, delMallProduct, addMallProduct, updateMallProduct, changeShelfStatus } from "@/api/mall/mallProduct"
import { getMallCategory } from "@/api/mall/mallCategory"
import { SHELF_STATUS_OPTIONS } from "@/utils/mallEnums"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useCategoryRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()
const router = useRouter()
const categoryRemote = useCategoryRemoteSelect()

const mallProductList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const columns = ref([
  { key: 'productId', label: '商品ID', visible: false },
  { key: 'categoryName', label: '分类', visible: true },
  { key: 'categoryId', label: '分类ID', visible: false },
  { key: 'productName', label: '商品名称', visible: true },
  { key: 'coverImage', label: '封面', visible: true },
  { key: 'salePrice', label: '销售价', visible: true },
  { key: 'freightAmount', label: '固定运费', visible: true },
  { key: 'stockTotal', label: '总库存', visible: true },
  { key: 'shelfStatus', label: '上架状态', visible: true },
])

const columnVisible = computed(() => {
  const result = {}
  columns.value.forEach(col => {
    result[col.key] = col.visible
  })
  return result
})

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    categoryId: undefined,
    productName: undefined,
    shelfStatus: undefined,
  },
  rules: {
    categoryId: [{ required: true, message: "请选择分类", trigger: "change" }],
    productName: [{ required: true, message: "商品名称不能为空", trigger: "blur" }],
  }
})

const { queryParams, form, rules } = toRefs(data)

function getList() {
  loading.value = true
  listMallProduct(queryParams.value).then(response => {
    mallProductList.value = response.rows
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
    productId: null,
    categoryId: null,
    productName: null,
    unitName: null,
    marketPrice: null,
    salePrice: null,
    coverImage: null,
    detailImages: null,
    videoUrl: null,
    freightAmount: null,
    stockTotal: null,
    salesCount: null,
    shelfStatus: 0,
    sortNum: null,
    remark: null
  }
  proxy.resetForm("mallProductRef")
}

function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.productId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

function handleAdd() {
  reset()
  open.value = true
  title.value = "添加商城商品SPU"
}

function handleUpdate(row) {
  reset()
  const _productId = row.productId || ids.value
  getMallProduct(_productId).then(response => {
    form.value = response.data
    categoryRemote.ensureOption(form.value.categoryId, getMallCategory, (row) => row.categoryName || `分类${row.categoryId}`)
    open.value = true
    title.value = "修改商城商品SPU"
  })
}

function submitForm() {
  proxy.$refs["mallProductRef"].validate(valid => {
    if (valid) {
      if (form.value.productId != null) {
        updateMallProduct(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addMallProduct(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

function handleDelete(row) {
  const _productIds = row.productId || ids.value
  proxy.$modal.confirm('是否确认删除商城商品SPU编号为"' + _productIds + '"的数据项？').then(function() {
    return delMallProduct(_productIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

function handleExport() {
  proxy.download('web/mall/mallProduct/export', { ...queryParams.value }, `mallProduct_${new Date().getTime()}.xlsx`)
}

function goSkuManage(row) {
  router.push({ path: '/mallManages/mallProductSku', query: { productId: row.productId } })
}

function handleToggleShelf(row) {
  const newStatus = row.shelfStatus === 1 ? 0 : 1
  const action = newStatus === 1 ? '上架' : '下架'
  proxy.$modal.confirm('确认' + action + '商品「' + row.productName + '」？').then(() => {
    return changeShelfStatus({ productId: row.productId, shelfStatus: newStatus })
  }).then(() => {
    proxy.$modal.msgSuccess(action + '成功')
    getList()
  }).catch(() => {})
}

getList()
</script>
