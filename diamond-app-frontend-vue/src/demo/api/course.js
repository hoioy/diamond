import request from '@/utils/request'

const base_url = '/sample/course'

export function queryPageCourses(data) {
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


