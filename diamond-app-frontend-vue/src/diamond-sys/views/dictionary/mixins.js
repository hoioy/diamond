import { mapGetters } from 'vuex'
import * as DictionaryAPI from '../../api/system-management/dictionary'
import * as DictionaryTypeAPI from '../../api/system-management/dictionary-type'

export default {
  props: {
    category: {
      required: true,
      type: String,
      default: () => {}
    },
    detail: {
      required: false,
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      parentDictionaryName: '',
      dataItemTypeId: ' ',
      dictionaryTypeList: []
    }
  },
  computed: {
    ...mapGetters([
      'dictionaries'
    ])
  },
  methods: {
    initForm() {
      return {
        id: null,
        token: null,
        parentId: null,
        state: '1',
        name: '',
        dataItemTypeId: '',
        dataItemTypeName: '',
        code: '',
        codeIndex: null,
        remark: ''
      }
    },
    initRules() {
      const isNum = (rule, value, callback) => {
        const age = /^[0-9]*$/
        if (!age.test(value)) {
          callback(new Error('只能为数字'))
        } else if (value >= 1000000) {
          callback(new Error('数字长度超出限制，请输入6位以下数字'))
        } else {
          callback()
        }
      }
      const isEmpty = (rule, value, callback) => {
        if (value.split(' ').join('').length === 0) {
          callback(new Error('输入变量不可为空'))
        } else {
          callback()
        }
      }
      return {
        name: [{
          required: true, message: '请输入字典名称', trigger: 'blur'
        }, {
          min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }],
        dataItemTypeId: [{
          required: true, message: '请选择字典分类', trigger: 'blur'
        }],
        code: [{
          required: true, message: '请选择字典规则码', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }],
        codeIndex: [{
          required: true, message: '请输入字典排序号', trigger: 'blur'
        },
        { validator: isNum, trigger: 'blur' }
        ],
        remark: [
          {
            min: 0, max: 200, message: '需要小于200个字符', trigger: 'blur'
          }]
      }
    },
    getParentDictionaryName(id) {
      this.parentDictionaryName = ''
      if (!id || id === 'root') return
      DictionaryAPI.queryDictionaryById(id).then(dictionary => {
        this.parentDictionaryName = dictionary.name
        DictionaryTypeAPI.queryDictionaryTypeById(dictionary.dataItemTypeId).then(dictionaryType => {
          this.dataItemTypeId = dictionaryType.typeName
        })
      })
    }
  }
}
