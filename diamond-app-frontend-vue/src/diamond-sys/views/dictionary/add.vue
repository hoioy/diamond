<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="200px">
    <el-form-item label="上级字典">
      <el-input :value="parentDictionaryName" disabled/>
    </el-form-item>
    <el-form-item label="字典名称" prop="name">
      <el-input v-model="form.name"/>
    </el-form-item>
    <el-form-item label="字典分类" prop="dataItemTypeId">
      <el-select v-model="dataItemTypeId" clearable placeholder="全部" @change="selectGet">
        <el-option v-for="item in dictionaryTypeList" :key="item.typeName" :label="item.typeName" :value="{value:item.id,label:item.typeName}"/>
      </el-select>
    </el-form-item>
    <el-form-item label="字典规则码" prop="code">
      <el-input v-model="form.code"/>
    </el-form-item>
    <el-form-item label="字典排序号" prop="codeIndex">
      <el-input v-model.number="form.codeIndex" type="text"/>
    </el-form-item>
    <el-form-item label="字典备注" prop="remark">
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
import * as DictionaryAPI from '../../api/system-management/dictionary'
import * as DictionaryTypeAPI from '../../api/system-management/dictionary-type'
import mixins from './mixins'

export default {
  mixins: [BaseEditForm, mixins],
  data() {
    const form = this.initForm()
    const rules = this.initRules()
    return {
      form: form,
      rules: rules,
      dictionaryTypeList: []
    }
  },
  activated() {
    deepMergeLeft(this.form, this.initForm())
    this.form.parentId = this.detail.id ? this.detail.id : ''
    this.getParentDictionaryName(this.form.parentId)
    this.$nextTick(() => {
      this.$refs['form'].clearValidate()
    })
    DictionaryTypeAPI.queryPageDictionaryTypesAll().then(data => {
      this.dictionaryTypeList = data
    })
  },
  methods: {
    customSubmitHandler() {
      DictionaryAPI.addDictionary(this.form).then(this.submitSuccessHandler)
    },
    selectGet(params) {
      const { value, label } = params
      this.form.dataItemTypeId = value
      this.form.dataItemTypeName = label
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-select {
    width: 100%;
  }
</style>
