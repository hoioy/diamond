<template>
  <el-row>
    <el-card>
      <el-col :span="18">
        <el-form
          :model="queryCriteria"
          :inline="true"
        >
          <el-form-item label="角色名称:" prop="roleName">
            <el-input v-model="queryCriteria.roleName" placeholder="请输入角色名称"/>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="6" class="query-btn">
        <el-button round type="info" @click="resetHandler">
          重置
        </el-button>
        <el-button round type="primary" @click="queryHandler">
          查询
        </el-button>
      </el-col>
    </el-card>
    <el-col :span="24" style="margin: 10px 0px;">
      <button-right>
        角色列表
        <template slot="button">
          <el-button-group>
            <el-button v-if="(selected && hasPerm('role/check')) || (selected&&hasPerm('role/*'))" type="primary" @click="$emit('option-changed','check', selected)">
              查看
            </el-button>
            <el-button v-if="hasPerm('role/add') || hasPerm('role/*')" type="primary" @click="$emit('option-changed','add')">
              新增
            </el-button>
            <el-button v-if="(selected && hasPerm('role/edit')) || (selected&&hasPerm('role/*'))" type="primary" @click="$emit('option-changed','edit', selected)">
              编辑
            </el-button>
            <el-button v-if="(selected && hasPerm('role/delete')) || (selected&&hasPerm('role/*'))" type="primary" @click="delHandler">
              删除
            </el-button>
          </el-button-group>
        </template>
      </button-right>
    </el-col>
    <el-col :span="24">
      <el-table :data="pagination.list" highlight-current-row stripe border :default-sort="{prop: 'roleIndex', order: 'Ascending'}" @current-change="(row) => { selected = row }" @row-dblclick="$emit('option-changed','check', selected)" @sort-change="sortChangeHandler">
        <el-table-column :show-overflow-tooltip="true" prop="roleName" label="名称" sortable="custom"/>
        <el-table-column :show-overflow-tooltip="true" prop="roleIndex" label="角色排序号"/>
        <el-table-column prop="createdDate" label="创建时间" width="180" sortable="custom" align="center">
          <template slot-scope="scope">
            {{ scope.row.createdDate | parseTime }}
          </template>
        </el-table-column>
        <el-table-column prop="modifiedDate" label="最后修改时间" width="180" sortable="custom" align="center">
          <template slot-scope="scope">
            {{ scope.row.modifiedDate | parseTime }}
          </template>
        </el-table-column>
      </el-table>
      <pagination :pagination="pagination" @page-size-changed="pageSizeChangeHandler" @page-changed="pageChangeHandler"/>
    </el-col>
  </el-row>
</template>

<script>
import { mapGetters } from 'vuex'
import { deepMerge } from '@folder-inside-utils'
import BaseQueryPageForm from '@folder-inside-views-common/BaseQueryPageForm'
import * as RoleAPI from '../../api/system-management/role'

export default {
  mixins: [BaseQueryPageForm],
  data() {
    const queryCriteria = this.initQueryCriteria()
    return {
      queryCriteria: queryCriteria,
      selected: null
    }
  },
  computed: {
    ...mapGetters([
      'dictionaries'
    ])
  },
  activated() {
    this.selected = null
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
    customDelHandler() {
      RoleAPI.delRole(this.selected.id).then(() => {
        this.queryHandler()
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  /deep/ .el-card {
    border: none;
  }
  .query-btn /deep/ .el-button {
    float: right;
    margin-left: 10px;
  }
</style>
