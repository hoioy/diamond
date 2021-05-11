import request from '@/utils/request'

const base_url = '/sample/student'
const base_urlCourse = '/sample/course-student-score'

export function queryPageStudents(data) {
  return request({
    url: base_url + '/search',
    method: 'post',
    data
  })
}

export function add(data) {
  return request({
    url: base_url + '/',
    method: 'post',
    data
  })
}

export function deleteById(id) {
  return request({
    url: base_url + '/' + id,
    method: 'delete'
  })
}

export function deleteByIds(data) {
  return request({
    url: base_url + '/deleteByIds',
    method: 'delete',
    data
  })
}

export function update(data) {
  return request({
    url: base_url + '/',
    method: 'put',
    data
  })
}

export function queryAllCourseStudents(data) {
  return request({
    url: base_urlCourse + '/query',
    method: 'post',
    data
  })
}

export function addCourseStudent(data) {
  return request({
    url: base_urlCourse + '/batchCreate',
    method: 'post',
    data
  })
}

export function updateCourseStudent(data) {
  return request({
    url: base_urlCourse + '/',
    method: 'PUT',
    data
  })
}

export function delCourseStudent(data) {
  return request({
    url: base_urlCourse + '/delete',
    method: 'DELETE',
    data
  })
}
