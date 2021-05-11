<template>
  <div>
    <!--<el-link :href="defaultIcon">-->
      <!--<el-button round type="info">-->
        <!--下载模板-->
      <!--</el-button>-->
    <!--</el-link>-->

    <a :href="defaultIcon"> <el-button round type="info">
      下载模板1
    </el-button></a>
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
    <el-button round>
      <!--上传用户中必填字段为：loginName(用户登录名，唯一id，需英文)，password（用户密码，至少六位），userName（用户姓名），email（邮箱须符合基本邮箱规范），gender（性别，女性：0，男性：1）-->
      上传用户中必填字段为：登陆ID(用户登录名，唯一id)，姓名，邮箱（邮箱须符合基本邮箱规范），性别（女性：0，男性：1）
    </el-button>
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
    </el-table>

    <el-button round type="primary" @click="submitUpload">
      上传服务器
    </el-button>
    <el-button @click="backHandler">
      取消
    </el-button>
  </div>
</template>

<script>
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'

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
      if(this.showTableData.length>0){
        UserAPI.addUserBatch(this.showTableData).then(data => {
          this.showTableData = []
          this.fileList = []
          this.$message({
            type: 'success',
            message: '操作成功'
          })
        }).catch((err) => {
          this.$message({
            type: 'warning',
            message: err.response.data.message
          })
        })
      }{
        this.$message({
            type: 'warning',
            message: "列表数据为空"
          })
      }

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
          const arr = []
          const formatFlag = true
          ws.map(v => {
            const obj = {}
            obj.loginName = v['登陆ID']
            obj.userName = v['姓名']
            obj.email = v['邮箱']
            obj.phoneNum = v['电话']
            obj.gender = v['性别']
            if(!obj.loginName){
              formatFlag=false
            }
            arr.push(obj)
          })
          if(!formatFlag){
            this.$message({
                type: 'warning',
                message: "上传数据格式错误"
              })
          }else{
            this.showTableData = this.tableData.concat(arr) // 把数据塞到表格预览
          }
          
          // this.addTableData = arr;
        } catch (e) {
          this.$message({
                type: 'warning',
                message: "上传数据格式错误"
              })
              this.fileList=[]
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
