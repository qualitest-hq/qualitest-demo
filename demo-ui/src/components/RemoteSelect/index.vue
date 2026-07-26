<template>
  <el-select
    :model-value="modelValue"
    @update:model-value="$emit('update:modelValue', $event)"
    filterable
    remote
    clearable
    reserve-keyword
    :placeholder="placeholder"
    :remote-method="remote.remoteSearch"
    :loading="remote.loading"
    :style="{ width }"
    @focus="handleFocus"
  >
    <el-option v-for="item in remote.options" :key="item.value" :label="item.label" :value="item.value" />
  </el-select>
</template>

<script setup>
const props = defineProps({
  modelValue: [String, Number],
  remote: { type: Object, required: true },
  placeholder: { type: String, default: '请输入关键词搜索' },
  width: { type: String, default: '220px' },
})
defineEmits(['update:modelValue'])

function handleFocus() {
  if (!props.remote.options?.length) {
    props.remote.remoteSearch('')
  }
}
</script>
