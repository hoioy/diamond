import request from '@folder-outside-request/request'

const base_url = process.env.VUE_APP_BASE_API_PREFIX + '/file/'

export function upload(data) {
  return request({
    url: base_url + 'upload',
    method: 'post',
    data
  })
}
