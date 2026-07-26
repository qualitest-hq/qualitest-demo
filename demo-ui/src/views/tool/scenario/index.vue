<template>
  <div class="app-container scenario-page">
    <el-row class="toolbar" justify="space-between" align="middle">
      <el-col :span="16">
        <span class="page-title">测试场景</span>
        <el-tag v-if="currentScenarioId" type="success" class="current-tag">当前：{{ currentScenarioId }}</el-tag>
        <el-tag v-else type="info" class="current-tag">当前：未加载场景</el-tag>
      </el-col>
      <el-col :span="8" class="toolbar-actions">
        <el-button type="warning" plain icon="Refresh" v-hasPermi="['tool:scenario:reset']" @click="handleReset">重置基线</el-button>
        <el-button type="primary" plain icon="Refresh" @click="fetchData">刷新</el-button>
      </el-col>
    </el-row>

    <el-alert type="info" :closable="false" show-icon class="hint-alert"
      title="加载场景将先清空 13 张业务表并恢复基线 seed，再写入场景数据。" />

    <el-tabs v-model="activeTab">
      <el-tab-pane label="成功场景" name="success">
        <el-row :gutter="16">
          <el-col v-for="item in successScenarios" :key="item.id" :xs="24" :sm="12" :lg="8" class="card-col">
            <el-card shadow="hover" class="scenario-card">
              <template #header>
                <el-tag :type="'success'" size="small">{{ item.id }}</el-tag>
                <span class="card-title">{{ item.name }}</span>
              </template>
              <p v-if="item.expect" class="meta"><span class="label">预期：</span>{{ item.expect }}</p>
              <p v-if="item.account" class="meta">
                <span class="label">账号：</span>{{ item.account.phone }}
                <el-button link type="primary" size="small" @click="copyText(item.account.password)">复制密码</el-button>
              </p>
              <template #footer>
                <el-button type="primary" v-hasPermi="['tool:scenario:load']" @click="handleLoad(item)">加载此场景</el-button>
              </template>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="失败场景" name="fail">
        <el-row :gutter="16">
          <el-col v-for="item in failScenarios" :key="item.id" :xs="24" :sm="12" :lg="8" class="card-col">
            <el-card shadow="hover" class="scenario-card">
              <template #header>
                <el-tag type="danger" size="small">{{ item.id }}</el-tag>
                <span class="card-title">{{ item.name }}</span>
              </template>
              <p v-if="item.expect" class="meta"><span class="label">预期：</span>{{ item.expect }}</p>
              <p v-if="item.account" class="meta">
                <span class="label">账号：</span>{{ item.account.phone }}
                <el-button link type="primary" size="small" @click="copyText(item.account.password)">复制密码</el-button>
              </p>
              <template #footer>
                <el-button type="primary" v-hasPermi="['tool:scenario:load']" @click="handleLoad(item)">加载此场景</el-button>
              </template>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>

    <div class="footer-links">
      <el-link type="primary" href="/swagger-ui.html" target="_blank">打开 Swagger 文档</el-link>
    </div>
  </div>
</template>

<script setup name="TestScenario">
import { listScenarios, getCurrentScenario, resetBaseline, loadScenario } from '@/api/tool/scenario'

const { proxy } = getCurrentInstance()
const activeTab = ref('success')
const scenarios = ref([])
const currentScenarioId = ref('')
const successScenarios = computed(() => scenarios.value.filter(s => s.type === 'success'))
const failScenarios = computed(() => scenarios.value.filter(s => s.type === 'fail'))

function copyText(text) {
  navigator.clipboard.writeText(text).then(() => proxy.$modal.msgSuccess('已复制')).catch(() => proxy.$modal.msgError('复制失败'))
}

function fetchCurrent() {
  getCurrentScenario().then(res => { currentScenarioId.value = res.data?.scenarioId || '' }).catch(() => {})
}

function fetchData() {
  proxy.$modal.loading('正在加载场景列表...')
  listScenarios().then(res => {
    scenarios.value = res.data || []
    fetchCurrent()
  }).finally(() => proxy.$modal.closeLoading())
}

function handleReset() {
  proxy.$modal.confirm('将清空 13 张业务表并恢复基线 seed，是否继续？')
    .then(() => { proxy.$modal.loading('正在重置...'); return resetBaseline() })
    .then(() => { proxy.$modal.msgSuccess('基线数据已重置'); currentScenarioId.value = '' })
    .finally(() => proxy.$modal.closeLoading())
    .catch(() => {})
}

function handleLoad(item) {
  proxy.$modal.confirm(`将重置基线并加载「${item.name}」（${item.id}），是否继续？`)
    .then(() => { proxy.$modal.loading(`正在加载 ${item.id}...`); return loadScenario(item.id) })
    .then(res => { proxy.$modal.msgSuccess(res.msg || '场景已加载'); currentScenarioId.value = item.id })
    .finally(() => proxy.$modal.closeLoading())
    .catch(() => {})
}

onMounted(fetchData)
</script>

<style scoped lang="scss">
.scenario-page {
  .toolbar { margin-bottom: 16px; }
  .page-title { font-size: 18px; font-weight: 600; margin-right: 12px; }
  .current-tag { vertical-align: middle; }
  .toolbar-actions { text-align: right; }
  .hint-alert { margin-bottom: 16px; }
  .card-col { margin-bottom: 16px; }
  .footer-links { margin-top: 24px; text-align: center; }
  .scenario-card {
    height: 100%;
    .card-title { margin-left: 8px; font-weight: 600; }
    .meta { margin: 0 0 8px; font-size: 13px; color: #606266; line-height: 1.5; }
    .label { color: #909399; }
  }
}
</style>
