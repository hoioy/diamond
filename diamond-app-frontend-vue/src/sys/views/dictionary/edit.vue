<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="200px">
    <el-button icon="el-icon-back" round @click="backHandler">
      返回
    </el-button>
    <el-form-item label="ID" prop="id">
      <el-input v-model="form.id" disabled/>
    </el-form-item>
    <el-form-item v-if="parentDictionaryName" label="上级字典">
      <el-input :value="parentDictionaryName" disabled/>
    </el-form-item>
    <!--<el-form-item label="是否启用" prop="state">-->
      <!--<el-switch v-model="form.state" :inactive-value="0" active-value="1"/>-->
    <!--</el-form-item>-->
    <el-form-item label="字典名称" prop="name">
      <el-input v-model="form.name"/>
    </el-form-item>
    <el-form-item label="字典规则码" prop="code">
      <el-input v-model="form.code"/>
    </el-form-item>
    <el-form-item label="字典分类" prop="dataItemTypeId">
      <el-select v-model="dataItemTypeId" clearable placeholder="全部" @change="selectGet">
        <el-option v-for="item in dictionaryTypeList" :key="item.typeName" :label="item.typeName" :value="{value:item.id,label:item.typeName}"/>
      </el-select>
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
import mixins from './mixins'
import * as DictionaryTypeAPI from '../../api/system-management/dictionary-type'

export default {
  mixins: [BaseEditForm, mixins],
  data() {
    const form = this.initForm()
    const rules = this.initRules()
    delete form.parentId
    delete rules.parentId
    rules.id = [{
      required: true, message: '编辑信息时ID不能为空', trigger: 'change'
    }]
    return {
      form: form,
      rules: rules,
      dictionaryTypeList: []
    }
  },
  activated() {
    DictionaryAPI.queryDictionaryById(this.detail.id).then((data) => {
      console.log('55446  ' + this.detail.dataItemTypeName)
      deepMergeLeft(this.form, data)
      this.getParentDictionaryName(this.form.parentId)
      this.dataItemTypeId = this.detail.dataItemTypeName
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
      DictionaryTypeAPI.queryPageDictionaryTypesAll().then(data => { this.dictionaryTypeList = data })
    })
  },
  methods: {
    customSubmitHandler() {
      DictionaryAPI.editDictionary(this.form).then(this.submitSuccessHandler)
    },
    selectGet(params) {
      const { value, label } = params
      console.log(value)
      console.log(label)
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
