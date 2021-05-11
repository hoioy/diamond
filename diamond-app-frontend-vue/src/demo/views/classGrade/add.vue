<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-form-item label="级别名称" prop="gradeName">
        <el-input v-model="form.gradeName" type="textarea"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark"/>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import { deepMergeLeft } from '@folder-inside-utils'
import * as DeptAPI from '@src/demo/api/classGrade'
import mixins from './mixins'

export default {
  mixins: [BaseEditForm, mixins],
  props: {
    detail: {
      required: false,
      type: Object,
      default: () => {}
    },
    datasf2c: { // 这里的datas用于接收
      type: String, // 我们接收验证的是字符串 也可以验证别的类型
      default: () => {}
    }
  },
  data() {
    const form = this.initForm()
    const rules = this.initRules()
    return {
      form: form,
      rules: rules
    }
  },
  activated() {
    deepMergeLeft(this.form, this.initForm())
    if (this.detail.id) {
      this.form.parentId = this.detail.id
    }
    this.getParentDeptName(this.form.parentId)
    this.$nextTick(() => {
      this.$refs['form'].clearValidate()
    })
  },
  methods: {
    submitForm() {
      if (this.datasf2c) {
        if (this.detail.id) {
          this.form.parentId = this.detail.id
        }
      } else {
        this.form.parentId = null
      }
      var status = false
      this.$refs['form'].validate(valid => {
        if (valid) {
          status = true
        } else {
          status = false
        }
      })
      return status
    },
    customSubmitHandler() {
      DeptAPI.addDept(this.form).then(data => {
        this.submitSuccessHandler(data)
      })
    },
    customSubmitSuccessHandler() {
      this.$emit('option-changed', 'query')
    }
  }
}
</script>

<style scoped>

</style>
