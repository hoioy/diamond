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
        total: 0,
        pageSize: 10,
        page: 1,
        list: [],
        filters: [],
        sorts: []
      })
    },
    resetHandler() {
      this.initQueryCriteria(this.queryCriteria)
      this.initPagination(this.pagination) // todo 这里应该需要初始化
      this.executeQueryPage()
    },
    queryHandler() {
      // todo 这里应该需要初始化
      // console.info('this.pagination------')
      // console.info(this.pagination)
      // console.info('this.pagination------')

      this.initPagination(this.pagination)
      this.executeQueryPage()
    },
    createQueryParams() {
      this.pagination.filters = {}
      Object.keys(this.queryCriteria).forEach(key => {
        const value = this.queryCriteria[key]
        if (value) {
          if (typeof value === 'object') {
            this.pagination.filters[key] = deepClone(value)
          } else {
            this.pagination.filters[key] = value
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
    queryResultHandler(result) {
      // console.info(deepMerge(this.pagination, result))
      return deepMerge(this.pagination, result)
    },
    pageSizeChangeHandler(pageSize) {
      this.pagination.pageSize = pageSize
      this.pagination.page = 1
      this.executeQueryPage()
    },
    pageChangeHandler(page) {
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
    delHandler() {
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
