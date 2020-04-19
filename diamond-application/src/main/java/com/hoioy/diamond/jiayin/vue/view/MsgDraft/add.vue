<template>
  <el-form ref="formItem" :model="formItem" :rules="rules" label-width="100px">
    <el-row>
      <el-col>
        <el-form-item label="标题:" prop="title">
          <el-input v-model="formItem.title"   placeholder=" 请输入标题"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="消息类型:" prop="msgType">
          <el-input v-model="formItem.msgType"   placeholder=" 请输入消息类型"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="消息内容:" prop="content">
          <el-input v-model="formItem.content"   placeholder=" 请输入消息内容"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="浏览次数:" prop="views">
          <el-input v-model="formItem.views"   placeholder=" 请输入浏览次数"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="(带交易 已完成):" prop="status">
          <el-input v-model="formItem.status"   placeholder=" 请输入(带交易 已完成)"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="联系人:" prop="contacts">
          <el-input v-model="formItem.contacts"   placeholder=" 请输入联系人"/>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <el-form-item label="联系电话:" prop="contactPhone">
          <el-input v-model="formItem.contactPhone"   placeholder=" 请输入联系电话"/>
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
                  title: '',
                  msgType: '',
                  content: '',
                  views: '',
                  status: '',
                  contacts: '',
                  contactPhone: ''
              },
              arrayDataStatus: false,
                // 检测
              rules: {
                   title: [
                      {required: true, message: '请输入标题', trigger: 'blur'}
                   ],
                   msgType: [
                      {required: true, message: '请输入消息类型', trigger: 'blur'}
                   ],
                   content: [
                      {required: true, message: '请输入消息内容', trigger: 'blur'}
                   ],
                   views: [
                      {required: true, message: '请输入浏览次数', trigger: 'blur'}
                   ],
                   status: [
                      {required: true, message: '请输入(带交易 已完成)', trigger: 'blur'}
                   ],
                   contacts: [
                      {required: true, message: '请输入联系人', trigger: 'blur'}
                   ],
                   contactPhone: [
                      {required: true, message: '请输入联系电话', trigger: 'blur'}
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
