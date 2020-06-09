<template>
	<view class="content">
		<view class="input-group">
			<picker disabled @change="bindMsgTypeChange" :value="msgType.selectedIndex" :range="msgType.types" range-key="typeName">
				<view class="input-row border">
					<text class="title">消息类型：</text>
					<view>{{msgType.types[msgType.selectedIndex].typeName}}</view>
				</view>
			</picker>
		</view>
		<view class="input-group">
			<view class="input-row border">
				<text class="title">标题：</text>
				<m-input type="text" clearable v-model="message.title" @focus="onFocus('title')" @blur="validate('title')"
				 placeholder="请输入标题"></m-input>
				<text class="validate-text" v-if="validateStatus.title">格式不正确</text>
			</view>
		</view>

		<view class="input-group">
			<picker mode="date" :value="message.expareTime" @change="bindDateChange">
				<view class="input-row border">
					<text class="title">过期日期：</text>
					<view>{{message.expareTime}}</view>
					<text class="validate-text" v-if="validateStatus.expareTime">请选择日期</text>
				</view>
			</picker>
		</view>
		<view class="input-group">
			<view class="input-row border">
				<text class="title">联系人：</text>
				<m-input type="text" clearable v-model="message.contacts" @focus="onFocus('contacts')" @blur="validate('contacts')"
				 placeholder="请输入联系人"></m-input>
				<text class="validate-text" v-if="validateStatus.contacts">格式不正确</text>
			</view>
			<view class="input-row border">
				<text class="title">联系电话：</text>
				<m-input type="text" clearable v-model="message.contactPhone" @focus="onFocus('contactPhone')" @blur="validate('contactPhone')"
				 placeholder="请输入联系电话"></m-input>
				<text class="validate-text" v-if="validateStatus.contactPhone">格式不正确</text>
			</view>
		</view>
		<view class="input-group">
			<view class="input-row border">
				<text class="title">消息内容：</text>
				<text class="validate-text" v-if="validateStatus.content">请填写消息内容</text>
			</view>
			<view class="uni-textarea">
				<textarea placeholder="请输入消息内容" v-model="message.content" @focus="onFocus('content')" @blur="validate('content')"
				 auto-height maxlength="1000"></textarea>
			</view>
		</view>
		<view class="btn-row">
			<button type="default" @tap="saveDraftMessage">保存为草稿</button>
		</view>
		<view class="btn-row">
			<button type="primary" class="primary" @tap="saveMessage">发布</button>
		</view>
	</view>
</template>

<script>
	import * as messageAPI from '@/api/message.js';
	import * as draftAPI from '@/api/draft.js';
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
				validateStatus: {
					title: false, //标题
					content: false, //消息内容
					expareTime: false, //过期时间
					contacts: false, //联系人
					contactPhone: false, //联系电话
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
			const that = this;
			if (option.draftId) {
				draftAPI.findById(option.draftId, (data) => {
					this.message = data.data;
					msgTypeAPI.findById(data.data.msgType, (msgTypeData) => {
						that.msgType.types = [msgTypeData.data]
					})
				})
			}

			if (option.msgTypeId) {
				this.msgType.types = [{
					id: option.msgTypeId,
					typeName: option.msgTypeName
				}]
			}
		},
		methods: {
			onFocus(type) {
				this.validateStatus[type] = false
			},
			validate(type) {
				switch (type) {
					case "title":
						this.validateStatus.title = (this.message.title == "")
						return this.validateStatus.title;
					case "content":
						this.validateStatus.content = (this.message.content == "")
						return this.validateStatus.content;
					case "expareTime":
						this.validateStatus.expareTime = (this.message.expareTime == "")
						return this.validateStatus.expareTime;
					case "contacts":
						this.validateStatus.contacts = (this.message.contacts == "")
						return this.validateStatus.contacts;
					case "contactPhone":
						this.validateStatus.contactPhone = (this.message.contactPhone == "")
						return this.validateStatus.contactPhone;
				}
			},
			bindMsgTypeChange: function(e) {
				this.msgType.selectedIndex = e.detail.value
			},
			bindDateChange: function(e) {
				this.validateStatus.expareTime = false
				this.message.expareTime = e.detail.value
			},
			// initMsgType() {
			// 	var that = this;
			// 	msgTypeAPI.selectParent((data) => {
			// 		that.msgType.types = data.data
			// 	})
			// },
			prepareMessage() {
				this.message.msgType = this.msgType.types[this.msgType.selectedIndex].id
				if (!this.validate("title") &&
					!this.validate("content") &&
					!this.validate("expareTime") &&
					!this.validate("contacts") &&
					!this.validate("contactPhone")) {
					return true
				}

				return false;
			},
			saveMessage() {
				if (this.prepareMessage()) {
					draftAPI.publishMessage(this.message, (data) => {
						uni.showToast({
							duration: 2000,
							title: '发布消息成功'
						});
					})
				}

			},
			saveDraftMessage() {
				if (this.prepareMessage()) {
					if (this.message.id) {
						draftAPI.updateMessage(this.message, (data) => {
							uni.navigateBack()
							uni.showToast({
								duration: 2000,
								title: '修改草稿成功'
							});
						})
					} else {
						draftAPI.addMessage(this.message, (data) => {
							uni.navigateBack()
							uni.showToast({
								duration: 2000,
								title: '保存为草稿成功'
							});
						})
					}
				}
			}
		}
	}
</script>

<style>
	.validate-text {
		color: #ff0000;
	}
</style>
