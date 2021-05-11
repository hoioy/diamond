<template>
  <div class="app-container" style="padding:5px 0 0 0">
    <el-select v-model="themecolor" placeholder="请选择主题颜色" style="width:115px;" @change="themeChange">
      <el-option
        v-for="item in themeColorOptions"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>
  </div>
</template>

<script>
import { toggleClass2 } from '@/utils/index'
import { mapGetters } from 'vuex'
import * as UserAPI from 'diamond-sys/api/system-management/user'

export default {
  data() {
    return {
      tags: [
        { name: '标签一', type: '' },
        { name: '标签二', type: 'gray' },
        { name: '标签三', type: 'primary' },
        { name: '标签四', type: 'success' },
        { name: '标签五', type: 'warning' },
        { name: '标签六', type: 'danger' }
      ],
      themeColorOptions: [{
        value: '3C9AFB',
        label: '主题-蓝色'
      }, {
        value: 'FA4F52',
        label: '主题-橙色'
      }, {
        value: '008000',
        label: '主题-草绿'
      }, {
        value: '5959AB',
        label: '主题-紫色'
      }]
    }
  },
  computed: {
    ...mapGetters([
      'user'
    ]),
    themecolor: {
      get() {
        if (this.$store.state.themecolor) {
          return this.$store.state.themecolor.replace('#', '')// todo
        } else {
          return this.$store.state.themecolor
        }
      },
      set(val) {
        this.$store.commit('setThemeColor', val)
      }
    }
  },
  watch: {
    themecolor: {
      handler() {
        toggleClass2(document.body, 'custom-' + this.themecolor)
      }
    }
  },
  mounted() {
    toggleClass2(document.body, 'custom-' + this.themecolor)
  },
  methods: {
    themeChange(val) {
      localStorage.setItem('themecolor', '#' + val)
      this.user.userMetadata = this.$store.state.themecolor
      UserAPI.editUser(this.user).then(this.optionSuccessHandler)
    }
  }
}
</script>
<style scoped>
    .app-container{
        float: right;
        width: 100%;
        padding: 0 auto
    }
    .box-card{
        width: 400px;
        margin: 20px auto;
    }
    .block{
        padding: 30px 24px;
    }
    .alert-item{
        margin-bottom: 10px;
    }
    .tag-item{
        margin-right: 15px;
    }
    .link-title{
        margin-left:35px;
    }
    .mulColor{
        margin-top: 15px;
        padding-bottom:5px;
    }
</style>

