<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>质衡 Demo</h2>
        <p>
          基于质衡（Qualitest）框架改造的演示工程，用于体验后台管理、系统监控、代码生成等基础能力。
          本 Demo 与主工程隔离运行（后端默认端口 8801），便于本地开发与学习参考。
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>
        </p>

        <el-card shadow="hover" class="scenario-entry-card">
          <template #header>
            <span class="entry-title">接口测试场景准备</span>
          </template>
          <p class="entry-desc">
            一键重置并加载 S01–S08 / F01–F12 测试场景，配合 Swagger 或质衡跑接口用例。
          </p>
          <p v-if="currentScenarioId" class="entry-current">
            当前场景：<el-tag type="success" size="small">{{ currentScenarioId }}</el-tag>
          </p>
          <router-link to="/tool/scenario">
            <el-button type="primary" icon="Pointer">进入测试场景控制台</el-button>
          </router-link>
        </el-card>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>Spring Boot 3</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Redis</li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue 3</li>
              <li>Pinia</li>
              <li>Element Plus</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Vite</li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
import { getCurrentScenario } from '@/api/tool/scenario'

const version = ref('1.0.0')
const currentScenarioId = ref('')

onMounted(() => {
  getCurrentScenario()
    .then((res) => {
      currentScenarioId.value = res.data?.scenarioId || ''
    })
    .catch(() => {})
})
</script>

<style scoped lang="scss">
.home {
  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
    padding: 0;
    margin: 0;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  h4 {
    margin-top: 0;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .scenario-entry-card {
    margin-top: 20px;
    max-width: 480px;
  }

  .entry-title {
    font-weight: 600;
  }

  .entry-desc {
    margin: 0 0 12px;
    line-height: 1.6;
  }

  .entry-current {
    margin-bottom: 12px;
  }
}
</style>
