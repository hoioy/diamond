import request from '@folder-outside-request/request'

const base_url = '/sample/schoolgrade/'

export function queryAllClassGrades() {
  return request({
    url: base_url + 'find-all-tree',
    method: 'get'
  })
}

export function queryGradeById(id) {
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

export function editGrade(data) {
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
