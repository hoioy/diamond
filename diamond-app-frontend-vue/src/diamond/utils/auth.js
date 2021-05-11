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
    var bearetoken = token.access_token
    // 在存储token时判断是否带前缀,如果带是自定义生成的token如果不带,oauthserver生成的token 拼接bearer
    var arr = bearetoken.split(' ')
    if (arr.length < 2) {
      token.access_token = 'Bearer ' + token.access_token
    }
    token = JSON.stringify(token)
  }
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
