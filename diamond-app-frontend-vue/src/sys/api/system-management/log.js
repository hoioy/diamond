import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/sys/log/'

export function queryPageLogs(data) {
  return request({
    url: base_url + 'search',
    method: 'post',
    data
  })
}

export function delLog(data) {
  return request({
    url: base_url + 'deleteByIds',
    method: 'delete',
    data
  })
}

