import axios from 'axios'
import { Message, MessageBox } from 'element-ui'
import store from '@/store'
import { refreshToken } from '@/utils/oauthLoginUtil'
import Cookies from 'js-cookie'

// 本工程中axios使用，均通过其API构建请求，可参考https://www.kancloud.cn/yunye/axios/234845
const service = axios.create({
  retry: 2,
  baseURL: process.env.NODE_ENV === 'development' ? process.env.VUE_APP_BASE_API : window.__config.VUE_APP_BASE_API,
  timeout: 50000, // request timeout,
  retryDelay: 1000
})

// request interceptor
service.interceptors.request.use(
  async request => {
    request.headers['Access-Control-Allow-Origin'] = '*'
    // 后端服务jwt token信息
    var tmpToken = store.getters.token
    if (!tmpToken) {
      tmpToken = Cookies.get('USER_TOKEN')
    }
    if (tmpToken) {
      request.headers['Authorization'] = tmpToken.access_token
    }
    return request
  },
  error => {
    // 请求发生错误
    console.log('request interceptor error') // for debug
    Promise.reject(error)
  }
)
// {
//   code: 1, //1：正确结果，2：回话过期，3：非法回话，4：权限不足，5：未知错误，200：正确，500：错误，401：权限问题
//   message: 后端错误信息
//   data: 后端正常处理结果封装
// }
// response interceptor
service.interceptors.response.use(
  async response => {
    var config = response.config
    // 如果配置不存在或未设置重试选项，则拒绝
    // if (!config || !config.retry) return Promise.reject(response)
    // config.__retryCount = config.__retryCount || 0
    // 判断是否超过总重试次数
    // if (config.__retryCount >= config.retry) {
    // 返回错误并退出自动重试
    // console.info('超出重试次数')
    // return Promise.reject('error')// TODO,需要优化
    // }
    // 增加重试次数
    // config.__retryCount += 1
    const res = response.data
    if (res.code !== 1 && res.code !== 200) {
      if (res.code === 2 || res.code === 3 || res.code === 500) {
        if (store.getters.token) {
          var tmpToken = store.getters.token
          // 第三方登录的refreshtoken
          if (tmpToken.refresh_token) {
            var response_retry
            await refreshToken(tmpToken.refresh_token).then(async data => {
              store.dispatch('LoginByOauth', data.data)
              console.info('重试')
              await service(config).then(data => {
                response_retry = data
              })// 为了重新刷新出刚才失败的接口
            })
            return response_retry
          } else {
            MessageBox.confirm('登录失效，你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
              confirmButtonText: '重新登录',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              console.info('retry-1')
              store.dispatch('FedLogOut').then(() => {
                location.reload() // 为了重新实例化vue-router对象 避免bug
              })
            })
            if (res.message) {
              Message({
                message: res.message,
                type: 'error',
                duration: 3 * 1000
              })
            }
          }
        } else {
          console.info('no token')
          MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            console.info('no token reload')
            store.dispatch('FedLogOut').then(() => {
              location.reload() // 为了重新实例化vue-router对象 避免bug
            })
          })
          if (res.message) {
            Message({
              message: res.message,
              type: 'error',
              duration: 3 * 1000
            })
          }
          return Promise.reject('error')
        }
      } else {
        console.info('other-error')
        if (res.message) {
          Message({
            message: res.message,
            type: 'error',
            duration: 3 * 1000
          })
        }
      }
    } else {
      return res.data
    }
  },
  async error => {
    console.info('status error')
    if (error && error.response) {
      switch (error.response.status) {
        case 401:// 登陆失效
          var tmpToken = store.getters.token
          if (!tmpToken) {
            tmpToken = Cookies.get('USER_TOKEN')
          }
          if (tmpToken.refresh_token) {
            await refreshToken(tmpToken.refresh_token).then(data => {
              store.dispatch('LoginByOauth', data.data).then(location.reload())// 为了重新刷新出刚才失败的接口)
            })
          } else {
            console.info('error-refresh')
            MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出', {
              confirmButtonText: '重新登录',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              console.info('error-refresh-reload')
              store.dispatch('FedLogOut').then(() => {
                location.reload() // 为了重新实例化vue-router对象 避免bug
              })
            })
            error.message = '登陆失效'
          }
          break
        case 500:
          // error.message = (error.response.data.message).split('####')[0] // 和后台的约定，从后台读取错误信息展示
          error.message = (error.response.data.message) // 和后台的约定，从后台读取错误信息展示
          break
      }
    }
    Message({
      message: error.message,
      type: 'error',
      duration: 3 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
