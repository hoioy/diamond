import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import permission from './modules/permission'
import tagsView from './modules/tagsView'
import user from './modules/user'
import dictionaries from './modules/dictionaries'
import getters from './getters'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    themecolor: '3C9AFB'// 默认为3C9AFB
  },
  mutations: {
    // 更新主题颜色
    setThemeColor(state, curcolor) {
      this.state.themecolor = curcolor
    }
  },
  modules: {
    app,
    permission,
    tagsView,
    user,
    dictionaries
  },
  getters
})

export default store
