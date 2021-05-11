<template>
  <div/>
</template>

<script>
import oauthLoginUtil from '@/utils/oauthLoginUtil'

export default {
  name: 'OauthLogin',
  data() {
    return {
      state: '',
      code: ''
    }
  },
  mounted: function() {
    this.code = this.$route.query.code
    this.state = this.$route.query.state
    this.loginByOauth()
  },
  methods: {
    loginByOauth: function() {
      oauthLoginUtil.getToken(this.code, (response) => {
        // 使用token拉去用户信息
        oauthLoginUtil.getUserInfo(response.data.access_token, (userResponse) => {
          // 获得统一认证中心的用户信息
          // 与本系统用户绑定
          // TODO andyzhao 流程优化
          oauthLoginUtil.bindOAuth2User(userResponse.data).then(() => {
            // 保存token
            this.$store.dispatch('LoginByOauth', response.data).then(() => {
              // 绑定成功后,跳转到主页
              this.$router.push({ path: this.redirect || '/' })
            })
          }, function(error) {
            alert(error)
          })
        }, function(error) {
          alert(error)
        })
      }, function(error) {
        alert(error)
      })
    }
  }
}
</script>
