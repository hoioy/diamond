<template>
  <div>
    <el-button icon="el-icon-back" round @click="$emit('option-changed')">
      返回
    </el-button>
    <el-collapse value="base-info" accordion>
      <el-collapse-item title="基本信息" name="base-info">
        <el-form :model="detail" :label-width="labelWidth">
          <el-row>
            <el-col :span="12">
              <input-item-view :label-width="labelWidth" label="ID">
                <el-input v-model="detail.id" disabled/>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="用户排序号">
                <el-input v-model="detail.userIndex" disabled/>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="登录ID">
                <el-input v-model="detail.loginName" disabled/>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="用户姓名">
                <el-input v-model="detail.userName" disabled/>
              </input-item-view>
            </el-col>
            <el-col :span="12">
              <input-item-view :label-width="labelWidth" label="肖像">
                <img v-if="detail.avatar" :src="defaultIcon+detail.avatar" height="200" width="200" class="avatar">
              </input-item-view>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <input-item-view :label-width="labelWidth" label="昵称">
                <el-input v-model="detail.nickname" disabled/>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="证件号码">
                <el-input v-model="detail.idNumber" disabled/>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="性别">
                <el-col>
                  <div v-if="detail.gender === '0'">
                    女
                  </div>
                  <div v-if="detail.gender === '1'">
                    男
                  </div>
                </el-col>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="生日">
                <el-input v-model="birthday" disabled/>
              </input-item-view>
            </el-col>
            <el-col :span="12">
              <input-item-view :label-width="labelWidth" label="电话">
                <el-input v-model="detail.phoneNum" disabled/>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="邮箱">
                <el-input v-model="detail.email" disabled/>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="住址">
                <el-input v-model="detail.address" disabled/>
              </input-item-view>
              <input-item-view :label-width="labelWidth" label="标签">
                <el-input v-model="detail.tag" disabled/>
              </input-item-view>
            </el-col>
            <el-col :span="24">
              <text-item-view :label-width="labelWidth" label="用户备注">
                <el-input v-model="detail.remark" disabled/>
              </text-item-view>
            </el-col>
          </el-row>
        </el-form>
      </el-collapse-item>
      <el-collapse-item title="用户分组" name="user-group">
        <el-table :data="groups" border style="width: 100%">
          <el-table-column :show-overflow-tooltip="true" prop="groupName" label="名称"/>
          <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注"/>
          <el-table-column prop="createdDate" label="创建时间" width="180" align="center">
            <template slot-scope="scope">
              {{ scope.row.createdDate | parseTime }}
            </template>
          </el-table-column>
          <el-table-column prop="modifiedDate" label="最后修改时间" width="180" align="center">
            <template slot-scope="scope">
              {{ scope.row.modifiedDate | parseTime }}
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
      <el-collapse-item title="用户部门" name="user-dept">
        <el-table :data="depts" border style="width: 100%">
          <el-table-column :show-overflow-tooltip="true" prop="deptName" label="名称"/>
          <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注"/>
          <el-table-column prop="createdDate" label="创建时间" width="180" align="center">
            <template slot-scope="scope">
              {{ scope.row.createdDate | parseTime }}
            </template>
          </el-table-column>
          <el-table-column prop="modifiedDate" label="最后修改时间" width="180" align="center">
            <template slot-scope="scope">
              {{ scope.row.modifiedDate | parseTime }}
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
      <el-collapse-item title="用户角色" name="user-role">
        <el-table :data="roles" border style="width: 100%">
          <el-table-column :show-overflow-tooltip="true" prop="roleName" label="名称"/>
          <el-table-column :show-overflow-tooltip="true" prop="remark" label="备注"/>
          <el-table-column prop="createdDate" label="创建时间" width="180" align="center">
            <template slot-scope="scope">
              {{ scope.row.createdDate | parseTime }}
            </template>
          </el-table-column>
          <el-table-column prop="modifiedDate" label="最后修改时间" width="180" align="center">
            <template slot-scope="scope">
              {{ scope.row.modifiedDate | parseTime }}
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
import mixins from '../user/mixins'
var baseURLForPic = process.env.VUE_APP_BASE_API
import moment from 'moment'
export default {
  mixins: [mixins],
  props: {
    detail: {
      required: true,
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      defaultIcon: baseURLForPic,
      labelWidth: '150px',
      depts: [],
      groups: [],
      roles: [],
      birthday: ''
    }
  },
  activated() {
    this.birthday = moment(this.detail.birthday).format('YYYY-MM-DD')
    this.queryAllUserDepts()
    this.queryAllUserGroups()
    this.queryAllUserRoles()
  }
}
</script>

<style lang="scss" scoped>
  .el-button {
    margin-bottom: 10px;
  }

  .photo {
    margin: 0 auto;
    width: 178px;
    height: 178px;
  }
</style>
