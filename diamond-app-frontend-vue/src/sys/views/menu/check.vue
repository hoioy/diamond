<template>
  <div>
    <el-button icon="el-icon-back" round @click="$emit('option-changed')">
      返回
    </el-button>
    <el-collapse value="base-info" accordion>
      <el-collapse-item title="基本信息" name="base-info">
        <el-form>
          <input-item-view label="ID">
            {{ detail.id }}
          </input-item-view>
          <input-item-view v-if="parentMenuName" label="上级菜单">
            {{ parentMenuName }}
          </input-item-view>
          <input-item-view label="菜单排序号">
            {{ detail.menuIndex }}
          </input-item-view>
          <input-item-view label="菜单名称">
            {{ detail.menuName }}
          </input-item-view>
          <input-item-view label="菜单图标名称">
            {{ detail.smallIconPath }}
          </input-item-view>
          <input-item-view label="菜单分类">
            {{ detail.menuClassify }}
          </input-item-view>
          <input-item-view label="菜单权限ID">
            {{ detail.authorityId }}
          </input-item-view>
          <text-item-view label="菜单备注">
            {{ detail.remark }}
          </text-item-view>
        </el-form>
      </el-collapse-item>
      <!--<el-collapse-item title="审计信息" name="audit-info">-->
      <!--<audit-info :detail="detail" :label-width="labelWidth"/>-->
      <!--</el-collapse-item>-->
      <!--<el-collapse-item title="菜单URL信息" name="menu-url">
        <el-col :span="24">
          <el-table :data="menuUrls" border style="width: 100%">
            <el-table-column prop="url" label="URL"/>
          </el-table>
        </el-col>
      </el-collapse-item>-->
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
    // this.queryMenuUrls()
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
      // const params = { id: this.detail.id }
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
