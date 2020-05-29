<template>
	<view class="content">
		<view class="message-detail">
			<view class="message-detail-title">{{ message.title }}</view>
			<view class="message-detail-item">
				<view>状态:</view>
				<view class="message-detail-item-status">{{ !message.status?'无状态': message.status}}</view>
			</view>
			<view class="message-detail-item">
				<view>有效期:</view>
				<view>{{ message.expareTime }}</view>
			</view>
			<view class="message-detail-content">{{ message.content }}</view>
			<view class="message-detail-item-contacts">
				<view>联系人:{{ message.contacts}}</view>
			</view>
			<view class="message-detail-item-contactPhone">
				<view>联系电话:>{{ message.contactPhone }}</view>
			</view>
			<view class="message-detail-item-views">
				<view>阅读次数:{{ message.views }}</view>
			</view>
		</view>
	</view>
</template>

<script>
	import * as messageAPI from '@/api/message.js';
	import mInput from '@/components/m-input.vue';

	export default {
		components: {
			mInput
		},
		data() {
			return {
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
					msgType: "0", //消息类型
					content: "", //消息内容
					status: "0", //待交易 已完成
					expareTime: "", //过期时间
					contacts: "", //联系人
					contactPhone: "", //联系电话
					views: "0" //浏览次数
				}
			};
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
				})
			}
		}
	}
</script>


<style>
	.message-detail {
		display: flex;
		flex-direction: column;
	}

	.message-detail-title {
		margin: auto;
		display: inline-block;
		font-size: 30rpx;
		line-height: 88rpx;
		box-sizing: border-box;
		border-bottom: 2rpx solid #ffd115;
	}

	.message-detail-content {
		margin: 5px;
		line-height: 30px;
	}

	.message-detail-item {
		display: flex;
	}

	.message-detail-item-status {
		background-color: #09BB07;
		color: #FFFFFF;
		padding-left: 5px;
		padding-right: 5px;
		padding-top: 2px;
		padding-bottom: 2px;
		border-radius: 20rpx;
	}

	.message-detail-item-views {
		align-self: flex-end
	}
</style>
