<template>
  <div>
    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-form-item v-if="datasf2c" label="上级部门" prop="parentName">
        <el-input :value="detail.deptName" disabled/>
      </el-form-item>
      <el-form-item label="部门类型" prop="deptType">
        <el-input v-model="form.deptType"/>
            <!--<el-select v-model="addOption.groupId" placeholder="部门类型" clearable filterable @change="addUserGroupHandler">-->
              <!--<el-option-->
                      <!--v-for="(item, index) in addOption.allGroups"-->
                      <!--:key="index"-->
                      <!--:label="item.groupName"-->
                      <!--:value="item.id"-->
              <!--/>-->
            <!--</el-select>-->
      </el-form-item>
      <el-form-item label="部门名称" prop="deptName">
        <el-input v-model="form.deptName"/>
      </el-form-item>
      <!--<el-form-item label="是否启用" prop="deptState">-->
        <!--<el-switch v-model="form.deptState" :active-value="1" :inactive-value="0"/>-->
      <!--</el-form-item>-->
      <el-form-item label="部门排序号" prop="deptIndex">
        <el-input v-model.number="form.deptIndex" type="text"/>
      </el-form-item>
      <el-form-item label="部门备注" prop="remark">
        <el-input v-model="form.remark" type="textarea"/>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import BaseEditForm from '@folder-inside-views-common/BaseEditForm'
import { deepMergeLeft } from '@folder-inside-utils'
import * as DeptAPI from '../../api/system-management/dept'
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
    console.info('this.detail.id')
    console.info(this.detail.id)
    if (this.detail.id) {
      console.info(this.detail.id)
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
          console.info(this.detail.id)
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
