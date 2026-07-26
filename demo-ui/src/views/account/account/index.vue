<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="86px">
      <el-form-item label="昵称" prop="nickName">
        <el-input
          v-model="queryParams.nickName"
          placeholder="请输入昵称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select v-model="queryParams.gender" placeholder="全部" clearable style="width: 100px">
          <el-option v-for="item in GENDER_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="注册来源" prop="registerSource">
        <el-input
          v-model="queryParams.registerSource"
          placeholder="请输入注册来源"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="账号状态" prop="status">
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
          v-hasPermi="['account:account:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['account:account:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Coin"
          :disabled="single"
          @click="handleGiftBalance()"
          v-hasPermi="['account:account:edit']"
        >赠送余额</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['account:account:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['account:account:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="账号ID" align="center" key="accountId" prop="accountId" v-if="columnVisible['accountId']" />
      <el-table-column label="昵称" align="center" key="nickName" prop="nickName" v-if="columnVisible['nickName']" />
      <el-table-column label="手机号" align="center" key="mobile" prop="mobile" v-if="columnVisible['mobile']" />
      <el-table-column label="账户余额" align="center" key="balance" prop="balance" v-if="columnVisible['balance']" />
      <el-table-column label="性别" align="center" key="gender" prop="gender" width="80" v-if="columnVisible['gender']">
        <template #default="scope">
          <dict-tag :options="GENDER_OPTIONS" :value="scope.row.gender" />
        </template>
      </el-table-column>
      <el-table-column label="注册来源" align="center" key="registerSource" prop="registerSource" v-if="columnVisible['registerSource']" />
      <el-table-column label="账号状态" align="center" key="status" prop="status" width="100" v-if="columnVisible['status']">
        <template #default="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="0"
            :inactive-value="1"
            @change="handleStatusChange(scope.row)"
            v-hasPermi="['account:account:edit']"
          />
        </template>
      </el-table-column>
      <el-table-column label="最后登录IP" align="center" key="lastLoginIp" prop="lastLoginIp" v-if="columnVisible['lastLoginIp']" />
      <el-table-column label="最后登录时间" align="center" key="lastLoginTime" prop="lastLoginTime" width="180" v-if="columnVisible['lastLoginTime']">
        <template #default="scope">
          <span>{{ parseTime(scope.row.lastLoginTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" key="remark" prop="remark" v-if="columnVisible['remark']" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="280">
        <template #default="scope">
          <el-button link type="primary" @click="goAddresses(scope.row)">收货地址</el-button>
          <el-button link type="primary" @click="handleGiftBalance(scope.row)" v-hasPermi="['account:account:edit']">赠送余额</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['account:account:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['account:account:remove']">删除</el-button>
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

    <!-- 添加或修改用户账号对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="accountRef" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入昵称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="手机号" prop="mobile">
              <el-input v-model="form.mobile" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" :placeholder="form.accountId ? '留空则不修改' : '请输入密码'" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="账户余额" prop="balance">
              <el-input v-model="form.balance" placeholder="请输入账户余额" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择" style="width: 100%">
                <el-option v-for="item in GENDER_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="注册来源" prop="registerSource">
              <el-input v-model="form.registerSource" placeholder="请输入注册来源" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="账号状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择" style="width: 100%">
                <el-option v-for="item in NORMAL_DISABLE_OPTIONS" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="最后登录IP" prop="lastLoginIp">
              <el-input v-model="form.lastLoginIp" placeholder="请输入最后登录IP" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="最后登录时间" prop="lastLoginTime">
              <el-date-picker clearable
                v-model="form.lastLoginTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择最后登录时间">
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

    <!-- 赠送余额对话框 -->
    <el-dialog title="赠送余额" v-model="giftOpen" width="480px" append-to-body>
      <el-form ref="giftRef" :model="giftForm" :rules="giftRules" label-width="100px">
        <el-form-item label="用户">
          <span>{{ giftForm.nickName }}（{{ giftForm.mobile }}）</span>
        </el-form-item>
        <el-form-item label="当前余额">
          <span>{{ giftForm.balance }}</span>
        </el-form-item>
        <el-form-item label="赠送金额" prop="amount">
          <el-input v-model="giftForm.amount" placeholder="请输入赠送金额" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="giftForm.remark" type="textarea" placeholder="可选，写入账号备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitGiftBalance">确 定</el-button>
          <el-button @click="giftOpen = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Account">
import { listAccount, getAccount, delAccount, addAccount, updateAccount, giftBalance } from "@/api/account/account"
import { GENDER_OPTIONS, NORMAL_DISABLE_OPTIONS } from "@/utils/mallEnums"

const { proxy } = getCurrentInstance()
const router = useRouter()

const accountList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const giftOpen = ref(false)
const giftForm = ref({
  accountId: null,
  nickName: '',
  mobile: '',
  balance: null,
  amount: null,
  remark: ''
})

const giftRules = {
  amount: [
    { required: true, message: '赠送金额不能为空', trigger: 'blur' },
    {
      validator: (_rule, value, callback) => {
        const num = Number(value)
        if (Number.isNaN(num) || num <= 0) {
          callback(new Error('赠送金额须大于0'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const columns = ref([
  { key: 'accountId', label: '账号ID', visible: false },
  { key: 'nickName', label: '昵称', visible: true },
  { key: 'mobile', label: '手机号', visible: true },
  { key: 'balance', label: '账户余额', visible: true },
  { key: 'gender', label: '性别', visible: true },
  { key: 'registerSource', label: '注册来源', visible: true },
  { key: 'status', label: '账号状态', visible: true },
  { key: 'lastLoginIp', label: '最后登录IP', visible: true },
  { key: 'lastLoginTime', label: '最后登录时间', visible: true },
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
    nickName: undefined,
    mobile: undefined,
    gender: undefined,
    registerSource: undefined,
    status: undefined,
  },
  rules: {
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询用户账号列表 */
function getList() {
  loading.value = true
  listAccount(queryParams.value).then(response => {
    accountList.value = response.rows
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
    accountId: null,
    nickName: null,
    mobile: null,
    password: null,
    balance: null,
    gender: null,
    registerSource: null,
    status: null,
    lastLoginIp: null,
    lastLoginTime: null,
    delFlag: null,
    createTime: null,
    updateTime: null,
    remark: null
  }
  proxy.resetForm("accountRef")
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
  ids.value = selection.map(item => item.accountId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加用户账号"
}

/** 修改按钮操作 */
function goAddresses(row) {
  router.push({ path: '/accountManages/accountAddress', query: { accountId: row.accountId } })
}

function handleUpdate(row) {
  reset()
  const _accountId = row.accountId || ids.value
  getAccount(_accountId).then(response => {
    form.value = response.data
    form.value.password = ''
    open.value = true
    title.value = "修改用户账号"
  })
}

function handleGiftBalance(row) {
  const accountId = row?.accountId || ids.value[0]
  getAccount(accountId).then(response => {
    const data = response.data
    giftForm.value = {
      accountId: data.accountId,
      nickName: data.nickName,
      mobile: data.mobile,
      balance: data.balance,
      amount: null,
      remark: ''
    }
    giftOpen.value = true
    nextTick(() => proxy.resetForm('giftRef'))
  })
}

function submitGiftBalance() {
  proxy.$refs.giftRef.validate(valid => {
    if (!valid) {
      return
    }
    giftBalance({
      accountId: giftForm.value.accountId,
      amount: giftForm.value.amount,
      remark: giftForm.value.remark
    }).then(response => {
      proxy.$modal.msgSuccess(`赠送成功，当前余额 ${response.data.balance}`)
      giftOpen.value = false
      getList()
    })
  })
}

function handleStatusChange(row) {
  const text = row.status === 0 ? '启用' : '停用'
  updateAccount({ accountId: row.accountId, status: row.status }).then(() => {
    proxy.$modal.msgSuccess(text + '成功')
  }).catch(() => {
    row.status = row.status === 0 ? 1 : 0
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["accountRef"].validate(valid => {
    if (valid) {
      const payload = { ...form.value }
      if (payload.accountId != null && !payload.password) {
        delete payload.password
      }
      if (form.value.accountId != null) {
        updateAccount(payload).then(() => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAccount(form.value).then(() => {
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
  const _accountIds = row.accountId || ids.value
  proxy.$modal.confirm('是否确认删除用户账号编号为"' + _accountIds + '"的数据项？').then(function() {
    return delAccount(_accountIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('web/account/account/export', {
    ...queryParams.value
  }, `account_${new Date().getTime()}.xlsx`)
}

getList()
</script>
