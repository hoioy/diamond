<template>
	<view>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
<template>
	<view class="content">
		<view class="input-group">
			<view class="input-row border">
				<text class="title">标题：</text>
				<m-input type="text" focus clearable v-model="message.title" placeholder="请输入标题"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">消息类型：</text>
				<m-input type="text" clearable v-model="message.msgType" placeholder="请输入消息类型"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">消息内容：</text>
				<m-input type="text" clearable v-model="message.content" placeholder="请输入消息内容"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">消息内容：</text>
				<m-input type="text" clearable v-model="message.status" placeholder="请输入消息内容"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">过期时间：</text>
				<m-input type="text" clearable v-model="message.expareTime" placeholder="请输入过期时间"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">联系人：</text>
				<m-input type="text" clearable v-model="message.contacts" placeholder="请输入联系人"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">联系电话：</text>
				<m-input type="text" clearable v-model="message.expareTime" contactPhone="请输入联系电话"></m-input>
			</view>
		</view>
		<view class="btn-row">
			<button type="primary" class="primary" @tap="saveMessage">保存</button>
		</view>
	</view>
</template>

<script>
	import * as messageAPI from '@/api/message.js';
	import mInput from '@/components/m-input.vue';

	export default {
		components: {
			mInput
		},
		data() {
			return {
				message: {
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
					children: [],
					title: "", //标题
					// msgType: 0,//消息类型
					msgType: "0",//消息类型
					content: "",//消息内容
					// status: 0,//带交易 已完成
					status: "0",//带交易 已完成
					expareTime: "",//过期时间
					contacts: "",//联系人
					contactPhone: "",//联系电话
					// views: 0//浏览次数
					views: "0"//浏览次数
				}
			};
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			if (option.id) {
				messageAPI.findById(option.id,(data) => {
					this.message = data.data;
				})
			}
		},
		methods: {
			saveMessage() {
				if (this.message.id) {
					messageAPI.updateMessage(this.message,(data) => {
						debugger
						uni.showToast({
							duration: 2000,
							title: '保存成功'
						});
					})
				} else {
					messageAPI.addMessage(this.message,(data) => {
						debugger
						uni.showToast({
							duration: 2000,
							title: '保存成功'
						});
					})
				}
			}
		}
	}
</script>

<style>

</style>
