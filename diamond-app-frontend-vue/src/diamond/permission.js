import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // 进度条组件
import 'nprogress/nprogress.css'// 进度条样式
import { getToken } from '@/utils/auth' // 从cookie中获取用户信息

// 获取组件的方法
import * as MenuAPI from '@/api/system-management/menu'
import defaultLayout from '@/views/layout/default/index'
import Submenu from '@/views/layout/submenu/index'
const _import = require('./router/_import_' + process.env.NODE_ENV)
var getRouter // 用来获取后台拿到的路由

NProgress.configure({ showSpinner: false })// NProgress Configuration

router.beforeEach((to, from, next) => {
  const menuId = to.name
  NProgress.start() // 开始进度条
  if (getToken()) { // 判断是否已存在token信息
    if (to.path.indexOf('/login') === 0) {
      next({ path: '/' })
      NProgress.done() // 部分页面可能不会触发路由afterEach钩子
    } else {
      if (store.getters.user === null) { // 判断当前用户是否已拉取完用户信息
        store.dispatch('GetUserInfo').then(data => { // 拉取用户信息
          if (!data.userMetadata) {
            data.userMetadata = '3C9AFB'
          }
          store.state.themecolor = data.userMetadata
          localStorage.setItem('themecolor', '#' + data.userMetadata)
          if (!getRouter) { // 不加这个判断，路由会陷入死循环
            getRouterFromBack(to, next)
          } else {
            next()
          }
        }).catch((err) => {
          store.dispatch('FedLogOut').then(() => {
            Message.error(err || '登录失败')
            next({ path: '/' })
          })
        })
      } else {
        if (!getRouter) { // 不加这个判断，路由会陷入死循环
          getRouterFromBack(to, next)
        } else {
          if (to.matched.length === 0) {
            NProgress.done()
            next('/404') // 判断此跳转路由的来源路由是否存在，存在的情况跳转到来源路由，否则跳转到404页面
          } else {
            if (menuId && menuId !== 'index') {
              store.dispatch('SetUsereElements', { menuId }).then(data => {
              })// todo
            }
            next()
          }
        }
      }
    }
  } else {
    if (to.path.indexOf('/login') === 0) {
      next()
    } else if (window.location.href.indexOf('/#/ssologin') >= 0) {
      next()
    } else if (window.location.href.indexOf('/ssologin') >= 0) {
      window.location.href = `/#/ssologin` + window.location.search
    } else {
      next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
      NProgress.done() // 防止当前页面与to相同，导致afterEach钩子未触发
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})

function getRouterFromBack(to, next) {
  MenuAPI.getRouterFromBack().then((data) => {
    getRouter = data.router// 后台拿到路由
    routerGo(to, next)// 执行路由跳转方法
  })
}

function routerGo(to, next) {
  getRouter = filterAsyncRouter(getRouter) // 过滤路由
  router.addRoutes(getRouter) // 动态添加路由
  global.antRouter = getRouter // 将路由数据传递给全局变量，做侧边栏菜单渲染工作
  next({ ...to, replace: true })
}

function filterAsyncRouter(asyncRouterMapForPromission) { // 遍历后台传来的路由字符串，转换为组件对象
  const accessedRouters = asyncRouterMapForPromission.filter(route => {
    if (route.component) {
      if (route.component === 'Layout') { // Layout组件特殊处理
        route.component = defaultLayout
      } else if (route.component === 'Submenu') {
        route.component = Submenu
      } else {
        try {
          route.component = _import(route.component)
        } catch (e) {
          route.component = require('@/components/ErrorPage/404.vue').default
          console.log(e)
        }
      }
    }
    if (route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }
    return true
  })

  return accessedRouters
}

