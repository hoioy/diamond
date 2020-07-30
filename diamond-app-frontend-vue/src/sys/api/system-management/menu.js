import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/menu/'
const base_role_menu_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/role-menu/'
// ####################################Menu主表API####################################
export function queryAllMenus(data) {
  return request({
    url: base_url + 'query/all/',
    method: 'get',
    data
  })
}
export function queryAllNoTreeList(data) {
  return request({
    // url: base_url + 'query/all-NoTreeList',
    url: base_url + 'query/all/',
    method: 'get',
    data
  })
}

export function queryMenuById(id) {
  return request({
    url: base_url + id,
    method: 'get'
  })
}
export function addMenu(data) {
  return request({
    url: base_url,
    method: 'post',
    data
  })
}

export function editMenu(data) {
  return request({
    url: base_url,
    method: 'put',
    data
  })
}

export function delMenu(id) {
  return request({
    url: base_url + id,
    method: 'delete'
  })
}

// ####################################MenuURL中间表API####################################
export function queryAllMenuUrl(params) {
  return request({
    url: base_url + 'all-urls',
    method: 'get',
    params
  })
}

// ####################################RoleMenu中间表API####################################
export function queryAllMenuRole(menuId) {
  return request({
    url: base_role_menu_url + 'findRolesByMenuId',
    method: 'get',
    params: { menuId }
  })
}

export function delMenuRole(data) {
  return request({
    url: base_role_menu_url + 'delete',
    method: 'delete',
    data
  })
}
