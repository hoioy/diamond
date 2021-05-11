<template>
  <el-row>
    <el-card>
      <el-col :span="24">
        <el-form :model="queryCriteria" label-width="150px">
          <el-col :span="8">
            <el-form-item label="登录ID:" prop="loginName">
              <el-input v-model="queryCriteria.loginName" placeholder="请输入登录ID"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="姓名:" prop="userName">
              <el-input v-model="queryCriteria.userName" placeholder="请输入姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别:" prop="gender">
              <el-select v-model="queryCriteria.gender" clearable placeholder="全部">
                <el-option v-for="item in dictionaries.gender" :key="item.key" :value="item.key" :label="item.value"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="邮箱:" prop="email">
              <el-input v-model="queryCriteria.email" placeholder="请输入邮箱"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <flex-center>
              <el-button round type="info" @click="resetHandler">
                重置
              </el-button>
              <el-button round type="primary" @click="queryHandler">
                查询
              </el-button>
            </flex-center>
          </el-col>
        </el-form>
      </el-col>
    </el-card>
    <el-col :span="24" style="margin: 10px 0px;">
      <button-right>
        用户列表
        <template slot="button">
          <el-button-group>
            <el-button v-if="(selected&&hasPerm('user/check'))||(selected&&hasPerm('user/*'))" type="primary" @click="$emit('option-changed','check', selected)">
              查看
            </el-button>
            <el-button v-if="hasPerm('user/add')||hasPerm('user/*')" type="primary" @click="$emit('option-changed','add')">
              新增
            </el-button>
            <el-button v-if="(selected&&hasPerm('user/edit'))||(selected&&hasPerm('user/*'))" type="primary" @click="$emit('option-changed','edit', selected)">
              编辑
            </el-button>
            <el-button v-if="(selected&&hasPerm('user/delete'))||(selected&&hasPerm('user/*'))" type="primary" @click="delHandler">
              删除
            </el-button>
            <el-button v-if="selectedUsers.length > 0&&hasPerm('user/*')" type="primary" @click="$emit('option-changed','batchEdit', selectedUsers)">
              批量编辑
            </el-button>
          </el-button-group>
        </template>
      </button-right>
    </el-col>
    <el-col :span="24">
      <el-table ref="listPowerSupplyTab" :data="pagination.list" highlight-current-row stripe border @current-change="(row) => { selected = row }" @row-dblclick="$emit('option-changed','check', selected)" @sort-change="sortChangeHandler" @selection-change="clickCheckboxHandler">
        <el-table-column type="selection" label="全选"/>
        <el-table-column :show-overflow-tooltip="true" prop="id" label="ID" sortable="custom" align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="loginName" label="登录ID" sortable="custom" align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="userName" label="姓名" sortable="custom" align="center"/>
        <el-table-column prop="gender" label="性别" sortable="custom" align="center">
          <template slot-scope="scope">
            {{ scope.row.gender | translateGender }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="email" label="邮箱" sortable="custom" align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="phoneNum" label="电话" sortable="custom" align="center"/>
        <el-table-column prop="createdDate" label="创建时间" sortable="custom" align="center">
          <template slot-scope="scope">
            {{ scope.row.createdDate | parseTime }}
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
import * as UserAPI from '../../api/system-management/user'
import mixins from './mixins'

export default {
  mixins: [BaseQueryPageForm, mixins],
  props: {
    datasf2c: { // 这里的datas用于接收
      type: String // 我们接收验证的是字符串 也可以验证别的类型
    },
    datasf2c4dpetrole: { // 这里的datas用于接收
      type: String // 我们接收验证的是字符串 也可以验证别的类型
    }
  },
  data() {
    const queryCriteria = this.initQueryCriteria()
    return {
      datasf2c4submit: null,
      queryCriteria: queryCriteria,
      selected: null,
      selectedUsers: [],
      checkedAddMenusForCurrentPage: [], // 需要添加的集合-当前页
      checkedDelMenusForCurrentPage: [], // 需要删除的集合-当前页
      checkedAddMenus: [], // 需要添加的集合
      checkedDelMenus: [], // 需要删除的集合
      checkedMenus: [], // 原始的勾选的集合
      checkedMenusForPage: [], // 展示的勾选的集合
      checkedMenuIds: [], // ID
      roleByUserIds: [] // 展示的勾选的集合
    }
  },
  activated() {
    this.selected = null
  },
  // todo
  mounted() {
    this.executeQueryPage()
  },
  methods: {
    initQueryCriteria(form = {}) {
      return deepMerge(form, {
        id: '',
        flag: '',
        state: '',
        gender: '',
        userName: '',
        email: '',
        loginName: ''
      })
    },
    childMethod(item) {
      this.datasf2c4submit = item
    },
    executeQueryPage() {
      if (this.datasf2c4submit === 'submitSuccess') {
        this.checkedAddMenus = [] // 提交之后清空需要添加的集合
        this.checkedDelMenus = [] // 提交之后清空需要删除的集合
        this.datasf2c4submit = '123'
      } else {
      }
      this.checkedAddMenusForCurrentPage = []
      this.checkedDelMenusForCurrentPage = []
      this.checkedMenus = [] // 初始化勾选的集合，确定就不在改变
      UserAPI.queryPageUsersByRole(this.createQueryParams()).then(data => {
        this.queryResultHandler(data)
        this.roleByUserIds = []
        this.pagination.list.forEach(row => {
          this.roleByUserIds.push(row.id)
        })
        this.checkedMenusForPage = [] // 页面展示的集合，包括已经删除的和新勾选的
        if (this.datasf2c4dpetrole === 'dept') {
          UserAPI.findDeptByUserIds((this.roleByUserIds)).then(data => { // TODO
            data.forEach(row => {
              if (row.deptId === this.datasf2c) { // 用户中和deptid相同的才勾选
                // 制作初始化的勾选集合
                this.checkedMenus.push(row.userId)
                if (this.checkedDelMenus.indexOf(row.userId) < 0) { // TODO
                  // 如果已经删除就不显示了
                  this.checkedMenusForPage.push(row.userId)
                }
              }
            })
            this.checkedAddMenus.forEach(row => {
              this.checkedMenusForPage.push(row)
            })
            this.checkedMenusForPage = [...new Set(this.checkedMenusForPage)] // 去重
          }).then(() => {
            // todo 可能这个环节出了问题,注意异步
            this.pagination.list.forEach(row => {
              if (this.checkedMenusForPage.indexOf(row.id) >= 0) {
                this.$refs.listPowerSupplyTab.toggleRowSelection(row, true)
              }
            })
          })
        } else {
          UserAPI.findRoleByUserIds((this.roleByUserIds)).then(data => { // TODO
            data.forEach(row => {
              if (row.roleId === this.datasf2c) { // 用户中和roleid相同的才勾选
                // 制作初始化的勾选集合
                this.checkedMenus.push(row.userId)
                if (this.checkedDelMenus.indexOf(row.userId) < 0) { // TODO
                  // 如果已经删除就不显示了
                  this.checkedMenusForPage.push(row.userId)
                }
              }
            })
            this.checkedAddMenus.forEach(row => {
              this.checkedMenusForPage.push(row)
            })
            this.checkedMenusForPage = [...new Set(this.checkedMenusForPage)] // 去重
          }).then(() => {
            // todo 可能这个环节出了问题,注意异步
            this.pagination.list.forEach(row => {
              if (this.checkedMenusForPage.indexOf(row.id) >= 0) {
                this.$refs.listPowerSupplyTab.toggleRowSelection(row, true)
              }
            })
          })
        }
      })
    },
    customDelHandler() {
      var list = []
      list.push(this.selected.id)
      UserAPI.delUser(list).then(() => {
        this.queryHandler()
      })
    },
    clickCheckboxHandler(selection) {
      // 将被勾选的object数组转换成用户id的数组-----------start
      this.checkedMenuIds = []
      selection.forEach(row => {
        this.checkedMenuIds.push(row.id)
      })
      // 将被勾选的object数组转换成用户id的数组-----------end

      // 新增的逻辑--------------------------------------------------------------start
      // 新增的遍历 ，如果在原始的勾选中没有最新勾选的某一个id，则放入到新增数组中
      selection.forEach(row => {
        // 如果初始化勾选的里面不包含页面目前勾选的，则将该项添加到临时本页的缓存和最终提交的集合中
        if (this.checkedMenus.indexOf(row.id) < 0) {
          this.checkedAddMenus.push(row.id) // 最终需要提交的新增的集合
          this.checkedAddMenusForCurrentPage.push(row.id) // 本页的缓存，用于判断用户后续的操作，例如，用户勾选后还可能会取消勾选
        }
      })

      // 去重--------------防止有特殊事件发生
      this.checkedAddMenus = [...new Set(this.checkedAddMenus)]
      this.checkedAddMenusForCurrentPage = [...new Set(this.checkedAddMenusForCurrentPage)]
      // 去重--------------

      // 如果勾选后又删除，则按照这个逻辑删除
      // 如果用户又将已经新勾选的选项删除，则按照下面逻辑删除，判断临时的新增集合中是否存在，对比当前页勾选的情况，
      // 如果临时集合中有，当前页又取消了，则删除临时缓存和最终提交的缓存
      for (let a = 0; a < this.checkedAddMenusForCurrentPage.length; a++) {
        if (this.checkedMenuIds.indexOf(this.checkedAddMenusForCurrentPage[a]) < 0) {
          if (this.checkedAddMenus.indexOf(this.checkedAddMenusForCurrentPage[a]) >= 0) {
            this.checkedAddMenus.splice(this.checkedAddMenus.indexOf(this.checkedAddMenusForCurrentPage[a]), 1)
          }
          if (this.checkedAddMenusForCurrentPage.indexOf(this.checkedAddMenusForCurrentPage[a]) >= 0) {
            this.checkedAddMenusForCurrentPage.splice(this.checkedAddMenusForCurrentPage.indexOf(this.checkedAddMenusForCurrentPage[a]), 1)
          }
          a -= 1// 每删除一个项就让a减少一个 保持a和长度同步
        }
      }
      // })
      // 新增的逻辑--------------------------------------------------------------end

      // 删除的逻辑--------------------------------------------------------------start
      // 删除的遍历，如果原始的勾选中的某一项不在最新的勾选中，则将该项放入到删除集合中
      // 遍历初始化集合，如果初始化集合中有元素在当前页勾选的集合中不存在，
      // 说明已经被取消勾选，则放置到最终提交删除的集合和本页临时缓存中
      this.checkedMenus.forEach(row => {
        if (this.checkedMenuIds.indexOf(row) < 0) {
          if (this.checkedDelMenus.indexOf(row) < 0) {
            this.checkedDelMenus.push(row)
            this.checkedDelMenusForCurrentPage.push(row)
          }
        }
      })

      // 去重--------------防止特殊事件
      this.checkedDelMenus = [...new Set(this.checkedDelMenus)]
      this.checkedDelMenusForCurrentPage = [...new Set(this.checkedDelMenusForCurrentPage)]
      // 去重--------------
      // 如果本页临时缓存中在本页勾选的集合中也发现了相同元素，则说明该元素在取消勾选后，又被重新勾选，需要从最终提交删除集合中去掉
      for (let a = 0; a < this.checkedDelMenusForCurrentPage.length; a++) {
        if (this.checkedMenuIds.indexOf(this.checkedDelMenusForCurrentPage[a]) >= 0) {
          if (this.checkedDelMenus.indexOf(this.checkedDelMenusForCurrentPage[a]) >= 0) {
            this.checkedDelMenus.splice(this.checkedDelMenus.indexOf(this.checkedDelMenusForCurrentPage[a]), 1)
          }
          if (this.checkedDelMenusForCurrentPage.indexOf(this.checkedDelMenusForCurrentPage[a]) >= 0) {
            this.checkedDelMenusForCurrentPage.splice(this.checkedDelMenusForCurrentPage.indexOf(this.checkedDelMenusForCurrentPage[a]), 1)
          }
          a -= 1// 每删除一个项就让a减少一个 保持a和长度同步
        }
      }
      // 删除的逻辑--------------------------------------------------------------end
      this.selectedUsers = selection
      // 提交给父类
      this.$emit('func', this.checkedAddMenus)// 最终提交给后台的添加的集合
      this.$emit('funcdel', this.checkedDelMenus)// 最终提交给后台的删除的集合
    }
  }
}
</script>

<style lang="scss" scoped>
  /deep/ .el-card {
    border: none;
  }
  /deep/ .el-button {
    margin-left: 10px;
  }
</style>
