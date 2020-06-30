<template>
	<view class="container">
		<uni-collapse>
			<uni-collapse-item class="container-item" :open="true" :show-animation="true" title="基础内容(必填)" thumb="/static/img/save/base.png">
				<view class='container-title'>
					<text class="validate-text" v-if="validateStatus.title">标题格式不正确</text>
				</view>
				<view class="container-input-wrapper">
					<input class="container-input" placeholder="(请在此处输入信息标题)" v-model="message.title" @focus="onFocus('title')" @blur="onValidate('title')" />
					<text class="container-input-icon" @click="cleartInput('title')">&#xe434;</text>
				</view>
				<view class='container-title'>
					<!-- <text class="title">{{msgType.typeName}}信息具体类型</text> -->
				</view>
				<picker class="container-picker" :disabled="msgTypeChildrenDisabled" @change="bindPickerChange" :value="msgTypeChildren.selectedIndex"
				 :range="msgTypeChildren.types" range-key="typeName" mode="selector">
					<input class="container-input" placeholder="(请在此处选择信息具体类型)" v-model="msgTypeChildren.types[msgTypeChildren.selectedIndex].typeName" />
				</picker>
				<view class='container-title'>
					<!-- <text class="title">信息有效截止日期</text> -->
				</view>
				<picker class="container-picker" mode="date" :value="message.expareTime" @change="bindDateChange">
					<input class="container-input" placeholder="(请在此处选择信息有效截止日期)" v-model="message.expareTime" />
				</picker>
				<view class='container-title'>
					<text class="validate-text" v-if="validateStatus.contacts">联系人格式不正确</text>
					<text class="validate-text" v-if="validateStatus.contactPhone">联系人电话格式不正确</text>
				</view>
				<view class="container-input-wrapper">
					<input class="container-input" placeholder="(请在此处输入联系人真实姓名)" v-model="message.contacts" @focus="onFocus('contacts')"
					 @blur="onValidate('contacts')" />
					<text class="container-input-icon" @click="cleartInput('contacts')">&#xe434;</text>
				</view>
				<view class="container-input-wrapper margin-bottom">
					<input class="container-input" type="number" placeholder="(请在此处输入联系人电话)" v-model="message.contactPhone" @focus="onFocus('contactPhone')"
					 @blur="onValidate('contactPhone')" />
					<text class="container-input-icon" @click="cleartInput('contactPhone')">&#xe434;</text>
				</view>
			</uni-collapse-item>

			<uni-collapse-item class="container-item" :open="false" :show-animation="true" title="地区和价格(选填)" thumb="/static/img/save/price.png">
				<view class='container-title'>
					<!-- <text class="title">地址</text> -->
				</view>
				<picker class="container-picker" mode="multiSelector" @change="bindAddressData" @columnchange="bindMultiPickerColumnChange"
				 :value="multiIndex" :range="multiArray" range-key="address">
					<input placeholder="(请在此处选择信息有效截止日期)" class="container-input" v-model="message.addressName" />
				</picker>
				<view class='container-title'>
					<text class="validate-text" v-if="validateStatus.price">请输入正确的价格</text>
				</view>
				<view class="container-input-wrapper margin-bottom">
					<input class="container-input" type="digit" placeholder="(请在此处输入价格(单位：元)" v-model="message.price" @focus="onFocus('price')"
					 @blur="onValidate('price')" />
					<text class="container-input-icon" @click="cleartInput('price')">&#xe434;</text>
				</view>
				<!-- <view class="input-group">
					<picker :disabled="msgTypeChildrenDisabled" mode="multiSelector" @change="bindAddressData" @columnchange="bindMultiPickerColumnChange"
					 :value="multiIndex" :range="multiArray" range-key="address">
						<view class="input-row border">
							<text class="title">地区：</text>
							<view class="uni-input" v-model="message.town">{{multiArray[0][multiIndex[0]].address}}</view>
							<view v-if="multiArray[1][multiIndex[1]]"> - </view>
							<view v-if="multiArray[1][multiIndex[1]]" class="uni-input">{{multiArray[1][multiIndex[1]].address}}</view>
						</view>
					</picker> 
					</view> -->
			</uni-collapse-item>

			<uni-collapse-item class="container-item" :open="true" :show-animation="true" title="主要内容(必填)" thumb="/static/img/save/wen.png">
				<view class='container-title'>
					<text>内容(最多1000字)</text>
					<text class="validate-text" v-if="validateStatus.content">请填写信息内容</text>
				</view>
				<view class="container-textarea">
					<textarea placeholder="请在此处输入信息内容" v-model="message.content" auto-height maxlength="1000" @focus="onFocus('content')"
					 @blur="onValidate('content')"></textarea>
				</view>
			</uni-collapse-item>
		</uni-collapse>

		<view class="button-container">
			<button type="default" class="button-container-save" @tap="saveDraftMessage">保存为草稿</button>
			<button type="default" class="button-container-pub" @tap="saveMessage">直接发布</button>
		</view>
	</view>
</template>

<script>
	import uniCollapse from '@/components/uni-collapse/uni-collapse.vue'
	import uniCollapseItem from '@/components/uni-collapse-item/uni-collapse-item.vue'

	import * as publishAPI from '@/api/publish.js';
	import * as messageAPI from '@/api/message.js';
	import * as draftAPI from '@/api/draft.js';
	import * as msgTypeAPI from '@/api/msgType.js';
	import * as zoneCodeAPI from '@/api/zoneCode.js';

	export default {
		components: {
			uniCollapse,
			uniCollapseItem
		},
		data() {
			return {
				msgType: {
					id: '',
					typeName: '',
					money: '',
					expiryDate: null
				},
				msgTypeChildrenDisabled: false,
				msgTypeChildren: {
					types: [{
						id: '',
						typeName: '',
						money: '',
						expiryDate: null
					}],
					selectedIndex: 0,
				},
				validateStatus: {
					title: false, //标题
					content: false, //信息内容
					expareTime: false, //过期时间
					contacts: false, //联系人
					contactPhone: false, //联系电话
					price: false, //联系电话
				},
				multiArray: [
					[{
						id: '1276540590057693185',
						address: '嘉荫县'
					}, {
						id: '1276522368780578817',
						address: '嘉荫农场'
					}],
					[]
				],
				multiIndex: [0, 0],
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
					price: 0, //标题
					town: "", //镇
					village: "", //村
					addressName: "", //默认的，或者当前选中的地区名称
					// msgType: 0,//信息类型
					msgTypeId: null, //信息类型
					msgTypeName: null, //信息类型
					content: "", //信息内容
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
				this.msgTypeChildrenDisabled = true
				this.initMessage(option.messageId)
				this.findParentByChildId(option.msgTypeChildrenId)
				this.findMsgTypeChildrenByMsgTypeId(option.msgTypeChildrenId)
				// this.initAddress(this.message.town,this.message.village)
			}

			if (option.msgTypeId) {
				this.initMsgType(option.msgTypeId)
				this.initMsgTypeChildren(option.msgTypeId)
				this.initAddress()
			}

			if (option.msgTypeName) {
				uni.setNavigationBarTitle({
					title: option.msgTypeName + "类信息编辑"
				});
			}
		},
		onReady() {

		},
		methods: {
			cleartInput(propertyName) {
				this.message[propertyName] = "";
			},
			bindMultiPickerColumnChange: function(e) {
				this.multiIndex[e.detail.column] = e.detail.value

				if (e.detail.column === 0) {
					console.log('修改的id：' + this.multiArray[e.detail.column][e.detail.value].id + '，值为：' + e.detail.value)
					zoneCodeAPI.findByParentId(this.multiArray[e.detail.column][e.detail.value].id, (data) => {
						this.multiArray[1] = data.data
						this.multiIndex.splice(1, 0)
					})
				}
				// this.$forceUpdate()
			},
			bindPickerChange: function(e) {
				this.msgTypeChildren.selectedIndex = e.target.value
			},
			bindAddressData: function(e) {
				// console.log('address发送选择改变，携带值为', e.target.value)
				this.message.town = this.multiArray[0][e.target.value[0]].id;
			
				if (this.multiArray[1][0]) {
					// console.log('bindAddressData' + this.multiArray[1])
					this.message.village = this.multiArray[1][e.target.value[1]].id
				}
				
				this.message.addressName = this.multiArray[0][this.multiIndex[0]].address + "-"+this.multiArray[1][this.multiIndex[1]].address
			},
			onFocus(type) {
				this.validateStatus[type] = false
			},
			onValidate(type) {
				switch (type) {
					case "price":
						this.validateStatus.price = (this.message.price == "")
						return this.validateStatus.price;
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
			bindDateChange: function(e) {
				this.validateStatus.expareTime = false
				this.message.expareTime = e.detail.value
			},
			prepareMessage() {
				this.message.msgTypeId = this.msgTypeChildren.types[this.msgTypeChildren.selectedIndex].id
				if (!this.onValidate("title") &&
					!this.onValidate("content") &&
					!this.onValidate("expareTime") &&
					!this.onValidate("contacts") &&
					!this.onValidate("contactPhone")) {
					return true
				}

				return false;
			},

			findParentByChildId(childId) {
				msgTypeAPI.findParentByChildId(childId, (msgTypeparentData) => {
					this.msgType = msgTypeparentData.data
				})
			},
			initMessage(messageId) {
				messageAPI.findById(messageId, (data) => {
					this.message = data.data;
					zoneCodeAPI.findById(data.data.town, (townData) => {
						console.log(townData.data.address)
						this.multiArray[0] = [townData.data]
						zoneCodeAPI.findById(data.data.village, (villageData) => {
							this.multiArray[1] = [villageData.data]
							this.multiIndex.splice(1, 0)
						})
					})

				})
			},
			initAddress() {
				zoneCodeAPI.findByParentId("", (data) => {
					this.multiArray[0] = data.data
					this.message.town = data.data[0].id
					zoneCodeAPI.findByParentId(data.data[0].id, (villageData) => {
						this.multiArray[1] = villageData.data
						this.multiIndex.splice(1, 0)
						this.message.village = villageData.data[0].id
					})
				})
			},
			findMsgTypeChildrenByMsgTypeId(msgTypeChildrenId) {
				msgTypeAPI.findById(msgTypeChildrenId, (msgTypeChildrenData) => {
					this.msgTypeChildren.types = [msgTypeChildrenData.data]
				})
			},
			initMsgType(msgTypeId) {
				msgTypeAPI.findById(msgTypeId, (msgTypeData) => {
					this.msgType = msgTypeData.data
				})
			},
			initMsgTypeChildren(msgTypeId) {
				msgTypeAPI.selectChildren(msgTypeId, (msgTypeChildrenData) => {
					this.msgTypeChildren.types = msgTypeChildrenData.data
				})
			},
			saveMessage() {
				if (this.prepareMessage()) {
					this.message.status = 1
					publishAPI.publish(this.message, (data) => {
						uni.showToast({
							duration: 2000,
							title: '发布信息成功'
						});
					})
				}

			},
			saveDraftMessage() {
				if (this.prepareMessage()) {
					this.message.status = 3
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

<style lang="scss">
	.container {
		width: 750rpx;

		.validate-text {
			color: $uni-color-error;
		}

		.container-item {
			.margin-bottom {
				margin-bottom: $uni-spacing-col-lg;
			}
		}

		.container-title {
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			align-items: center;
			color: $uni-text-color-grey;
			font-size: $uni-font-size-base;
			margin-top: $uni-spacing-col-base;
			margin-left: 2*$uni-spacing-col-base;
			margin-right: 2*$uni-spacing-col-base;
		}

		.container-input-wrapper {
			display: flex;
			flex-direction: row;
			flex-wrap: nowrap;
			align-items: center;
			margin-top: $uni-spacing-col-base;
			margin-left: 2*$uni-spacing-col-base;
			margin-right: 2*$uni-spacing-col-base;
			border: 1px solid $uni-border-color;

			.container-input {
				height: 50rpx;
				padding: $uni-spacing-col-base;
				font-size: $uni-font-size-lg;
				flex-grow: 1;
			}

			.container-input-icon {
				font-family: uniicons;
				font-size: 24px;
				color: $uni-text-color-grey;
				padding: $uni-spacing-col-base;
			}
		}

		.container-picker {
			border: 1px solid $uni-border-color;
			margin-top: $uni-spacing-col-base;
			margin-left: 2*$uni-spacing-col-base;
			margin-right: 2*$uni-spacing-col-base;

			.container-input {
				font-size: $uni-font-size-lg;
				height: 50rpx;
				padding: $uni-spacing-col-base;
			}
		}

		.container-textarea {
			min-height: 500rpx;
			font-size: $uni-font-size-lg;
			box-sizing: border-box;
			padding: $uni-spacing-col-base;
			margin-top: $uni-spacing-col-base;
			margin-left: 2*$uni-spacing-col-base;
			margin-right: 2*$uni-spacing-col-base;
			border: 1px solid $uni-border-color;

			textarea {
				width: 100%;
			}
		}

		.button-container {
			display: flex;
			flex-direction: row;
			flex-wrap: nowrap;
			align-items: center;
			margin-top: $uni-spacing-col-base;
			margin-left: 2*$uni-spacing-col-base;
			margin-right: 2*$uni-spacing-col-base;

			.button-container-pub {
				width: 50%;
				margin: $uni-spacing-col-base;
				background: $jiayin-bg-color;
				color: #000000;
			}

			.button-container-pub:hover,
			.button-container-pub:active {
				background: $jiayin-bg-color-active;
			}

			.button-container-save {
				width: 50%;
				margin: $uni-spacing-col-base;
				background: #dd6572;
				color: #fff;
			}

			.button-container-save:hover,
			.button-container-save:active {
				background: #b6535e;
			}
		}
	}
</style>
