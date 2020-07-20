import Cookies from 'js-cookie'
const TokenKey = 'USER_TOKEN'

export function getToken() {
  var token = Cookies.get(TokenKey)
  if (token) {
    var value = JSON.parse(token)
    return value
  }
  return null
}

export function setToken(token) {
  if (token) {
    token = JSON.stringify(token)
  }
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
