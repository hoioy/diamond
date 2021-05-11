<template>
  <transition name="fade-transform" mode="out-in">
    <keep-alive>
      <component :is="optionType" :detail="detail" @option-changed="optionChangeHandler"/>
    </keep-alive>
  </transition>
</template>

<script>
import NProgress from 'nprogress' // 进度条组件
import query from './query'
import addCourse from './addCourse'

export default {
  components: { query, addCourse },
  data() {
    return {
      optionType: 'query',
      detail: {}
    }
  },
  methods: {
    optionChangeHandler(optionType = 'query', detail = {}) {
      NProgress.start()
      this.detail = detail
      this.optionType = optionType
      NProgress.done()
    }
  }
}
</script>
