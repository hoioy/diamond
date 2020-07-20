import Mock from 'mockjs'
import { deepMerge, param2Obj } from '@/utils'
import { asyncMenuMap } from '@/router'
import { mockConfig as roleMockConfig, roles } from './role'
// import { menusTree } from './menu'
import Utils from '../utils'

export const mockConfig = {
  'id|1': Utils.id,
  'parentId|1': Utils.id, // 父级id
  'flag|1': Utils.flag, // 是否删除
  'state|1': Utils.state, // 是否启用
  index: '@increment', // 菜单序号
  name: '', // 名称
  icon: '', // 图标
  remark: '', // 备注
  createdBy: '', // 创建人
  createdDate: '', // 创建时间
  modifiedBy: '', // 最后修改人
  modifiedDate: '' // 最后修改时间
}

function createMenu(router, parentId, menus) {
  const menu = {}
  menu.id = router.name
  menu.parentId = parentId
  menu.flag = 1
  menu.state = 0
  menu.index = router.meta.index
  menu.name = router.meta.title
  menu.icon = router.meta.icon
  menu.remark = Mock.mock('@cparagraph(1, 3)')
  menu.createdBy = Mock.mock('@id')
  menu.createdDate = Mock.mock('@date(\'T\')')
  menu.modifiedBy = Mock.mock('@id')
  menu.modifiedDate = Mock.mock('@date(\'T\')')

  if (router.children && router.children.length > 0) {
    router.children.forEach(children => {
      createMenu(children, router.name, menus)
    })
  }
  menus.push(menu)
}
export const menusTree = []

export const menus = []

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
  for (let i = 0; i < menusTree.length; i++) {
    menu = findMenuTreeNode(deptId, menusTree[i])
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
    const params = param2Obj(config.url)
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
      menusTree.push(menu)
    }

    return {
      code: 1,
      message: '操作成功',
      data: menu
    }
  },
  edit: config => {
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
    const params = param2Obj(config.url)
    let menu = findMenu(params.id)
    if (menu.parentId === null) {
      menusTree.splice(menusTree.findIndex(item => { return item.id === params.id }), 1)
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
  sync: config => {
    asyncMenuMap.forEach(router => createMenu(router, null, menus))
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  },

  queryMenuUrls: config => {
    const params = param2Obj(config.url)
    return {
      code: 1,
      message: '操作成功',
      data: menuUrls.filter(item => { return item.menuId === params.id })
    }
  },
  queryMenuRoles: config => {
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
