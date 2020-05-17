<template>
	<view class="content">
		<view class="message-list-item">
			<view class="message-list-item-body">
				<view class="message-list-item-title">{{ message.title }}</view>
				<view class="message-list-item-status-expareTime">
					<view class="message-list-item-status">{{ !message.status?'无状态': message.status}}</view>
					<view class="message-list-item-expareTime">有效期:{{ message.expareTime }}</view>
				</view>
				<view class="message-list-item-content">{{ message.content }}</view>
				<view class="message-list-item-contacts-views">
					<view class="message-list-item-contacts">{{ message.contacts+":"+message.contactPhone }}</view>
					<view class="message-list-item-views">浏览:{{ message.views }}次</view>
				</view>
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
					// msgType: 0,//消息类型
					msgType: "0", //消息类型
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
	.message-list-item {
		display: flex;
		flex-direction: column;
		border-bottom: 1px solid #B2B2B2;
	}

	.message-list-item-body {
		margin-top: 10px;
		margin-bottom: 10px;
		display: flex;
		flex-direction: column;
		width: 750rpx;
	}

	.message-list-item-title {
		font-size: 17pt;
		color: #000000;
		padding-left: 15px;
	}

	.message-list-item-status-expareTime {
		display: flex;
		justify-content: space-between;
		margin-top: 8px;
		font-size: 11pt;
		color: #888888;
		padding-left: 15px;
		padding-right: 15px;
	}

	.message-list-item-status {
		background-color: #09BB07;
		color: #FFFFFF;
		padding-left: 5px;
		padding-right: 5px;
		padding-top: 2px;
		padding-bottom: 2px;
		border-radius: 20rpx;
	}

	.message-list-item-status-color {}

	.message-list-item-expareTime {}

	.message-list-item-content {
		margin-top: 8px;
		font-size: 14pt;
		color: #353535;
		padding-left: 15px;
		padding-right: 15px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.message-list-item-contacts-views {
		display: flex;
		justify-content: space-between;
		margin-top: 8px;
		font-size: 11pt;
		color: #888888;
		padding-left: 15px;
		padding-right: 15px;
	}

	.message-list-item-contacts {}

	.message-list-item-views {}
</style>
