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
      return {
        typeName: [{
          required: true, message: '请输入字典分类名称', trigger: 'blur'
        }, {
          min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur'
        }]
      }
    }
  }
}
