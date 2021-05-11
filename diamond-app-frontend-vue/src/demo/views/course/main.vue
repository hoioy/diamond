<template>
  <transition name="fade-transform" mode="out-in">
    <keep-alive>
      <component :is="optionType" :detail="detail" @option-changed="optionChangeHandler"/>
    </keep-alive>
  </transition>
</template>

<script>
import NProgress from 'nprogress'
import query from './query'
import editGrade from './editGrade'

export default {
  components: { query, editGrade },
  data() {
    return {
      optionType: 'query',
      detail: {}
    }
  },
  methods: {
    optionChangeHandler(optionType = 'query', detail = {}) {
      // 进度条组件开始
      NProgress.start()
      this.detail = detail
      this.optionType = optionType
      NProgress.done()
    }
  }
}
</script>
