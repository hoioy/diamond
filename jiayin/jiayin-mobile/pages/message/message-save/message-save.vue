<template>
	<view class="content">
		<view class="input-group">
			<view class="input-row border">
				<text class="title">标题：</text>
				<m-input type="text" clearable v-model="message.title" placeholder="请输入标题"></m-input>
			</view>
		</view>
		<view class="input-group">
			<picker @change="bindMsgTypeChange" :value="msgType.selectedIndex" :range="msgType.types" range-key="typeName">
				<view class="input-row border">
					<text class="title">选择类型：</text>
					<view>{{msgType.types[msgType.selectedIndex].typeName}}</view>
				</view>
			</picker>
		</view>

		<view class="input-group">
			<picker mode="date" :value="message.expareTime" @change="bindDateChange">
				<view class="input-row border">
					<text class="title">过期日期：</text>
					<view>{{message.expareTime}}</view>
				</view>
			</picker>
		</view>
		<view class="input-group">
			<view class="input-row border">
				<text class="title">联系人：</text>
				<m-input type="text" clearable v-model="message.contacts" placeholder="请输入联系人"></m-input>
			</view>
			<view class="input-row border">
				<text class="title">联系电话：</text>
				<m-input type="text" clearable v-model="message.contactPhone" placeholder="请输入联系电话"></m-input>
			</view>
		</view>
		<view class="input-group">
			<view class="input-row border">
				<text class="title">消息内容：</text>
			</view>
			<view class="uni-textarea">
				<textarea placeholder="请输入消息内容" v-model="message.content" auto-height maxlength="1000"></textarea>
			</view>
		</view>
		<view class="btn-row">
			<button type="primary" class="primary" @tap="saveMessage">保存</button>
		</view>
	</view>
</template>

<script>
	import * as messageAPI from '@/api/message.js';
	import * as msgTypeAPI from '@/api/msgType.js';
	import mInput from '@/components/m-input.vue';

	export default {
		components: {
			mInput
		},
		data() {
			return {
				msgType: {
					types: [{
						id: '',
						typeName: '',
						money: '',
						expiryDate: null
					}],
					selectedIndex: 0
				},
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
					msgType: null, //消息类型
					content: "", //消息内容
					// status: 0,//带交易 已完成
					status: "0", //带交易 已完成
					expareTime: "", //过期时间
					contacts: "", //联系人
					contactPhone: "", //联系电话
					// views: 0//浏览次数
					views: "0" //浏览次数
				}
			};
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			if (option.id) {
				messageAPI.findById(option.id, (data) => {
					this.message = data.data;
				})
			}
			this.initMsgType()
		},
		methods: {
			bindMsgTypeChange: function(e) {
				this.msgType.selectedIndex = e.detail.value
				this.message.msgType = this.msgType.types[this.msgType.selectedIndex].id
			},
			bindDateChange: function(e) {
				this.message.expareTime = e.detail.value
			},
			initMsgType() {
				var that = this;
				msgTypeAPI.selectParent((data) => {
					that.msgType.types = data.data
				})
			},
			saveMessage() {
				debugger
				if (this.message.id) {
					messageAPI.updateMessage(this.message, (data) => {
						uni.showToast({
							duration: 2000,
							title: '保存成功'
						});
					})
				} else {
					messageAPI.addMessage(this.message, (data) => {
						uni.showToast({
							duration: 2000,
							title: '保存成功'
						});
					})
				}
			},
		}
	}
</script>

<style>

</style>
