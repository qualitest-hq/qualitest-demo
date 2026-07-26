<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="优惠券" prop="couponId">
        <RemoteSelect v-model="queryParams.couponId" :remote="couponRemote" placeholder="优惠券名称搜索" />
      </el-form-item>
      <el-form-item label="账号" prop="accountId">
        <RemoteSelect v-model="queryParams.accountId" :remote="accountRemote" placeholder="昵称/手机号搜索" />
      </el-form-item>
      <el-form-item label="券名称" prop="couponName">
        <el-input
          v-model="queryParams.couponName"
          placeholder="请输入券名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="券状态" prop="couponStatus">
        <el-select v-model="queryParams.couponStatus" placeholder="全部" clearable style="width: 120px">
          <el-option v-for="item in COUPON_STATUS_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单编号" prop="orderNo">
        <el-input v-model="queryParams.orderNo" placeholder="请输入订单编号" clearable @keyup.enter="handleQuery" />
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
          v-hasPermi="['coupon:accountCoupon:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['coupon:accountCoupon:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['coupon:accountCoupon:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['coupon:accountCoupon:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountCouponList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="账号优惠券ID" align="center" key="accountCouponId" prop="accountCouponId" v-if="columnVisible['accountCouponId']" />
      <el-table-column label="优惠券ID" align="center" key="couponId" prop="couponId" v-if="columnVisible['couponId']" />
      <el-table-column label="账号" align="center" key="accountNickName" prop="accountNickName" min-width="110" show-overflow-tooltip v-if="columnVisible['accountNickName']" />
      <el-table-column label="账号ID" align="center" key="accountId" prop="accountId" v-if="columnVisible['accountId']" />
      <el-table-column label="券名称" align="center" key="couponName" prop="couponName" v-if="columnVisible['couponName']" />
      <el-table-column label="满减门槛" align="center" key="thresholdAmount" prop="thresholdAmount" v-if="columnVisible['thresholdAmount']" />
      <el-table-column label="满减金额" align="center" key="discountAmount" prop="discountAmount" v-if="columnVisible['discountAmount']" />
      <el-table-column label="有效开始" align="center" key="validStartTime" prop="validStartTime" width="180" v-if="columnVisible['validStartTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.validStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="有效结束" align="center" key="validEndTime" prop="validEndTime" width="180" v-if="columnVisible['validEndTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.validEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" key="couponStatus" prop="couponStatus" width="90" v-if="columnVisible['couponStatus']">
        <template #default="scope">
          <dict-tag :options="COUPON_STATUS_OPTIONS" :value="scope.row.couponStatus" />
        </template>
      </el-table-column>
      <el-table-column label="领取时间" align="center" key="receiveTime" prop="receiveTime" width="180" v-if="columnVisible['receiveTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.receiveTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="使用时间" align="center" key="useTime" prop="useTime" width="180" v-if="columnVisible['useTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.useTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="使用订单ID" align="center" key="orderId" prop="orderId" v-if="columnVisible['orderId']" />
      <el-table-column label="订单编号" align="center" key="orderNo" prop="orderNo" min-width="140" show-overflow-tooltip v-if="columnVisible['orderNo']" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['coupon:accountCoupon:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['coupon:accountCoupon:remove']">删除</el-button>
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

    <!-- 添加或修改账号优惠券对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="accountCouponRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="优惠券ID" prop="couponId">
              <el-input v-model="form.couponId" placeholder="请输入优惠券ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="账号ID" prop="accountId">
              <el-input v-model="form.accountId" placeholder="请输入账号ID" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="券名称" prop="couponName">
              <el-input v-model="form.couponName" placeholder="请输入券名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="满减门槛" prop="thresholdAmount">
              <el-input v-model="form.thresholdAmount" placeholder="请输入满减门槛" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="满减金额" prop="discountAmount">
              <el-input v-model="form.discountAmount" placeholder="请输入满减金额" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="有效开始" prop="validStartTime">
              <el-date-picker clearable
                v-model="form.validStartTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择有效开始">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="有效结束" prop="validEndTime">
              <el-date-picker clearable
                v-model="form.validEndTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择有效结束">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="领取时间" prop="receiveTime">
              <el-date-picker clearable
                v-model="form.receiveTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择领取时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="使用时间" prop="useTime">
              <el-date-picker clearable
                v-model="form.useTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择使用时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="使用订单ID" prop="orderId">
              <el-input v-model="form.orderId" placeholder="请输入使用订单ID" />
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

<script setup name="AccountCoupon">
import { listAccountCoupon, getAccountCoupon, delAccountCoupon, addAccountCoupon, updateAccountCoupon } from "@/api/coupon/accountCoupon"
import { COUPON_STATUS_OPTIONS } from "@/utils/mallEnums"
import RemoteSelect from "@/components/RemoteSelect/index.vue"
import { useAccountRemoteSelect, useCouponRemoteSelect } from "@/utils/useRemoteSelect"

const { proxy } = getCurrentInstance()
const accountRemote = useAccountRemoteSelect()
const couponRemote = useCouponRemoteSelect()

const accountCouponList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const columns = ref([
  { key: 'accountCouponId', label: '账号优惠券ID', visible: false },
  { key: 'couponId', label: '优惠券ID', visible: false },
  { key: 'accountNickName', label: '账号', visible: true },
  { key: 'accountId', label: '账号ID', visible: false },
  { key: 'couponName', label: '券名称', visible: true },
  { key: 'thresholdAmount', label: '满减门槛', visible: true },
  { key: 'discountAmount', label: '满减金额', visible: true },
  { key: 'validStartTime', label: '有效开始', visible: true },
  { key: 'validEndTime', label: '有效结束', visible: true },
  { key: 'couponStatus', label: '状态', visible: true },
  { key: 'receiveTime', label: '领取时间', visible: true },
  { key: 'useTime', label: '使用时间', visible: true },
  { key: 'orderId', label: '使用订单ID', visible: false },
  { key: 'orderNo', label: '订单编号', visible: true },
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
    couponId: undefined,
    accountId: undefined,
    couponName: undefined,
    couponStatus: undefined,
    orderNo: undefined,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询账号优惠券列表 */
function getList() {
  loading.value = true
  listAccountCoupon(queryParams.value).then(response => {
    accountCouponList.value = response.rows
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
    accountCouponId: null,
    couponId: null,
    accountId: null,
    couponName: null,
    thresholdAmount: null,
    discountAmount: null,
    validStartTime: null,
    validEndTime: null,
    couponStatus: null,
    receiveTime: null,
    useTime: null,
    orderId: null,
    delFlag: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("accountCouponRef")
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
  ids.value = selection.map(item => item.accountCouponId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加账号优惠券"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _accountCouponId = row.accountCouponId || ids.value
  getAccountCoupon(_accountCouponId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改账号优惠券"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["accountCouponRef"].validate(valid => {
    if (valid) {
      if (form.value.accountCouponId != null) {
        updateAccountCoupon(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAccountCoupon(form.value).then(() => {
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
  const _accountCouponIds = row.accountCouponId || ids.value
  proxy.$modal.confirm('是否确认删除账号优惠券编号为"' + _accountCouponIds + '"的数据项？').then(function() {
    return delAccountCoupon(_accountCouponIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('web/coupon/accountCoupon/export', {
    ...queryParams.value
  }, `accountCoupon_${new Date().getTime()}.xlsx`)
}

getList()
</script>
