## 二、TDF封装的Element-ui组件介绍

今天给大家介绍下TDF前端封装了哪些组件，方便大家阅读源码
在阅读TDF组件封装源码前线学习vue组件的封装
[vue怎么封装一个组件(一)](http://tech.taiji.com.cn/#/blog/view/90cf4601a2a64deb8a8c395dd2190084 "vue怎么封装一个组件(一)")
[vue 组件封装父子组件交互问题（二）](http://tech.taiji.com.cn/#/blog/view/eddbd12801e9454cb1844dfe680eeed7 "vue 组件封装父子组件交互问题（二）")

### 1、TDF封装的组件
TDF封装了哪些组件，首先是架构组件
架构组件是放在路径src/tdf/components下面的
##### 1.1 Abbreviation组件
https://element.eleme.cn/#/zh-CN/component/popover
以下是Abbreviation组件代码
```javascript
<template>
  <el-popover :placement="placement" :width="width" :trigger="trigger"> // Element-ui Popover 弹出框组件的封装
// el-popover组件绑定了三个属性 placement表示弹出框出现的位置 width表示弹出框的宽度 trigger表示触发方式
    <span slot="reference">{{ abbr }}</span>
    <span class="break-word">{{ content }}</span>
  </el-popover>
</template>

<script>
export default {
  props: {
    content: {
      required: false,
      type: String,
      default: ''
    },
    size: {
      required: false,
      type: Number,
      default: 10
    },
    placement: {
      required: false,
      type: String,
      default: 'top-start'
    },
    width: {
      required: false,
      type: Number,
      default: 200
    },
    trigger: {
      required: false,
      type: String,
      default: 'hover'
    }
  },
  data() {
    let abbr = ''
    if (this.content) {
      abbr = this.content.substring(0, this.size)
    }
    return {
      abbr: abbr
    }
  }
}
</script>

<style lang="scss" scoped>
.break-word {
  word-wrap: break-word;
  word-break: break-all;
  white-space: pre-wrap !important;
}
</style>
在TDF中的效果图
```
##### 1.2 Breadcrumb组件
这个组件是对面包屑组件做了封装
vue的[transiton-group](https://cn.vuejs.org/v2/api/#transition-group "transiton-group")
<transition-group> 元素作为多个元素/组件的过渡效果，<transition-group> 渲染一个真实的 DOM 元素。默认渲染 <span>，可以通过 tag 属性配置哪个元素应该被渲染。

elementui的[面包屑组件](https://element.eleme.cn/#/zh-CN/component/breadcrumb "面包屑")
```javascript
<template>
  <el-breadcrumb class="app-breadcrumb" separator="/">
    <transition-group name="breadcrumb">
      <el-breadcrumb-item v-for="(item,index) in levelList" v-if="item.meta.title" :key="item.path">
        <span v-if="item.redirect==='noredirect'||index===levelList.length-1" class="no-redirect">{{ item.meta.title }}</span>
        <router-link v-else :to="item.redirect||item.path">
          {{ item.meta.title }}
        </router-link>
// https://router.vuejs.org/zh/api/#router-link
      </el-breadcrumb-item>
    </transition-group>
  </el-breadcrumb>
</template>

<script>
import pathToRegexp from 'path-to-regexp'

export default {
  data() {
    return {
      levelList: null
    }
  },
  watch: {
    $route() {
      this.getBreadcrumb()
    }
  },
  created() {
    this.getBreadcrumb()
  },
  methods: {
    getBreadcrumb() {
      const { params } = this.$route
      const matched = this.$route.matched.filter(item => {
        if (item.name) {
          var toPath = pathToRegexp.compile(item.path)
          item.path = toPath(params)
          return true
        }
      })
      this.levelList = matched
    }
  }
}
</script>

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
在TDF上展示效果图
![](./image/2.1.png)

##### 1.3 ErrorPage页面
首先是401页面  然后是404页面 在任何一个前端页面 一定要有错误页面
```javascript
<template>
  <div class="wscn-http404-container">
    <div class="wscn-http404">
      <div class="pic-404">
        <img class="pic-404__parent" src="@/assets/images/404.png">
        <img class="pic-404__child left" src="@/assets/images/404_cloud.png">
        <img class="pic-404__child mid" src="@/assets/images/404_cloud.png">
        <img class="pic-404__child right" src="@/assets/images/404_cloud.png">
      </div>
      <div class="bullshit">
        <div class="bullshit__oops">
          Error!
        </div>
        <div class="bullshit__info"/>
        <div class="bullshit__headline">
          401权限不足
        </div>
        <div class="bullshit__info">
          没有权限访问该页面，请点击以下按钮返回主页或者发送错误报告
        </div>
        <a href="" class="bullshit__return-home">返回首页</a>
      </div>
    </div>
  </div>
</template>

<style rel="stylesheet/scss" lang="scss" scoped>
  .wscn-http404-container{
    transform: translate(-50%,-50%);
    position: absolute;
    top: 40%;
    left: 50%;
  }
  .wscn-http404 {
    position: relative;
    width: 1200px;
    padding: 0 50px;
    overflow: hidden;
    .pic-404 {
      position: relative;
      float: left;
      width: 600px;
      overflow: hidden;
      &__parent {
        width: 100%;
      }
      &__child {
        position: absolute;
        &.left {
          width: 80px;
          top: 17px;
          left: 220px;
          opacity: 0;
          animation-name: cloudLeft;
          animation-duration: 2s;
          animation-timing-function: linear;
          animation-fill-mode: forwards;
          animation-delay: 1s;
        }
        &.mid {
          width: 46px;
          top: 10px;
          left: 420px;
          opacity: 0;
          animation-name: cloudMid;
          animation-duration: 2s;
          animation-timing-function: linear;
          animation-fill-mode: forwards;
          animation-delay: 1.2s;
        }
        &.right {
          width: 62px;
          top: 100px;
          left: 500px;
          opacity: 0;
          animation-name: cloudRight;
          animation-duration: 2s;
          animation-timing-function: linear;
          animation-fill-mode: forwards;
          animation-delay: 1s;
        }
        @keyframes cloudLeft {
          0% {
            top: 17px;
            left: 220px;
            opacity: 0;
          }
          20% {
            top: 33px;
            left: 188px;
            opacity: 1;
          }
          80% {
            top: 81px;
            left: 92px;
            opacity: 1;
          }
          100% {
            top: 97px;
            left: 60px;
            opacity: 0;
          }
        }
        @keyframes cloudMid {
          0% {
            top: 10px;
            left: 420px;
            opacity: 0;
          }
          20% {
            top: 40px;
            left: 360px;
            opacity: 1;
          }
          70% {
            top: 130px;
            left: 180px;
            opacity: 1;
          }
          100% {
            top: 160px;
            left: 120px;
            opacity: 0;
          }
        }
        @keyframes cloudRight {
          0% {
            top: 100px;
            left: 500px;
            opacity: 0;
          }
          20% {
            top: 120px;
            left: 460px;
            opacity: 1;
          }
          80% {
            top: 180px;
            left: 340px;
            opacity: 1;
          }
          100% {
            top: 200px;
            left: 300px;
            opacity: 0;
          }
        }
      }
    }
    .bullshit {
      position: relative;
      float: left;
      width: 300px;
      padding: 30px 0;
      overflow: hidden;
      &__oops {
        font-size: 32px;
        font-weight: bold;
        line-height: 40px;
        color: #1482f0;
        opacity: 0;
        margin-bottom: 20px;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-fill-mode: forwards;
      }
      &__headline {
        font-size: 20px;
        line-height: 24px;
        color: #222;
        font-weight: bold;
        opacity: 0;
        margin-bottom: 10px;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-delay: 0.1s;
        animation-fill-mode: forwards;
      }
      &__info {
        font-size: 13px;
        line-height: 21px;
        color: grey;
        opacity: 0;
        margin-bottom: 30px;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-delay: 0.2s;
        animation-fill-mode: forwards;
      }
      &__return-home {
        display: block;
        float: left;
        width: 110px;
        height: 36px;
        background: #1482f0;
        border-radius: 100px;
        text-align: center;
        color: #ffffff;
        opacity: 0;
        font-size: 14px;
        line-height: 36px;
        cursor: pointer;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-delay: 0.3s;
        animation-fill-mode: forwards;
      }
      @keyframes slideUp {
        0% {
          transform: translateY(60px);
          opacity: 0;
        }
        100% {
          transform: translateY(0);
          opacity: 1;
        }
      }
    }
  }
</style>
```
我再路径上随便输一串数字，我们可以看到页面跳转到了404页面，如图展示
![](./image/2.2.png)
##### 1.4 Hamburiger组件
这个是前端常见的Hamburiger组件的封装 可以看到源码 基本上是用纯html+css写的
```javascript
<template>
  <div>
    <svg
      :class="{'is-active':isActive}"
      xmlns="http://www.w3.org/2000/svg"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      version="1.1"
      t="1492500959545"
      p-id="1691"
      class="hamburger"
      style=""
      viewBox="0 0 1024 1024"
      width="64"
      height="64"
      @click="toggleClick"
    >
      <path d="M966.8023 568.849776 57.196677 568.849776c-31.397081 0-56.850799-25.452695-56.850799-56.850799l0 0c0-31.397081 25.452695-56.849776 56.850799-56.849776l909.605623 0c31.397081 0 56.849776 25.452695 56.849776 56.849776l0 0C1023.653099 543.397081 998.200404 568.849776 966.8023 568.849776z" p-id="1692"/>
      <path d="M966.8023 881.527125 57.196677 881.527125c-31.397081 0-56.850799-25.452695-56.850799-56.849776l0 0c0-31.397081 25.452695-56.849776 56.850799-56.849776l909.605623 0c31.397081 0 56.849776 25.452695 56.849776 56.849776l0 0C1023.653099 856.07443 998.200404 881.527125 966.8023 881.527125z" p-id="1693"/>
      <path d="M966.8023 256.17345 57.196677 256.17345c-31.397081 0-56.850799-25.452695-56.850799-56.849776l0 0c0-31.397081 25.452695-56.850799 56.850799-56.850799l909.605623 0c31.397081 0 56.849776 25.452695 56.849776 56.850799l0 0C1023.653099 230.720755 998.200404 256.17345 966.8023 256.17345z" p-id="1694"/>
    </svg>
  </div>
</template>

<script>
export default {
  name: 'Hamburger',
  props: {
    isActive: {
      type: Boolean,
      default: false
    },
    toggleClick: {
      type: Function,
      default: null
    }
  }
}
</script>

<style scoped>
.hamburger {
  display: inline-block;
  cursor: pointer;
  width: 20px;
  height: 20px;
  transform: rotate(90deg);
  transition: .38s;
  transform-origin: 50% 50%;
}
.hamburger.is-active {
  transform: rotate(0deg);
}
</style>
```
##### 1.5 Redirect
这个是重定向组件
看下面代码我们可以知道主要做了一件事就是在钩子函数beforeCreate（）做了约束，让每个页面一进入加载query组件。
```javascript
<script>
export default {
  beforeCreate() {
    const { params, query } = this.$route
    const { path } = params
    this.$router.replace({ path: '/' + path, query })
  },
  render: function(h) {
    return h() // avoid warning message
  }
}
</script>
```
##### 1.6 Screenfull组件
这个组件的作用就是对svg图标的统一处理约定.
```javascript
<template>
  <div>
    <svg
      t="1508738709248"
      class="screenfull-svg"
      viewBox="0 0 1024 1024"
      version="1.1"
      xmlns="http://www.w3.org/2000/svg"
      p-id="2069"
      xmlns:xlink="http://www.w3.org/1999/xlink"
      width="32"
      height="32"
      @click="click"
    >
		      </svg>
  </div>
import screenfull from 'screenfull'

export default {
  name: 'Screenfull',
  props: {
    width: {
      type: Number,
      default: 22
    },
    height: {
      type: Number,
      default: 22
    },
    fill: {
      type: String,
      default: '#48576a'
    }
  },
  data() {
    return {
      isFullscreen: false
    }
  },
  methods: {
    click() {
      if (!screenfull.enabled) {
        this.$message({
          message: 'you browser can not work',
          type: 'warning'
        })
        return false
      }
      screenfull.toggle()
    }
  }
}
</script>

<style scoped>
.screenfull-svg {
  display: inline-block;
  cursor: pointer;
  fill: #5a5e66;;
  width: 20px;
  height: 20px;
  vertical-align: 10px;
}
</style>

```
##### 1.7 ScrollPane组件
这个组件是页面滚动窗格的封装。
这块的代码要与src/tdf/views/layout/default/components/Sidebar/index里面的代码结合着一块看。
```javascript
<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <el-menu
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      mode="vertical"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
    >
      <sidebar-item v-for="route in routes" :key="route.name" :item="route" :base-path="route.path"/>
    </el-menu>
    <!--<el-menu :default-active="$route.path" mode="vertical" unique-opened background-color="#304156" text-color="#fff" active-text-color="#409EFF">-->
    <!--<sidebar-item :routes="routes"/>-->
    <!--</el-menu>-->
  </el-scrollbar>
</template>
```
```javascript
<template>
  <el-scrollbar ref="scrollContainer" :vertical="false" class="scroll-container" @wheel.native.prevent="handleScroll">
    <slot/>
  </el-scrollbar>
</template>

<script>
const tagAndTagSpacing = 4 // tagAndTagSpacing

export default {
  name: 'ScrollPane',
  data() {
    return {
      left: 0
    }
  },
  methods: {
    handleScroll(e) {
      const eventDelta = e.wheelDelta || -e.deltaY * 40
      const $scrollWrapper = this.$refs.scrollContainer.$refs.wrap
      $scrollWrapper.scrollLeft = $scrollWrapper.scrollLeft + eventDelta / 4
    },
    moveToTarget(currentTag) {
      const $container = this.$refs.scrollContainer.$el
      const $containerWidth = $container.offsetWidth
      const $scrollWrapper = this.$refs.scrollContainer.$refs.wrap
      const tagList = this.$parent.$refs.tag

      let firstTag = null
      let lastTag = null
      let prevTag = null
      let nextTag = null

      // find first tag and last tag
      if (tagList.length > 0) {
        firstTag = tagList[0]
        lastTag = tagList[tagList.length - 1]
      }

      // find preTag and nextTag
      for (let i = 0; i < tagList.length; i++) {
        if (tagList[i] === currentTag) {
          if (i === 0) {
            nextTag = tagList[i].length > 1 && tagList[i + 1]
          } else if (i === tagList.length - 1) {
            prevTag = tagList[i].length > 1 && tagList[i - 1]
          } else {
            prevTag = tagList[i - 1]
            nextTag = tagList[i + 1]
          }
          break
        }
      }

      if (firstTag === currentTag) {
        $scrollWrapper.scrollLeft = 0
      } else if (lastTag === currentTag) {
        $scrollWrapper.scrollLeft = $scrollWrapper.scrollWidth - $containerWidth
      } else {
        // the tag's offsetLeft after of nextTag
        const afterNextTagOffsetLeft = nextTag.$el.offsetLeft + nextTag.$el.offsetWidth + tagAndTagSpacing

        // the tag's offsetLeft before of prevTag
        const beforePrevTagOffsetLeft = prevTag.$el.offsetLeft - tagAndTagSpacing

        if (afterNextTagOffsetLeft > $scrollWrapper.scrollLeft + $containerWidth) {
          $scrollWrapper.scrollLeft = afterNextTagOffsetLeft - $containerWidth
        } else if (beforePrevTagOffsetLeft < $scrollWrapper.scrollLeft) {
          $scrollWrapper.scrollLeft = beforePrevTagOffsetLeft
        }
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .scroll-container {
    white-space: nowrap;
    position: relative;
    overflow: hidden;
    width: 100%;
    /deep/ {
      .el-scrollbar__bar {
        bottom: 0px;
      }
      .el-scrollbar__wrap {
        height: 49px;
      }
    }
  }
</style>

```
TDF中的效果
![](./image/2.3.png)
滚动条布局效果。

##### 1.8 SvgIcon组件
这个组件是对svgIcon的封装 ，在icon上绑定两个属性iconName和svgClass 可在父组件中进行值得绑定。
代码是比较简单的 是纯html+css编写的 用来展示和处理所有的svg图标。
```javascript
<template>
  <svg :class="svgClass" aria-hidden="true">
    <use :xlink:href="iconName"/>
  </svg>
</template>

<script>
export default {
  name: 'SvgIcon',
  props: {
    iconClass: {
      type: String,
      required: true
    },
    className: {
      type: String,
      default: ''
    }
  },
  computed: {
    iconName() {
      return `#icon-${this.iconClass}`
    },
    svgClass() {
      if (this.className) {
        return 'svg-icon ' + this.className
      } else {
        return 'svg-icon'
      }
    }
  }
}
</script>

<style scoped>
.svg-icon {
  width: 1em;
  height: 1em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>

```
### 2、封装的业务组件
在src/tdf/views/common/components里面的是业务组件，也是就页面中公共的组件
##### 2.1 auditInfo组件
这个组件主要是对审计信息的封装，包括创建人、创建时间、最后修改人、最后修改时间。
```javascript
<template>
  <el-form :model="detail" :label-width="labelWidth" :size="size" :inline="inline">
    <input-item-view label="创建人">
      {{ detail.createdBy }}
    </input-item-view>
    <input-item-view label="创建时间">
      {{ detail.createdDate | parseTime }}
    </input-item-view>
    <input-item-view label="最后修改人">
      {{ detail.modifiedBy }}
    </input-item-view>
    <input-item-view label="最后修改时间">
      {{ detail.modifiedDate | parseTime }}
    </input-item-view>
  </el-form>
</template>

<script>

export default {
  props: {
    inline: {
      required: false,
      type: Boolean,
      default: false
    },
    size: {
      required: false,
      type: String,
      default: 'small'
    },
    labelWidth: {
      required: false,
      type: String,
      default: '200px'
    },
    detail: {
      required: true,
      type: Object,
      default: () => {}
    }
  }
}
</script>

<style scoped>

</style>

```

##### 2.2 buttons组件
这个主要是对button的封装，在TDF页面中有很多按钮的右侧对齐需求，对该公共组件进行统一封装。
```javascript
<template>
  <div class="full">
    <div class="left">
      <slot/>
    </div>
    <div class="right">
      <slot name="button"/>
    </div>
  </div>
</template>

<style lang="scss" scoped>
  .full {
    width: 100%;
    .left {
      display: inline;
      width: 50%;
      min-height: 32px;
      line-height: 32px;
    }
    .right {
      display: inline;
      width: 50%;
      min-height: 32px;
      line-height: 32px;

      .el-button, .el-button-group {
        float: right;
        margin-left: 15px;
      }
    }
  }
</style>

```
该组件的使用，我们在所有的query页面中都有多个按钮组件 那么统一要求组件右对齐 利用<button-right></button-right> 可以快速实现按钮组的右对齐。
![](./image/2.4.png)
![](./image/2.5.png)
```javascript
<template>
  <div class="full">
    <slot/>
    <div class="buttons">
      <slot name="buttons"/>
    </div>
  </div>
</template>

<style lang="scss" scoped>
  .full {
    width: 100%;

    .buttons {
      width: 100%;
      margin-top: 10px;

      .el-button, .el-button-group {
        float: right;
        margin-left: 15px;
      }
    }
  }
</style>

```
这个组件是换行右对齐

##### 2.3 dictionaryTranslates组件
这个组件主要是对字典进行转译，提供了极大的方便。
```javascript
<template>
  <el-tag :type="state === '1' ? 'success' : 'info'" plain>
    {{ state | translateState }}
  </el-tag>
</template>

<script>
export default {
  props: {
    state: {
      required: true,
      type: [String, Number],
      default: () => {}
    }
  }
}
</script>

```
在TDF中的使用，TDF中有创建时间的转译，可以使用该组件快速进行转译，将时间戳转换成时间格式
如下
```javascript
 <template slot-scope="scope">
            {{ scope.row.createdDate | parseTime }}
          </template>
```
显示成我们想要的时间格式。
![](./image/2.6.png)
eg.对性别的转译
![](./image/2.7.png)
##### 2.4 formItemViews组件
该组件是对form表单项的封装。
```javascript
// InputItemView组件是对所有input输入框的封装
<template>
  <div :class="['el-form-item', elFormItemSize]">
    <label :style="{ width: labelWidth}" class="el-form-item__label">{{ label }}:</label> //在input框上面绑定label标签属性
    <div :style="{ marginLeft: labelWidth}" class="el-form-item__content">
      <div :class="['el-input', 'is-disabled', elInputSize]">
        <div class="el-input__inner break-word">
          <slot/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    label: {
      required: true,
      type: String
    },
    labelWidth: {
      required: false,
      type: String,
      default: '200px'
    },
    size: {
      required: false,
      type: String,
      default: 'small'
    }
  },
  computed: {
    elFormItemSize() {
      return 'el-form-item--' + this.size
    },
    elInputSize() {
      return 'el-input--' + this.size
    }
  }
}
</script>

使用示例
​```javascript
<el-form ref="form" :model="form" :rules="rules" label-width="150px">
    <el-row>
      <el-col :span="12">
        <el-form-item label="是否启用" prop="state">
          <el-switch v-model="form.state" :active-value="1" :inactive-value="0"/>
        </el-form-item>
        <el-form-item label="用户排序号" prop="userIndex">
          <el-input v-model="form.userIndex"/>
        </el-form-item>
        <el-form-item label="登录ID" prop="loginName">
          <el-input v-model="form.loginName"/>
        </el-form-item>
```
<style lang="scss" scoped>
  .el-input__inner {
    background-color: white !important;
    border: none !important;
  }
  .break-word {
    word-wrap: break-word;
    word-break: break-all;
    white-space: pre-wrap !important;
  }
</style>

```
显示效果
![](https://tech.taiji.com.cn/ui/api/upload/34442faeec2647b3a2cd4fb5d4b5e4d2_-_图片.png)
每个输入框有固定的label-width
##### 5.layouts组件
layouts组件包括flex的居中 靠左 靠右
这块是对布局的封装，主要是对样式的封装，包括居中、靠左、靠右对齐
​```javascript
<template>
  <div class="flex-container">
    <slot/>
  </div>
</template>

<style lang="scss" scoped>
  .flex-container{
    width: 100%;
    padding: 10px 0px;
    display: flex;
    flex-flow: row wrap;
    justify-content: center;
  }
</style>

```
在TDF中的使用
```javascript
 <flex-center>
              <el-button round type="info" @click="resetHandler">
                重置
              </el-button>
              <el-button round type="primary" @click="queryHandler">
                查询
              </el-button>
            </flex-center>
```
利用 <flex-center></flex-center>可以快速实现按钮的居中 同理 靠左靠右也都是一样的，而且可以套在其他组件外部实现居中 靠左 靠右。是不是很便捷。

##### 2.5 paginations组件
主要是对分页组建的封装，可看[TDF分页原理](http://tech.taiji.com.cn/#/blog/view/b4202d03fd8e4f8ab25c57e2a90bd1d0 "TDF分页原理")介绍。
```javascript
<template>
  <el-pagination
    :current-page="pagination.page"
    :page-size="pagination.pageSize"
    :total="pagination.total"
    :page-sizes="pageSizes"
    :pager-count="pagerCount"
    :layout="layout"
    @size-change="pageSizeChangeHandler"
    @current-change="pageChangeHandler"
  />
</template>

<script>
export default {
  props: {
    pagination: {
      required: true,
      type: Object
    },
    pageSizes: {
      required: false,
      type: Array,
      default: () => [10, 30, 50, 100, 200]
    },
    pagerCount: {
      required: false,
      type: Number,
      default: 5
    },
    layout: {
      required: false,
      type: String,
      default: 'total, sizes, prev, pager, next, jumper'
    }
  },
  methods: {
    pageSizeChangeHandler(pageSize) {
      this.$emit('page-size-changed', pageSize)
    },
    pageChangeHandler(page) {
      this.$emit('page-changed', page)
    }
  }
}
</script>

<style lang="scss" scoped>
  .el-pagination {
    padding-top: 10px;
  }
</style>

```
组件效果
![](./image/2.8.png)
##### 2.6 selects组件
该组件主要是对左右布局的封装。
```javascript
<template>
  <div class="full">
    <div class="left">
      <slot name="left"/>
    </div>
    <div class="right">
      <slot/>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style lang="scss" scoped>
  .full {
    width: 100%;
    .left {
      display: inline;
      width: 50%;
      min-height: 32px;
      line-height: 32px;
    }
    .right {
      display: inline;
      width: 50%;
      min-height: 32px;
      line-height: 32px;

      .el-select {
        float: right;
        margin-left: 15px;
      }
    }
  }
</style>

```
组件效果
![](./image/2.9.png)
