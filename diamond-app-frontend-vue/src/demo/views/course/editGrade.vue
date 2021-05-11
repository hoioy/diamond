<template>
  <div>
    <el-button icon="el-icon-back" round @click="backHandler">
      返回
    </el-button>
    <el-card>
      <el-table :data="courses" border style="width: 100%" @row-click="handleCurrentChange">
        <el-table-column :show-overflow-tooltip="true" prop="studentName" label="学生姓名"/>
        <el-table-column :show-overflow-tooltip="true" prop="courseName" label="课程名称"/>
        <el-table-column :show-overflow-tooltip="true" label="成绩">
          <template scope="scope">
            <el-input v-model="scope.row.score" size="small" placeholder="请输入内容" @change="handleEdit(scope.$index, scope.row)"/>
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

    return {
      defaultIcon: baseURLForPic,
      form: form,
      courses: [],

      addOption: {
        allCourses: [],
        courseId: null
      },
      newScore: 0
    }
  },
  activated() {
    const paramsCourse = {
      page: 1,
      pageSize: 100,
      filters: { courseId: this.detail.id },
      sorts: []
    }

    studentAPI.queryAllCourseStudents(paramsCourse).then((data) => {
      this.courses = data.list
      deepMergeLeft(this.form, data)
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    })

    const params = {
      page: 1,
      pageSize: 100,
      filters: {},
      sorts: []
    }

    courseAPI.queryPageCourses(params).then(data => {
      this.addOption.allCourses = data.list
    })
  },
  methods: {
    customSubmitSuccessHandler() {
      this.$refs['form'].clearValidate()
    },
    handleCurrentChange(row, event, column) {
      console.log(row, event, column, event.currentTarget)
    },
    handleEdit(index, row) {
      console.log(index, row)
      const params = {
        studentId: row.studentId,
        courseId: row.courseId,
        score: row.score,
        id: row.id
      }
      studentAPI.updateCourseStudent(params).then(data => {
        console.log('9999  ' + data)
      })
    },
    addStudentCourseHandler(id) {
      if (!id) return
      studentAPI.addCourseStudent([{
        courseId: id
      }]).then(data => {
        this.queryAllStudentCourses(this.detail.id)
        this.optionSuccessHandler()
        this.addOption.courseId = null
      })
    },
    handleChange(files, fileList) {
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
