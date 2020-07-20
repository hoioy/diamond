import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/role/'
const base_role_user_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/role-user/'
const base_role_menu_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/role-menu/'

export function queryPageRoles(data) {
  return request({
    url: base_url + 'search',
    method: 'post',
    data
  })
}

export function queryAllRoles(data) {
  return request({
    url: base_url + 'query/all/',
    method: 'get',
    data
  })
}

export function queryRoleById(id) {
  return request({
    url: base_url + id,
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: base_url,
    method: 'post',
    data
  })
}

export function editRole(data) {
  return request({
    url: base_url,
    method: 'put',
    data
  })
}

export function delRole(id) {
  return request({
    url: base_url + id,
    method: 'delete'
  })
}

export function queryAllRoleMenus(roleId) {
  return request({
    url: base_role_menu_url + 'findMenuIdsByRoleId',
    method: 'get',
    params: { roleId }
  })
}

export function resetRoleMenus(data) {
  return request({
    url: base_role_menu_url + 'reset-menus',
    method: 'post',
    data
  })
}

export function queryAllRoleUsers(roleId) {
  return request({
    url: base_role_user_url + 'findUsersByRoleId',
    method: 'get',
    params: { roleId }
  })
}

export function findByUserIds(data) {
  return request({
    url: base_role_user_url + 'findByUserIds',
    method: 'get',
    data
  })
}

export function addRoleUser(data) {
  return request({
    url: base_url,
    method: 'post',
    data
  })
}

export function delRoleUser(data) {
  return request({
    url: base_role_user_url + 'delete',
    method: 'delete',
    data
  })
}
