import Mock from 'mockjs'
import { deepMerge, param2Obj, param2ObjForSplit } from '@/utils'
// import { asyncMenuMap } from '@/router'
import { mockConfig as roleMockConfig, roles } from './role'
// import { menusTree } from './menu'
import Utils from '../utils'
import { depts, deptsTree } from './dept'

export const mockConfig = {
  'id|1': Utils.id,
  'parentId|1': Utils.id, // 父级id
  'authorityId|1': '', // 父级id
  'flag|1': Utils.flag, // 是否删除
  'state|1': Utils.state, // 是否启用
  index: '@increment', // 菜单序号
  name: '@cword(5, 10)', // 名称
  iconPath: '', // 图标
  remark: '', // 备注
  mark: '', // 备注
  'menuClassify|1': ['menu'], // 是否启用
  menuDesc: '', // 备注
  'menuIndex|1': '', // 父级id
  'menuName|1': ['demo', '系统管理', '资源管理', '角色管理', '用户管理', '机构管理'], // 是否启用
  'menuUrl|1': ['Layout', 'diamond/views/system-management/dept/main', 'diamond/views/dashboard/index'], // 是否启用
  createdBy: '@increment', // 创建人
  createdDate: +Mock.Random.date('T'), // 创建时间
  modifiedBy: '@increment', // 最后修改人
  modifiedDate: +Mock.Random.date('T'), // 最后修改时间
  children: [],
  orderIndex: '',
  parentId: null,
  smallIconPath: '',
  token: ''
}

export const menus = []

function createMenuTree(menu) {
  menu.children = []
  if (Mock.mock('@boolean')) {
    const children = Mock.mock(mockConfig)
    children.parentId = menu.id
    menu.children.push(children)
    createMenuTree(children)
  }
  return menu
}

const length = Mock.mock('@integer(2, 4)')
for (let i = 0; i < length; i++) {
  const menu = Mock.mock(mockConfig)
  menu.parentId = null
  menus.push(createMenuTree(menu))
}
//
// function createMenu(router, parentId, menus) {
//   const menu = {}
//   menu.id = router.name
//   menu.parentId = parentId
//   menu.flag = 1
//   menu.state = 0
//   menu.index = router.meta.index
//   menu.name = router.meta.title
//   menu.icon = router.meta.icon
//   menu.remark = Mock.mock('@cparagraph(1, 3)')
//   menu.createdBy = Mock.mock('@id')
//   menu.createdDate = Mock.mock('@date(\'T\')')
//   menu.modifiedBy = Mock.mock('@id')
//   menu.modifiedDate = Mock.mock('@date(\'T\')')
//
//   if (router.children && router.children.length > 0) {
//     router.children.forEach(children => {
//       createMenu(children, router.name, menus)
//     })
//   }
//   debugger
//   menus.push(menu)
// }
// asyncMenuMap.forEach(router => createMenu(router, null, menus))

export const menusTree = []

// const mockConfig = {
//   menuId: '',
//   url: ''
// }
export const menuUrls = []

// const mockConfig = {
//   menuId: '',
//   roleId: ''
// }
export const roleMenus = []

function findMenuTreeNode(menuId, menu) {
  if (menu.id === menuId) {
    return menu
  }
  if (menu.children && menu.children.length > 0) {
    for (let i = 0; i < menu.children.length; i++) {
      const find = findMenuTreeNode(menuId, menu.children[i])
      if (find !== null) {
        return find
      }
    }
  }
  return null
}

function findMenu(deptId) {
  let menu = null
  for (let i = 0; i < menus.length; i++) {
    menu = findMenuTreeNode(deptId, menus[i])
    if (menu !== null) {
      break
    }
  }
  return menu
}

export default {
  queryAll: config => {
    return {
      code: 1,
      message: '操作成功',
      data: menus
    }
  },
  queryById: config => {
    const params = param2ObjForSplit(config.url)
    const menu = menus[menus.findIndex(item => { return item.id === params.id })]
    return {
      code: 1,
      message: '操作成功',
      data: menu
    }
  },
  add: config => {
    const params = JSON.parse(config.body)
    const menu = Mock.mock(mockConfig)
    params.id = menu.id
    deepMerge(menu, params)

    if (menu.parentId) {
      const find = findMenu(menu.parentId)
      if (!find.children) {
        find.children = []
      }
      find.children.push(menu)
    } else {
      menus.push(menu)
    }

    return {
      code: 1,
      message: '操作成功',
      data: menu
    }
  },
  edit: config => {
    debugger
    const params = JSON.parse(config.body)
    const menu = menus[menus.findIndex(item => { return item.id === params.id })]
    deepMerge(menu, params)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  },
  del: config => {
    debugger
    const params = param2ObjForSplit(config.url)
    let menu = findMenu(params.id)
    if (menu.parentId === null) {
      menus.splice(menus.findIndex(item => { return item.id === params.id }), 1)
    } else {
      menu = findMenu(menu.parentId)
      menu.children.splice(menu.children.findIndex(item => { return item.id === params.id }), 1)
    }
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  },
  // sync: config => {
  //   asyncMenuMap.forEach(router => createMenu(router, null, menus))
  //   return {
  //     code: 1,
  //     message: '操作成功',
  //     data: {}
  //   }
  // },

  queryMenuUrls: config => {
    const params = param2Obj(config.url)
    return {
      code: 1,
      message: '操作成功',
      data: menuUrls.filter(item => { return item.menuId === params.id })
    }
  },
  queryMenuRoles: config => {
    debugger
    const params = param2Obj(config.url)
    if (roleMenus.findIndex(item => { return item.menuId === params.id }) === -1) {
      // 生成几个role
      for (let i = 0; i < 5; i++) {
        const role = Mock.mock(roleMockConfig)
        roles.push(role)
        roleMenus.push({
          menuId: params.id,
          roleId: role.id
        })
      }
    }
    const roleMenusResult = roleMenus.filter(item => { return params.id === item.menuId })
    return {
      code: 1,
      message: '操作成功',
      data: roles.filter(role => {
        return roleMenusResult.findIndex(menuRole => { return role.id === menuRole.roleId }) !== -1
      })
    }
  },

  delMenuRole: config => {
    const params = JSON.parse(config.body)
    roleMenus.splice(roleMenus.findIndex(item => {
      return item.menuId === params.menuId && item.roleId === params.roleId
    }), 1)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  }

}
