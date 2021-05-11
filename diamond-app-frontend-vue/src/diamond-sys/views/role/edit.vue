<template>
  <div>
    <el-button icon="el-icon-back" round @click="backHandler">
      返回
    </el-button>
    <el-card>
      <div slot="header">
        <button-right>
          角色信息
          <template slot="button">
            <el-button type="primary" @click="submitHandler('form')">
              保存
            </el-button>
          </template>
        </button-right>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="200px">
        <el-form-item label="ID" prop="id">
          <el-input v-model="form.id" disabled/>
        </el-form-item>
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName"/>
        </el-form-item>
        <el-form-item label="角色排序号" prop="roleIndex">
          <el-input v-model.number="form.roleIndex" type="text"/>
        </el-form-item>
        <el-form-item label="角色备注" prop="remark">
          <el-input v-model="form.remark" type="textarea"/>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card>
      <div slot="header">
        <button-right>
          可操作菜单信息
          <template slot="button">
            <el-button type="primary" @click="resetRoleMenus">
              保存
            </el-button>
          </template>
        </button-right>
      </div>
      <el-row>
        <el-col :span="24">
          <el-input v-model="menuFilter" placeholder="输入关键字进行过滤"/>
        </el-col>
        <el-col :span="24" style="margin-top: 10px;">
          <el-tree :data="[{}]">
            <div class="custom-tree-node">
              <div class="name">
                名称
              </div>
              <div class="remark">
                备注
              </div>
              <div class="menuClassify">
                分类
              </div>
              <div class="authorityId">
                权限ID
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
              <div class="menuClassify">
                {{ data.menuClassify }}
              </div>
              <div class="authorityId">
                {{ data.authorityId }}
              </div>
            </div>
          </el-tree>
        </el-col>
      </el-row>
    </el-card>
    <el-card>
      <div slot="header">
        <button-right>
          用户信息
          <template slot="button">
            <el-button type="primary" @click="openDialog('addUser')">
              添加用户
            </el-button>
            <el-button type="primary" @click="queryHandler">
              刷新
            </el-button>
          </template>
        </button-right>
      </div>

      <el-table :data="pagination.list" border style="width: 100%">
        <el-table-column :show-overflow-tooltip="true" prop="id" label="ID" sortable align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="loginName" label="登录ID" sortable align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="userName" label="姓名" sortable align="center"/>
        <el-table-column prop="gender" label="性别" sortable align="center">
          <template slot-scope="scope">
            {{ scope.row.gender | translateGender }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="email" label="邮箱" sortable align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="phoneNum" label="电话" sortable align="center"/>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="warning" @click="delUserRoleHandler(scope.row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination :pagination="pagination" @page-size-changed="pageSizeChangeHandler" @page-changed="pageChangeHandler"/>
      <el-dialog
        :visible.sync="dialogVisibleUserDetail"
        :title="title"
        :show-close="false"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        width="90%"
        top="60px"
      >
        <user-info v-if="hackReset" ref="userInfo4submit" :datasf2c="f2cmsg" :datasf2c4submit="f2cmsg4submit" @func="child2father" @funcdel="child2fatherdel"/>
        <span slot="footer" class="dialog-footer">
          <el-button @click="colesDialog">关闭</el-button>
          <el-button v-if="addStatus" type="primary" @click="addSubmit">提交</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { deepMerge } from '@folder-inside-utils'
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import BaseQueryPageForm from '@folder-inside-views-common/BaseQueryPageForm'
import { deepMergeLeft } from '@folder-inside-utils'
import * as RoleAPI from '../../api/system-management/role'
import mixins from './mixins'
import UserInfo from '../user/query4role'
import * as UserAPI from '../../api/system-management/user'

export default {
  components: {
    UserInfo
  },
  mixins: [BaseEditForm, BaseQueryPageForm, mixins],
  props: {
    detail: {
      required: false,
      type: Object,
      default: () => {}
    }
  },

  data() {
    const queryCriteria = this.initQueryCriteria()
    const form = this.initForm()
    const rules = this.initRules()
    rules.id = [{
      required: true, message: '编辑信息时ID不能为空', trigger: 'change'
    }]
    return {
      hackReset: true, // 定义hackReset初始值 true 保证初始化子标签正常显示
      queryCriteria: queryCriteria,
      f2cmsg: this.detail.id, // 角色ID
      f2cmsg4submit: null,
      dialogVisibleUserDetail: false,
      form: form,
      rules: rules,
      menusTree: [],
      menuFilter: null,
      users: [],
      users4add: [], // 用户界面传递过来的需要添加的数组
      users4del: [], // 用户界面传递过来的需要添加的数组
      usersRole4add: [], // 用户和角色的匹配的数组，传递给后台，用于添加用户和角色的关联关系
      usersRole4del: [], // 用户和角色的匹配的数组，传递给后台，用于删除用户和角色的关联关系
      title: '',
      addStatus: true
    }
  },
  watch: {
    menuFilter(filter) {
      this.$refs.menusTree.filter(filter)
    }
  },

  created() {
    this.queryCriteria = this.initQueryCriteria()
  },

  activated() {
    RoleAPI.queryRoleById(this.detail.id).then((data) => {
      deepMergeLeft(this.form, data)
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    })
    this.initMenus()
    this.executeQueryPage()
  },
  methods: {
    // edit页面的根据角色查询用户的分页接口
    executeQueryPage() {
      UserAPI.queryPageUsersByRole(this.createQueryParamsForRole(this.detail.id)).then(data => {
        this.queryResultHandler(data)
      })
    },
    initQueryCriteria(form = {}) {
      return deepMerge(form, {
        roleId: this.detail.id
      })
    },
    customSubmitHandler() {
      RoleAPI.editRole(this.form).then(this.submitSuccessHandler)
    },
    customSubmitSuccessHandler() {
      this.$refs['form'].clearValidate()
    },
    resetRoleMenus() {
      this.$confirm('此操作将修改菜单权限, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const params = {
          roleId: this.detail.id,
          menuIds: (this.$refs['menusTree'].getCheckedKeys()).concat(this.$refs['menusTree'].getHalfCheckedKeys())
        }
        RoleAPI.resetRoleMenus(params).then(this.optionSuccessHandler)
      })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    delUserRoleHandler(id) {
      const params = {
        userId: id,
        roleId: this.detail.id
      }
      RoleAPI.delRoleUser(params).then(data => {
        this.optionSuccessHandler()
        this.executeQueryPage()
      })
    },
    // ---------------------弹出窗口的一系列操作--用户和角色的关联关系------------------start
    colesDialog() {
      this.dialogVisibleUserDetail = false
      this.executeQueryPage()
    },
    // 打开弹出框
    openDialog(type) {
      this.addStatus = true
      if (type === 'addUser') {
        // 销毁子标签
        this.hackReset = false
        // 重新创建子标签
        this.$nextTick(() => {
          this.hackReset = true
        })
        this.title = '添加用户'
        this.f2cmsg = this.form.id
      }
      this.dialogVisibleUserDetail = true
    },
    // 新增用户和角色的关联关系
    addSubmit() {
      this.usersRole4add = []
      this.usersRole4del = []
      for (let i = 0; i < this.users4add.length; i++) {
        const params4add = {
          roleId: this.form.id,
          userId: this.users4add[i]
        }
        this.usersRole4add.push(params4add)
      }
      for (let j = 0; j < this.users4del.length; j++) {
        const params4del = {
          roleId: this.form.id,
          userId: this.users4del[j]
        }
        this.usersRole4del.push(params4del)
      }
      const map = new Map()
      map.set('createDTOs', this.usersRole4add)
      map.set('deleteDTOs', this.usersRole4del)
      UserAPI.addUserRoleBatchSave((this.strMapToObj(map))).then(data => {
        this.$refs.userInfo4submit.childMethod('submitSuccess')
        this.submitSuccessHandler(data)
      })
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
  .el-card {
    margin-top: 10px;
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
      font-size: 14px;
    }

    .remark {
      float: right;
      width: 300px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
      font-size: 14px;
    }

    .icon {
      float: right;
      width: 100px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
      font-size: 14px;
    }
    .menuClassify {
      float: right;
      width: 100px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
      font-size: 14px;
    }
    .authorityId {
      float: right;
      width: 100px;
      min-height: 40px;
      line-height: 40px;
      border-left: 1px solid #ebeef5;
      text-align: center;
      font-size: 14px;
    }
  }
</style>
