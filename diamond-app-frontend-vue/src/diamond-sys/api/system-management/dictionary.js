import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/dataitem/'

export function queryPageDictionarys(data) {
  return request({
    url: base_url + 'search',
    method: 'post',
    data
  })
}

export function queryAllDictionaries(data) {
  return request({
    url: base_url + 'query/all',
    method: 'post',
    data
  })
}

export function queryAllDictionariesTypeId(data) {
  return request({
    url: base_url + 'query/all',
    method: 'post',
    data
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

export function delDictionaryByIds(data) {
  return request({
    url: base_url + 'deleteByIds',
    method: 'delete',
    data
  })
}

