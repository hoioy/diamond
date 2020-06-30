<template>
	<view class="message">
		<view class="message-detail">
			<view class="message-detail-head">
				<view class="message-detail-title">{{message.title}}</view>
				<view class="msg-detail-head-body">
						<view>更新时间:{{message.modifiedDate|formatDate}}</view>
						<view>浏览:{{message.views}}次</view>
				</view>
			</view>
			<view class="message-detail-content">
				<view class="message-detail-content-head">详细内容</view>
				<view class="message-detail-content-body">{{ message.content }}</view>
			</view>
			<view class="message-detail-item-contacts">
				<view>联系人:{{ message.contacts}}</view>
				<view>联系电话:{{ message.contactPhone }}</view>
			</view>
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
				buttonGroup: [
					{
						text: '分享',
						backgroundColor: '#00BFFF',
						color: '#fff',
						share: 'share'
					},
					{
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
				path: '/pages/message/message-detail/message-detail?id='+this.message.id
			}
		},
		filters:{
			formatDate(date){
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
				if(e.index == 1){
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


<style>
	.message {
		padding-bottom: 50px;
		width: 750rpx;
	}

	.message-nav {
		position: fixed;
		bottom: 0;
		width: 100%;
	}


	.message-detail-title{
		margin: 2rpx 2rpx 5rpx 2rpx;
	}

	.message-detail-item-contacts{
			margin: 2rpx 2rpx 2rpx 2rpx;
	}
	.message-detail-head{
		border-bottom: 2rpx solid #DDDDDD;
	}
	.message-detail-content-head{
			margin: 2rpx 2rpx 2rpx 2rpx;
	}
	.message-detail-content-body{
			margin: 2rpx 2rpx 2rpx 2rpx;
	}
	.message-detail-content{
		border-bottom: 2rpx solid #DDDDDD;
	}
	.msg-detail-head-body{
		display: flex;
		justify-content: space-between;
		margin-top: 8px;
		font-size: 11pt;
		margin: 2rpx 2rpx 5rpx 2rpx  /* 上 右 下 左 顺序调节边距*/
	}
</style>
