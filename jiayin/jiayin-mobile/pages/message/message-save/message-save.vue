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
                    <picker @change="bindPickerChange" :value="msgTypeChildren.selectedIndex" :range="msgTypeChildren.types" range-key="typeName" mode="selector">
						<view class="input-row border">
							<text class="title">具体分类：</text>
							<view class="uni-input">{{msgTypeChildren.types[msgTypeChildren.selectedIndex].typeName}}</view>
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
	import * as publishAPI from '@/api/publish.js';
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
					selectedIndex: 0,
				},
				msgTypeChildren: {
					types: [{
						id: '',
						typeName: '',
						money: '',
						expiryDate: null
					}],
					selectedIndex: 0,
				},
				array: ['中国', '美国', '巴西', '日本'],
				index: 0,
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
					msgTypeId: null, //消息类型
					msgTypeName: null, //消息类型
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
		console.log(option)
			const that = this;
			if (option.messageId) {
				messageAPI.findById(option.messageId, (data) => {
					this.message = data.data;
					msgTypeAPI.findById(this.message.msgTypeId, (msgTypeData) => {
						that.msgType.types = [msgTypeData.data]
					})
				})
			
			}

			if (option.msgTypeId) {
				this.msgType.types = [{
					id: option.msgTypeId,
					typeName: option.msgTypeName
				}]
				this.initMsgTypeChildren(option.msgTypeId)
			}
		},
		methods: {
			     bindPickerChange: function(e) {
			            console.log('picker发送选择改变，携带值为', e.target.value)
			            this.msgTypeChildren.selectedIndex = e.target.value
			        },
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
			prepareMessage() {
				this.message.msgTypeId = this.msgTypeChildren.types[this.msgTypeChildren.selectedIndex].id
				if (!this.validate("title") &&
					!this.validate("content") &&
					!this.validate("expareTime") &&
					!this.validate("contacts") &&
					!this.validate("contactPhone")) {
					return true
				}

				return false;
			},
			initMsgTypeChildren(msgTypeId){
				msgTypeAPI.selectChildren(msgTypeId, (msgTypeChildrenData) => {
					this.msgTypeChildren.types = msgTypeChildrenData.data
				})
			},
			saveMessage() {
				debugger
				if (this.prepareMessage()) {
					this.message.status=1
					publishAPI.publish(this.message, (data) => {
						uni.showToast({
							duration: 2000,
							title: '发布消息成功'
						});
					})
				}

			},
			saveDraftMessage() {
				if (this.prepareMessage()) {
					    this.message.status=3
						publishAPI.saveDraft(this.message, (data) => {
							uni.navigateBack()
							uni.showToast({
								duration: 2000,
								title: '保存草稿成功'
							});
						})
				}
			}
		}
	}
</script>

<style>
	.validate-text {
		color: #ff0000;
	}
	m-input {
		width: 100%;
		display: flex;
		flex: 1;
	}
	
	.content {
		display: flex;
		flex: 1;
		flex-direction: column;
		background-color: #efeff4;
		padding: 10px;
	}
	
	.input-group {
		background-color: #ffffff;
		margin-top: 20px;
		position: relative;
	}
	
	.input-group::before {
		position: absolute;
		right: 0;
		top: 0;
		left: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}
	
	.input-group::after {
		position: absolute;
		right: 0;
		bottom: 0;
		left: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}
	
	.input-row {
		display: flex;
		flex-direction: row;
		position: relative;
		font-size: 18px;
		line-height: 40px;
	}
	
	.input-row .title {
		padding-left: 15px;
	}
	
	.input-row.border::after {
		position: absolute;
		right: 0;
		bottom: 0;
		left: 8px;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}
	
	.uni-textarea{
		width:100%;
		background:#FFF;
	}
	.uni-textarea textarea{
		width:96%;
		padding:18rpx 2%;
		line-height:1.6;
		font-size:28rpx;
		height:150rpx;
	}
	
	.btn-row {
		margin-top: 25px;
		padding: 10px;
	}
</style>
