<template>
  <el-form ref="formItem" :model="formItem" :rules="rules" label-width="100px">
    <el-row>
      <el-col>
        <el-form-item label="消息表主键键:" prop="msgId">
          <el-input v-model="formItem.msgId"   placeholder=" 请输入消息表主键键"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="用户id:" prop="userId">
          <el-input v-model="formItem.userId"   placeholder=" 请输入用户id"/>
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
                  msgId: '',
                  userId: ''
              },
              arrayDataStatus: false,
                // 检测
              rules: {
                   msgId: [
                      {required: true, message: '请输入消息表主键键', trigger: 'blur'}
                   ],
                   userId: [
                      {required: true, message: '请输入用户id', trigger: 'blur'}
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
