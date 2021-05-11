<template>
  <div>
    <el-card>
      <div slot="header">
        <button-right>
          用户信息
          <template slot="button">
            <el-button type="primary" @click="submitHandler('form')">
              保存
            </el-button>
          </template>
        </button-right>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="ID" prop="id">
              <el-input v-model="form.id" disabled/>
            </el-form-item>
            <el-form-item label="登录ID" prop="loginName">
              <el-input v-model="form.loginName" disabled/>
            </el-form-item>
            <el-form-item label="用户姓名" prop="userName">
              <el-input v-model="form.userName"/>
            </el-form-item>
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="form.nickname"/>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="照片" prop="avatar">
              <el-upload :show-file-list="false" class="avatar-uploader" action="" :http-request="fileUpload">
                <img v-if="form.avatar" :src="defaultIcon+form.avatar" height="200" width="200" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"/>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio v-for="item in dictionaries.gender" :key="item.key" v-model="form.gender" :label="item.key">
                {{ item.value }}
              </el-radio>
            </el-form-item>
            <el-form-item label="生日" prop="birthday">
              <el-date-picker v-model="form.birthday" :picker-options="(time) => { return time.getTime() > Date.now() }" align="right" type="date"/>
            </el-form-item>
            <el-form-item label="证件号码" prop="idNumber">
              <el-input v-model="form.idNumber"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电话" prop="phoneNum">
              <el-input v-model="form.phoneNum"/>
            </el-form-item>
            <el-form-item label="住址" prop="address">
              <el-input v-model="form.address"/>
            </el-form-item>
            <el-form-item label="标签" prop="tag" placeholder="支持英文逗号隔开">
              <el-input v-model="form.tag"/>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="用户备注" prop="remark">
              <el-input v-model="form.remark" type="textarea"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <el-card>
      <div slot="header">
        <button-right>
          密码修改
          <template slot="button">
            <el-button type="primary" @click="submitHandler('password', editUserPassword)">
              保存
            </el-button>
          </template>
        </button-right>
      </div>
      <el-form ref="password" :model="password" :rules="passwordRules" label-width="150px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="新密码" prop="value">
              <el-input v-model="password.value" type="password"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="再次填写密码" prop="validator">
              <el-input v-model="password.validator" type="password"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    <el-card header="分组信息">
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
    </el-card>
    <el-card header="部门信息">
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
    </el-card>
    <el-card header="角色信息">
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
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import { deepMergeLeft } from '@folder-inside-utils'
import * as UserAPI from '../../../api/system-management/user'
import mixins from '../mixins'

var baseURLForPic = process.env.VUE_APP_BASE_API
export default {
  name: 'DiamondMyself',
  mixins: [BaseEditForm, mixins],
  props: {
    detail: {
      required: false,
      type: Object,
      default: () => {}
    }
  },
  data() {
    const form = this.initForm()
    delete form.password
    delete form.state
    delete form.index
    const rules = this.editRules()
    delete rules.password
    delete rules.state
    delete rules.index
    rules.id = [{
      required: true, message: '编辑信息时ID不能为空', trigger: 'change'
    }]

    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.password.value) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }

    return {
      defaultIcon: baseURLForPic,
      form: form,
      rules: rules,

      depts: [],
      groups: [],
      roles: [],

      password: {
        value: '',
        validator: ''
      },
      passwordRules: {
        value: [{
          required: true, message: '请输入密码！', trigger: 'blur'
        }, {
          min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur'
        }],
        validator: [{
          required: true, message: '请再次输入密码！', trigger: 'blur'
        }, {
          validator: validatePassword, trigger: 'blur'
        }]
      }
    }
  },
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  mounted() {
    UserAPI.queryUserById(this.user.id).then((data) => {
      deepMergeLeft(this.form, data)
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    })
    this.queryAllUserDepts(this.user.id)
    this.queryAllUserGroups(this.user.id)
    this.queryAllUserRoles(this.user.id)
  },
  methods: {
    customSubmitHandler() {
      UserAPI.editUser(this.form).then(this.submitSuccessHandler)
    },
    editUserPassword() {
      UserAPI.editUserPassword({
        id: this.form.id,
        password: this.password.value
      }).then(this.submitSuccessHandler)
    },
    customSubmitSuccessHandler() {
      this.$refs['form'].clearValidate()
      this.$refs['password'].clearValidate()
    },
    fileUpload(item) {
      const fd = new FormData()
      fd.append('file', item.file)
      fd.append('id', this.form.id)
      const config = { headers: { 'Content-Type': 'multipart/form-data' }}
      UserAPI.avatarUpload(fd, config).then((data) => {
        this.form.avatar = data
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-card {
    margin-top: 10px;
  }

  .avatar-uploader {

    /deep/ .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;

      &:hover {
        border-color: #409EFF;
      }

      .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
      }
    }

    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }
  }
</style>
