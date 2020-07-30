<template>
  <el-row>
    <el-col :span="24">
      <el-card>
        <el-input v-model="filter" placeholder="输入关键字进行过滤"/>
      </el-card>
    </el-col>
    <el-col :span="24" style="margin: 10px 0px;">
      <button-right>
        菜单树列表
        <template slot="button">
          <el-button-group>
            <el-button
              v-if="(selected && !needSync && hasPerm('menu/check')) ||
                (selected && !needSync && hasPerm('menu/*'))"
              type="primary"
              @click="$emit('option-changed','check', selected)"
            >
              查看
            </el-button>
            <el-button v-if="hasPerm('menu/add') || hasPerm('menu/*')" type="primary" @click="$emit('option-changed','add')">
              新增
            </el-button>
            <el-button
              v-if="(selected && !needSync && hasPerm('menu/edit')) ||
                (selected && !needSync && hasPerm('menu/*'))"
              type="primary"
              @click="$emit('option-changed','edit', selected)"
            >
              编辑
            </el-button>
            <el-button v-if="(selected && hasPerm('menuChildren/add')) || (selected&&hasPerm('menu/*')) " type="primary" @click="$emit('option-changed','add', selected)">
              新增子菜单
            </el-button>
            <el-button v-if="(selected && hasPerm('menu/delete')) || (selected&&hasPerm('menu/*')) " type="primary" @click="delHandler">
              删除
            </el-button>
          </el-button-group>
        </template>
      </button-right>
    </el-col>
    <el-col :span="24">
      <el-tree :data="[{}]" :props="defaultProps">
        <div slot-scope="{ data }" class="custom-tree-node">
          <div class="menuName">
            名称
          </div>
          <div class="remark">
            备注
          </div>
          <div class="icon">
            图标
          </div>
          <div class="menuClassify">
            分类
          </div>
          <div class="authorityId">
            权限ID
          </div>
        </div>
      </el-tree>
      <el-tree ref="tree" :data="menusTree" :filter-node-method="filterNodeHandler" :props="defaultProps" class="filter-tree" highlight-current accordion @current-change="(value, node) => selected = value">
        <div slot-scope="{ data }" class="custom-tree-node">
          <div class="menuName">
            {{ data.menuName }}
          </div>
          <div class="remark">
            {{ data.remark }}
          </div>
          <div class="icon">
            <!--<svg-icon :icon-class="data.icon"/>-->
          </div>
          <div class="menuClassify">
            {{ data.menuClassify }}
            <!--<svg-icon :icon-class="data.icon"/>-->
          </div>
          <div class="authorityId">
            {{ data.authorityId }}
            <!--<svg-icon :icon-class="data.icon"/>-->
          </div>
        </div>
      </el-tree>
    </el-col>
  </el-row>
</template>

<script>

import * as MenuAPI from '../../api/system-management/menu'
import mixins from './mixins'

export default {
  // components: { Abbreviation, SvgIcon },
  components: {},
  mixins: [mixins],
  data() {
    return {
      filter: null,
      selected: null,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      menusTree: [],
      allMenus: [],
      needSync: false
    }
  },
  watch: {
    filter(filter) {
      this.$refs.tree.filter(filter)
    }
  },
  activated() {
    this.selected = null
    this.initMenus()
  },
  methods: {
    delHandler() {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        MenuAPI.delMenu(this.selected.id).then((data) => {
          this.initMenus()
          if (data) {
            this.$message({
              type: 'success',
              message: '删除成功'
            })
          } else {
            this.$message({
              type: 'error',
              message: '删除失败'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    filterNodeHandler(value, data) {
      if (!value) return true
      const show = data.menuName.indexOf(value) !== -1
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
    .menuName {
      float: left;
      min-height: 40px;
      line-height: 40px;
    }
    .remark {
      float: right;
      width: 30%;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }
    .icon {
      float: right;
      width: 10%;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }
    .menuClassify {
      float: right;
      width: 10%;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }
    .authorityId {
      float: right;
      width: 10%;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
    }
  }
</style>
