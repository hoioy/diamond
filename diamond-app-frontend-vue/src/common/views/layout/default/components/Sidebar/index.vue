<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      mode="vertical"
      background-color="#304156"
      text-color="#bfcbd9"
      :active-text-color="curThemeColor"
    >
      <sidebar-item v-for="route in routes" :key="route.name" :item="route" :base-path="route.path"/>
    </el-menu>
    <!--<el-menu :default-active="$route.path" mode="vertical" unique-opened background-color="#304156" text-color="#fff" active-text-color="#409EFF">-->
    <!--<sidebar-item :routes="routes"/>-->
    <!--</el-menu>-->
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'
import { toggleClass2 } from '@/utils/index'

export default {
  components: { SidebarItem },
  data() {
    return {
      curThemeColor: '#3C9AFB'
    }
  },
  computed: {
    ...mapGetters([
      'permission_routers',
      'sidebar'
    ]),
    isCollapse() {
      return !this.sidebar.opened
    },
    routes() {
      return global.antRouter
    },
    themecolor: {
      get() {
        return this.$store.state.themecolor
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
        console.log('55555 ' + this.themecolor)
        this.curThemeColor = '#' + this.themecolor.toString()
        console.log('6666  ' + this.curThemeColor)
      }
    }

  }
}
</script>
