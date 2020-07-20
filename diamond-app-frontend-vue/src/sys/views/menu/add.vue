<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="200px">
    <el-form-item v-if="parentMenuName" label="上级菜单">
      <el-input :value="parentMenuName"/>
    </el-form-item>
    <el-form-item label="菜单名称" prop="menuName">
      <el-input v-model="form.menuName"/>
    </el-form-item>
    <el-form-item label="菜单分类" prop="menuClassify">
      <el-select v-model="form.menuClassify" placeholder="添加分类" clearable filterable @change="showHide($event)">
        <el-option label="按钮" value="button"/>
        <el-option label="菜单" value="menu"/>
        <!--<el-option label="页面元素" value="content"/>-->
        <el-option label="外链" value="external"/>
      </el-select>
      <el-popover
        placement="top-start"
        title="菜单分类"
        width="200"
        trigger="hover"
        content="需要选择相应的菜单类型"
      >
        <el-button slot="reference" icon="el-icon-question"/>
      </el-popover>
    </el-form-item>
    <el-form-item label="菜单图标名称" prop="smallIconPath">
      <el-input v-model="form.smallIconPath"/>
    </el-form-item>
    <el-form-item label="菜单排序号" prop="menuIndex">
      <el-input v-model.number="form.menuIndex" type="text"/>
    </el-form-item>
    <el-form-item v-if="external==='external'||menu==='menu'" label="路由路径（path）" prop="menuDesc">
      <el-input v-model="form.menuDesc"/>
      <el-popover
        placement="top-start"
        title="路由路径（path）"
        width="200"
        trigger="hover"
        content="为浏览器中显示路径，一级菜单需要添加'/'；如果是外链地址，如果是第三方外链，需要加http或者https前缀"
      >
        <el-button slot="reference" icon="el-icon-question"/>
      </el-popover>
    </el-form-item>
    <el-form-item v-if="menu==='menu'" label="组件路径（component）" prop="menuUrl">
      <el-input v-model="form.menuUrl"/>
      <el-popover
        placement="top-start"
        title="组件路径（component）"
        width="200"
        trigger="hover"
        content="如果选择菜单，此处应填写页面在前端项目中位置地址，如果选择外链，应填写外链地址，如选择按钮，此处可不填"
      >
        <el-button slot="reference" icon="el-icon-question"/>
      </el-popover>
    </el-form-item>

    <el-form-item v-if="button==='button'" label="按钮权限ID" prop="authorityId">
      <el-input v-model="form.authorityId"/>
      <el-popover
        placement="top-start"
        title="按钮权限ID"
        width="200"
        trigger="hover"
        content="如果选择按钮类型，需要在此处设置按钮权限标识，标识需要根据页面内容和前端开发工程师协商制定。"
      >
        <el-button slot="reference" icon="el-icon-question"/>
      </el-popover>
    </el-form-item>
    <el-form-item label="菜单备注" prop="remark">
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
import * as MenuAPI from '../../api/system-management/menu'
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
      external: 'external',
      button: 'button',
      menu: 'menu',
      content: 'content'
    }
  },
  activated() {
    deepMergeLeft(this.form, this.initForm())
    if (this.detail.id) {
      this.form.parentId = this.detail.id
    }
    this.getParentMenuName(this.form.parentId)
    this.$nextTick(() => {
      this.$refs['form'].clearValidate()
    })
  },
  methods: {
    customSubmitHandler() {
      MenuAPI.addMenu(this.form).then(data => {
        this.submitSuccessHandler(data)
      })
    },
    customSubmitSuccessHandler(data) {
      this.$emit('option-changed', 'edit', data)
    },
    showHide: function(show) {
      switch (show) {
        case 'external' :
          this.external = 'external'
          this.menu = ''
          this.button = ''
          break
        case 'button' :
          this.external = ''
          this.menu = ''
          this.button = 'button'
          break
        case 'menu' :
          this.external = ''
          this.menu = 'menu'
          this.button = ''
          break
        case 'content' :
          this.external = 'external'
          this.menu = ''
          this.button = ''
          break
      }
    }
  }
}
</script>

<style scoped>

</style>
