<template>
  <el-form ref="formItem" :model="formItem" :rules="rules" label-width="100px">
    <el-row>
      <el-col>
        <el-form-item label="课程名称:" prop="courseName">
          <el-input v-model="formItem.courseName" placeholder="请输入课程名称"/>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
export default {
  name: 'Add',
  data() {
    return {
      disabled: false,
      formItem: {
        courseName: ''
      },
      arrayDataStatus: false,
      // 检测
      rules: {
        courseName: [
          { required: true, message: '请输入课程名称', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm() {
      var status = false
      this.$refs['formItem'].validate(valid => {
        if (valid) {
          status = true
        } else {
          status = false
        }
      })
      return status
    }
  }
}
</script>
