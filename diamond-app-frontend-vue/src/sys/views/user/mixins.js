import { mapGetters } from 'vuex'
import * as UserAPI from '../../api/system-management/user'
// import * as GroupAPI from '../../api/system-management/group'
// import * as DeptAPI from '../../api/system-management/dept'

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
      return {
        userIndex: [{
          required: true, message: '请输入菜单排序号', trigger: 'blur'
        }, { validator: isNum, trigger: 'blur' }
        ],
        // state: [{
        //   required: true, message: '请选择用户启用状态', trigger: 'blur'
        // }],
        index: [{
          required: true, message: '请输入用户排序号', trigger: 'blur'
        }],
        loginName: [{
          required: true, message: '请输入用户登录名', trigger: 'blur'
        }, {
          validator: validateLoginName, trigger: 'blur'
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
          min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'
        }],
        // nickname: [{
        //   required: true, message: '请输入用户昵称', trigger: 'blur'
        // }],
        gender: [{
          required: true, message: '请选择用户性别', trigger: 'blur'
        }],
        email: [{
          required: true, message: '请输入用户邮箱', trigger: 'blur'
        }]
      }
    },
    editRules() {
      return {
        // state: [{
        //   required: true, message: '请选择用户启用状态', trigger: 'blur'
        // }],
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
        // nickname: [{
        //   required: true, message: '请输入用户昵称', trigger: 'blur'
        // }],
        gender: [{
          required: true, message: '请选择用户性别', trigger: 'blur'
        }],
        email: [{
          required: true, message: '请输入用户邮箱', trigger: 'blur'
        }]
      }
    },
    queryAllUserDepts(id) {
      this.depts = []
      if (!id) id = this.detail.id
      // const params = { id: id }
      UserAPI.queryAllUserDepts(id).then(depts => { this.depts = depts })
    },
    queryAllUserGroups(id) {
      this.groups = []
      if (!id) id = this.detail.id
      // const params = { id: id }
      UserAPI.queryAllUserGroups(id).then(groups => { this.groups = groups })
    },
    queryAllUserRoles(id) {
      this.roles = []
      if (!id) id = this.detail.id
      // const params = { id }
      UserAPI.queryAllUserRoles(id).then(roles => { this.roles = roles })
    },
    queryAllGroupRoles(id) {
      // this.roles = []
      // if (!id) id = this.detail.id
      // // const params = { id }
      // GroupAPI.queryAllGroupRoles(id).then(roles => { this.roles = roles })
    },
    queryAllDeptRoles(id) {
      // this.roles = []
      // if (!id) id = this.detail.id
      // // const params = { id }
      // DeptAPI.queryAllDeptRoles(id).then(roles => { this.roles = roles })
    }
  }
}
