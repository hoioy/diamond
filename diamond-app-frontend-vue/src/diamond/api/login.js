import request from '@folder-outside-request/request'

export function captcha(data) {
  return request({
    url: '/captcha',
    method: 'get'
  })
}

export function loginByUsername(data) {
  return request({
    url: '/login' + '?username=' + data.username + '&password=' + data.password + '&captchaKey=' + data.captchaKey + '&captchaCode=' + data.captchaCode,
    method: 'post'
  })
}

export function getUserInfo() {
  return request({
    url: '/web-user-details',
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
  const menuId = data.menuId
  return request({
    url: '/permission/hasPermission',
    method: 'get',
    params: { menuId }
  })
}
