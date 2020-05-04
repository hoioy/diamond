// import { loginByUsername, logout, getUserInfo, setElements } from '@/api/login'
// import { getToken, setToken, removeToken } from '@/utils/auth'
// import { getRouterFromBack } from '@/api/system-management/menu'
// import { refreshToken } from '@/utils/oauthLoginUtil'

const authentication = {
	state: {
		token: uni.getStorageSync('token'),
		user: {},
		roles: []
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
			  uni.setStorageSync('token', token);
			} else {
			  uni.removeStorageSync('token');
			}
		},
		SET_USER: (state, user) => {
			state.user = user
		},
		CLEAR_USER: (state) => {
			state.user = null
			state.roles = []
		}
	},

  // mutations: {
  //   SET_TOKEN: (state, token) => {
  //     if (token) {
  //       // 使用oauth2第三方登录，存储复杂token结构暂时没有用到，目前只用到了access_token
  //       // andyzhao changed 20191127 使用复杂token格式
  //       if (token instanceof Object) {
  //         // 如果是通过统一认证服务登录
  //         token.create_timestamp = new Date().getTime()// 时间戳，精确到了毫秒
  //       } else {
  //         token = { 'access_token': token, 'create_timestamp': new Date().getTime() }
  //       }

  //       setToken(token)
  //       state.token = token
  //     } else {
  //       removeToken()
  //       state.token = token
  //     }
  //   },
  //   // SET_USER_ROUTER: (state, user_Router) => {
  //   //   state.user_Router = user_Router
  //   // },
  //   // SET_ROLES: (state, roles) => {
  //   //   state.roles = roles
  //   // },
  //   CLEAR_USER: (state) => {
  //     state.user = null
  //     state.roles = []
  //   },
  //   SET_ELEMENTS: (state, elements) => {
  //     state.elements = elements
  //   }
  // },

  actions: {
    // 登录
    LoginSuccess({ commit }, token) {
      return new Promise((resolve, reject) => {
        commit('SET_TOKEN', token)
        commit('CLEAR_USER')
        resolve()
      })
    },
    // // 使用第三方统一认证登录
    // LoginByOauth({ commit }, token) {
    //   return new Promise((resolve, reject) => {
    //     commit('SET_TOKEN', token)
    //     commit('CLEAR_USER')
    //     resolve()
    //   })
    // },
    // 获取用户信息
    GetUserSuccesss({ commit, state },userData) {
      return new Promise((resolve, reject) => {
        commit('SET_USER', userData)
        resolve()
      })
    },
    //  登出
    Logout({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', null)
        commit('CLEAR_USER')
        resolve()
      })
    }
  }
}

export default authentication
