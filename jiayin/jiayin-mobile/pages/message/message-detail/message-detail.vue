<template>
	<view class="message">
		<view class="message-detail">
			<view class="message-detail-creator">
				<image class="message-detail-creator-logo" src="../../../static/img/home.png"></image>
				<view class="message-detail-creator-info">
					<view class="message-detail-creator-info-name">{{ message.createdBy }}</view>
					<view class="message-detail-creator-info-type">{{ message.msgType}}</view>
				</view>
			</view>
			<view class="message-detail-title">{{ message.title }}</view>
			<view class="message-detail-item-status">{{ !message.status?'无状态': message.status}}</view>
			<view class="message-detail-content">
				<view>{{ message.content }}</view>
			</view>
			<view class="message-detail-item-contacts">
				<view>联系人:{{ message.contacts}}</view>
				<view>联系电话:{{ message.contactPhone }}</view>
				<view>有效期:{{ message.expareTime }}</view>
			</view>
			<view class="message-detail-item-views">阅读次数:{{ message.views }}</view>

		</view>
		<view class="message-nav">
			<button type="primary" open-type="share">分享</button>
			<message-detail-nav :fill="true" :options="options" :buttonGroup="buttonGroup" @click="onClick" @buttonClick="buttonClick" />
		</view>
	</view>
</template>

<script>
	import * as messageAPI from '@/api/message.js';
	import * as collectAPI from '@/api/collect.js';
	import mInput from '@/components/m-input.vue';
	import messageDetailNav from '@/components/message-detail-nav/message-detail-nav.vue'

	export default {
		components: {
			mInput,
			messageDetailNav
		},
		data() {
			return {
				options: [{
					icon: 'weixin',
					text: '分享',
					iconColor: "#00BB00"
				}, {
					icon: 'icon-shoucang-copy',
					text: '收藏',
					infoBackgroundColor: '#007aff',
					infoColor: "red",
					iconColor: "#FFD700"
				}],
				buttonGroup: [{
					text: '打电话',
					backgroundColor: '#ffa200',
					color: '#fff'
				}],
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
					msgType: "0", //消息类型
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
				path: '/pages/message/message-detail/message-detail?id='+this.message.id
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
				if (e.index === 0) {

				}
				//收藏
				if (e.index === 1) {
					if (this.collect.flag === 0) {
						collectAPI.addCollect({
							"msgId": this.message.id,
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
				uni.makePhoneCall({
					phoneNumber: '17710666027' //仅为示例
				});
				console.log(e)
				this.options[2].info++
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


<style>
	.message {
		padding-bottom: 50px;
		width: 750rpx;
	}

	.message-detail {
		display: flex;
		flex-direction: column;
		color: #333333;
		padding: 10px;
	}

	.message-detail-creator {
		display: flex;
		border-bottom: 2rpx solid #acac97;
	}

	.message-detail-creator-logo {
		width: 60px;
		height: 60px;
	}

	.message-detail-creator-info {
		display: flex;
		flex-direction: column;
	}

	.message-detail-creator-info-name {
		font-size: 20pt;
	}

	.message-nav {
		position: fixed;
		bottom: 0;
		width: 100%;
	}

	.message-detail-creator-info-type {}

	.message-detail-title {
		margin: auto;
		display: inline-block;
		font-size: 30rpx;
		line-height: 88rpx;
		box-sizing: border-box;
		border-bottom: 2rpx solid #ffd115;
	}

	.message-detail-content {
		line-height: 30px;
		font-size: 17pt;
		word-wrap: break-word;
	}

	.message-detail-item-contacts {
		display: flex;
		flex-direction: column;
		background-color: white;
		padding-top: 20px;
		padding-bottom: 20px;
	}

	.message-detail-item-status {
		background-color: #09BB07;
		color: #FFFFFF;
		padding-left: 5px;
		padding-right: 5px;
		padding-top: 2px;
		padding-bottom: 2px;
		border-radius: 20rpx;
		align-self: flex-end
	}

	.message-detail-item-views {
		align-self: flex-end
	}
</style>
