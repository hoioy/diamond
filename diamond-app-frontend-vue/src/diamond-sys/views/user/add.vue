<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="150px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="登录ID" prop="loginName">
          <el-input v-model="form.loginName"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password"/>
        </el-form-item>
        <el-form-item label="用户排序号" prop="userIndex">
          <el-input v-model.number="form.userIndex" type="text"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="12">
        <el-form-item label="用户姓名" prop="userName">
          <el-input v-model="form.userName"/>
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname"/>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email"/>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio v-for="item in dictionaries.gender" :key="item.key" v-model="form.gender" :label="item.key">
            {{ item.value }}
          </el-radio>
        </el-form-item>
        <el-form-item label="生日" prop="birthday">
          <el-date-picker v-model="form.birthday" :picker-options="(time) => { return time.getTime() > Date.now() }" align="right" type="date"/>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="证件号码" prop="idNumber">
          <el-input v-model="form.idNumber"/>
        </el-form-item>
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
      <el-col :span="24">
        <el-form-item>
          <el-button type="primary" @click="submitHandler('form')">
            保存
          </el-button>
          <el-button @click="backHandler">
            取消
          </el-button>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import { deepMergeLeft } from '@folder-inside-utils'
import * as UserAPI from '../../api/system-management/user'
import mixins from './mixins'

export default {
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
    const rules = this.initRules()
    return {
      form: form,
      rules: rules,
      uploadFile: ''
    }
  },
  activated() {
    deepMergeLeft(this.form, this.initForm())
    this.$nextTick(() => {
      this.$refs['form'].clearValidate()
    })
  },
  methods: {
    getAvatarHandler(data) {
      UserAPI.getAvatar(data).then()
    },
    customSubmitHandler() {
      UserAPI.addUser(this.form).then(data => {
        this.submitSuccessHandler(data)
      })
    },
    uploadSectionFile(param) {
      const fileObj = param.file
      const isLt2M = fileObj.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
        return
      }
      if (fileObj.type === 'image/jpeg') {
        this.uploadFile = new File([fileObj], new Date().getTime() + '.jpg', {
          type: 'image/jpeg'
        })
      } else if (fileObj.type === 'image/png') {
        this.uploadFile = new File([fileObj], new Date().getTime() + '.png', {
          type: 'image/png'
        })
      } else {
        this.$message.error('只能上传jpg/png文件')
        return
      }
    },
    upload() {
      const formData = new FormData()
      formData.append('file', this.uploadFile)
      formData.append('id', this.detail.id)
      UserAPI.avatarUpload(formData).then(data => {
        this.formDataoptionSuccessHandler(data)
      })
    },
    formDataoptionSuccessHandler() {
      this.$message({
        type: 'success',
        message: '操作成功'
      })
    },
    customSubmitSuccessHandler(data) {
      this.$emit('option-changed', 'edit', data)
    }
  }
}
</script>

<style lang="scss" scoped>
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
