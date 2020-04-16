<template>
  <el-form ref="formItem" :model="formItem" :rules="rules" label-width="100px">
    <el-row>
      <el-col>
        <el-form-item label="类型名称:" prop="typeName">
          <el-input v-model="formItem.typeName"   placeholder=" 请输入类型名称"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="价格:" prop="money">
          <el-input v-model="formItem.money"   placeholder=" 请输入价格"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="有效期:" prop="expiryDate">
          <el-input v-model="formItem.expiryDate"   placeholder=" 请输入有效期"/>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
    name: 'Add',
    data(){
        return {
              disabled: false,
              formItem: {
                  typeName: '',
                  money: '',
                  expiryDate: ''
              },
              arrayDataStatus: false,
                // 检测
              rules: {
                   typeName: [
                      {required: true, message: '请输入类型名称', trigger: 'blur'}
                   ],
                   money: [
                      {required: true, message: '请输入价格', trigger: 'blur'}
                   ],
                   expiryDate: [
                      {required: true, message: '请输入有效期', trigger: 'blur'}
                   ]
              }
        }
    },
    methods: {
        submitForm(){
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
