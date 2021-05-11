import request from '@folder-outside-request/request'

import 'url-search-params-polyfill'
const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/user/'
const base_role_user_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/role-user/'
const base_dept_user_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/dept-user/'
const base_group_user_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/group-user/'

export function queryPageUsers(data) {
  return request({
    url: base_url + 'search',
    method: 'post',
    data
  })
}

export function queryPageUsersByRole(data) {
  return request({
    url: base_url + 'composite-search',
    method: 'post',
    data
  })
}

export function queryUserById(id) {
  return request({
    // url: 'sys-vue/user-id-temp.json',
    url: base_url + id,
    method: 'get'
  })
}

export function findIdByLoginName(loginName) {
  return request({
    url: base_url + 'findIdByLoginName/',
    method: 'get',
    params: { loginName }
  })
}

export function checkUser(id) {
  return request({
    url: base_url + 'check',
    method: 'get',
    params: { id }
  })
}

export function addUser(data) {
  return request({
    url: base_url,
    method: 'post',
    data
  })
}
export function addUserBatch(data) {
  return request({
    url: base_url + 'batchCreate',
    method: 'post',
    data
  })
}

export function editUser(data) {
  return request({
    url: base_url,
    method: 'put',
    data
  })
}

export function editUserPassword(data) {
  return request({
    url: base_url,
    method: 'put',
    data
  })
}

export function delUser(data) {
  return request({
    url: base_url + 'deleteByIds/',
    method: 'delete',
    data
  })
}

export function queryAllUserRoles(userId) {
  return request({
    url: base_role_user_url + 'findRolesByUserId',
    method: 'get',
    params: { userId }
  })
}

export function addUserRole(data) {
  return request({
    url: base_role_user_url + 'batchCreate',
    method: 'post',
    data
  })
}

export function addUserRoleBatchSave(data) {
  return request({
    url: base_role_user_url + 'batch-save',
    method: 'post',
    data
  })
}

export function delUserRole(data) {
  return request({
    url: base_role_user_url + 'delete',
    method: 'delete',
    data
  })
}

export function findRoleByUserIds(data) {
  return request({
    url: base_role_user_url + 'findByUserIds',
    method: 'post',
    data
  })
}

export function addUserDeptBatchSave(data) {
  return request({
    url: base_dept_user_url + 'batch-save',
    method: 'post',
    data
  })
}

export function findDeptByUserIds(data) {
  return request({
    url: base_dept_user_url + 'findByUserIds',
    method: 'post',
    data
  })
}

export function queryAllUserDepts(userId) {
  return request({
    url: base_dept_user_url + 'findDeptInfosByUserId',
    method: 'get',
    params: { userId }
  })
}

export function addUserDept(data) {
  return request({
    url: base_dept_user_url + 'batchCreate',
    method: 'post',
    data
  })
}

export function delUserDept(data) {
  return request({
    url: base_dept_user_url + 'delete',
    method: 'delete',
    data
  })
}

export function queryAllUserGroups(userId) {
  return request({
    url: base_group_user_url + 'findGroupsByUserId',
    method: 'get',
    params: { userId }
  })
}

export function addUserGroup(data) {
  return request({
    url: base_group_user_url + 'batchCreate',
    method: 'post',
    data
  })
}

export function delUserGroup(data) {
  return request({
    url: base_group_user_url + 'delete',
    method: 'delete',
    data
  })
}

export function avatarUpload(data) {
  return request({
    url: base_url + 'user-upload-avatar-rest',
    method: 'post',
    data
  })
}

export function getAvatar(data) {
  return request({
    url: '/file/upload/' + data,
    method: 'get',
    data
  })
}

