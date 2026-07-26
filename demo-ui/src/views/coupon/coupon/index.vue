<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="98px">
      <el-form-item label="优惠券名称" prop="couponName">
        <el-input
          v-model="queryParams.couponName"
          placeholder="请输入优惠券名称"
          clearable
          @keyup.enter="handleQuery"
        />
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
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['coupon:coupon:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['coupon:coupon:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['coupon:coupon:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['coupon:coupon:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="couponList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="优惠券ID" align="center" key="couponId" prop="couponId" v-if="columnVisible['couponId']" />
      <el-table-column label="优惠券名称" align="center" key="couponName" prop="couponName" v-if="columnVisible['couponName']" />
      <el-table-column label="满金额" align="center" key="thresholdAmount" prop="thresholdAmount" v-if="columnVisible['thresholdAmount']" />
      <el-table-column label="减金额" align="center" key="discountAmount" prop="discountAmount" v-if="columnVisible['discountAmount']" />
      <el-table-column label="发放总量" align="center" key="totalCount" prop="totalCount" width="90" v-if="columnVisible['totalCount']" />
      <el-table-column label="已领取" align="center" key="receiveCount" prop="receiveCount" width="80" v-if="columnVisible['receiveCount']">
        <template #default="scope">
          <el-tag type="info">{{ scope.row.receiveCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="已使用" align="center" key="usedCount" prop="usedCount" width="80" v-if="columnVisible['usedCount']">
        <template #default="scope">
          <el-tag type="success">{{ scope.row.usedCount }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="有效开始时间" align="center" key="validStartTime" prop="validStartTime" width="180" v-if="columnVisible['validStartTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.validStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="有效结束时间" align="center" key="validEndTime" prop="validEndTime" width="180" v-if="columnVisible['validEndTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.validEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" key="status" prop="status" width="80" v-if="columnVisible['status']">
        <template #default="scope">
          <dict-tag :options="NORMAL_DISABLE_OPTIONS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" key="remark" prop="remark" v-if="columnVisible['remark']" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['coupon:coupon:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['coupon:coupon:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改优惠券对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="couponRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="优惠券名称" prop="couponName">
              <el-input v-model="form.couponName" placeholder="请输入优惠券名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="满金额" prop="thresholdAmount">
              <el-input v-model="form.thresholdAmount" placeholder="请输入满金额" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="减金额" prop="discountAmount">
              <el-input v-model="form.discountAmount" placeholder="请输入减金额" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="发放总量" prop="totalCount">
              <el-input v-model="form.totalCount" placeholder="请输入发放总量" :disabled="!!form.couponId" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="已领取数量" prop="receiveCount">
              <el-input v-model="form.receiveCount" disabled placeholder="系统自动统计" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="已使用数量" prop="usedCount">
              <el-input v-model="form.usedCount" disabled placeholder="系统自动统计" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择" style="width: 100%">
                <el-option v-for="item in NORMAL_DISABLE_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="有效开始时间" prop="validStartTime">
              <el-date-picker clearable
                v-model="form.validStartTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择有效开始时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="有效结束时间" prop="validEndTime">
              <el-date-picker clearable
                v-model="form.validEndTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择有效结束时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Coupon">
import { listCoupon, getCoupon, delCoupon, addCoupon, updateCoupon } from "@/api/coupon/coupon"
import { NORMAL_DISABLE_OPTIONS } from "@/utils/mallEnums"

const { proxy } = getCurrentInstance()

const couponList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const columns = ref([
  { key: 'couponId', label: '优惠券ID', visible: false },
  { key: 'couponName', label: '优惠券名称', visible: true },
  { key: 'thresholdAmount', label: '满金额', visible: true },
  { key: 'discountAmount', label: '减金额', visible: true },
  { key: 'totalCount', label: '发放总量', visible: true },
  { key: 'receiveCount', label: '已领取', visible: true },
  { key: 'usedCount', label: '已使用', visible: true },
  { key: 'validStartTime', label: '有效开始时间', visible: true },
  { key: 'validEndTime', label: '有效结束时间', visible: true },
  { key: 'status', label: '状态', visible: true },
  { key: 'remark', label: '备注', visible: true },
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
    couponName: undefined,
    status: undefined,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询优惠券列表 */
function getList() {
  loading.value = true
  listCoupon(queryParams.value).then(response => {
    couponList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 取消按钮 */
function cancel() {
  open.value = false
  reset()
}

/** 表单重置 */
function reset() {
  form.value = {
    couponId: null,
    couponName: null,
    thresholdAmount: null,
    discountAmount: null,
    totalCount: null,
    receiveCount: null,
    usedCount: null,
    validStartTime: null,
    validEndTime: null,
    status: 0,
    delFlag: null,
    createTime: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("couponRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.couponId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加优惠券"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _couponId = row.couponId || ids.value
  getCoupon(_couponId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改优惠券"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["couponRef"].validate(valid => {
    if (valid) {
      if (form.value.couponId != null) {
        updateCoupon(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCoupon(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _couponIds = row.couponId || ids.value
  proxy.$modal.confirm('是否确认删除优惠券编号为"' + _couponIds + '"的数据项？').then(function() {
    return delCoupon(_couponIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('web/coupon/coupon/export', {
    ...queryParams.value
  }, `coupon_${new Date().getTime()}.xlsx`)
}

getList()
</script>
