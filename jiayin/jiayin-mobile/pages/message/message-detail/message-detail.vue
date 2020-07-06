<template>
	<view class="message">
		<view class="message-detail">
			<view class="message-detail-head">
				<view class="message-detail-title">{{message.title}}</view>
				<view class="msg-detail-head-body">
					<view class="msg-detail-head-body-content">更新: {{message.modifiedDate|formatDate}}</view>
					<view class="msg-detail-head-body-content">浏览: {{message.views}}次</view>
				</view>
			</view>
			<view class="message-detail-body">
				<view  class="message-detail-body-item">
					<view class="message-detail-body-price">价格:{{ message.price}}</view>
					<view class="message-detail-body-item-key">类别: <text class="message-detail-body-type" :style="{backgroundColor: message.msgTypeColor}">{{ message.msgTypeName }}</text></view>
				</view>
				<view class="message-detail-body-item">
					<view class="message-detail-body-item-value">联系人: {{ message.contacts}}</view>
					<view class="message-detail-body-item-value">联系电话:{{ message.contactPhone}}</view>
				</view>
				<view class="message-detail-body-item">
					<view class="message-detail-body-item-address">
						<view class="message-detail-body-item-key">地区: </view>
						<view class="message-detail-body-item-value">{{ message.town}}</view>
					</view>
		
				</view>
			</view>
	    </view>
		<view class="message-nav">
			<view class="button-container">
				<view class="button-container-icon" @click="onCollect">
					<text class="container-input-icon" :style="{color:collectColor}">&#xe438;</text>
					<text class="button-container-text" >{{collectButtonName}}</text>
				</view>
				<button  class="button-container-button" open-type="share"  style="background-color: #0FAEFF;color: #fff;border-radius: 100px 0px 0px 100px;">分享</button>
				<button  class="button-container-button" style="background-color: #ffa200;color: #fff;border-radius: 0px 100px 100px 0px;" @tap="onPhone">打电话</button>
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
				collectColor: "#FFC0CB",
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
						that.collectColor="#FFD700"
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
							that.collectColor="#FFC0CB"
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
							that.collectColor="#FFD700"
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
	page {
	 background-color: #F5F5F5;
	}
	.message {
		padding-bottom: 200rpx;
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
                background-color: $jiayin-msg-color;
				.message-detail-title {
					align-self: left;
					font-size: $uni-font-size-title;
					color: $uni-color-title;
				}

				.message-detail-body-price {
					font-size: $uni-font-size-subtitle;
					color: $uni-color-error;
				}

				.msg-detail-head-body {
					display: flex;
					justify-content: space-between;
					font-size: $uni-font-size-base;
					color: $uni-text-color-placeholder;
					margin-top: $uni-spacing-col-base;
				
					.msg-detail-head-body-content{
						color: $jiayin-head-content-color;
						font-size: $jiayin-head-content-font-size;
					}
				}
			}

			.message-detail-body {
				display: flex;
				flex-direction: column;
				margin-top: $uni-spacing-col-base;
				box-shadow: 1px 1px 10px $uni-border-color;
				border-radius: $uni-border-radius-lg;
				padding: $uni-spacing-col-base;
				background-color: $jiayin-msg-color;
				.message-detail-body-item-address{
						display: flex;
						flex-direction: row;
				}
				.message-detail-body-item {
					display: flex;
					flex-wrap: nowrap;
					justify-content: space-between;
					font-size: 13pt;
					color: #353535;
					margin-top: $uni-spacing-col-base;
				}
				.message-detail-body-type {
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
			background-color: $jiayin-msg-color ;
			.button-container-icon{
				margin: 0 10px;
				display: flex;
				flex-direction: column;
				position: relative;
				align-items: center;
				justify-content: center;
				align-content: center;
				 .button-container-text{
					 margin-top: 3px;
					 align-items: center;
					 font-size: 30rpx;
					 justify-content: center;
				 }
				 
				 .container-input-icon {
				 	font-family: uniicons;
				 }
			}
			.button-container {
				display: flex;
				flex: 1;
				flex-direction: row;
				flex-wrap: nowrap;
			    align-items: center;
				margin: 4px 8px 4px 0px;
				.button-container-button {
			
					border-left: 20rpx ;
					width: 50%;
					flex: 1;
					justify-content: center;
					align-items: center;
				}
			}
		}
	}
</style>
