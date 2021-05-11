<template>
  <div>
    <el-button icon="el-icon-back" round @click="$emit('option-changed')">
      返回
    </el-button>
    <el-collapse value="base-info" accordion>
      <el-collapse-item title="基本信息" name="base-info">
        <el-form>
          <input-item-view label="ID">
            <el-input v-model="detail.id" disabled/>
          </input-item-view>
          <input-item-view v-if="parentMenuName" label="上级菜单">
            <el-input v-model="parentMenuName" disabled/>
          </input-item-view>
          <input-item-view label="菜单排序号">
            <el-input v-model="detail.menuIndex" disabled/>
          </input-item-view>
          <input-item-view label="菜单名称">
            <el-input v-model="detail.menuName" disabled/>
          </input-item-view>
          <input-item-view label="菜单图标名称">
            <el-input v-model="detail.smallIconPath" disabled/>
          </input-item-view>
          <input-item-view label="菜单分类">
            <el-input v-model="detail.menuClassify" disabled/>
          </input-item-view>
          <input-item-view label="菜单权限ID">
            <el-input v-model="detail.authorityId" disabled/>
          </input-item-view>
          <text-item-view label="菜单备注">
            <el-input v-model="detail.remark" disabled/>
          </text-item-view>
        </el-form>
      </el-collapse-item>
      <el-collapse-item title="菜单角色信息" name="menu-role">
        <el-table :data="roles" border style="width: 100%">
          <el-table-column prop="roleName" label="角色名称"/>
          <el-table-column prop="createdDate" label="创建时间">
            <template slot-scope="scope">
              {{ scope.row.createdDate | parseTime }}
            </template>
          </el-table-column>
          <el-table-column prop="modifiedDate" label="最后修改时间">
            <template slot-scope="scope">
              {{ scope.row.modifiedDate | parseTime }}
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import * as MenuAPI from '../../api/system-management/menu'
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
      labelWidth: '200px'
    }
  },
  activated() {
    this.getParentMenuName(this.detail.parentId)
    this.queryMenuRoles()
  },
  methods: {
    queryMenuUrls() {
      this.menuUrls = []
      const params = { id: this.detail.id }
      MenuAPI.queryAllMenuUrl(params).then(menuUrls => { this.menuUrls = menuUrls })
    },
    queryMenuRoles() {
      this.roles = []
      MenuAPI.queryAllMenuRole(this.detail.id).then(roles => { this.roles = roles })
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-button {
    margin-bottom: 10px;
  }
</style>
