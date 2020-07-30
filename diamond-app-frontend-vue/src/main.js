import 'core-js/stable'
import 'regenerator-runtime/runtime'
import Vue from 'vue'

import 'normalize.css/normalize.css'
import Element from 'element-ui'
import '@/styles/index.scss' // global css
import '../element-variables.scss'
import '../theme/index.css'

import App from '@/App'
import router from '@/router'
import store from '@/store'

import '@/icons'
import '@/permission'

import components from '@/views/common'
import * as filters from '@/filters'
import { hasPermissionforviews } from '@/utils/permission' // 按钮权限

Vue.use(Element, {
  size: 'small'
})

// 全局注册组件
Object.keys(components).forEach(key => {
  Vue.component(key, components[key])
})

// 全局注册指令
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.config.productionTip = false
// 全局注册检测元素权限方法
Vue.prototype.hasPerm = hasPermissionforviews
new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
