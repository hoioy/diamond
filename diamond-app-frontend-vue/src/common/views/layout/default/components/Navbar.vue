<template>
  <div class="navbar">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
    <breadcrumb class="breadcrumb-container"/>
    <div class="right-menu">
      <div class="theme right-menu-item" style="padding-bottom:15px;">
        <theme style="margin-bottom:-20px"></theme>
      </div>
      <el-tooltip content="全屏" effect="dark" placement="bottom">
        <screenfull class="screenfull right-menu-item"/>
      </el-tooltip>
      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <!--<img :src="user.avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">-->
          <img :src="'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'" class="user-avatar">
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <router-link to="/myself/modify">
              <span style="display:block;">个人信息</span>
            </router-link>
          </el-dropdown-item>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Hamburger from '@/components/Hamburger'
import Breadcrumb from '@/components/Breadcrumb'
import Screenfull from '@/components/Screenfull'
import oauthLoginUtil from '@/utils/oauthLoginUtil'
import theme from '@/components/theme/theme'
import '@src/assets/css/theme/00a597/index.css'
import '@src/assets/css/theme/3C9AFB/index.css'
import '@src/assets/css/theme/5959AB/index.css'
import '@src/assets/css/theme/20a0ff/index.css'
import '@src/assets/css/theme/008000/index.css'
import '@src/assets/css/theme/FA4F52/index.css'
import '@src/assets/common.scss'

export default {
  components: {
    Hamburger,
    Breadcrumb,
    Screenfull,
    theme
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'user'
    ])
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    logout() {
      oauthLoginUtil.logout()
      this.$store.dispatch('FedLogOut').then(() => {
        location.reload()
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container{
    float: left;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .changeTheme {
    float: left;
    height: 100%;
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus{
     outline: none;
    }
    .theme{
      height: 50px;
      padding-bottom: 10px;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international{
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 50px;
      margin-right: 15px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;
        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
