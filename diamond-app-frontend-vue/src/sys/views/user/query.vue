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
            <el-collapse v-model="activeName">
              <el-collapse-item title="导入导出" name="1">
                <flex-center>
                  <el-button round type="primary" @click="exportExcel">
                    导出为excel
                  </el-button>
                  <el-button v-if="hasPerm('user/add')||hasPerm('user/*')" round type="primary" @click="$emit('option-changed','importExcel')">
                    excel导入
                  </el-button>
                </flex-center>
              </el-collapse-item>
            </el-collapse>
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
              批量新增用户关联关系（角色，部门，用户组）
            </el-button>
          </el-button-group>
        </template>
      </button-right>
    </el-col>
    <el-col :span="24">
      <el-table id="userTable" ref="listPowerSupplyTab" :data="pagination.list" highlight-current-row stripe border @current-change="(row) => { selected = row }" @row-dblclick="$emit('option-changed','check', selected)" @sort-change="sortChangeHandler" @selection-change="clickCheckboxHandler">
        <el-table-column type="selection" label="全选"/>
        <el-table-column :show-overflow-tooltip="true" prop="loginName" label="登录ID" sortable="custom" align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="userName" label="姓名" sortable="custom" align="center"/>
        <el-table-column prop="gender" label="性别" sortable="custom" align="center">
          <template slot-scope="scope">
            {{ scope.row.gender | translateGender }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="email" label="邮箱" sortable="custom" align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="phoneNum" label="电话" sortable="custom" align="center"/>
        <el-table-column prop="createdDate" label="创建时间" w sortable="custom" align="center">
          <template slot-scope="scope">
            {{ scope.row.createdDate | parseTime }}
          </template>
        </el-table-column>
        <!--<el-table-column prop="state" label="启用状态" sortable="custom" align="center">-->
          <!--<template slot-scope="scope">-->
            <!--<state :state="scope.row.state"/>-->
          <!--</template>-->
        <!--</el-table-column>-->
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
import * as ExcelApi from './excel/export'

export default {
  mixins: [BaseQueryPageForm, mixins],
  data() {
    const queryCriteria = this.initQueryCriteria()
    return {
      activeName: '1', // 展开name为1的折叠板
      queryCriteria: queryCriteria,
      selected: null,
      selectedUsers: [],
      checkedMenus: [],
      userList: [],
      fileList: [],
      // 展示用
      showTableData: [],
      // 新增用
      addTableData: [],
      // 导出excel名称
      exportExcelName: 'export-excel.xlsx'
    }
  },
  activated() {
    this.selected = null
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
    executeQueryPage() {
      UserAPI.queryPageUsers(this.createQueryParams()).then(data => {
        console.info(data)
        this.userList = data.list
        this.queryResultHandler(data)
      })
      //         .then(() => {
      //   this.checkedMenus = ['6613831cac9e4597abbd0138116a8f3c', '3085607c6b6b4bf989ce8438d6492af0', '2428a93cce1f447f823169f2626ec7a0']
      //   console.info(this.pagination.list)
      //   this.pagination.list.forEach(row => {
      //     if (this.checkedMenus.indexOf(row.id) >= 0) {
      //       this.$refs.listPowerSupplyTab.toggleRowSelection(row, true)
      //     }
      //   })
      // })
    },
    customDelHandler() {
      var list = []
      list.push(this.selected.id)
      // console.log('list2222' + list)
      UserAPI.delUser(list).then(() => {
        this.queryHandler()
      })
    },
    clickCheckboxHandler(selection) {
      // console.info(selection)
      this.selectedUsers = selection
    },
    exportExcel() {
      // d导出全部json数据，从dto取值;
      ExcelApi.exportExcelAllField(this.exportExcelName, this.userList)
      // 导出table中数据，从table取值
      // ExcelApi.exportExcelTableField('export-excel-table.xlsx','userTable')
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
