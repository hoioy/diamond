import request from '@folder-outside-request/request'

export function captcha(data) {
  return request({
    url: '/captcha',
    method: 'get'
  })
}

export function loginByUsername(data) {
  return request({
    url: '/auth',
    method: 'post',
    data
  })
}

export function getUserInfo() {
  return request({
    url: '/user-details',
    method: 'get'
    // , params: { token }
  })
}

export function getRouterRoles(token) {
  return request({
    url: '/routers',
    method: 'get'
    // , params: { token }
  })
}

export function logout(token) {
  return request({
    url: '/logout',
    method: 'post'
    // , params: { token }
  })
}
export function setElements(data) {
  const menuId = data.menuId ;
  return request({
    url: '/permission/hasPermission',
    method: 'get',
    params: { menuId }
  })
}
