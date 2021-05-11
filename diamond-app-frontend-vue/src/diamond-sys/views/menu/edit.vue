<template>
  <div>
    <el-button icon="el-icon-back" round @click="backHandler">
      返回
    </el-button>
    <el-card>
      <div slot="header">
        <button-right>
          菜单信息
          <template slot="button">
            <el-button type="primary" @click="submitHandler('form')">
              保存
            </el-button>
          </template>
        </button-right>
      </div>
      <el-form ref="form" :model="form" :rules="rules" :label-width="labelWidth">
        <el-form-item label="ID" prop="id">
          <el-input v-model="form.id" disabled/>
        </el-form-item>
        <el-form-item v-if="parentMenuName" label="上级菜单">
          <el-input :value="parentMenuName" disabled/>
        </el-form-item>
        <el-form-item label="菜单名称" prop="menuName">
          <el-input v-model="form.menuName"/>
        </el-form-item>
        <el-form-item label="菜单分类" prop="menuClassify">
          <el-select v-model="form.menuClassify" placeholder="添加分类" clearable filterable @change="showHide($event)">
            <el-option v-if="this.forButton" label="按钮" value="button"/>
            <el-option label="菜单" value="menu"/>
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
        <el-form-item label="菜单排序号" prop="menuIndex">
          <el-input v-model.number="form.menuIndex" type="text"/>
        </el-form-item>
        <el-form-item v-if="externalOrButtonOrMenu==='external'||externalOrButtonOrMenu==='menu'" label="路由路径（path）" prop="menuDesc">
          <el-input v-model="form.menuDesc"/>
          <el-popover
            placement="top-start"
            title="路由路径（path）"
            width="200"
            trigger="hover"
            content="为浏览器中显示路径，一级菜单需要添加'/'"
          >
            <el-button slot="reference" icon="el-icon-question"/>
          </el-popover>
        </el-form-item>
        <el-form-item v-if="externalOrButtonOrMenu==='menu'" label="组件路径（component）" prop="menuUrl">
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
        <el-form-item v-if="externalOrButtonOrMenu==='external'||externalOrButtonOrMenu==='menu'" label="菜单图标名称" prop="smallIconPath">
          <el-select v-model="form.smallIconPath" clearable placeholder="全部">
            <el-option v-for="item in iconArrayList" :key="item.index" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item v-if="externalOrButtonOrMenu==='button'" label="按钮权限ID" prop="authorityId">
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
      </el-form>
    </el-card>
    <el-card header="角色信息">
      <el-table :data="roles" border style="width: 100%">
        <el-table-column prop="roleName" label="角色名称"/>
        <el-table-column prop="createdDate" label="创建时间">
          <template slot-scope="scope">
            {{ scope.row.createdDate | parseTime }}
          </template>
        </el-table-column>
        <el-table-column prop="modifiedDate" label="最后修改时间">
          <template slot-scope="scope">
            {{ scope.row.modifiedDate | parseTime }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="100" align="center">
          <template slot-scope="scope">
            <el-button type="warning" @click="delMenuRoleHandler(scope.row.id)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
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
      labelWidth: '200px',
      form: form,
      rules: rules,

      addUrl: {
        show: false,
        url: null
      },
      iconArray: [],
      iconArrayList: {},
      externalOrButtonOrMenu: '',
      forButton: true
    }
  },
  watch: {
    externalOrButtonOrMenu(value) {
      this.rules = this.initRules(value)
    }
  },
  activated() {
    if (this.detail.parentId) {
      this.forButton = true
    } else {
      this.forButton = false
    }
    MenuAPI.queryMenuById(this.detail.id).then((data) => {
      deepMergeLeft(this.form, data)
      this.getParentMenuName(this.detail.parentId)
      this.$nextTick(() => {
        this.$refs['form'].clearValidate()
      })
    })
    // this.queryMenuUrls()
    this.queryMenuRoles()
    const requireContext = require.context('@/icons/svg', false, /^\.\/.*\.svg$/)
    let iconImages = {}
    requireContext.keys().map(req => {
      let iconName = req.split('./')[1]
      iconName = iconName.split('.')[0]
      const iconNameObj = {}
      iconNameObj[iconName] = require(`@/icons/svg/${iconName}.svg`)
      iconImages = {
        ...iconImages,
        ...iconNameObj
      }
      return {
        iconName: require(`@/icons/svg/${iconName}.svg`)
      }
    })
    for (var i in iconImages) {
      var obj = {
        iconsName: i,
        realName: iconImages[i]
      }
      this.iconArray.push(obj.iconsName)
    }
    for (const key in this.iconArray) {
      obj[key] = this.iconArray[key]
    }

    var obj1 = {}
    for (const key in this.iconArray) {
      obj1[key] = this.iconArray[key]
    }
    var newObj = Object.keys(obj1).map(val => ({
      label: obj1[val],
      value: obj1[val]
    }))
    this.iconArrayList = newObj
    this.externalOrButtonOrMenu = this.detail.menuClassify
  },
  methods: {
    customSubmitHandler() {
      MenuAPI.editMenu(this.form).then((data) => {
        this.submitSuccessHandler(data)
      })
    },
    customSubmitSuccessHandler() {
      // 便于继续编辑url,此处不返回
      this.$refs['form'].clearValidate()
    },
    delMenuRoleHandler(id) {
      MenuAPI.delMenuRoleIncludeSon(this.detail.id, id).then(data => {
        this.queryMenuRoles()
      })
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-card {
    margin-top: 10px;
  }
</style>
