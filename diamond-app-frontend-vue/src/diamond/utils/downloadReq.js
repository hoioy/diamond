import axios from 'axios'
import store from '@/store'
import Cookies from 'js-cookie'

// 本工程中axios使用，均通过其API构建请求，可参考https://www.kancloud.cn/yunye/axios/234845
const service = axios.create({
  // baseURL: process.env.VUE_APP_BASE_API, // api 的 base_url
  baseURL: process.env.VUE_APP_BASE_API, // api 的 base_url
  timeout: 15000, // request timeout
  responseType: 'blob'
})
// service.defaults.withCredentials = true

// request interceptor
service.interceptors.request.use(
  request => {
    request.headers['Access-Control-Allow-Origin'] = '*'
    // request.headers['Content-Type'] = 'application/json'
    // 后端服务jwt token信息
    var tmpToken = store.getters.token
    if (!tmpToken) {
      tmpToken = Cookies.get('USER_TOKEN')
    }
    if (tmpToken) {
      request.headers['Authorization'] = 'Bearer ' + tmpToken.access_token
    }
    return request
  },
  error => {
    // 请求发生错误
    console.log('request interceptor error') // for debug
    Promise.reject(error)
  }
)

export default service
