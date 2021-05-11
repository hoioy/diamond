import request from '@folder-outside-request/request'

const base_url = '/sys/menu/'

// ####################################Menu主表API####################################
export function getRouterFromBack() {
  return request({
    url: base_url + 'query/all/tree',
    method: 'get'
  })
}
