import * as RoleAPI from '../../api/system-management/role'
import * as MenuAPI from '../../api/system-management/menu'

import * as UserAPI from '../../api/system-management/user'

export default {
  methods: {
    initForm() {
      return {
        id: null,
        token: null,
        roleName: '',
        state: '1',
        roleIndex: null,
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
        roleName: [{
          required: true, message: '请输入角色名称', trigger: 'blur'
        }, {
          min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }],
        roleIndex: [{
          required: true, message: '请输入角色排序号', trigger: 'blur'
        }, { validator: isNum, trigger: 'blur' }
        ],
        remark: [
          {
            min: 0, max: 200, message: '需要小于200个字符', trigger: 'blur'
          }]
      }
    },

    queryAllUsers() {
      this.users = []
      UserAPI.queryPageUsersByRole(this.createQueryParams()).then(data => {
        this.queryResultHandler(data)
      })
    },
    filterNodeHandler(value, data) {
      if (!value) return true
      return data.menuName.indexOf(value) !== -1
    },
    initMenus() {
      MenuAPI.queryAllNoTreeList({}).then(allMenus => {
        const menusTree = this.getTreeList(allMenus, null)
        this.menusTree = menusTree

        RoleAPI.queryAllRoleMenus(this.detail.id).then(roleMenus => {
          const checkedMenus = []
          roleMenus.forEach(item => {
            checkedMenus.push(item)
          })
          this.$refs['menusTree'].setCheckedKeys(checkedMenus)
        })
      })
    },
    // 树形结构遍历
    getTreeList(data, parentId) {
      const treeList = []
      for (var key in data) {
        if (data[key].parentId === parentId && data[key].id !== null) {
          treeList.push(this.findChildren(data[key], data))
        }
      }
      return treeList
    },
    findChildren(tree, treeList) {
      for (let i = 0; i < treeList.length; i++) {
        if (treeList[i].parentId === tree.id) {
          if (tree.children == null) {
            tree.children = []
          }
          tree.children.push(this.findChildren(treeList[i], treeList))
        }
      }
      return tree
    }
  }
}
