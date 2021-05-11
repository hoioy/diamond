import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/data-item-type/'

export function queryPageDictionaryTypes(data) {
  return request({
    url: base_url + 'search',
    method: 'post',
    data
  })
}

export function queryPageDictionaryTypesAll(data) {
  return request({
    url: base_url + 'search/all/',
    method: 'get',
    data
  })
}

export function addDictionaryType(data) {
  return request({
    url: base_url,
    method: 'post',
    data
  })
}

export function queryDictionaryTypeById(id) {
  return request({
    url: base_url + id,
    method: 'get'
  })
}

export function editDictionaryType(data) {
  return request({
    url: base_url,
    method: 'put',
    data
  })
}

export function delDictionaryType(id) {
  return request({
    url: base_url + id,
    method: 'delete'
  })
}
