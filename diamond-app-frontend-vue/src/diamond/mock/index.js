import Mock from 'mockjs'
import loginAPI from './login'

import groupAPI from './system-management/group'
import userAPI from './system-management/user'
import roleAPI from './system-management/role'
import deptAPI from './system-management/dept'
import dictionaryAPI from './system-management/dictionary'
import dataItemTypeAPI from './system-management/dataItemType'
import menuAPI from './system-management/menu'
import logAPI from './system-management/log'

// 修复在使用 MockJS 情况下，设置 withCredentials = true，且未被拦截的跨域请求丢失 Cookies 的问题
// https://github.com/nuysoft/Mock/issues/300
Mock.XHR.prototype.proxy_send = Mock.XHR.prototype.send
Mock.XHR.prototype.send = function() {
  if (this.custom.xhr) {
    this.custom.xhr.withCredentials = this.withCredentials || false
  }
  this.proxy_send(...arguments)
}

Mock.setup({
  timeout: '350-600'
})

// 测试通过

// 登录相关
Mock.mock(/\/captcha/, 'get', loginAPI.captcha)
Mock.mock(/\/auth/, 'post', loginAPI.auth)
Mock.mock(/\/logout/, 'get', loginAPI.logout)
Mock.mock(/\/user-details/, 'get', loginAPI.userdetails)
Mock.mock(/\/sys\/menu\/query\/all\/tree/, 'get', loginAPI.tree)
Mock.mock(/\/permission\/hasPermission/, 'get', loginAPI.hasPermission)

//  系统设置相关---菜单
Mock.mock(/\/sys\/menu\/query\/all/, 'get', menuAPI.queryAll)

//  系统设置相关---用户分组
Mock.mock(/\/sys\/group\/query\/all/, 'post', groupAPI.queryAll)
Mock.mock(/\/sys\/group\/query-page/, 'post', groupAPI.queryPage)
Mock.mock(/\/sys\/group\/query-by-id/, 'get', groupAPI.queryById)
Mock.mock(/\/sys\/group\/edit/, 'post', groupAPI.edit)
Mock.mock(/\/sys\/group\/del/, 'get', groupAPI.del)
Mock.mock(/\/sys\/group\/all-users/, 'get', groupAPI.queryAllGroupUsers)
Mock.mock(/\/sys\/group\/del-user/, 'post', groupAPI.delGroupUser)

Mock.mock(/\/sys\/group/, 'put', groupAPI.edit)
Mock.mock(/\/sys\/group\/search/, 'post', groupAPI.search)
Mock.mock(/\/sys\/group/, 'post', groupAPI.add)
Mock.mock(/\/sys\/group/, 'get', groupAPI.queryById)
Mock.mock(/\/sys\/group-user\/findUsersByGroupId/, 'get', groupAPI.queryAllGroupUsers)
Mock.mock(/\/sys\/group/, 'delete', groupAPI.del)

//  系统设置相关---用户
Mock.mock(/\/sys\/user\/query-page/, 'post', userAPI.queryPage)
Mock.mock(/\/sys\/user\/query-by-id/, 'get', userAPI.queryById)
Mock.mock(/\/sys\/user\/add-group/, 'post', userAPI.addUserGroup)
Mock.mock(/\/sys\/user\/add-dept/, 'post', userAPI.addUserDept)
Mock.mock(/\/sys\/user\/add-role/, 'post', userAPI.addUserRole)
Mock.mock(/\/sys\/user\/check-login-name/, 'post', userAPI.checkLoginName)
// Mock.mock(/\/sys\/user\/add/, 'post', userAPI.add)
// Mock.mock(/\/sys\/user\/edit/, 'post', userAPI.edit)
Mock.mock(/\/sys\/user\/edit-password/, 'post', userAPI.editUserPassword)
Mock.mock(/\/sys\/user\/del-role/, 'post', userAPI.delUserRole)
Mock.mock(/\/sys\/user\/del-dept/, 'post', userAPI.delUserDept)
Mock.mock(/\/sys\/user\/del-group/, 'post', userAPI.delUserGroup)
Mock.mock(/\/sys\/user\/del/, 'get', userAPI.del)
Mock.mock(/\/sys\/user\/all-roles/, 'get', userAPI.queryAllUserRoles)
Mock.mock(/\/sys\/user\/all-depts/, 'get', userAPI.queryAllUserDepts)
Mock.mock(/\/sys\/user\/all-groups/, 'get', userAPI.queryAllUserGroups)

Mock.mock(/\/sys\/user\/search/, 'post', userAPI.search)
Mock.mock(/\/sys\/user\/findIdByLoginName/, 'get', userAPI.search)
Mock.mock(/\/sys\/user/, 'post', userAPI.add)
Mock.mock(/\/sys\/user/, 'get', userAPI.queryById)
Mock.mock(/\/sys\/user/, 'delete', userAPI.del)
Mock.mock(/\/sys\/user/, 'put', userAPI.edit)
Mock.mock(/\/sys\/user\/composite-search/, 'post', userAPI.search)
Mock.mock(/\/sys\/role-user\/findRolesByUserId/, 'get', userAPI.queryAllUserRoles)
Mock.mock(/\/sys\/dept-user\/findDeptInfosByUserId/, 'get', userAPI.queryAllUserDepts)
Mock.mock(/\/sys\/group-user\/findGroupsByUserId/, 'get', userAPI.queryAllUserGroups)
Mock.mock(/\/sys\/dept-user\/findByUserIds/, 'post', userAPI.queryAllUserDepts)

//  系统设置相关---角色
Mock.mock(/\/sys\/role\/query\/all/, 'post', roleAPI.queryAll)
Mock.mock(/\/sys\/role\/query-page/, 'post', roleAPI.queryPage)
Mock.mock(/\/sys\/role\/query-by-id/, 'get', roleAPI.queryById)
Mock.mock(/\/sys\/role\/add/, 'post', roleAPI.add)
Mock.mock(/\/sys\/role\/edit/, 'post', roleAPI.edit)
Mock.mock(/\/sys\/role\/del/, 'get', roleAPI.del)
Mock.mock(/\/sys\/role\/all-menus/, 'get', roleAPI.queryAllRoleMenus)
Mock.mock(/\/sys\/role\/reset-menus/, 'post', roleAPI.resetRoleMenus)
Mock.mock(/\/sys\/role\/all-users/, 'get', roleAPI.queryAllRoleUsers)
Mock.mock(/\/sys\/role\/del-user/, 'post', roleAPI.delRoleUser)

Mock.mock(/\/sys\/role\/search/, 'post', roleAPI.search)
Mock.mock(/\/sys\/role\/search/, 'post', roleAPI.search)
Mock.mock(/\/sys\/role\/findIdByLoginName/, 'get', roleAPI.search)
Mock.mock(/\/sys\/role/, 'post', roleAPI.add)
Mock.mock(/\/sys\/role/, 'get', roleAPI.queryById)
Mock.mock(/\/sys\/role/, 'delete', roleAPI.del)
Mock.mock(/\/sys\/role/, 'put', roleAPI.edit)

//  系统设置相关---日志
Mock.mock(/\/sys\/log\/query\/all/, 'post', logAPI.queryAll)
Mock.mock(/\/sys\/log\/query-page/, 'post', logAPI.queryPage)
Mock.mock(/\/sys\/log\/deleteByIds/, 'delete', logAPI.del)

Mock.mock(/\/sys\/log\/search/, 'post', logAPI.queryPage)

//  系统设置相关---部门
Mock.mock(/\/sys\/dept\/query\/all\/tree/, 'get', deptAPI.queryAllTree)
Mock.mock(/\/sys\/dept\/query\/all/, 'post', deptAPI.queryAll)
Mock.mock(/\/sys\/dept\/query-by-id/, 'get', deptAPI.queryById)
Mock.mock(/\/sys\/dept\/add/, 'post', deptAPI.add)
Mock.mock(/\/sys\/dept\/edit/, 'post', deptAPI.edit)
Mock.mock(/\/sys\/dept\/del/, 'get', deptAPI.del)
Mock.mock(/\/sys\/dept\/all-users/, 'get', deptAPI.queryAllDeptUsers)
Mock.mock(/\/sys\/dept\/del-user/, 'post', deptAPI.delDeptUser)

Mock.mock(/\/sys\/dept\/query\/all/, 'get', deptAPI.queryAll)
Mock.mock(/\/sys\/dept\/query\/all\/tree/, 'get', deptAPI.queryAllTree)
Mock.mock(/\/sys\/dept/, 'put', deptAPI.edit)
Mock.mock(/\/sys\/dept\/search/, 'post', deptAPI.search)
Mock.mock(/\/sys\/dept/, 'post', deptAPI.add)
Mock.mock(/\/sys\/dept/, 'get', deptAPI.queryById)
Mock.mock(/\/sys\/dept/, 'delete', deptAPI.del)

//  系统设置相关---字典
Mock.mock(/\/sys\/dictionary\/query\/all/, 'post', dictionaryAPI.queryAll)
Mock.mock(/\/sys\/dictionary\/query-page/, 'post', dictionaryAPI.queryPage)
Mock.mock(/\/sys\/dictionary\/query-by-id/, 'get', dictionaryAPI.queryById)
Mock.mock(/\/sys\/dictionary\/add/, 'post', dictionaryAPI.add)
Mock.mock(/\/sys\/dictionary\/edit/, 'post', dictionaryAPI.edit)
Mock.mock(/\/sys\/dictionary\/del/, 'get', dictionaryAPI.del)

Mock.mock(/\/sys\/dataitem\/query\/all/, 'post', dictionaryAPI.queryAll)

Mock.mock(/\/sys\/data-item-type\/search\/all/, 'get', dataItemTypeAPI.queryAll)
Mock.mock(/\/sys\/data-item-type\/search/, 'post', dataItemTypeAPI.queryPage)
Mock.mock(/\/sys\/data-item-type/, 'put', dataItemTypeAPI.edit)
Mock.mock(/\/sys\/data-item-type/, 'post', dataItemTypeAPI.add)
Mock.mock(/\/sys\/data-item-type/, 'get', dataItemTypeAPI.queryById)
Mock.mock(/\/sys\/data-item-type/, 'delete', dataItemTypeAPI.del)

//  系统设置相关---菜单
Mock.mock(/\/sys\/menu\/query\/all/, 'post', menuAPI.queryAll)
Mock.mock(/\/sys\/menu\/query-by-id/, 'get', menuAPI.queryById)
Mock.mock(/\/sys\/menu\/edit/, 'post', menuAPI.edit)
Mock.mock(/\/sys\/menu\/sync/, 'post', menuAPI.sync)
Mock.mock(/\/sys\/menu\/add/, 'post', menuAPI.add)
Mock.mock(/\/sys\/menu\/del/, 'get', menuAPI.del)
Mock.mock(/\/sys\/menu\/query\/all/, 'post', menuAPI.queryAll)
Mock.mock(/\/sys\/menu\/query-by-id/, 'get', menuAPI.queryById)
Mock.mock(/\/sys\/menu\/edit/, 'post', menuAPI.edit)
Mock.mock(/\/sys\/menu\/sync/, 'post', menuAPI.sync)
Mock.mock(/\/sys\/menu\/all-urls/, 'get', menuAPI.queryMenuUrls)
Mock.mock(/\/sys\/menu\/add-url/, 'post', menuAPI.addMenuUrl)
Mock.mock(/\/sys\/menu\/del-url/, 'post', menuAPI.delMenuUrl)
Mock.mock(/\/sys\/menu\/all-roles/, 'get', menuAPI.queryMenuRoles)
Mock.mock(/\/sys\/menu\/del-role/, 'post', menuAPI.delMenuRole)

//
// Mock.mock(/\/sys\/menu\/query\/all/, 'get', menuAPI.queryAll)
// Mock.mock(/\/sys\/menu\/query\/all\/tree/, 'get', menuAPI.queryAllTree)
Mock.mock(/\/sys\/menu/, 'put', menuAPI.edit)
// Mock.mock(/\/sys\/menu\/search/, 'post', menuAPI.search)
Mock.mock(/\/sys\/menu/, 'post', menuAPI.add)
Mock.mock(/\/sys\/menu/, 'get', menuAPI.queryById)
Mock.mock(/\/sys\/role-menu\/findRolesByMenuId/, 'get', menuAPI.queryMenuRoles)

Mock.mock(/\/sys\/menu/, 'delete', menuAPI.del)

export default Mock
