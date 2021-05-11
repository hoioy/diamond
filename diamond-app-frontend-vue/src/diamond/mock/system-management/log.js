import Mock from 'mockjs'
import { deepClone, fieldQueryLike, sortArray } from '@/utils'
import Utils from '../utils'

export const mockConfig = {
  'id|1': Utils.id,
  'flag|1': Utils.flag, // 是否删除
  startTime: +Mock.Random.date('T'), // 操作开始时间
  endTime: +Mock.Random.date('T'), // 结束时间
  logClassName: '@cword(5, 10)', // 类名
  logInfo: '@cword(5, 10)', // 方法描述
  logMethodName: '@cword(5, 10)', // 方法名
  logOperationType: '@cword(5, 10)', // 操作类型
  logPrimaryKey: '@cword(5, 10)',
  logTableName: '@cword(5, 10)',
  logUrl: '@cword(5, 10)',
  logUserName: '@cname', // 操作人
  module: '@cword(5, 10)', // 模块名
  remark: '@cword(5, 10)', // 备注
  info: '@cword(5, 10)', // 预留字段
  logClientIp: '@ip', // 操作IP
  logServerIp: '@ip', // 服务IP
  type: '@cword(5, 10)', // 预留字段
  createdBy: '@increment', // 创建人
  createdDate: +Mock.Random.date('T'), // 创建时间
  modifiedBy: '@increment', // 最后修改人
  modifiedDate: +Mock.Random.date('T') // 最后修改时间
}

export const logs = []

for (let i = 0; i < 20; i++) {
  logs.push(Mock.mock(mockConfig))
}

export default {
  queryPage: config => {
    const params = JSON.parse(config.body)
    const query = params.filters
    const queryResult = deepClone(fieldQueryLike(logs, query))
    params.sorts.forEach(sort => {
      // 前端目前无法实现多字段排序，因此排序以最后一个字段为准
      sortArray(queryResult, sort.field, sort.value === 'desc')
    })
    return {
      code: 1,
      message: '',
      data: {
        total: queryResult.length,
        pageSize: params.pageSize,
        page: params.page,
        list: queryResult.slice((params.page - 1) * params.pageSize, params.page * params.pageSize)
      }
    }
  },
  queryAll: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    const query = params.filters
    const queryResult = deepClone(fieldQueryLike(logs, query))
    params.sorts.forEach(sort => {
      // 前端目前无法实现多字段排序，因此排序以最后一个字段为准
      sortArray(queryResult, sort.field, sort.value === 'desc')
    })
    return {
      code: 1,
      message: '操作成功',
      data: queryResult
    }
  },
  del: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    params.forEach(id => {
      logs.splice(logs.findIndex(item => {
        return item.id === id
      }), 1)
    })
    return {
      code: 1,
      message: '操作成功',
      data: params
    }
  }
}
