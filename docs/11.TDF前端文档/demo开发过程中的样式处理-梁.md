## 四、 样式规范

#### 4.1 主题样式全局配置

TDF新增了主题切换 对于主题切换的原理也写得很清楚 可以去翻看查阅 每一主题对应一个css文件

![](./image/4.1.png)

这种样式是全局样式 我们打开最外层文件 element-variables.sass文件可以看到全局设定得一些样式 这里举几个例子，比如

```
.......
/* Color
-------------------------- */
/// color|1|Brand Color|0
$--color-primary: #3C9AFB !default;  //主题色
/// color|1|Background Color|4
$--color-white: #FFFFFF !default;
/// color|1|Background Color|4
$--color-black: #000000 !default;
$--color-primary-light-1: mix($--color-white, $--color-primary, 10%) !default; /* 53a8ff */
$--color-primary-light-2: mix($--color-white, $--color-primary, 20%) !default; /* 66b1ff */
$--color-primary-light-3: mix($--color-white, $--color-primary, 30%) !default; /* 79bbff */
$--color-primary-light-4: mix($--color-white, $--color-primary, 40%) !default; /* 8cc5ff */
$--color-primary-light-5: mix($--color-white, $--color-primary, 50%) !default; /* a0cfff */
$--color-primary-light-6: mix($--color-white, $--color-primary, 60%) !default; /* b3d8ff */
$--color-primary-light-7: mix($--color-white, $--color-primary, 70%) !default; /* c6e2ff */
$--color-primary-light-8: mix($--color-white, $--color-primary, 80%) !default; /* d9ecff */
$--color-primary-light-9: mix($--color-white, $--color-primary, 90%) !default; /* ecf5ff */
/// color|1|Functional Color|1
$--color-success: #67C23A !default;  //成功按钮颜色
/// color|1|Functional Color|1
$--color-warning: #E6A23C !default;  //警告按钮颜色
/// color|1|Functional Color|1
$--color-danger: #F56C6C !default;   //危险按钮颜色
/// color|1|Functional Color|1
$--color-info: #909399 !default;  //信息按钮颜色
......
以上只是主题设置颜色的一部分，可根据需要自定义配置哦~
```

#### 4.2 单个页面样式配置

如果没有特别设置，所有页面都走主题统一的样式设置，那么对于在页面众的组件如果有特殊需求，也可以在页面或者组件中定义颜色class进行定义，写法和普通写html页面时定义样式class时一样的。特别说明 如果想要改变elementui 框架自带的样式 推荐两种方式

方式一： 给要设置样式的组件增加id 或者class 在style里面修改 以面包屑组件为例

```
<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">  // 给组件增加app-breadcrumb class
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item,index) in levelList" v-if="item.meta.title" :key="item.path">
        <span v-if="item.redirect==='noredirect'||index===levelList.length-1" class="no-redirect">{{ item.meta.title }}</span>
        <router-link v-else :to="item.redirect||item.path">
          {{ item.meta.title }}
        </router-link>
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>
.....
<style rel="stylesheet/scss" lang="scss" scoped>
  .app-breadcrumb.el-breadcrumb {
    display: inline-block;
    font-size: 14px;
    line-height: 50px;
    margin-left: 10px;
    .no-redirect {
      color: #97a8be;
      cursor: text;
    }
  }
</style>
```

方式二、用/deep/深度作用选择器修改elementui的默认样式

![](./image/4.2.png)

```
/deep/ .el-tree {     // 深度作用域修改el-tree样式
  border-bottom: 1px solid #ebeef5;

  .el-tree-node__content {
    border-top: 1px solid #ebeef5;
    border-left: 1px solid #ebeef5;
    border-right: 1px solid #ebeef5;
    min-height: 40px;
  }
}
```

