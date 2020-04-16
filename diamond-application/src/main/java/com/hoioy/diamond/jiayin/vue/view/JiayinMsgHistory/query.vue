<template>
    <el-row>
        <el-card>
            <el-col :span="24">
                <el-form :model="queryCriteria" label-width="150px">
                    <el-col :span="8">
                        <el-form-item label="消息表主键键:" prop="msgId">
                            <el-input v-model="queryCriteria.msgId"   placeholder=" 请输入消息表主键键"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="用户id:" prop="userId">
                            <el-input v-model="queryCriteria.userId"   placeholder=" 请输入用户id"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <flex-center>
                            <el-button round type="info" @click="resetHandler">
                                重置
                            </el-button>
                            <el-button round type="primary" @click="queryHandler">
                                查询
                            </el-button>
                        </flex-center>
                    </el-col>
                </el-form>
            </el-col>
        </el-card>
        <el-col :span="24" style="margin: 10px 0px;">
            <button-right>
                观看历史列表
                <template slot="button">
                    <el-button-group>
                        <el-button v-if="hasPerm('${tableInfo.getEntityName()}/add') || hasPerm('${tableInfo.getEntityName()}/*')" type="primary" @click="openDialog('add')">
                            新增
                        </el-button>
                        <el-button v-if="(selected)" type="primary" @click="delHandler">
                            删除
                        </el-button>
                        <el-button
                          v-if="(selected)"
                          type="primary"
                          @click="openDialog('edit',selected)"
                        >
                            编辑
                        </el-button>
                        <el-button
                          v-if="(selectedLogs.length > 0 )"
                          type="primary"
                          @click="batchDelHandler"
                        >
                            批量删除
                        </el-button>
                    </el-button-group>
                </template>
            </button-right>
        </el-col>
        <el-col :span="24">
            <el-table
               :data="pagination.list"
               highlight-current-row
               stripe
               border
               @current-change="(row) => { selected = row }"
               @sort-change="sortChangeHandler"
               @selection-change="clickLogCheckboxHandler"
            >
                <el-table-column type="selection" label="全选"/>

                    <el-table-column
                      :show-overflow-tooltip="true"
                      prop="msgId"
                      label="消息表主键键"
                      sortable="custom"
                      align="center"
                    />
                    <el-table-column
                      :show-overflow-tooltip="true"
                      prop="userId"
                      label="用户id"
                      sortable="custom"
                      align="center"
                    />
            </el-table>
            <pagination
               :pagination="pagination"
               @page-size-changed="pageSizeChangeHandler"
               @page-changed="pageChangeHandler"
            />
        </el-col>
        <el-dialog
           :visible.sync="dialogVisible"
           :title="title"
           :show-close="false"
           :close-on-click-modal="false"
           :close-on-press-escape="false"
           width="45%"
           top="20px"
        >
           <add ref="add"/>
           <span slot="footer" class="dialog-footer">
        <el-button @click="colesDialog">取 消</el-button>
        <el-button v-if="editStatus" type="primary" @click="addSubmit">提 交</el-button>
        <el-button v-else type="primary" @click="updateSubmit">提 交</el-button>
      </span>
        </el-dialog>
    </el-row>
</template>
<script>
import { deepMerge } from '@/utils'
import BaseQueryPageForm from '@/views/common/mixins/BaseQueryPageForm'
import * as JiayinMsgHistoryAPI from '@src/demo/api/JiayinMsgHistory'
import add from './add'

export default {
    components: {
        add
    },
    mixins: [BaseQueryPageForm],
    data() {
        const queryCriteria = this.initQueryCriteria()
        return {
            queryCriteria: queryCriteria,
            selected: null,
            selectedLogs: [],
            dialogVisible: false,
            title: '',
            editStatus: true // 判断是修改还是添加
        }
    },
    activated() {
        this.selected = null
    },
    mounted() {},
    methods: {
        colesDialog() {
            this.resetFormItem()
            this.dialogVisible = false
        },
        // 打开弹出框
        openDialog(type, data) {
            this.editStatus = true
            if (type === 'add') {
                this.title = '添加观看历史'
                this.$nextTick(function() {
                    this.$refs.add.disabled = false
                })
            } else {
                this.title = '修改观看历史'
                this.editStatus = false
                this.$nextTick(function() {
                    this.$refs.add.formItem = {
                        id: data.id,
                        msgId: data.msgId,
                        userId: data.userId
                    }
                    this.$refs.add.disabled = true
                })
            }
            this.dialogVisible = true
        },
        // 修改
        updateSubmit() {
            if (this.$refs.add.submitForm()) {
                const formItem = this.$refs.add.formItem
                        formItem['createdUser'] = this.$store.state.user.user.loginName
                        formItem['updatedUser'] = this.$store.state.user.user.loginName
                studentAPI
                        .update(formItem)
                        .then(data => {
                    this.$message({
                        showClose: true,
                        message: '修改成功',
                        type: 'success'
                    })
                    this.dialogVisible = false
                    this.resetFormItem()
                    this.executeQueryPage()
                })
            .catch(() => {
                    this.$message({
                        type: 'error',
                        message: '修改失败'
                    })
                })
            }
        },
        // 新增参数
        addSubmit() {
            if (this.$refs.add.submitForm()) {
                const formItem = this.$refs.add.formItem
                        formItem['createdUser'] = this.$store.state.user.user.loginName
                        formItem['updatedUser'] = this.$store.state.user.user.loginName
                studentAPI
                        .add(formItem)
                        .then(data => {
                    this.$message({
                        showClose: true,
                        message: '添加成功',
                        type: 'success'
                    })
                    this.dialogVisible = false
                    this.resetFormItem()
                    this.executeQueryPage()
                })
                // .catch(() => {
                //   this.$message({
                //     type: 'error',
                //     message: ''
                //   })
                // })
            }
        },
        // 清除formItem参数
        resetFormItem() {
            this.$refs.add.formItem = {
                msgId: '',
                userId: ''
            }
        },
        initQueryCriteria(form = {}) {
            return deepMerge(form, {
                msgId: '',
                userId: ''
            })
        },
        executeQueryPage() {
            studentAPI.queryPageLogs(this.createQueryParamsForsifa()).then(data => {
                this.queryResultHandler(data)
            })
        },
        delHandler() {
            studentAPI
                    .deleteById(this.selected.id)
                    .then(() => {
                this.queryHandler()
                this.$message({
                    showClose: true,
                    message: '删除成功',
                    type: 'success'
                })
            })
        .catch(() => {
                this.$message({
                    type: 'warning',
                    message: '删除失败'
                })
            })
        },
        clickLogCheckboxHandler(selection) {
            this.selectedLogs = selection
        },
        batchDelHandler() {
            this.$confirm('此操作将批量删除, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                if (this.selectedLogs.length !== 0) {
                const parameterKeys = []
                this.selectedLogs.forEach(log => {
                    parameterKeys.push(log.id)
                })
                studentAPI
                        .deleteByIds(parameterKeys)
                        .then(() => {
                    this.queryHandler()
                    this.$message({
                        showClose: true,
                        message: '删除成功',
                        type: 'success'
                    })
                })
            .catch(() => {
                    this.$message({
                        type: 'warning',
                        message: '删除失败'
                    })
                })
            } else {
                this.$message({
                    type: 'info',
                    message: '请勾选数据'
                })
            }
        })
        }
    }
}
</script>

<style lang="scss" scoped>
    /deep/ .el-card {
        border: none;
    }
    /deep/ .el-button {
        margin-left: 10px;
    }
</style>