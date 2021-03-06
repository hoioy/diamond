<template>
  <el-row>
    <el-card>
      <el-col :span="18">
        <el-form :model="queryCriteria" :inline="true">
          <el-form-item label="字典类型名称:" prop="typeName">
            <el-input v-model="queryCriteria.typeName" placeholder="请输入字典类型名称"/>
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
        字典类型列表
        <template slot="button">
          <el-button-group>
            <el-button v-if="selected || (selected&&hasPerm('dictionary-type/*'))" type="primary" @click="$emit('option-changed','check', selected)">
              查看
            </el-button>
            <el-button type="primary" @click="$emit('option-changed','add')">
              新增
            </el-button>
            <el-button v-if="selected || (selected&&hasPerm('dictionary-type/*'))" type="primary" @click="$emit('option-changed','edit', selected)">
              编辑
            </el-button>
            <el-button v-if="selected || (selected&&hasPerm('dictionary-type/*'))" type="primary" @click="delHandler">
              删除
            </el-button>
          </el-button-group>
        </template>
      </button-right>
    </el-col>
    <el-col :span="24">
      <el-table :data="pagination.list" highlight-current-row stripe border @current-change="changeSelected" @row-dblclick="$emit('option-changed','check', selected)" @sort-change="sortChangeHandler">
        <el-table-column :show-overflow-tooltip="true" prop="typeName" label="名称" sortable="custom" align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注" align="center"/>
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

import { deepMerge } from '@folder-inside-utils'
import BaseQueryPageForm from '@folder-inside-views-common/BaseQueryPageForm'
import * as DictionaryTypeAPI from '../../api/system-management/dictionary-type'
import mixins from './mixins'

export default {
  mixins: [BaseQueryPageForm, mixins],
  data() {
    const queryCriteria = this.initQueryCriteria()
    return {
      queryCriteria: queryCriteria,
      selected: null
    }
  },
  activated() {
    this.selected = null
  },
  methods: {
    initQueryCriteria(form = {}) {
      return deepMerge(form, {
        id: '',
        remark: '',
        flag: '',
        typeName: ''
      })
    },
    executeQueryPage() {
      DictionaryTypeAPI.queryPageDictionaryTypes(this.createQueryParams()).then(data => {
        this.queryResultHandler(data)
      })
    },
    customDelHandler() {
      DictionaryTypeAPI.delDictionaryType(this.selected.id).then(() => {
        this.queryHandler()
      })
    },
    changeSelected(row) {
      this.selected = row
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
