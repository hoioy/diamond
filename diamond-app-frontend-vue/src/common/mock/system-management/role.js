import Mock from 'mockjs'
import { param2Obj, deepMerge, deepClone, fieldQueryLike, sortArray } from '@/utils'
import Utils from '../utils'
import { roleMenus } from './menu'
import { mockConfig as userMockConfig, users } from './user'

export const mockConfig = {
  'id|1': Utils.id,
  'flag|1': Utils.flag, // 是否删除
  'state|1': Utils.state, // 是否启用
  index: '@increment', // 序号
  name: '@cword(5, 10)', // 名称
  remark: '@cparagraph(1, 3)', // 描述
  createdBy: '@increment', // 创建人
  createdDate: +Mock.Random.date('T'), // 创建时间
  modifiedBy: '@increment', // 最后修改人
  modifiedDate: +Mock.Random.date('T') // 最后修改时间
}

export const roles = []

for (let i = 0; i < 10; i++) {
  roles.push(Mock.mock(mockConfig))
}
/**
 * {
 *  userId: '',
 *  roleId: ''
 * }
 */
export const userRoles = []

export default {
  queryPage: config => {
    const params = JSON.parse(config.body)
    const query = {}
    params.filters.forEach(filter => {
      query[filter.field] = filter.value
    })
    const queryResult = deepClone(fieldQueryLike(roles, query))
    params.sorts.forEach(sort => {
      // 前端目前无法实现多字段排序，因此排序以最后一个字段为准
      sortArray(queryResult, sort.field, sort.value === 'desc')
    })
    return {
      code: 1,
      message: '',
      data: {
        total: queryResult.length,
        pageSize: params.pageSize,
        page: params.page,
        list: queryResult.slice((params.page - 1) * params.pageSize, params.page * params.pageSize)
      }
    }
  },
  queryAll: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    const query = {}
    params.filters.forEach(filter => {
      query[filter.field] = filter.value
    })
    const queryResult = deepClone(fieldQueryLike(roles, query))
    params.sorts.forEach(sort => {
      // 前端目前无法实现多字段排序，因此排序以最后一个字段为准
      sortArray(queryResult, sort.field, sort.value === 'desc')
    })
    return {
      code: 1,
      message: '操作成功',
      data: queryResult
    }
  },
  queryById: config => {
    // console.log(config)
    const params = param2Obj(config.url)
    const role = roles[roles.findIndex(item => { return item.id === params.id })]
    return {
      code: 1,
      message: '操作成功',
      data: role
    }
  },
  add: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    const role = Mock.mock(mockConfig)
    params.id = role.id
    deepMerge(role, params)
    roles.push(role)
    return {
      code: 1,
      message: '操作成功',
      data: role
    }
  },
  edit: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    const role = roles[roles.findIndex(item => { return item.id === params.id })]
    deepMerge(role, params)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  },
  del: config => {
    // console.log(config)
    const params = param2Obj(config.url)
    roles.splice(roles.findIndex(item => { return item.id === params.id }), 1)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  },

  // ###################################RoleMenu中间表操作MOCK#######################################
  queryAllRoleMenus: config => {
    // console.log(config)
    const params = param2Obj(config.url)
    return {
      code: 1,
      message: '操作成功',
      data: roleMenus.filter(item => { return params.id === item.roleId })
    }
  },
  /**
   * {
   *  roleId: '', //本次操作的roleId
   *  menuIds: [] //新勾选的菜单IDs
   * }
   * @param config
   * @returns {{code: number, message: string, data: ''}}
   */
  resetRoleMenus: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    roleMenus.forEach(roleMenu => {
      roleMenus.splice(roleMenus.findIndex(item => {
        return item.roleId === params.roleId
      }), 1)
    })
    params.menuIds.forEach(menuId => {
      roleMenus.push({
        roleId: params.roleId,
        menuId
      })
    })
    return {
      code: 1,
      message: '操作成功',
      data: ''
    }
  },

  queryAllRoleUsers: config => {
    // console.log(config)
    const params = param2Obj(config.url)
    if (userRoles.findIndex(item => { return item.roleId === params.id }) === -1) {
      // 生成几个user
      for (let i = 0; i < 20; i++) {
        const user = Mock.mock(userMockConfig)
        users.push(user)
        userRoles.push({
          userId: user.id,
          roleId: params.id
        })
      }
    }
    const userRolesResult = userRoles.filter(item => { return item.roleId === params.id })
    return {
      code: 1,
      message: '操作成功',
      data: users.filter(user => {
        return userRolesResult.findIndex(userRole => { return user.id === userRole.userId }) !== -1
      })
    }
  },

  delRoleUser: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    userRoles.splice(userRoles.findIndex(item => {
      return item.userId === params.userId && item.roleId === params.roleId
    }), 1)
    return {
      code: 1,
      message: '操作成功',
      data: ''
    }
  }
}
