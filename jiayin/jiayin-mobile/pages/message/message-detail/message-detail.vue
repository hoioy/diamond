<template>
	<view class="message">
		<view class="message-detail">
			<view class="message-detail-head">
				<view class="message-detail-title">{{message.title}}</view>
				<view class="msg-detail-head-body-price">{{message.price}}元</view>
				<view class="msg-detail-head-body">
					<view>发布时间:{{message.createdDate|formatDate}}</view>
					<view>浏览:{{message.views}}次</view>
				</view>
			</view>
			<view class="message-detail-content">
				<view>
					<text class="message-detail-content-type" :style="{backgroundColor: message.msgTypeColor}">{{ message.msgTypeName }}类型信息</text>
				</view>
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
			<view class="button-container">
				<view>
									<view  class="iconfont icon-shoucang1 "  size="50" color="#ffa200" ></view>
									<text class="uni-tab__text">收藏</text>
				</view>
				<button type="default" class="button-container-button" open-type="share" style="background-color: #0FAEFF;color: #fff">分享</button>
				<button type="default" class="button-container-button" style="background-color: #ffa200;color: #fff" @tap="onPhone">打电话</button>
			</view>
		</view>
	</view>
</template>

<script>
	import * as messageAPI from '@/api/message.js';
	import * as collectAPI from '@/api/collect.js';
	import dateFormat from '@/utils/date.js'

	export default {
		data() {
			return {
				message: {},
				collectButtonName: "收藏",
				collectId: ""
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
			initData(id) {
				var that = this;
				messageAPI.findById(id, function(data) {
					that.message = data.data;
					that.initCollect()
				})
			},
			initCollect() {
				var that = this;
				collectAPI.getPage({
					"filters": {
						"openid": that.message.openid,
						"msgId": that.message.id
					},
					"page": 1,
					"pageSize": 1
				}, (data) => {
					if (data.data.list && data.data.list.length > 0) {
						that.collectButtonName = "取消收藏"
						that.collectId = data.data.list[0].id
					} else {
						that.collectButtonName = "收藏"
					}
				})
			},
			onCollect(e) {
				var that = this
				switch (that.collectButtonName) {
					case "取消收藏":
						collectAPI.delCollect(that.collectId, function(data) {
							that.collectButtonName = "收藏"
							uni.showToast({
								title: `取消收藏成功`
							})
						})
						break;
					case "收藏":
						collectAPI.addCollect({
							"msgId": this.message.id,
							"msgTitle": this.message.title,
							"msgTypeId": this.message.msgTypeId,
						}, (data) => {
							that.collectId = data.data.id
							that.collectButtonName = "取消收藏"
							uni.showToast({
								title: `收藏成功`
							})
						})
						break;
				}
			},
			onPhone(e) {
				uni.makePhoneCall({
					phoneNumber: this.message.contactPhone
				});
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

			.message-detail-head {
				display: flex;
				flex-direction: column;
				box-shadow: 1px 1px 5px $uni-border-color;
				border-radius: $uni-border-radius-lg;
				padding: $uni-spacing-col-base;

				.message-detail-title {
					align-self: center;
					font-size: $uni-font-size-title;
					color: $uni-color-title;
				}

				.msg-detail-head-body-price {
					font-size: $uni-font-size-subtitle;
					color: $uni-color-error;
				}

				.msg-detail-head-body {
					display: flex;
					justify-content: space-between;
					font-size: $uni-font-size-base;
					color: $uni-text-color-placeholder;
					margin-top: $uni-spacing-col-base;
				}
			}

			.message-detail-content {
				display: flex;
				flex-direction: column;
				margin-top: $uni-spacing-col-base;
				box-shadow: 1px 1px 5px $uni-border-color;
				border-radius: $uni-border-radius-lg;
				padding: $uni-spacing-col-base;

				.message-detail-content-type {
					font-size: $uni-font-size-sm;
					color: $uni-text-color-inverse;
					padding: $uni-spacing-col-sm $uni-spacing-row-sm;
					border-radius: $uni-border-radius-lg;
				}

				.message-detail-content-body {
					color: $uni-color-paragraph;
					font-size: $uni-font-size-paragraph;
					margin-top: $uni-spacing-col-base;
				}
			}

			.message-detail-item-contacts {
				display: flex;
				flex-direction: column;
				margin-top: $uni-spacing-col-base;
				box-shadow: 1px 1px 10px $uni-border-color;
				border-radius: $uni-border-radius-lg;
				padding: $uni-spacing-col-base;

				.message-detail-item-contacts-item {
					display: flex;
					flex-wrap: nowrap;
					margin-top: $uni-spacing-col-base;
					.message-detail-item-contacts-item-key {
						width: 20%;
					}

					.message-detail-item-contacts-item-value {
						width: 80%;
					}
				}
			}

			.message-detail-expare {
				font-size: $uni-font-size-base;
				color: $uni-text-color-placeholder;
				align-self: center;
				margin-top: $uni-spacing-col-base;
			}
		}

		.message-nav {
			position: fixed;
			bottom: 0;
			width: 100%;
			background-color: #F8F8F8 ;
			height: 50rpx;
			.uni-tab__text {
				font-size: 12px;
				color: #646566;
				height: 40rpx;
			}
			.button-container {
				display: flex;
				flex-direction: row;
				flex-wrap: nowrap;
				.button-container-button {
					width: 50%;
					/* #ifndef APP-NVUE */
					display: flex;
					flex-direction: column;
					/* #endif */
					flex: 1;
					justify-content: center;
					align-items: center;
					height: 40rpx;
				}
			}
		}
	}
</style>
