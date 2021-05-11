import { mapGetters } from 'vuex'
import * as studentAPI from '@src/demo/api/student'
import { deepMergeLeft } from '@folder-inside-utils'

export default {
  computed: {
    ...mapGetters([
      'dictionaries'
    ])
  },
  methods: {
    initForm() {
      return {
        id: null,
        token: null,
        studentClass: '',
        studentName: '',
        studentValue: '',
        studentSex: ''
      }
    },
    initRules() {

    },
    editRules() {
      return {
        index: [{
          required: true, message: '请输入用户排序号', trigger: 'blur'
        }],
        loginName: [{
          required: true, message: '请输入用户登录名', trigger: 'blur'
        }],
        password: [{
          required: true, message: '请输入用户密码', trigger: 'blur'
        },
        {
          min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'change'
        }],
        userName: [{
          required: true, message: '请输入用户姓名', trigger: 'blur'
        }, {
          min: 2, max: 20, message: '长度在 4 到 20 个字符', trigger: 'blur'
        }],
        gender: [{
          required: true, message: '请选择用户性别', trigger: 'blur'
        }],
        email: [{
          required: true, message: '请输入用户邮箱', trigger: 'blur'
        }]
      }
    },
    queryAllStudentCourses(id) {
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
    }
  }
}
