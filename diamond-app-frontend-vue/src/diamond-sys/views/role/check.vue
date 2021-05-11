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
          <input-item-view label="角色排序号">
            <el-input v-model="detail.roleIndex" disabled/>
          </input-item-view>
          <input-item-view label="角色名称">
            <el-input v-model="detail.roleName" disabled/>
          </input-item-view>
          <text-item-view label="角色备注">
            <el-input v-model="detail.remark" disabled/>
          </text-item-view>
        </el-form>
      </el-collapse-item>
      <el-collapse-item title="角色菜单信息" name="role-menu">
        <el-tree :data="[{}]">
          <div class="custom-tree-node">
            <div class="name">
              名称
            </div>
            <div class="remark">
              备注
            </div>
          </div>
        </el-tree>
        <el-tree ref="menusTree" :data="menusTree" :filter-node-method="filterNodeHandler" class="filter-tree" node-key="id" show-checkbox accordion>
          <div slot-scope="{ data }" class="custom-tree-node">
            <div class="name">
              {{ data.menuName }}
            </div>
            <div class="remark">
              {{ data.remark }}
            </div>
          </div>
        </el-tree>
      </el-collapse-item>
      <el-collapse-item title="角色用户" name="role-user">
        <el-table :data="pagination.list" border style="width: 100%">
          <el-table-column type="index" width="100" align="center"/>
          <el-table-column :show-overflow-tooltip="true" prop="loginName" label="登录ID" sortable align="center"/>
          <el-table-column :show-overflow-tooltip="true" prop="userName" label="姓名" sortable align="center"/>
          <el-table-column prop="gender" label="性别" width="100" sortable align="center">
            <template slot-scope="scope">
              {{ scope.row.gender | translateGender }}
            </template>
          </el-table-column>
          <el-table-column :show-overflow-tooltip="true" prop="email" label="邮箱" sortable align="center"/>
          <el-table-column :show-overflow-tooltip="true" prop="phoneNum" label="电话" width="160" sortable align="center"/>
        </el-table>
        <pagination :pagination="pagination" @page-size-changed="pageSizeChangeHandler" @page-changed="pageChangeHandler"/>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import { deepMerge } from '@folder-inside-utils'
import mixins from './mixins'
import BaseQueryPageForm from '@folder-inside-views-common/BaseQueryPageForm'
import * as RoleAPI from '../../api/system-management/role'
import * as UserAPI from '../../api/system-management/user'

export default {
  mixins: [mixins, BaseQueryPageForm],
  props: {
    detail: {
      required: true,
      type: Object,
      default: () => {}
    }
  },
  data() {
    const queryCriteria = this.initQueryCriteria()
    return {
      queryCriteria: queryCriteria,
      labelWidth: '200px',
      users: [],
      menusTree: []
    }
  },
  activated() {
    this.executeUserQueryPage()
    this.initMenus()
  },
  methods: {
    initQueryCriteria(form = {}) {
      return deepMerge(form, {
        flag: '',
        roleName: ''
      })
    },
    executeQueryPage() {
      RoleAPI.queryPageRoles(this.createQueryParams()).then(data => {
        this.queryResultHandler(data)
      })
    },
    executeUserQueryPage() {
      UserAPI.queryPageUsersByRole(this.createQueryParamsForRole(this.detail.id)).then(data => {
        this.queryResultHandler(data)
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-button {
    margin-bottom: 10px;
  }

  /deep/ .el-tree {
    border-bottom: 1px solid #ebeef5;

    .el-tree-node__content {
      border-top: 1px solid #ebeef5;
      border-left: 1px solid #ebeef5;
      border-right: 1px solid #ebeef5;
      min-height: 40px;
    }
  }

  .custom-tree-node {
    width: 100%;

    .name {
      float: left;
      min-height: 40px;
      line-height: 40px;
    }

    .remark {
      float: right;
      width: 300px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }

    .icon {
      float: right;
      width: 100px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }
  }
</style>
