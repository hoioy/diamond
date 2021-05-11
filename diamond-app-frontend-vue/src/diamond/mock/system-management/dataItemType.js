import Mock from 'mockjs'
import { param2ObjForSplit, deepMerge, deepClone, fieldQueryLike, sortArray } from '@/utils'
import Utils from '../utils'

const mockConfig = {
  'id|1': Utils.id,
  'flag|1': Utils.flag, // 是否删除
  typeName: '@cword(5, 10)', // 名称
  'token': '@cparagraph(1, 3)', // 编号
  remark: '@cparagraph(1, 3)', // 备注
  createdBy: '@increment', // 创建人
  createdDate: +Mock.Random.date('T'), // 创建时间
  modifiedBy: '@increment', // 最后修改人
  modifiedDate: +Mock.Random.date('T') // 最后修改时间
}

export const dataItemTypes = []

const dataItemType = Mock.mock(mockConfig)
dataItemType.flag = 1
dataItemTypes.push(dataItemType)

// 单级字典
for (let i = 0; i < 13; i++) {
  const item = Mock.mock(mockConfig)
  item.flag = 1
  dataItemTypes.push(item)
}

export default {
  queryPage: config => {
    const params = JSON.parse(config.body)
    const query = params.filters
    const queryResult = deepClone(fieldQueryLike(dataItemTypes, query))
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
    // const params = JSON.parse(config.body)
    const query = {}
    const queryResult = deepClone(fieldQueryLike(dataItemTypes, query))
    // params.sorts.forEach(sort => {
    //   // 前端目前无法实现多字段排序，因此排序以最后一个字段为准
    //   sortArray(queryResult, sort.field, sort.value === 'desc')
    // })
    return {
      code: 1,
      message: '操作成功',
      data: queryResult
    }
  },
  queryById: config => {
    // console.log(config)
    const params = param2ObjForSplit(config.url)
    const dictionary = dataItemTypes[dataItemTypes.findIndex(item => { return item.id === params.id })]
    return {
      code: 1,
      message: '操作成功',
      data: dictionary
    }
  },
  add: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    const dictionary = Mock.mock(mockConfig)
    params.id = dictionary.id
    deepMerge(dictionary, params)
    dataItemTypes.push(dictionary)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  },
  edit: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    const dictionary = dataItemTypes[dataItemTypes.findIndex(item => { return item.id === params.id })]
    deepMerge(dictionary, params)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  },
  del: config => {
    // console.log(config)
    const params = param2ObjForSplit(config.url)
    dataItemTypes.splice(dataItemTypes.findIndex(item => { return item.id === params.id }), 1)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  }
}
