<template>
  <div>
    <el-button icon="el-icon-back" round @click="backHandler">
      返回
    </el-button>
    <el-card>
      <template slot="header">
        <select-right>
          <template slot="left">
            课程信息
          </template>
          <el-select v-model="addOption.courseId" placeholder="添加课程" clearable filterable @change="addStudentCourseHandler">
            <el-option
              v-for="(item, index) in addOption.allCourses"
              :key="index"
              :label="item.courseName"
              :value="item.id"
            />
          </el-select>
        </select-right>
      </template>
      <el-table :data="courses" border style="width: 100%">
        <el-table-column :show-overflow-tooltip="true" prop="courseName" label="课程名称"/>
        <el-table-column :show-overflow-tooltip="true" prop="score" label="成绩"/>
        <el-table-column label="操作" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="warning" @click="delstudentCourseHandler(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import { deepMergeLeft } from '@folder-inside-utils'
import * as courseAPI from '@src/demo/api/course'
import * as studentAPI from '@src/demo/api/student'
import mixins from './mixins'

var baseURLForPic = process.env.VUE_APP_BASE_API

export default {
  mixins: [BaseEditForm, mixins],
  props: {
    detail: {
      required: false,
      type: Object,
      default: () => {
      }
    }
  },

  data() {
    const form = this.initForm()
    delete form.password
    const rules = this.editRules()
    delete rules.password
    rules.id = [{
      required: true, message: '编辑信息时ID不能为空', trigger: 'change'
    }]

    return {
      defaultIcon: baseURLForPic,
      form: form,
      rules: rules,
      courses: [],

      addOption: {
        allCourses: [],
        courseId: null
      }
    }
  },
  activated() {
    const paramsCourse = {
      page: 1,
      pageSize: 100,
      filters: { studentId: this.detail.id },
      sorts: []
    }
    studentAPI.queryAllCourseStudents(paramsCourse).then((data) => {
      this.courses = data.list
      deepMergeLeft(this.form, data)
      this.$nextTick(() => {
        // this.$refs['form'].clearValidate()
      })
    })
    const params = {
      page: 1,
      pageSize: 100,
      filters: {},
      sorts: []
    }
    courseAPI.queryPageCourses(params).then(data => {
      console.log('7777 ' + data.list)
      this.addOption.allCourses = data.list
    })
  },
  methods: {
    customSubmitSuccessHandler() {
      // this.$refs['form'].clearValidate()
      // this.$refs['password'].clearValidate()
    },
    addStudentCourseHandler(id) {
      if (!id) return
      const studentCourses = []
      studentCourses.push({
        studentId: this.detail.id,
        courseId: id,
        score: 0
      })
      studentAPI.addCourseStudent(studentCourses).then(data => {
        this.queryAllStudentCourses()
        this.optionSuccessHandler()
        this.addOption.courseId = null
      })
    },
    delstudentCourseHandler(row) {
      const params = {
        studentId: row.studentId,
        courseId: row.courseId
      }
      studentAPI.delCourseStudent(params).then(data => {
        this.queryAllStudentCourses()
        this.optionSuccessHandler()
      })
    },
    handleChange(files, fileList) {
      debugger
      if (fileList.length > 1) {
        fileList.splice(0, 1)
      }
    },
    formDataoptionSuccessHandler() {
      this.$message({
        type: 'success',
        message: '操作成功'
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-card {
    margin-top: 10px;
  }

  .avatar-uploader {

    /deep/ .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;

      &:hover {
        border-color: #409EFF;
      }

      .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
      }
    }

    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
  }
</style>
