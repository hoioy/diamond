import DiamondLog from './main'

DiamondLog.install = function(Vue) {
  Vue.component(DiamondLog.name, DiamondLog)
}

export default DiamondLog

