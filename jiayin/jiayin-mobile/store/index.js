import Vue from 'vue'
import Vuex from 'vuex'
import authentication from './modules/authentication.js'
import dictionaries from './modules/dictionaries.js'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    authentication,
    dictionaries
  }
})

export default store
