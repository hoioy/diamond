import { mapGetters } from 'vuex'
import * as UserAPI from '../../api/system-management/user'

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
        state: '',
        userIndex: null,
        loginName: '',
        userName: '',
        nickname: '',
        avatar: '',
        idNumber: '',
        gender: '',
        birthday: '',
        phoneNum: '',
        email: '',
        address: '',
        tag: '',
        remark: ''
      }
    },
    initRules() {
      const validateLoginName = (rule, value, callback) => {
        UserAPI.findIdByLoginName(value).then(data => {
          if (data) {
            callback(new Error('用户名已存在'))
          } else {
            callback()
          }
        })
      }

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
        userIndex: [{
          required: true, message: '请输入用户排序号', trigger: 'blur'
        }, { validator: isNum, trigger: 'blur' }
        ],
        loginName: [{
          required: true, message: '请输入用户登录名', trigger: 'blur'
        }, {
          validator: validateLoginName, trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }],
        password: [{
          required: true, message: '请输入用户密码', trigger: 'blur'
        },
        {
          min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'
        }],
        remark: [
          {
            min: 0, max: 200, message: '需要小于200个字符', trigger: 'blur'
          }],
        userName: [{
          required: true, message: '请输入用户姓名', trigger: 'blur'
        }, {
          min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }],
        gender: [{
          required: true, message: '请选择用户性别', trigger: 'blur'
        }],
        email: [{
          required: true, message: '请输入用户邮箱', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }]
      }
    },
    editRules() {
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
        userIndex: [{
          required: true, message: '请输入用户排序号', trigger: 'blur'
        }, { validator: isNum, trigger: 'blur' }
        ],
        loginName: [{
          required: true, message: '请输入用户登录名', trigger: 'blur'
        }],
        password: [{
          required: true, message: '请输入用户密码', trigger: 'blur'
        },
        {
          min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'
        }],
        userName: [{
          required: true, message: '请输入用户姓名', trigger: 'blur'
        }, {
          min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }],
        gender: [{
          required: true, message: '请选择用户性别', trigger: 'blur'
        }],
        email: [{
          required: true, message: '请输入用户邮箱', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }]
      }
    },
    queryAllUserDepts(id) {
      if (!id) id = this.detail.id
      UserAPI.queryAllUserDepts(id).then(depts => { this.depts = depts })
    },
    queryAllUserGroups(id) {
      this.groups = []
      if (!id) id = this.detail.id
      UserAPI.queryAllUserGroups(id).then(groups => { this.groups = groups })
    },
    queryAllUserRoles(id) {
      this.roles = []
      if (!id) id = this.detail.id
      UserAPI.queryAllUserRoles(id).then(roles => { this.roles = roles })
    }
  }
}
