// import { constantRouterMap } from '@/router'
// import { initRouterRoles } from '@/utils/auth'
// import { getRouterRoles } from '@/api/login'
// import { getToken } from '@/utils/auth'
//
// const permission = {
//   state: {
//     token: getToken(),
//     adminCode: 'ROLE_ADMIN---',
//     routers: constantRouterMap,
//     addRouters: []
//   },
//   mutations: {
//     // SET_ROUTERS: (state, routers) => {
//     //   state.routers = constantRouterMap.concat(routers)
//     //   state.addRouters = routers
//     // }
//   },
//   actions: {
//     // GenerateRoutes({ commit, state }, data) {
//     //   return new Promise(resolve => {
//     //     getRouterRoles(getToken().access_token).then(routerRoles => {
//     //       const routerRolesMap = new Map(routerRoles)
//     //       var tempRouter
//     //       tempRouter = global.beforeRouter
//     //       tempRouter.forEach(module => {
//     //         initRouterRoles(module, routerRolesMap)
//     //       })
//     //       // 将未知路由归置到404页面
//     //       tempRouter.push({ path: '*', redirect: '/404', hidden: true })
//     //       commit('SET_ROUTERS', tempRouter)
//     //       resolve()
//     //     })
//     //     // }
//     //   })
//     // }
//   }
// }
//
// export default permission
