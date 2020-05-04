<template>
	<view class="content">
		<view class="input-group">
			<view class="input-row border">
				<text class="title">姓名：</text>
				<m-input type="text" focus clearable v-model="student.studentName" placeholder="请输入姓名"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">性别：</text>
				<m-input type="text" clearable v-model="student.studentSex" placeholder="请输入性别"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">班级：</text>
				<m-input type="text" clearable v-model="student.studentClass" placeholder="请输入班级"></m-input>
			</view>
		</view>
		<view class="btn-row">
			<button type="primary" class="primary" @tap="saveStudent">保存</button>
		</view>
	</view>
</template>

<script>
	import * as messageAPI from '@/api/message.js';
	import mInput from '../../../components/m-input.vue';

	export default {
		components: {
			mInput
		},
		data() {
			return {
				student: {
					id: null,
					parentId: null,
					createdBy: null,
					createdDate: null,
					modifiedBy: null,
					modifiedDate: null,
					flag: null,
					remark: null,
					version: null,
					token: null,
					children: null,
					studentName: "",
					studentSex: "",
					studentClass: "",
					parameterJson: null
				}
			};
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			if (option.id) {
				messageAPI.findById(option.id).then(student => {
					var [error, res] = student;
					if (res.data.code == 200) {
						console.log(res.data.data)
						this.student = res.data.data;
					}
				})
			}
		},
		methods: {
			saveStudent() {
				if (this.student.id) {
					messageAPI.updateStudent({
						id: this.student.id,
						studentName: this.student.studentName,
						studentSex: this.student.studentSex,
						studentClass: this.student.studentClass
					}).then(data => {
						var [error, res] = data;
						if (res.data.code == 200) {
							uni.showToast({
								title: '保存成功'
							});
						}
					})
				} else {
					messageAPI.addStudent({
						studentName: this.student.studentName,
						studentSex: this.student.studentSex,
						studentClass: this.student.studentClass
					}).then(data => {
						var [error, res] = data;
						if (res.data.code == 200) {
							uni.showToast({
								title: '保存成功'
							});
						}
					})
				}

			}
		}
	}
</script>

<style lang="scss">

</style>
