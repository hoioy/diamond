export default {
  methods: {
    initForm() {
      return {
        id: null,
        token: null,
        typeName: '',
        remark: ''
      }
    },
    initRules() {
      const isEmpty = (rule, value, callback) => {
        if (value.split(' ').join('').length === 0) {
          callback(new Error('输入变量不可为空'))
        } else {
          callback()
        }
      }
      return {
        typeName: [{
          required: true, message: '请输入字典分类名称', trigger: 'blur'
        }, {
          min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur'
        }, { validator: isEmpty, trigger: 'blur' }],
        remark: [
          {
            min: 0, max: 200, message: '需要小于200个字符', trigger: 'blur'
          }]
      }
    }
  }
}
