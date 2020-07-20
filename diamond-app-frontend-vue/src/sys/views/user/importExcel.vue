<template>
  <dev>
    <el-link :href="defaultIcon">
      <el-button round type="info">
        下载模板
      </el-button>
    </el-link>
    <el-upload
      ref="upload"
      class="upload-demo"
      action=""
      :on-success="handleAvatarSuccess"
      :on-change="upload"
      :on-remove="handleRemove"
      :before-remove="beforeRemove"
      :limit="1"
      :on-exceed="handleExceed"
      accept=".xlsx"
      :file-list="fileList"
      :auto-upload="false"
    >
      <el-button round type="primary">
        上传Excel
      </el-button>
    </el-upload>
    <el-table id="out-table" :data="showTableData">
      <el-table-column prop="loginName" label="登录ID"/>
      <el-table-column prop="userName" label="姓名"/>
      <el-table-column prop="gender" label="性别">
        <template slot-scope="scope">
          {{ scope.row.gender | translateGender }}
        </template>
      </el-table-column>
      <el-table-column prop="email" label="邮箱"/>
      <el-table-column prop="phoneNum" label="电话"/>
      <el-table-column :formatter="dateFormat" prop="createdDate" label="创建时间"/>
      <!--<el-table-column prop="state" label="启用状态">-->
        <!--<template slot-scope="scope">-->
          <!--<state :state="scope.row.state"/>-->
        <!--</template>-->
      <!--</el-table-column>-->
    </el-table>

    <el-button round type="primary" @click="submitUpload">
      上传服务器
    </el-button>
    <el-button @click="backHandler">
      取消
    </el-button>
  </dev>
</template>

<script>
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import { deepMergeLeft } from '@folder-inside-utils'
import * as UserAPI from '../../api/system-management/user'
import mixins from './mixins'
import XLSX from 'xlsx'
import moment from 'moment'

var baseURLForPic = process.env.VUE_APP_BASE_API + 'file/img/user-blank.xlsx'

export default {
  mixins: [BaseEditForm, mixins],
  props: {
    detail: {
      required: false,
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      defaultIcon: baseURLForPic,
      tableData: [],
      fileList: [],
      showTableData: [],
      addTableData: []
    }
  },
  activated() {
  },
  methods: {
    submitUpload() {
      UserAPI.addUserBatch(this.showTableData).then(data => {
        // this.submitSuccessHandler(data)
        this.showTableData = []
        this.fileList = []
        this.$message({
          type: 'success',
          message: '操作成功'
        })
      }).catch(() => {
        this.$message({
          type: 'warning',
          message: '上传excel有id或者用户名重复'
        })
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      if (response && response.code === '200') {
        this.$notify.success({
          title: '成功',
          message: '导入成功' })
      }
    },
    // 导入Excel事件
    upload(file, fileList) {
      const files = { 0: file.raw }
      this.readExcel1(files)
    },
    readExcel1(files) {
      // console.log(files)
      if (files.length <= 0) {
        return false
      } else if (!/\.(xls|xlsx)$/.test(files[0].name.toLowerCase())) {
        this.$Message.error('上传格式不正确，请上传xls或者xlsx格式')
        return false
      }
      const fileReader = new FileReader()
      fileReader.onload = (ev) => {
        try {
          const data = ev.target.result
          const workbook = XLSX.read(data, {
            type: 'binary',
            cellDates: true
          })
          const wsname = workbook.SheetNames[0]// 取第一张表
          const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname])// 生成json表格内容
          /*                        const arr = []
                        ws.map(v => {
                            const obj = {}
                            obj.date = v['日期']
                            obj.name = v['姓名']
                            obj.address = v['地址']
                            arr.push(obj)
                        })*/
          this.showTableData = this.tableData.concat(ws) // 把数据塞到表格预览
          // this.addTableData = arr;
        } catch (e) {
          return false
        }
      }
      fileReader.readAsBinaryString(files[0])
    },
    uploadUrl() {
      return process.env.BASE_API + '/upload/importIncomeExcel'
    },
    // 时间转换
    dateFormat: function(row, column) {
      const date = row[column.property]
      if (date === undefined) {
        return ''
      }
      return moment(date).format('YYYY-MM-DD HH:mm:ss')
    },
    handleRemove(file, fileList) {
      this.showTableData = this.tableData
      console.log(file, fileList)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    }
  }
}
</script>

1
<style lang="scss" scoped>
    .el-button {
        margin-top: 10px;
        margin-bottom: 10px;
        margin-left: 10px;
        float: left;
    }
    .el-link {
        float: left;
    }
    .el-upload {
        margin-top: 10px;
        margin-bottom: 10px;
        margin-left: 10px;
        float: left;
    }
</style>
