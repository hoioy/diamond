import * as DeptAPI from '@src/demo/api/classGrade'

export default {
  data() {
    return {
      parentDeptName: ''
    }
  },
  methods: {
    initForm() {
      return {
        id: null,
        gradeName: '',
        flag: 1,
        remark: ''
      }
    },
    initRules() {
      const isNum = (rule, value, callback) => {
        const age = /^[0-9]*$/
        if (!age.test(value)) {
          callback(new Error('只能为数字'))
        } else if (value >= 1000000) {
          callback(new Error('数字长度超出限制，请输入6位以下数字'))
        } else {
          callback()
        }
      }
      return {
        deptType: [{
          required: true, message: '请输入部门类型', trigger: 'blur'
        }],
        deptName: [{
          required: true, message: '请输入部门名称', trigger: 'blur'
        }, {
          min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'
        }],
        deptIndex: [{
          required: true, message: '请输入部门排序号', trigger: 'blur'
        },
        { validator: isNum, trigger: 'blur' }
        ]
      }
    },
    queryAllUsers() {
      this.users = []
      DeptAPI.queryAllDeptUsers(this.detail.id).then(users => {
        this.users = users
      })
    }
  }
}
