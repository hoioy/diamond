# TDF分页原理
TDF 快速入门参考
太极云学堂创新院上传的TDF开发框架快速入门视频http://tj.yunxuetang.cn/search/index.htm?kw=tdf 里面有详细的demo讲解。
在看此博客前请先了解组件的封装
[vue怎么封装一个组件](http://tech.taiji.com.cn/#/blog/view/90cf4601a2a64deb8a8c395dd2190084 "vue怎么封装一个组件")

[vue 通用组件的封装 ](http://tech.taiji.com.cn/#/blog/view/eddbd12801e9454cb1844dfe680eeed7 "vue 通用组件的封装 ")

首先需要大家了解在TDF项目中路径别名进行了配置，配置在vue.config.js里面
![](http://192.168.99.76:8083/upload/975d2ceec7554ca39d966f3721e17b57_-_图片.png)
在TDF路径引用中我们会使用@表示路径src/tdf，以此类推 如上图。
TDF分页部分因为在TDF系统所有的query页面都有，属于公共组件，所以TDF对分页进行了简单的封装，所有业务相关的公共组件都放在src/tdf/views/common/components/下面，那么我们封装的paginations也在当前路径下。
![](http://192.168.99.76:8083/upload/13b04243f5d5456182d05b349c705f0a_-_图片.png)
如图，上图右侧我们可以看到封装的组件，其实他使用的是element-ui的pagination https://element.eleme.io/#/zh-CN/component/pagination 只是把本系统用到的一些属性和事件在paginatons组件进行一个统一的封装。组件的封装以及在父组件的对属性和事件的使用请参考上面讲的组件封装博客。在此不在重复赘述。需要提醒大家的是，有的人可能会觉得没有找到组件的申明，其实是因为我们TDF做了简化操作，对所有组件进行统一注册，大家可以打开 src/main.js
看到下面这两段代码
![](http://192.168.99.76:8083/upload/8d414cbe38ee4172bdc3a22bfc1f6777_-_图片.png)
![](http://192.168.99.76:8083/upload/b662af034fc24119880ebf89b5ed1e3f_-_图片.png)
这是一种很好的统一注册的方法。大家在以后也可也对组件进行统一注册。
那么剩下的就是paninations组件注册后在父组件中的使用了，此处不在赘述。
然后由于所有模块均有query页面，TDF对query页进行了简单的封装，代码在src/tdf/views/common/mixins/BaseQueryPageForm.js里面
![](http://192.168.99.76:8083/upload/512719a7813f4ccb93be98b8e2ffe467_-_图片.png)
对于vue mixins的使用请参考官网 https://cn.vuejs.org/v2/api/#mixins 
我们可以看到在BaseQueryPageForm.js里面有这样的描述
```javascript
/**
 * 适用于一个一个查询对象+一个分页结果集展示的简单组件
 * 约束：
 * 寄主对象中应具有：
 * --this.queryCriteria : 表单查询对象封装
 * --this.initQueryCriteria() : 表单查询对象初始化函数
 * --this.executeQueryPage : 分页表单查询接口
 */
 import { deepMerge, deepClone } from '@/utils'

export default {
  data() {
    const pagination = this.initPagination()
    return {
      pagination: pagination
    }
  },
  activated() {
    this.executeQueryPage()
  },
  methods: {
    initPagination(pagination = {}) {
      return deepMerge(pagination, {
        total: 0,   //总条目数
        pageSize: 10,  //每页最大显示的条目数
        page: 1,    //页数起始页
        list: [],    //条目列表
        filters: [],  //过滤列表
        sorts: []   //条目的排序
      })
    },
    resetHandler() {   //重置按钮方法
      this.initQueryCriteria(this.queryCriteria)
      this.initPagination(this.pagination)
      this.executeQueryPage()
    },
    queryHandler() {  //query操作
      this.initPagination(this.pagination)
      this.executeQueryPage()
    },
    createQueryParams() {  //按照过滤条件查询函数
      this.pagination.filters = []
      Object.keys(this.queryCriteria).forEach(key => {
        const value = this.queryCriteria[key]
        if (value) {
          if (typeof value === 'object') {
            this.pagination.filters.push({ field: key, value: deepClone(value) })
          } else {
            this.pagination.filters.push({ field: key, value: value })
          }
        }
      })
      return {
        page: this.pagination.page,
        pageSize: this.pagination.pageSize,
        filters: this.pagination.filters,
        sorts: this.pagination.sorts
      }
    },
    createQueryParamsForRole(data) {  
      return {
        roleId: data
      }
    },
    queryResultHandler(result) {  //查询结果显示函数
      return deepMerge(this.pagination, result)
    },
    pageSizeChangeHandler(pageSize) {  //监听pageSize的变化
      this.pagination.pageSize = pageSize
      this.pagination.page = 1
      this.executeQueryPage()
    },
    pageChangeHandler(page) {   //监听page变化
      this.pagination.page = page
      this.executeQueryPage()
    },
    sortChangeHandler({ column, prop, order }) {
      // ElementUI 目前并不支持远端多字段排序
      // sorts数组设计的目的是为了支持ElementUI后续拓展
      this.pagination.sorts = []
      if (column) {
        this.pagination.sorts.push({
          field: prop,
          value: order === 'descending' ? 'desc' : 'asc'
        })
      }
      this.pagination.page = 1
      this.executeQueryPage()
    },
    delHandler() {  //删除操作
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.customDelHandler) {
          this.customDelHandler()
        } else {
          this.$message({
            type: 'info',
            message: '您未定义删除逻辑处理方法customDelHandler'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}

```
分页pagination用到的创建参数
```javascript
initPagination(pagination = {}) {
      return deepMerge(pagination, {
        total: 0,  //总数目
        pageSize: 10,  //每页最大的条目数
        page: 1,    // 从第一页开始
        list: [],   //数据条目列表
        filters: [],  // 过滤 参数
        sorts: []     //排序 
      })
    },
```
前端传给后端的参数 
```javascript
 return {
        page: this.pagination.page,
        pageSize: this.pagination.pageSize,
        filters: this.pagination.filters,
        sorts: this.pagination.sorts
      }
```
paginations组件上绑定的事件 在父组件中的实现
```javascript
 pageSizeChangeHandler(pageSize) {
      this.pagination.pageSize = pageSize
      this.pagination.page = 1
      this.executeQueryPage()
    },
    pageChangeHandler(page) {
      this.pagination.page = page
      this.executeQueryPage()
    },
```
我们可以看到有这么一段代码
![](http://192.168.99.76:8083/upload/e38eb4f5916846999d104a60e3f43028_-_图片.png)
这个代码表示在vue生命周期的activated（）钩子函数 执行 this.executeQueryPage() 函数，那我就是执行query页面。
eg.打开user模块的query页面，可以看到对BaseQueryPageForm的引入以及使用 包括发起请求和返回结果。
![](http://192.168.99.76:8083/upload/66f5b782a7834be7a01d15462fbe7b17_-_图片.png)
![](http://192.168.99.76:8083/upload/ed36a4272e76465cbfb68880bb91ca44_-_图片.png)
到此 ，分页原理已全部梳理完。



大家在使用前端有任何问题都可以在gitlab上issue提issues或者您可以在本博客回复，感谢您对TDF的支持和信任，相信我们会越来越好。