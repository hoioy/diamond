import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/dataitem/'

export function queryPageDictionarys(data) {
  return request({
    url: base_url + 'search',
    method: 'post',
    data
  })
}

export function queryAllDictionaries(parentId) {
  return request({
    url: base_url + 'query/all',
    method: 'get',
    params: { parentId }
  })
}

export function queryAllDictionariesTypeId(id) {
  return request({
    url: base_url + 'query/all',
    method: 'post',
    params: { id }
  })
}

export function queryDictionaryById(id) {
  return request({
    url: base_url + id,
    method: 'get'
  })
}

export function addDictionary(data) {
  return request({
    url: base_url,
    method: 'post',
    data
  })
}

export function editDictionary(data) {
  return request({
    url: base_url,
    method: 'put',
    data
  })
}

export function delDictionary(id) {
  return request({
    url: base_url + id,
    method: 'delete'
  })
}
