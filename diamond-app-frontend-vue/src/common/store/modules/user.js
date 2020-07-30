import { loginByUsername, logout, getUserInfo, setElements } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
// import { getRouterFromBack } from '@/api/system-management/menu'
// import { refreshToken } from '@/utils/oauthLoginUtil'

const user = {
  state: {
    token: getToken(),
    user: null,
    roles: [],
    rolesName: [],
    elements: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      if (token) {
        // 使用oauth2第三方登录，存储复杂token结构暂时没有用到，目前只用到了access_token
        // andyzhao changed 20191127 使用复杂token格式
        if (token instanceof Object) {
          // 如果是通过统一认证服务登录
          token.create_timestamp = new Date().getTime()// 时间戳，精确到了毫秒
        } else {
          token = { 'access_token': token, 'create_timestamp': new Date().getTime() }
        }

        setToken(token)
        state.token = token
      } else {
        removeToken()
        state.token = token
      }
    },
    SET_USER: (state, user) => {
      state.user = user
    },
    CLEAR_USER: (state) => {
      state.user = null
      state.roles = []
    },
    SET_ELEMENTS: (state, elements) => {
      state.elements = elements
    }
  },

  actions: {
    // 用户名登录
    LoginByUsername({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        loginByUsername(userInfo).then(token => {
          commit('SET_TOKEN', token)
          commit('CLEAR_USER')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 使用第三方统一认证登录
    LoginByOauth({ commit }, token) {
      return new Promise((resolve, reject) => {
        commit('SET_TOKEN', token)
        commit('CLEAR_USER')
        resolve()
      })
    },
    // 获取用户信息
    GetUserInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(data => {
          console.info(data)
          if (data) {
            commit('SET_USER', data)
            resolve(data)
          }
        }).catch(error => {
          commit('CLEAR_USER')
          reject(error)
        })
      })
    },
    // 存储用户当前页面元素的权限信息
    SetUsereElements({ commit, state }, { menuId }) {
      return new Promise((resolve, reject) => {
        var menu = {
          menuId: menuId
        }
        setElements(menu).then(data => {
          const datas = data
          commit('SET_ELEMENTS', datas)
          resolve(data)
        }).catch(error => {
          console.info(error)
          // reject(error)
        })
      })
    },
    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', null)
          commit('CLEAR_USER')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', null)
        commit('CLEAR_USER')
        resolve()
      })
    }
  }
}

export default user
