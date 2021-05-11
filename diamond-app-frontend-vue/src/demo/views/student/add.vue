<template>
  <el-form ref="formItem" :model="formItem" :rules="rules" label-width="100px">
    <el-row>
      <el-col>
        <el-form-item label="学生班级:" prop="studentClass">
          <el-input v-model="formItem.studentClass" :disabled="disabled" placeholder="请输入学生班级"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="学生姓名:" prop="studentName">
          <el-input v-model="formItem.studentName" placeholder="请输入学生姓名"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="性别:" prop="studentSex">
          <el-select v-model="formItem.studentSex" placeholder="性别" clearable filterable>
            <el-option
                    v-for="(item, index) in allSexTypeChild"
                    :key="index"
                    :label="item.name"
                    :value="item.name"
            />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="备注:" prop="remark">
          <el-input v-model="formItem.remark" :rows="3" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>
<script>
export default {
  name: 'Add',
  props: {
    allSexTypeChild: { // 这里的datas用于接收
      type: Array // 我们接收验证的是 也可以验证别的类型
    }
  },
  data() {
    return {
      disabled: false,
      formItem: {
        studentClass: '',
        studentName: '',
        studentValue: '',
        studentSex: '',
        remark: ''
      },
      arrayDataStatus: false,
      // 检测
      rules: {
        studentClass: [
          { required: true, message: '请输入学生班级', trigger: 'blur' },
          {
            min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'change'
          }
        ],
        studentName: [
          { required: true, message: '请输入学生名称', trigger: 'blur' },
          {
            min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'change'
          }
        ],
        studentValue: [
          { required: true, message: '请输入学生成绩', trigger: 'blur' }
        ],
        studentSex: [
          { required: true, message: '请输入学生性别', trigger: 'blur' }
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
