import axios from 'axios'
import request from '@folder-outside-request/request'

import querystring from 'querystring'
import { removeToken } from './auth'
const Base64 = require('js-base64').Base64

// 判断一下在开发环境使用vue配置的环境，生产环境要后面可以改的，所以生产环境从外部js取值
const userAuthorizationUri = process.env.NODE_ENV === 'development' ? process.env.VUE_APP_OAUTH_userAuthorizationUri : window.__config.VUE_APP_OAUTH_userAuthorizationUri
const redirect_uri = process.env.NODE_ENV === 'development' ? process.env.VUE_APP_OAUTH_redirect_uri : window.__config.VUE_APP_OAUTH_redirect_uri
const accessTokenUri = process.env.NODE_ENV === 'development' ? process.env.VUE_APP_OAUTH_accessTokenUri : window.__config.VUE_APP_OAUTH_accessTokenUri
const userInfoUri = process.env.NODE_ENV === 'development' ? process.env.VUE_APP_OAUTH_userInfoUri : window.__config.VUE_APP_OAUTH_userInfoUri
const logoutUri = process.env.NODE_ENV === 'development' ? process.env.VUE_APP_OAUTH_logoutUri : window.__config.VUE_APP_OAUTH_logoutUri

// oauth2单点登录方法
var oauthLoginUtil = {
  getCode: function() {
    // TODO andyzhao code没有使用
    // process.env.oauth.code = RndNum(4)
    // 浏览器端重定向到统一认证服务地址，获取授权码
    window.location.href = userAuthorizationUri + ('?' + querystring.stringify({
      client_id: process.env.VUE_APP_OAUTH_clientId,
      response_type: process.env.VUE_APP_OAUTH_response_type,
      scope: process.env.VUE_APP_OAUTH_scope,
      state: process.env.VUE_APP_OAUTH_state,
      redirect_uri: redirect_uri
    }))
  },
  getToken: function(code, callback, error) {
    const baseStr = Base64.encode(process.env.VUE_APP_OAUTH_clientId + ':' + process.env.VUE_APP_OAUTH_client_secret)
    const tokenUrlStr = '?' + 'client_id=' + process.env.VUE_APP_OAUTH_clientId +
      '&' + 'client_secret=' + process.env.VUE_APP_OAUTH_client_secret +
      '&' + 'code=' + code +
      '&' + 'redirect_uri=' + redirect_uri +
      '&' + 'grant_type=' + process.env.VUE_APP_OAUTH_grant_type

    axios.post(accessTokenUri + tokenUrlStr, null, {
      headers: {
        'Accept': 'application/json',
        // "Accept": "application/json,application/x-www-form-urlencoded",
        // "Content-type": "application/x-www-form-urlencoded;charset=UTF-8",
        'authorization': 'Basic ' + baseStr }
    }).then(callback).catch(error)
  },
  getUserInfo: function(token, callback, error) {
    axios({
      url: userInfoUri + '?' + 'access_token=' + token,
      headers: { 'Accept': 'application/json' }
    }).then(callback).catch(error)
  },
  bindOAuth2User: function(data) {
    return request({
      url: '/bindOAuth2User',
      method: 'post',
      data
    })
  },
  logout: function(callBack) { // 注销
    // 1. 调用认证中心退出接口
    return axios.get(logoutUri, {
      withCredentials: true // 正确写法
    }).then((data) => {
      if (data.data.success) {
        // 2. 删除access_token
        removeToken()
        if (callBack) {
          callBack(data)
        }
      } else {
        alert('退出失败：' + JSON.stringify(data))
      }
    })
  }
  // logout:function (vue,return_url) {
  //   vue.$router.push('/logout'+"?return_url="+return_url)
  // },
  // checkLogin:function(vue){
  //   var tokenInfo = vue.$token.loadToken();
  //   if (tokenInfo.access_token===null ||tokenInfo.access_token==="null" ){
  //     return false;
  //   }
  //   return true;
  // }
}

export default oauthLoginUtil

// export function refreshToken(refresh_token) {
//   const oauthKey = Base64.encode(process.env.VUE_APP_OAUTH_clientId + ':' + process.env.VUE_APP_OAUTH_client_secret)
//   // redirect_uri不放在qs中序列化，转码导致请求400，所以放在下面拼接
//   const refreshTokenUrlParam = '?' + 'refresh_token=' + refresh_token +
//       '&' + 'grant_type=refresh_token'
//   return axios.post(process.env.VUE_APP_OAUTH_accessTokenUri + refreshTokenUrlParam
//     , null, {
//       headers: {
//         Accept: 'application/json',
//         Authorization: 'Basic ' + oauthKey
//       }
//     })
// }

export function refreshToken(refresh_token) {
  const oauthKey = Base64.encode(process.env.VUE_APP_OAUTH_clientId + ':' + process.env.VUE_APP_OAUTH_client_secret)
  // redirect_uri不放在qs中序列化，转码导致请求400，所以放在下面拼接
  const refreshTokenUrlParam = '?' + 'refresh_token=' + refresh_token +
    '&' + 'grant_type=refresh_token'
  // 解决refresh token过期问题
  const refreshTokenReturn = axios.post(accessTokenUri + refreshTokenUrlParam
    , null, {
      headers: {
        Accept: 'application/json',
        Authorization: 'Basic ' + oauthKey
      }
    }).then(data => {
    return data
  }, error => { // http status不是2xx的视为error
    console.log('error', error)
    removeToken()
    // 重定向到首页
    window.location.href = '/'
  })
  return refreshTokenReturn
}
