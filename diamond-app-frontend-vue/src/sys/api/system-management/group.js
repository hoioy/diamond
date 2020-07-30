import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/group/'
const base_group_user_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/group-user/'

export function queryPageGroups(data) {
  return request({
    url: base_url + 'search',
    method: 'post',
    data
  })
}

export function queryAllGroups() {
  return request({
    url: base_url + 'query/all',
    method: 'get'
  })
}

export function queryGroupById(id) {
  return request({
    url: base_url + id,
    method: 'get'
  })
}

export function addGroup(data) {
  return request({
    url: base_url,
    method: 'post',
    data
  })
}

export function editGroup(data) {
  return request({
    url: base_url,
    method: 'put',
    data
  })
}

export function delGroup(id) {
  return request({
    url: base_url + id,
    method: 'delete'
  })
}

export function queryAllGroupUsers(groupId) {
  return request({
    url: base_group_user_url + 'findUsersByGroupId',
    method: 'get',
    params: { groupId }
  })
}

export function delGroupUser(data) {
  return request({
    url: base_group_user_url + 'delete',
    method: 'delete',
    data
  })
}
