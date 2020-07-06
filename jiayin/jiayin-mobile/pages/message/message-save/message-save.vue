<template>
	<view class="container">
		<!-- <uni-collapse> -->
		<view class="container-item" :open="true" :show-animation="true" title="基础内容(必填)" thumb="/static/img/save/base.png">
			<view class='container-title'>
				<text class="validate-text" v-if="validateStatus.title">标题格式不正确,标题不能超过18个字</text>
			</view>
			<view class="container-input-wrapper">
				<text class="container-input-lable">标题:</text>
				<input class="container-input" placeholder="(请输入信息标题)" v-model="message.title" @focus="onFocus('title')" @blur="onValidate('title')" />
				<text class="container-input-icon" @click="cleartInput('title')">&#xe434;</text>
			</view>
			<view class='container-title'>
				<text class="validate-text" v-if="false">（预留校验信息位置）</text>
			</view>
			<picker v-if="msgType.children.msgTypes[0].id" @change="bindMsgTypeChange" :value="msgType.children.selectedIndex"
			 :range="msgType.children.msgTypes" range-key="typeName" mode="selector">
				<view class="container-input-wrapper">
					<text class="container-input-lable">具体类型:</text>
					<view class="container-input">{{msgType.children.msgTypes[msgType.children.selectedIndex].typeName}}</view>
				</view>
			</picker>
			<view class='container-title'>
				<text class="validate-text" v-if="validateStatus.expareTime">截至日期不能小于今天,最多一个月时间</text>
			</view>
			<picker mode="date" :value="message.expareTime" @change="bindDateChange">
				<view class="container-input-wrapper">
					<text class="container-input-lable">有效期:</text>
					<view class="container-input">{{message.expareTime}}</view>
				</view>
			</picker>
			<view class='container-title'>
				<text class="validate-text" v-if="validateStatus.contacts">联系人格式不正确</text>
			</view>
			<view class="container-input-wrapper">
				<text class="container-input-lable">联系人姓名:</text>
				<input class="container-input" placeholder="(请输入联系姓名)" v-model="message.contacts" @focus="onFocus('contacts')"
				 @blur="onValidate('contacts')" />
				<text class="container-input-icon" @click="cleartInput('contacts')">&#xe434;</text>
			</view>
			<view class='container-title'>
				<text class="validate-text" v-if="validateStatus.contactPhone">联系人电话格式不正确</text>
			</view>
			<view class="container-input-wrapper">
				<text class="container-input-lable">联系人电话:</text>
				<input class="container-input" type="number" placeholder="(请输入联系人电话)" v-model="message.contactPhone" @focus="onFocus('contactPhone')"
				 @blur="onValidate('contactPhone')" />
				<text class="container-input-icon" @click="cleartInput('contactPhone')">&#xe434;</text>
			</view>
		</view>


		<view class="container-item" :open="false" :show-animation="true" title="地区和价格(选填)" thumb="/static/img/save/price.png">
			<view class='container-title'>
				<text class="validate-text" v-if="false">（预留校验信息位置）</text>
			</view>
			<picker @change="bindTownChange" :value="address.town.zoneCodes.selectedIndex" :range="address.town.zoneCodes"
			 range-key="address" mode="selector">
				<view class="container-input-wrapper">
					<text class="container-input-lable">一级地区(选填):</text>
					<view class="container-input">{{address.town.zoneCodes[address.town.selectedIndex].address}}</view>
				</view>
			</picker>
			<view class='container-title'>
				<text class="validate-text" v-if="false">（预留校验信息位置）</text>
			</view>
			<picker v-if="address.village.zoneCodes[0].id" @change="bindVillageChange" :value="address.village.zoneCodes.selectedIndex"
			 :range="address.village.zoneCodes" range-key="address" mode="selector">
				<view class="container-input-wrapper">
					<text class="container-input-lable">二级地区(选填):</text>
					<view class="container-input">{{address.village.zoneCodes[address.village.selectedIndex].address}}</view>
				</view>
			</picker>
			<view class='container-title'>
				<text class="validate-text" v-if="validateStatus.price">请输入正确的价格</text>
			</view>
			<view class="container-input-wrapper">
				<text class="container-input-lable">价格(选填):</text>
				<input class="container-input" type="digit" placeholder="(请输入价格(单位：元))" v-model="message.price" @focus="onFocus('price')"
				 @blur="onValidate('price')" />
				<text class="container-input-icon" @click="cleartInput('price')">&#xe434;</text>
			</view>
		</view>

		<view class="container-item" :open="true" :show-animation="true" title="主要内容(必填)" thumb="/static/img/save/wen.png">
			<view class='container-title'>
				<text class="validate-text" v-if="validateStatus.content">请填写信息内容</text>
			</view>
			<view class="container-textarea">
				<text class="container-input-lable">内容(最多1000字)</text>
				<textarea placeholder="请输入信息内容" v-model="message.content" auto-height maxlength="1000" @focus="onFocus('content')"
				 @blur="onValidate('content')"></textarea>
			</view>
		</view>
		<!-- </uni-collapse> -->

		<view class="button-container">
			<button v-if="!isPublished" class="button-container-save" @tap="saveDraftMessage">保存为草稿</button>
			<button v-if="!isPublished" class="button-container-pub" @tap="saveMessage">直接发布</button>
			<button v-if="isPublished" class="button-container-pub" @tap="rePublishMesage">更新</button>
		</view>
	</view>
</template>

<script>
	// import uniCollapse from '@/components/uni-collapse/uni-collapse.vue'
	// import uniCollapseItem from '@/components/view/view.vue'

	import * as publishAPI from '@/api/publish.js';
	import * as messageAPI from '@/api/message.js';
	import * as draftAPI from '@/api/draft.js';
	import * as msgTypeAPI from '@/api/msgType.js';
	import * as zoneCodeAPI from '@/api/zoneCode.js';

	export default {
		components: {
			// uniCollapse,
			// uniCollapseItem
		},
		data() {
			return {
				msgType: {
					id: '',
					typeName: '',
					children: {
						msgTypes: [{}],
						selectedIndex: 0
					},
				},
				validateStatus: {
					title: false, //标题
					content: false, //信息内容
					expareTime: false, //过期时间
					contacts: false, //联系人
					contactPhone: false, //联系电话
					price: false //价格
				},
				address: {
					town: {
						zoneCodes: [{}],
						selectedIndex: 0
					},
					village: {
						zoneCodes: [{}],
						selectedIndex: 0
					}
				},
				isPublished: false,
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
					price: 0,
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
			this.msgType.id = option.msgTypeId
			this.msgType.typeName = option.msgTypeName
			uni.setNavigationBarTitle({
				title: option.msgTypeName + "类信息编辑"
			});
			if (option.messageId) {
				this.initMessage(option.messageId)
			} else {
				this.initMsgTypeChildren()
				this.initAddressTown()
			}

			if (option.from && option.from == 'published') {
				//从发布入口进来
				this.isPublished = true;
			}

		},
		methods: {
			initMessage(messageId) {
				messageAPI.findById(messageId, (data) => {
					this.message = data.data;
					this.initMsgTypeChildren()
					this.initAddressTown()
				})
			},
			initMsgTypeChildren() {
				const that = this
				msgTypeAPI.selectChildren(this.msgType.id, (data) => {
					if (data.data.length > 0) {
						that.msgType.children.msgTypes = data.data
					} else {
						that.msgType.children.msgTypes = [{}]
						that.msgType.children.selectedIndex = 0
					}
				})
			},
			initAddressTown() {
				const that = this
				this.address.town.selectedIndex = 0
				zoneCodeAPI.findByParentId("", (data) => {
					that.address.town.zoneCodes = data.data
					if (that.message.town) {
						//初始化选中值
						var index = -1
						that.address.town.zoneCodes.forEach(item => {
							index++;
							if (item.id == that.message.town) {
								that.address.town.selectedIndex = index
								return;
							}
						})
					}
					this.initAddressVillage()
				})
			},
			initAddressVillage() {
				this.address.village.selectedIndex = 0
				var id = this.address.town.zoneCodes[this.address.town.selectedIndex].id;
				const that = this
				zoneCodeAPI.findByParentId(id, (data) => {
					if (data.data.length > 0) {
						that.address.village.zoneCodes = data.data
						if (that.message.village) {
							//初始化选中值
							var index = -1
							that.address.village.zoneCodes.forEach(item => {
								index++;
								if (item.id == that.message.village) {
									that.address.village.selectedIndex = index
									return;
								}
							})
						}
					} else {
						that.address.village.zoneCodes = [{}]
						that.address.village.selectedIndex = 0
					}
				})
			},
			cleartInput(propertyName) {
				this.message[propertyName] = "";
			},
			bindMsgTypeChange: function(e) {
				this.msgType.children.selectedIndex = e.target.value
			},
			bindTownChange: function(e) {
				this.address.town.selectedIndex = e.target.value
				this.initAddressVillage()
			},
			bindVillageChange: function(e) {
				this.address.village.selectedIndex = e.target.value
			},
			bindDateChange: function(e) {
				this.message.expareTime = e.detail.value
				this.validateStatus.expareTime = (new Date(this.message.expareTime) <= new Date())
			},
			onFocus(type) {
				this.validateStatus[type] = false
			},
			onValidate(type) {
				switch (type) {
					case "price":
						this.validateStatus.price = (this.message.price < 0)
						return this.validateStatus.price;
					case "title":
						this.validateStatus.title = (this.message.title == "" || this.message.title.length > 18)
						return this.validateStatus.title;
					case "content":
						this.validateStatus.content = (this.message.content == "")
						return this.validateStatus.content;
					case "expareTime":
						return (new Date(this.message.expareTime) <= new Date());
					case "contacts":
						this.validateStatus.contacts = (this.message.contacts == "")
						return this.validateStatus.contacts;
					case "contactPhone":
						if (this.message.contactPhone) {
							this.validateStatus.contactPhone = false
						} else {
							this.validateStatus.contactPhone = true
						}
						return this.validateStatus.contactPhone;
				}
			},
			prepareMessage() {
				if (this.msgType.children.msgTypes[this.msgType.children.selectedIndex].id) {
					this.message.msgTypeId = this.msgType.children.msgTypes[this.msgType.children.selectedIndex].id
				} else {
					this.message.msgTypeId = this.msgType.id
				}

				if (this.address.village.zoneCodes[this.address.village.selectedIndex].id) {
					this.message.village = this.address.village.zoneCodes[this.address.village.selectedIndex].id
				} else {
					this.message.village = ""
				}

				if (this.address.town.zoneCodes[this.address.town.selectedIndex].id) {
					this.message.town = this.address.town.zoneCodes[this.address.town.selectedIndex].id
				} else {
					this.message.town = ""
				}

				if (!this.onValidate("title") &&
					!this.onValidate("content") &&
					!this.onValidate("expareTime") &&
					!this.onValidate("contacts") &&
					!this.onValidate("contactPhone")) {
					return true
				}

				return false;
			},
			saveMessage() {
				if (this.prepareMessage()) {
					this.message.status = 1
					publishAPI.publish(this.message, (data) => {
						uni.showToast({
							duration: 2000,
							title: '发布信息成功',
							success: () => {
								uni.navigateBack()
							}
						});

					})
				}
			},
			rePublishMesage() {
				if (this.prepareMessage()) {
					this.message.status = 1
					publishAPI.rePublish(this.message, (data) => {
						uni.showToast({
							duration: 2000,
							title: '更新发布信息成功',
							success: () => {
								uni.navigateBack()
							}
						});

					})
				}
			},
			saveDraftMessage() {
				if (this.prepareMessage()) {
					this.message.status = 3
					publishAPI.saveDraft(this.message, (data) => {
						uni.showToast({
							duration: 2000,
							title: '保存草稿成功',
							success: () => {
								uni.navigateBack()
							}
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
			.container-row {
				display: flex;
				flex-direction: row;
				align-items: center;
			}
		}

		.container-title {
			display: flex;
			flex-direction: row;
			justify-content: space-between;
			align-items: center;
			color: $uni-text-color-grey;
			font-size: $uni-font-size-base;
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
				color: $uni-color-subtitle;
			}

			.container-input-icon {
				font-family: uniicons;
				font-size: 24px;
				color: $jiayin-bg-color-active;
				padding: $uni-spacing-col-base;
			}

			.container-input-lable {
				font-size: $uni-font-size-lg;
				padding: $uni-spacing-col-base;
			}
		}


		.container-textarea {
			font-size: $uni-font-size-lg;
			box-sizing: border-box;
			padding: $uni-spacing-col-base;
			margin-top: $uni-spacing-col-base;
			margin-left: 2*$uni-spacing-col-base;
			margin-right: 2*$uni-spacing-col-base;
			border: 1px solid $uni-border-color;

			textarea {
				width: 100%;
				min-height: 500rpx;
			}
		}

		.button-container {
			display: flex;
			flex-direction: row;
			flex-wrap: nowrap;
			align-items: center;
			justify-content: center;
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
