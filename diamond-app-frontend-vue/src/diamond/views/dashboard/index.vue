<template>
  <div class="dashboard-container">
    <el-container style="margin:0">
      <el-main>
        <el-row style="background: #f5f7fa;margin-bottom: 30px;padding-top:30px">
          <el-col
            style="text-align: center"
            :xs="24"
            :sm="24"
            :lg="24"
          >
            <h2>
              Diamond开发框架
            </h2>
          </el-col>
          <el-col
            style="text-align: center;margin-bottom: 35px"
            :xs="24"
            :sm="24"
            :lg="24"
          >
            <p style="margin:10px 30px;line-height:30px">
              Diamond开发框架, 主要提供用户、用户组、角色、权限、机构、菜单、数据字典、日志等通用系统功能。
            </p>
            <el-button type="primary" plain>
              <a href="http://diamonddoc.hoioy.com/" target="_blank">Diamond 开发文档</a>
            </el-button>
            <el-button plain style="width: 125px;margin-left:35px">
              <a href="https://gitee.com/hoioy/diamond" target="_blank">源码地址</a>
            </el-button>
          </el-col>
        </el-row>
        <el-row style="padding-left:37px">
          <div class="client-item-content">
            <client-item
              v-for="item in clientList"
              :key="item.clientId"
              :base-url="item.baseUrl"
              :image="item.icon"
              :custom-name="item.customName"
              :custom-sub-name="item.customSubName"
            />
          </div>
        </el-row>
      </el-main>
      <!--<el-footer>-->
      <!--Footer-->
      <!--</el-footer>-->
    </el-container>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { deepMerge } from '@/utils'
import BaseQueryPageForm from '@/views/common/mixins/BaseQueryPageForm'
import ClientItem from './components/ClientItem'
export default {
  name: 'Dashboard',
  components: { ClientItem },
  mixins: [BaseQueryPageForm],
  data() {
    const queryCriteria = this.initQueryCriteria()
    return {
      currentRole: 'adminDashboard',
      clientList: [],
      selectTable: {},
      queryCriteria: queryCriteria,
      selected: null,
      selectedOauthClients: []
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },

  methods: {
    initQueryCriteria(form = {}) {
      return deepMerge(form, {
        clientId: '',
        customName: '',
        authorizedGrantTypes: ''
      })
    }

  }
}
</script>

<style lang="scss" scoped>
  .client-item-content{
    margin-left:5px
  }
</style>
