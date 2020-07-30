import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/dept/'
const base_dept_user_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/dept-user/'

export function queryAllDepts() {
  return request({
    url: base_url + 'query/all/',
    method: 'get'
  })
}

export function queryAllTreeDepts(id) {
  return request({
    url: base_url + 'query/all/tree/',
    method: 'get'
  })
}

export function queryDeptById(id) {
  return request({
    url: base_url + id,
    method: 'get'
  })
}

export function addDept(data) {
  return request({
    url: base_url,
    method: 'post',
    data
  })
}

export function editDept(data) {
  return request({
    url: base_url,
    method: 'put',
    data
  })
}

export function delDept(id) {
  return request({
    url: base_url + id,
    method: 'delete'
  })
}

export function queryAllDeptUsers(deptInfoId) {
  return request({
    url: base_dept_user_url + 'findUsersByDeptInfoId',
    method: 'get',
    params: { deptInfoId }
  })
}

export function delDeptUser(data) {
  return request({
    url: base_dept_user_url + 'delete',
    method: 'delete',
    data
  })
}
