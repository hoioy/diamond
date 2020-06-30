<template>
	<view class="message">
		<view class="message-detail">
			<view class="message-detail-head">
				<text class="message-detail-title">{{message.title}}</text>
				<view class="msg-detail-head-body">
					<view class="msg-detail-head-body-price">{{message.price}}元</view>
				</view>
				<view class="msg-detail-head-body">
					<view>发布时间:{{message.createdDate|formatDate}}</view>
					<view>浏览:{{message.views}}次</view>
				</view>
			</view>
			<view class="message-detail-content">
				<view class="message-detail-content-body">{{ message.msgTypeName }}类型消息</view>
			</view>
			<view class="message-detail-content">
				<view class="message-detail-content-body">{{ message.content }}</view>
			</view>
			<view class="message-detail-item-contacts">
				<view class="message-detail-item-contacts-item">
					<view class="message-detail-item-contacts-item-key">联系人: </view>
					<view class="message-detail-item-contacts-item-value">{{ message.contacts}}</view>
				</view>
				<view class="message-detail-item-contacts-item">
					<view class="message-detail-item-contacts-item-key">联系电话: </view>
					<view class="message-detail-item-contacts-item-value">{{ message.contactPhone}}</view>
				</view>
				<view class="message-detail-item-contacts-item">
					<view class="message-detail-item-contacts-item-key">所在地: </view>
					<view class="message-detail-item-contacts-item-value">{{ message.town}}</view>
				</view>
			</view>
			<view class="message-detail-expare">有效期：截止到{{message.expareTime}}有效</view>
		</view>
		<view class="message-nav">
			<message-detail-nav :fill="true" :options="options" :buttonGroup="buttonGroup" @click="onClick" @buttonClick="buttonClick" />
		</view>
	</view>
</template>

<script>
	import * as messageAPI from '@/api/message.js';
	import * as collectAPI from '@/api/collect.js';
	import messageDetailNav from '@/components/message-detail-nav/message-detail-nav.vue'
	import dateFormat from '@/utils/date.js'
	export default {
		components: {
			messageDetailNav
		},
		data() {
			return {
				options: [{
					icon: 'icon-shoucang-copy',
					text: '收藏',
					infoBackgroundColor: '#007aff',
					infoColor: "red",
					iconColor: "#FFD700"
				}],
				buttonGroup: [{
						text: '分享',
						backgroundColor: '#00BFFF',
						color: '#fff',
						share: 'share'
					},
					{
						text: '打电话',
						backgroundColor: '#ffa200',
						color: '#fff'
					}
				],
				message: {
					id: null,
					parentId: null,
					createdBy: '',
					createdDate: null,
					modifiedBy: null,
					modifiedDate: null,
					flag: null,
					remark: null,
					version: null,
					token: null,
					children: [],
					title: "", //标题
					msgTypeId: "", //消息类型
					msgTypeName: "", //消息类型
					content: "", //消息内容
					status: "0", //待交易 已完成
					expareTime: "", //过期时间
					contacts: "", //联系人
					contactPhone: "", //联系电话
					views: "0" //浏览次数
				},
				"collect": {
					flag: 0,
					id: ""
				}
			};
		},
		onShareAppMessage(res) {
			return {
				title: this.message.title,
				path: '/pages/message/message-detail/message-detail?id=' + this.message.id
			}
		},
		filters: {
			formatDate(date) {
				let nDate = new Date(date);
				return dateFormat.formatDate(nDate, "yyyy-MM-dd");
			}
		},
		onLoad: function(option) { //option为object类型，会序列化上个页面传递的参数
			if (option.id) {
				this.initData(option.id)
			}
		},
		methods: {
			onClick(e) {
				//分享
				//收藏
				if (e.index === 0) {
					if (this.collect.flag === 0) {
						collectAPI.addCollect({
							"msgId": this.message.id,
							"msgTitle": this.message.title,
							"msgTypeId": this.message.msgTypeId,
						}, (data) => {
							this.collect.id = data.data.id
							uni.showToast({
								title: `收藏成功`,
								icon: 'none'
							})
						})
						this.collect.flag = 1
						this.options[1].iconColor = '#FFD700'
					} else {
						collectAPI.delCollect(this.collect.id, function(data) {
							uni.showToast({
								title: `取消收藏`,
								icon: 'none'
							})
						})
						this.options[1].iconColor = '#646566'
						this.collect.flag = 0
					}

				}

				uni.showToast({
					title: `点击${e.content.text}`,
					icon: 'none'
				})
			},
			buttonClick(e) {
				console.log(e)
				if (e.index == 1) {
					uni.makePhoneCall({
						phoneNumber: '17710666027' //仅为示例
					});
				}
			},
			initData(id) {
				var that = this;
				messageAPI.findById(id, function(data) {
					that.message = data.data;
				})
			}
		}
	}
</script>

<style lang="scss">
	.message {
		padding-bottom: 50px;
		width: 750rpx;

		.message-detail {
			display: flex;
			flex-direction: column;
			align-items: center;

			.message-detail-head {
				display: flex;
				flex-direction: column;
				align-items: center;
				border-bottom: 2rpx solid #DDDDDD;

				.message-detail-title {
					font-size: $uni-font-size-title;
					color: $uni-color-title;
				}

				.msg-detail-head-body {
					width: 750rpx;
					display: flex;
					justify-content: space-between;
					font-size: $uni-font-size-base;
					color: $uni-text-color-placeholder;

					.msg-detail-head-body-price {
						font-size: $uni-font-size-title;
						color: $uni-color-error;
					}
				}
			}

			.message-detail-content {
				width: 750rpx;
				.message-detail-content-body {
					color: $uni-color-paragraph;
					font-size: $uni-font-size-paragraph;
				}
			}

			.message-detail-item-contacts {
				flex-direction: column;
				display: flex;
				width: 750rpx;

				.message-detail-item-contacts-item {
					display: flex;
					flex-wrap: nowrap;

					.message-detail-item-contacts-item-key {
						width: 20%;
					}

					.message-detail-item-contacts-item-value {
						width: 80%;
					}
				}
			}
			
			.message-detail-expare{
				font-size: $uni-font-size-base;
				color: $uni-text-color-placeholder;
			}
		}

		.message-nav {
			position: fixed;
			bottom: 0;
			width: 100%;
		}
	}
</style>
