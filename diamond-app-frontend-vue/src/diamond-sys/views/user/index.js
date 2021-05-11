import DiamondUser from './main'

DiamondUser.install = function(Vue) {
  Vue.component(DiamondUser.name, DiamondUser)
}

export default DiamondUser

