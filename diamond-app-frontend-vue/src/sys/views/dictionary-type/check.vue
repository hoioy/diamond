<template>
  <div>
    <el-button icon="el-icon-back" round @click="$emit('option-changed')">
      返回
    </el-button>
    <el-collapse value="base-info" accordion>
      <el-collapse-item title="基本信息" name="base-info">
        <el-form :model="detail" :label-width="labelWidth">
          <input-item-view label="ID">
            {{ detail.id }}
          </input-item-view>
          <input-item-view label="字典分类名称">
            {{ detail.typeName }}
          </input-item-view>
          <text-item-view label="字典分类备注">
            {{ detail.remark }}
          </text-item-view>
        </el-form>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>

export default {
  props: {
    detail: {
      required: true,
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      labelWidth: '200px'
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-button {
    margin-bottom: 10px;
  }
</style>
