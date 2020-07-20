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
          <input-item-view v-if="parentDictionaryName" label="上级字典">
            {{ parentDictionaryName }}
          </input-item-view>
          <!--<input-item-view label="启用状态">-->
            <!--{{ detail.state | translateState }}-->
          <!--</input-item-view>-->
          <input-item-view label="字典名称">
            {{ detail.name }}
          </input-item-view>
          <input-item-view label="字典规则码">
            {{ detail.code }}
          </input-item-view>
          <input-item-view label="字典排序号">
            {{ detail.codeIndex }}
          </input-item-view>
          <text-item-view label="字典备注">
            {{ detail.remark }}
          </text-item-view>
        </el-form>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import mixins from './mixins'

export default {
  mixins: [mixins],
  data() {
    return {
      labelWidth: '200px'
    }
  },
  activated() {
    this.getParentDictionaryName(this.detail.parentId)
  }
}
</script>

<style lang="scss" scoped>
  .el-button {
    margin-bottom: 10px;
  }
</style>
