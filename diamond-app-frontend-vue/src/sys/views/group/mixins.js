import * as GroupAPI from '../../api/system-management/group'

export default {
  methods: {
    initForm() {
      return {
        id: null,
        token: null,
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
      return {
        groupName: [{
          required: true, message: '请输入分组名称', trigger: 'blur'
        }, {
          min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'
        }],
        // state: [{
        //   required: true, message: '请选择分组启用状态', trigger: 'blur'
        // }],
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
