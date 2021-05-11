import * as GroupAPI from '../../api/system-management/group'

export default {
  methods: {
    initForm() {
      return {
        id: null,
        groupName: '',
        state: '1',
        flag: 1,
        groupIndex: null,
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
        groupName: [{
          required: true, message: '请输入分组名称', trigger: 'blur'
        }, {
          min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }],
        remark: [
          {
            min: 0, max: 200, message: '需要小于200个字符', trigger: 'blur'
          }],
        groupIndex: [{
          required: true, message: '请输入分组排序号', trigger: 'blur'
        },
        { validator: isNum, trigger: 'blur' }
        ]
      }
    },
    queryAllUsers() {
      this.users = []
      GroupAPI.queryAllGroupUsers(this.detail.id).then(users => {
        this.users = users
      })
    }
  }
}
