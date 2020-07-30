const getters = {
  sidebar: state => state.app.sidebar,

  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,

  token: state => state.user.token,
  user: state => state.user.user,
  roles: state => state.user.roles,
  elements: state => state.user.elements,

  adminCode: state => state.permission.adminCode,
  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters,

  dictionaries: state => state.dictionaries
}
export default getters
