import * as DeptAPI from '../../api/system-management/dept'

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
        token: null,
        parentId: null,
        parentName: null,
        deptType: '',
        deptName: '',
        deptState: '1',
        flag: 1,
        deptIndex: null,
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
      const isEmpty = (rule, value, callback) => {
        if (value.split(' ').join('').length === 0) {
          callback(new Error('输入变量不可为空'))
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
        }, { validator: isEmpty, trigger: 'blur' }],
        deptIndex: [{
          required: true, message: '请输入部门排序号', trigger: 'blur'
        },
        { validator: isNum, trigger: 'blur' }
        ],
        remark: [
          {
            min: 0, max: 200, message: '需要小于200个字符', trigger: 'blur'
          }]
      }
    },
    queryAllUsers() {
      this.users = []
      DeptAPI.queryAllDeptUsers(this.detail.id).then(users => {
        this.users = users
      })
    },
    getParentDeptName(id) {
      this.parentDeptName = ''
      if (!id) return
      DeptAPI.queryDeptById(id).then(dept => {
        this.parentDeptName = dept.name
      })
    }

  }
}
