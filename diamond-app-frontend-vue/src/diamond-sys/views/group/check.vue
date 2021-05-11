<template>
  <div>
    <el-button icon="el-icon-back" round @click="$emit('option-changed')">
      返回
    </el-button>
    <el-collapse value="base-info" accordion>
      <el-collapse-item title="基本信息" name="base-info">
        <el-form :model="detail" :label-width="labelWidth">
          <input-item-view label="ID">
            <el-input v-model="detail.id" disabled/>
          </input-item-view>
          <input-item-view label="分组排序号">
            <el-input v-model="detail.groupIndex" disabled/>
          </input-item-view>
          <input-item-view label="分组名称">
            <el-input v-model="detail.groupName" disabled/>
          </input-item-view>
          <text-item-view label="分组备注">
            <el-input v-model="detail.remark" disabled/>
          </text-item-view>
        </el-form>
      </el-collapse-item>
      <el-collapse-item title="用户信息" name="group-user">
        <el-table :data="users" border style="width: 100%">
          <el-table-column type="index" width="100" align="center"/>
          <el-table-column :show-overflow-tooltip="true" prop="loginName" label="登录ID" sortable align="center"/>
          <el-table-column :show-overflow-tooltip="true" prop="userName" label="姓名" sortable align="center"/>
          <el-table-column prop="gender" label="性别" width="100" sortable align="center">
            <template slot-scope="scope">
              {{ scope.row.gender | translateGender }}
            </template>
          </el-table-column>
          <el-table-column :show-overflow-tooltip="true" prop="email" label="邮箱" sortable align="center"/>
          <el-table-column :show-overflow-tooltip="true" prop="phone" label="电话" width="160" sortable align="center"/>
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import mixins from './mixins'

export default {
  mixins: [mixins],
  props: {
    detail: {
      required: true,
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      labelWidth: '200px',
      users: []
    }
  },
  activated() {
    this.queryAllUsers()
  }
}
</script>

<style lang="scss" scoped>
  .el-button {
    margin-bottom: 10px;
  }
</style>
