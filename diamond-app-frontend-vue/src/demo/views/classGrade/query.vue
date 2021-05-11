<template>
  <el-row :gutter="20">
    <el-col :span="7">
      <el-card>
        <el-input v-model="filter" placeholder="输入关键字进行过滤"/>
      </el-card>
      <el-button-group>
        <el-button
          type="primary"
          icon="el-icon-plus"
          @click="openDialog('add',selected)"
        >
          新增一级部门
        </el-button>
        <el-button
          type="primary"
          icon="el-icon-plus"
          @click="openDialog('addChils',selected)"
        >
          新增子部门
        </el-button>
        <el-button
          type="primary"
          icon="el-icon-delete"
          @click="delHandler"
        >
          删除
        </el-button>
      </el-button-group>
      <el-tree :data="[{}]" :props="defaultProps">
        <div slot-scope="{ data }" class="custom-tree-node">
          <div class="gradeName"/>
        </div>
      </el-tree>
      <el-tree ref="tree" :data="nodes" :props="defaultProps" :filter-node-method="filterNodeHandler" class="filter-tree" highlight-current accordion @node-click="handleNodeClick" @current-change="(value, node) => selected = value">
        <div slot-scope="{ data }" class="custom-tree-node">
          <div class="name">
            {{ data.gradeName }}
          </div>
        </div>
      </el-tree>
    </el-col>
    <el-col :span="17">
      <el-card>
        <div slot="header">
          <button-right>
            级别信息
            <template slot="button">
              <el-button type="primary" @click="submitHandler('form')">
                保存
              </el-button>
            </template>
          </button-right>
        </div>
        <el-form ref="form" :model="form" :rules="rules" :label-width="labelWidth">
          <el-form-item label="级别名称" prop="gradeName">
            <el-input v-model="form.gradeName"/>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark"/>
          </el-form-item>
        </el-form>
      </el-card>
      <el-dialog
        :visible.sync="dialogVisible"
        :show-close="false"
        :title="title"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        width="45%"
        top="20px"
      >
        <add ref="add" :datasf2c="f2cmsg4add" :detail="form"/>
        <span slot="footer" class="dialog-footer">
          <el-button @click="colesDialog">取 消</el-button>
          <el-button type="primary" @click="addSubmit">提 交</el-button>
        </span>
      </el-dialog>
      <el-dialog
        :visible.sync="dialogVisibleUserDetail"
        :title="title"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        width="90%"
        top="60px"
      >
        <user-info ref="userInfo4submit" :datasf2c4dpetrole="f2cmsg4DeptOrRole" :datasf2c="f2cmsg" :datasf2c4submit="f2cmsg4submit" @func="child2father" @funcdel="child2fatherdel"/>
        <span slot="footer" class="dialog-footer">
          <el-button @click="colesDialog4user">关闭</el-button>
          <el-button type="primary" @click="addSubmit4User">提交</el-button>
        </span>
      </el-dialog>
    </el-col>
  </el-row>
</template>
<script>
import { deepMerge } from '@folder-inside-utils'
import * as GradeAPI from '@src/demo/api/classGrade'
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import BaseQueryPageForm from '@folder-inside-views-common/BaseQueryPageForm'
import { deepMergeLeft } from '@folder-inside-utils'
import mixins from './mixins'
import add from './add'

export default {
  components: {
    add
  },
  mixins: [BaseEditForm, BaseQueryPageForm, mixins],
  data() {
    const queryCriteria = this.initQueryCriteria()
    const form = this.initForm()
    const rules = this.initRules()
    rules.id = [{
      required: true, message: '编辑信息时ID不能为空', trigger: 'change'
    }]
    return {
      queryCriteria: queryCriteria,
      users4add: [], // 用户界面传递过来的需要添加的数组
      users4del: [], // 用户界面传递过来的需要添加的数组
      usersDept4add: [], // 用户和角色的匹配的数组，传递给后台，用于添加用户和角色的关联关系
      usersDept4del: [], // 用户和角色的匹配的数组，传递给后台，用于删除用户和角色的关联关系
      f2cmsg: '', // 部门ID
      f2cmsg4DeptOrRole: '', // 区分部门还是角色
      f2cmsg4submit: null,
      dialogVisibleUserDetail: false,
      f2cmsg4add: null,
      labelWidth: '100px',
      form: form,
      rules: rules,
      users: [],
      filter: null,
      selected: null,
      nodes: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      title: '部门信息',
      dialogVisible: false
    }
  },
  watch: {
    filter(filter) {
      this.$refs.tree.filter(filter)
    }
  },
  activated() {
    this.selected = null
    this.queryAllHandler()
  },
  methods: {
    initQueryCriteria(form = {}) {
      if (this.selected) {
        return deepMerge(form, {
          deptId: this.selected.id
        })
      } else {
        return deepMerge(form, {
          deptId: 'null'
        })
      }
    },
    executeQueryPage() {

    },
    colesDialog() {
      this.resetFormItem()
      this.dialogVisible = false
      this.dialogVisibleUserDetail = false
      this.executeQueryPage()
    },
    colesDialog4user() {
      this.dialogVisibleUserDetail = false
      this.executeQueryPage()
    },
    openDialog(type) {
      if (type === 'addUser') {
        this.title = '添加用户'
        this.f2cmsg = this.selected.id
        this.f2cmsg4DeptOrRole = 'dept'
        this.dialogVisibleUserDetail = true
      } else if (type === 'add') {
        this.f2cmsg4add = null
        this.title = '添加部门'
        this.$nextTick()
        this.dialogVisible = true
      } else if (type === 'addChils') {
        this.f2cmsg4add = 'first'
        this.title = '添加部门'
        this.$nextTick()
        this.dialogVisible = true
      }
    },
    addSubmit() {
      if (this.$refs.add.submitForm()) {
        const formItem = this.$refs.add.form
        GradeAPI
          .addDept(formItem)
          .then(data => {
            this.$message({
              showClose: true,
              message: '添加成功',
              type: 'success'
            })
            this.dialogVisible = false
            this.resetFormItem()
            this.queryAllHandler()
          })
          .catch(() => {
            this.$message({
              type: 'error',
              message: '添加失败'
            })
          })
        this.dialogVisible = false
      }
    },
    // 清除formItem参数
    resetFormItem() {
      this.$refs.add.form = {
        parentId: null,
        parentName: null,
        deptType: '',
        gradeName: '',
        deptState: '1',
        flag: 1,
        deptIndex: null,
        remark: ''
      }
    },
    queryAllHandler() {
      GradeAPI.queryAllClassGrades({}).then(data => {
        this.nodes = data
      })
    },
    handleNodeClick(data) {
      this.selected.id = data.id
      GradeAPI.queryGradeById(this.selected.id).then((data) => {
        deepMergeLeft(this.form, data)
        this.getParentDeptName(this.form.parentId)
        this.$nextTick(() => {
          this.$refs['form'].clearValidate()
        })
      })
      this.executeQueryPage()
    },
    filterNodeHandler(value, data) {
      if (!value) return true
      const show = data.deptName.indexOf(value) !== -1
      if (!show && this.selected && this.selected.id === data.id) {
        this.selected = null
      }
      return show
    },
    delHandler() {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        GradeAPI.delDept(this.selected.id).then((data) => {
          this.queryAllHandler()
          if (data) {
            this.$message({
              type: 'success',
              message: '删除成功'
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
    customSubmitHandler() {
      // GradeAPI.editDept(this.form).then(this.submitSuccessHandler)
    },
    customSubmitSuccessHandler() {
      this.$refs['form'].clearValidate()
      this.queryAllHandler()
    },
    delDeptUserHandler(id) {
      const params = {
        userId: id,
        deptId: this.selected.id
      }
      GradeAPI.delDeptUser(params).then(data => {
        this.optionSuccessHandler()
        this.executeQueryPage()
      })
    },
    // 新增用户和角色的关联关系
    addSubmit4User() {
      this.usersDept4add = []
      this.usersDept4del = []
      for (let i = 0; i < this.users4add.length; i++) {
        const params4add = {
          deptId: this.form.id,
          userId: this.users4add[i]
        }
        this.usersDept4add.push(params4add)
      }
      for (let j = 0; j < this.users4del.length; j++) {
        const params4del = {
          deptId: this.form.id,
          userId: this.users4del[j]
        }
        this.usersDept4del.push(params4del)
      }
      const map = new Map()
      map.set('createDTOs', this.usersDept4add)
      map.set('deleteDTOs', this.usersDept4del)
    },
    /**
     *map转化为对象（map所有键都是字符串，可以将其转换为对象）
     */
    strMapToObj(strMap) {
      const obj = Object.create(null)
      for (const [k, v] of strMap) {
        obj[k] = v
      }
      return obj
    },
    // 用户界面像角色传递添加数组
    child2father(data) {
      this.users4add = []
      if (data) {
        for (let i = 0; i < data.length; i++) {
          this.users4add.push(data[i])
        }
      }
    },
    // 用户界面像角色传递删除数组
    child2fatherdel(data) {
      this.users4del = []
      if (data) {
        for (let i = 0; i < data.length; i++) {
          this.users4del.push(data[i])
        }
      }
    }
    // ---------------------弹出窗口的一系列操作--用户和角色的关联关系------------------end
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
  .custom-tree-node {
    width: 100%;

    .name {
      float: left;
      min-height: 40px;
      line-height: 40px;
    }

    .time {
      float: right;
      width: 200px;
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
