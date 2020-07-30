<template>
  <el-row>
    <el-card>
      <el-col :span="24">
        <el-form :model="queryCriteria" label-width="150px">
          <el-col :span="8">
            <el-form-item label="操作方法:" prop="logOperationType">
              <el-input v-model="queryCriteria.logOperationType" placeholder="请输入操作方法"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="操作人:" prop="logUserName">
              <el-input v-model="queryCriteria.logUserName" placeholder="请输入姓名"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="模块名称:" prop="module">
              <el-input v-model="queryCriteria.module" placeholder="请输入模块名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开始时间:" prop="startTime">
              <el-date-picker v-model="queryCriteria.startTime" type="datetime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择开始日期" :picker-options="pickerOptionsStart" @change="startTimeChang"/>
            </el-form-item>
            <!--<el-button style="margin-left: 10px;" plain size="mini" @click="theWeek">-->
            <!--最近一周-->
            <!--</el-button>-->
            <!--<el-button style="margin-left: 10px;" type="primary" icon="el-icon-search" @click="searchTime">-->
            <!--查询-->
            <!--</el-button>-->
          </el-col>
          <el-col :span="8">
            <el-form-item label="结束时间:" prop="endTime">
              <el-date-picker v-model="queryCriteria.endTime" type="datetime" format="yyyy-MM-dd" value-format="yyyy-MM-dd" placeholder="选择结束日期" :picker-options="pickerOptionsOver" @change="endTimeChang"/>
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
        日志列表
        <template slot="button">
          <el-button-group>
            <el-button v-if="hasPerm('log/delete')&&hasPerm('log/*')" type="primary" @click="delHandler">
              删除
            </el-button>
            <el-button
              v-if="(selectedLogs.length > 0 &&hasPerm('log/delete')) ||
                (selectedLogs.length > 0 &&hasPerm('log/*'))"
              type="primary"
              @click="batchDelHandler"
            >
              批量删除
            </el-button>
          </el-button-group>
        </template>
      </button-right>
    </el-col>
    <el-col :span="24">
      <el-table :data="pagination.list" highlight-current-row stripe border @current-change="(row) => { selected = row }" @sort-change="sortChangeHandler" @selection-change="clickLogCheckboxHandler">
        <el-table-column type="selection" label="全选"/>
        <el-table-column prop="startTime" label="创建时间" width="180" sortable="custom" align="center">
          <template slot-scope="scope">
            {{ scope.row.startTime }}
          </template>
        </el-table-column>
        <el-table-column :show-overflow-tooltip="true" prop="logInfo" label="方法描述" sortable="custom" align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="logClientIp" label="操作IP" sortable="custom" align="center"/>
        <el-table-column :show-overflow-tooltip="true" prop="logUserName" label="操作人" sortable="custom" align="center"/>
      </el-table>
      <pagination :pagination="pagination" @page-size-changed="pageSizeChangeHandler" @page-changed="pageChangeHandler"/>
    </el-col>
  </el-row>
</template>
<script>
import { deepMerge } from '@folder-inside-utils'
import BaseQueryPageForm from '@folder-inside-views-common/BaseQueryPageForm'
import * as LogAPI from '../../api/system-management/log'

export default {
  mixins: [BaseQueryPageForm],
  data() {
    const queryCriteria = this.initQueryCriteria()
    return {
      queryCriteria: queryCriteria,
      selected: null,
      selectedLogs: [],
      startTime: '',
      endTime: '',
      pickerOptionsStart: {
        disabledDate(time) {
          return time.getTime() < 1488297600000 || time.getTime() >= Date.now()
        }
      },
      pickerOptionsOver: {
        disabledDate(time) {
          return time.getTime() < 1488297600000 || time.getTime() >= Date.now()
        }
      }
    }
  },
  activated() {
    this.selected = null
  },
  methods: {
    initQueryCriteria(form = {}) {
      return deepMerge(form, {
        logMethodName: '',
        logUserName: '',
        module: '',
        startTime: '',
        endTime: ''
      })
    },
    executeQueryPage() {
      LogAPI.queryPageLogs(this.createQueryParams()).then(data => {
        this.queryResultHandler(data)
      })
    },
    customDelHandler() {
      const params = []
      params.push(this.selected.id)
      LogAPI.delLog(params).then(() => {
        this.queryHandler()
      })
    },
    clickLogCheckboxHandler(selection) {
      this.selectedLogs = selection
    },
    batchDelHandler() {
      this.$confirm('此操作将批量删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.selectedLogs.length !== 0) {
          const params = []
          this.selectedLogs.forEach(log => {
            params.push(log.id)
          })
          LogAPI.delLog(params).then(() => {
            this.queryHandler()
          })
        } else {
          this.$message({
            type: 'info',
            message: '您未定义删除逻辑处理方法customDelHandler'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'warning',
          message: '已取消删除'
        })
      })
    },
    dateFilter: function(input) {
      var d = new Date(input)
      var year = d.getFullYear()
      var month = d.getMonth() < 9 ? '0' + (d.getMonth() + 1) : '' + (d.getMonth() + 1)
      var day = d.getDate() < 10 ? '0' + d.getDate() : '' + d.getDate()
      // var hour = d.getHours();
      // var minutes = d.getMinutes();
      // var seconds = d.getSeconds();
      return year + '-' + month + '-' + day
    },
    startTimeChang(val) {
      const startTime = this.dateFilter(val)
      console.info(startTime)
      this.startTime = startTime
    },
    endTimeChang(val) {
      const endTime = this.dateFilter(val)
      this.endTime = endTime
    },
    async theWeek() {
      const end = new Date()
      const start = new Date()
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)

      this.startTime = this.dateFilter(start)
      this.endTime = this.dateFilter(end)
      // this.searchTime()
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
