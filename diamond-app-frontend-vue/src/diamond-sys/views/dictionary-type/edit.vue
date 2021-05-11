<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="200px">
    <el-form-item label="ID" prop="id">
      <el-input v-model="form.id" disabled/>
    </el-form-item>
    <el-form-item label="字典分类名称" prop="name">
      <el-input v-model="form.typeName"/>
    </el-form-item>
    <el-form-item label="字典分类备注" prop="remark">
      <el-input v-model="form.remark" type="textarea"/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitHandler('form')">
        保存
      </el-button>
      <el-button @click="backHandler">
        取消
      </el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import { deepMergeLeft } from '@folder-inside-utils'
import * as DictionaryTypeAPI from '../../api/system-management/dictionary-type'
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
    delete form.category
    const rules = this.initRules()
    rules.id = [{
      required: true, message: '编辑信息时ID不能为空', trigger: 'change'
    }]
    return {
      form: form,
      rules: rules
    }
  },
  activated() {
    DictionaryTypeAPI.queryDictionaryTypeById(this.detail.id).then((data) => {
      deepMergeLeft(this.form, data)
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    })
  },
  methods: {
    customSubmitHandler() {
      DictionaryTypeAPI.editDictionaryType(this.form).then(this.submitSuccessHandler)
    }
  }
}
</script>

