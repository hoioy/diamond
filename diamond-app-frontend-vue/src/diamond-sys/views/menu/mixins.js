import * as MenuAPI from '../../api/system-management/menu'
export default {
  data() {
    return {
      parentMenuName: '',
      menuUrls: [],
      roles: []
    }
  },
  methods: {
    initForm() {
      return {
        id: null,
        token: null,
        menuIndex: null,
        menuName: '',
        remark: '',
        menuClassify: '',
        authorityId: '',
        smallIconPath: '',
        menuDesc: '',
        menuUrl: ''
      }
    },
    initRules(inData) {
      const arrayDataValidator = (rule, value, callback) => {
        const age = /^[0-9]*$/
        if (!age.test(value)) {
          callback(new Error('只能为数字'))
        } else if (value >= 1000000) {
          callback(new Error('数字长度超出限制，请输入6位以下数字'))
        } else {
          callback()
        }
      }
      const MenuUrlValidator = (rule, value, callback) => {
        if (inData === 'menu') {
          if (!value) {
            callback(new Error('菜单分类时该项必填'))
          } else {
            callback()
          }
        } else {
          callback()
        }
      }
      const MenuDescValidator = (rule, value, callback) => {
        if (inData === 'menu' || inData === 'external') {
          if (!value) {
            callback(new Error('菜单或者外链分类时该项必填'))
          } else {
            console.info(value)
            callback()
          }
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
        id: [{
          required: true, message: '编辑信息时ID不能为空', trigger: 'change'
        }, { validator: isEmpty, trigger: 'blur' }],
        menuIndex: [{
          required: true, message: '请输入菜单排序号', trigger: 'blur'
        }, { validator: arrayDataValidator, trigger: 'blur' }],
        menuName: [{
          required: true, message: '请输入菜单名称', trigger: 'blur'
        }, {
          min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'
        }],
        menuClassify: [{
          required: true, message: '请输入菜单分类', trigger: 'blur'
        }],
        menuDesc: [
          { validator: MenuDescValidator, trigger: 'blur' }
        ],
        menuUrl: [
          { validator: MenuUrlValidator, trigger: 'blur' }
        ],
        remark: [
          {
            min: 0, max: 200, message: '需要小于200个字符', trigger: 'blur'
          }]
      }
    },
    queryMenuRoles() {
      this.roles = []
      MenuAPI.queryAllMenuRole(this.detail.id).then(roles => {
        this.roles = roles
      })
    },
    queryMenuUrls() {
      this.menuUrls = []
      const params = { id: this.detail.id }
      MenuAPI.queryAllMenuUrl(params).then(menuUrls => {
        this.menuUrls = menuUrls
      })
    },
    getParentMenuName(id) {
      this.parentMenuName = ''
      if (!id) return
      MenuAPI.queryMenuById(id).then(menu => {
        this.parentMenuName = menu.name
      })
    },
    initMenus() {
      MenuAPI.queryAllNoTreeList({}).then(data => {
        this.allMenus = data
        const menusTree = this.getTreeList(this.allMenus, null)
        this.menusTree = menusTree
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
          // if (treeList[i].parentName === '' || treeList[i].parentName === null) {
          //   for (let j = 0; j < treeList.length; j++) {
          //     if (treeList[j].id === tree.parentId) {
          //       treeList[j].children.push(treeList[i])
          //     }
          //   }
          // } else {
          // if (treeList[i].menuName !== null && treeList[i].menuName !== '') {
          if (tree.children == null) {
            tree.children = []
          }
          tree.children.push(this.findChildren(treeList[i], treeList))
          //  }
          // }
        }
      }
      return tree
    },
    showHide: function(show) {
      switch (show) {
        case 'external' :
          this.externalOrButtonOrMenu = 'external'
          break
        case 'button' :
          this.externalOrButtonOrMenu = 'button'
          break
        case 'menu' :
          this.externalOrButtonOrMenu = 'menu'
          break
      }
    }
  }
}
