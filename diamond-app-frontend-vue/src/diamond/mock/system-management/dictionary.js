import Mock from 'mockjs'
import { param2ObjForSplit, deepMerge, deepClone, fieldQueryLike, fieldQuery, sortArray } from '@/utils'
import Utils from '../utils'
import { depts, deptsTree } from './dept'

const mockConfig = {
  'id|1': Utils.id,
  'parentId': null, // 上级ID
  'flag|1': Utils.flag, // 是否删除
  'state|1': Utils.state, // 是否启用
  'index': '@increment', // 编号
  'dataItemTypeId|1': Utils.id,
  'dataItemTypeName|1': ['部门类型', '性别', '中文'], // 名称
  'code|1': ['SBU', 'BU', 'man', 'women'], // 名称
  'codeIndex': '@increment', // 编号
  remark: '@cparagraph(1, 3)', // 备注
  name: '@cword(5, 10)', // 名称
  children: [],

  createdBy: '@increment', // 创建人
  createdDate: +Mock.Random.date('T'), // 创建时间
  modifiedBy: '@increment', // 最后修改人
  modifiedDate: +Mock.Random.date('T') // 最后修改时间
}

export const dictionaries = []

const length = Mock.mock('@integer(5, 20)')

for (let i = 0; i < length; i++) {
  const dictionary = Mock.mock(mockConfig)
  dictionary.parentId = null
  dictionaries.push(createDictionaryTree(dictionary))
}

function createDictionaryTree(dictionary) {
  dictionary.children = []
  if (Mock.mock('@boolean')) {
    const children = Mock.mock(mockConfig)
    children.parentId = dictionary.id
    dictionary.children.push(children)
    // createDictionaryTree(children)
  }
  return dictionary
}

export default {
  queryPage: config => {
    const params = JSON.parse(config.body)
    const query = params.filters
    const queryResult = deepClone(fieldQueryLike(dictionaries, query))
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
    const dictionariesQuery = []
    const length = Mock.mock('@integer(2, 4)')
    for (let i = 0; i < length; i++) {
      const dictionary = Mock.mock(mockConfig)
      dictionary.parentId = null
      dictionariesQuery.push(createDictionaryTree(dictionary))
    }
    const params = JSON.parse(config.body)
    const query = params.filters
    const queryResult = deepClone(fieldQuery(dictionariesQuery, query))
    return {
      code: 1,
      message: '操作成功',
      data: {
        total: queryResult.length,
        pageSize: params.pageSize,
        page: params.page,
        list: queryResult.slice((params.page - 1) * params.pageSize, params.page * params.pageSize)
      }
    }
  },
  queryById: config => {
    // console.log(config)
    const params = param2ObjForSplit(config.url)
    const dictionary = dictionaries[dictionaries.findIndex(item => { return item.id === params.id })]
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
    dictionaries.push(dictionary)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  },
  edit: config => {
    // console.log(config)
    const params = JSON.parse(config.body)
    const dictionary = dictionaries[dictionaries.findIndex(item => { return item.id === params.id })]
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
    dictionaries.splice(dictionaries.findIndex(item => { return item.id === params.id }), 1)
    return {
      code: 1,
      message: '操作成功',
      data: {}
    }
  }
}
