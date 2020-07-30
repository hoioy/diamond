<template>
  <el-row>
    <el-card>
      <el-col :span="20">
        <el-form :model="form" :inline="true">
          <el-form-item label="字典分类:" prop="dataItemTypeName">
            <el-select v-model="form.typeName" filterable clearable placeholder="全部" @change="queryFilterHandler">
              <el-option v-for="(item, index) in dictionaryTypeList" :key="index" :label="item.typeName" :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="字典名称:" prop="name">
            <el-input v-model="filter" placeholder="输入关键字对已加载的数据进行过滤"/>
          </el-form-item>
        </el-form>
      </el-col>
    </el-card>
    <el-col :span="24" style="margin: 10px 0px;">
      <button-right>
        字典列表
        <template slot="button">
          <el-button-group>
            <el-button v-if="(selected && hasPerm('dictionary-multi/check')) || (selected&&hasPerm('dictionary-multi/*'))" type="primary" @click="$emit('option-changed','check', selected)">
              查看
            </el-button>
            <el-button v-if="(selected && hasPerm('dictionary-multi/add')) || (selected&&hasPerm('dictionary-multi/*'))" type="primary" @click="$emit('option-changed','addnofather', selected)">
              新增下级
            </el-button>
            <el-button v-if="hasPerm('dictionary-multi/add') || hasPerm('dictionary-multi/*')" type="primary" @click="$emit('option-changed','add')">
              新增
            </el-button>
            <el-button v-if="(selected && hasPerm('dictionary-multi/edit')) || (selected&&hasPerm('dictionary-multi/*'))" type="primary" @click="$emit('option-changed','edit', selected)">
              编辑
            </el-button>
            <el-button v-if="(selected && hasPerm('dictionary-multi/delete')) || (selected&&hasPerm('dictionary-multi/*'))" type="primary" @click="delHandler">
              删除
            </el-button>
          </el-button-group>
        </template>
      </button-right>
    </el-col>
    <el-col :span="24">
      <el-tree :data="[{}]" :props="defaultProps">
        <div slot-scope="{ data }" class="custom-tree-node">
          <div class="name">
            字典名称
          </div>
          <div class="code">
            字典规则码
          </div>
          <div class="dataItemTypeName">
            字典类型
          </div>
          <!--<div class="state">-->
            <!--状态-->
          <!--</div>-->
        </div>
      </el-tree>
      <!-- eslint-disable-next-line vue/max-attributes-per-line -->
      <el-tree ref="tree" :data="pagination.list" :load="loadChildren" :props="defaultProps" :filter-node-method="filterNodeHandler" class="filter-tree" highlight-current accordion lazy @current-change="(value, node) => selected = value">
        <div slot-scope="{ data }" class="custom-tree-node">
          <div class="name">
            {{ data.name }}
          </div>
          <div class="code">
            {{ data.code }}
          </div>
          <div class="dataItemTypeName">
            {{ data.dataItemTypeName }}
          </div>
          <!--<div class="state">-->
            <!--<state :state="data.state"/>-->
          <!--</div>-->
        </div>
      </el-tree>
      <pagination :pagination="pagination" @page-size-changed="pageSizeChangeHandler" @page-changed="pageChangeHandler"/>
    </el-col>
  </el-row>
</template>

<script>
import { deepMerge } from '@folder-inside-utils'
import BaseQueryPageForm from '@folder-inside-views-common/BaseQueryPageForm'
import * as DictionaryAPI from '../../api/system-management/dictionary'
import mixins from './mixins'
import * as DictionaryTypeAPI from '../../api/system-management/dictionary-type'

export default {
  mixins: [BaseQueryPageForm, mixins],
  data() {
    const queryCriteria = this.initQueryCriteria()
    return {
      queryCriteria: queryCriteria,
      selected: null,
      form: {},
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      filter: '',
      filterForRefresh: '',
      dataItemTypeId: null
    }
  },
  watch: {
    filter(filter) {
      this.filterForRefresh = filter
      this.$refs.tree.filter(filter)
    }
  },
  activated() {
    this.selected = null
    // this.executeQueryPage()
  },
  methods: {
    initQueryCriteria(form = {}) {
      return deepMerge(form, {
        flag: '',
        parentId: '',
        name: '',
        code:'',
        dataItemTypeName: ''
      })
    },
    executeQueryPage() {
      // TODO 自动刷新页面的问题
      DictionaryTypeAPI.queryPageDictionaryTypesAll().then(data => {
        this.dictionaryTypeList = data
      })
      DictionaryAPI.queryAllDictionaries(null).then(data => {
        this.pagination = data
      }).then(() => {
        // console.info(this.filterForRefresh)
        this.$refs.tree.filter(this.filterForRefresh)
      })
    },
    queryFilterHandler(id) {
      if (!id) return

      DictionaryAPI.queryAllDictionariesTypeId(id).then(data => {
        this.queryResultHandler(data)
      })
    },
    customDelHandler() {
      DictionaryAPI.delDictionary(this.selected.id).then(() => {
        this.queryHandler()
      })
    },
    loadChildren(node, resolve) {
      if (node.data.id) {
        DictionaryAPI.queryAllDictionaries(node.data.id).then(data => {
          resolve(data.list)
        })
      }
    },
    filterNodeHandler(value, data) {
      if (!value) return true
      const show = data.name.indexOf(value) !== -1
      if (!show && this.selected && this.selected.id === data.id) {
        this.selected = null
      }
      return show
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

    .type {
      float: right;
      width: 400px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }

    .name {
      float: left;
      min-height: 40px;
      line-height: 40px;
    }
    .code {
      float: right;
      width: 100px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }
    .dataItemTypeName {
      float: right;
      width: 100px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }
    .state {
      float: right;
      width: 100px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }
  }
</style>
