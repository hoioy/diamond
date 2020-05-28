<template>
	<view>
		<view class="message-publish-type-title">新增一条消息</view>
		<view class="message-publish-type">
			<view class="message-publish-type-cell" v-for="(value, key) in msgTypelistData" :key="key" @click="goMessageSave(value)">
				<view class="message-publish-type-item">
					<view class="message-publish-type-item-img">
						<image class="message-publish-type-item-img-image" :src="'../../../static/img/msgType/'+value.typeName+'.png'"
						 @error="imageError"></image>
					</view>
					<view class="message-publish-type-item-name">新增{{ value.typeName }}消息</view>
				</view>
			</view>
		</view>
		<view class="uni-list-title">草稿箱</view>
		<view class="uni-list">
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in listData" :key="key" @click="goDetail(value)">
				<view class="message-list-item">
					<!-- <image class="message-list-item-logo" :src="value.cover"></image> -->
					<view class="message-list-item-body">
						<view class="message-list-item-title">{{ value.title }}</view>
						<view class="message-list-item-status-expareTime">
							<view class="message-list-item-status">{{ !value.status?'无状态': value.status}}</view>
							<view class="message-list-item-expareTime">有效期:{{ value.expareTime }}</view>
						</view>
						<view class="message-list-item-content">{{ value.content }}</view>
						<view class="message-list-item-contacts-views">
							<view class="message-list-item-contacts">{{ value.contacts+":"+value.contactPhone }}</view>
							<view class="message-list-item-views">浏览:{{ value.views }}次</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<uni-load-more :status="status" :icon-size="16" :content-text="contentText" />
	</view>
</template>

<script>
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	import * as messageAPI from '@/api/message.js';
	import * as msgTypeAPI from '@/api/msgType.js';

	export default {
		components: {
			uniLoadMore
		},
		data() {
			return {
				listData: [],
				msgTypelistData: [],
				last_page: 1,
				reload: false, //是否刷新模式，false：瀑布流
				status: 'more',
				contentText: {
					contentdown: '上拉加载更多',
					contentrefresh: '加载中',
					contentnomore: '没有更多'
				}
			};
		},
		onLoad() {
			this.getList();
			this.initMsgType();
		},
		onPullDownRefresh() {
			this.reload = true;
			this.last_page = 1;
			this.getList();
		},
		onReachBottom() {
			this.status = 'more';
			this.last_page += 1;
			this.getList();
		},
		methods: {
			imageError(e) {
				console.error('image发生error事件，携带值为' + e.detail.errMsg)
			},
			initMsgType() {
				var that = this;
				msgTypeAPI.selectParent((data) => {
					that.msgTypelistData = data.data

				})
			},
			getList() {
				this.status = 'loading';
				messageAPI.getPage({
					"filters": {},
					"page": this.last_page,
					"pageSize": 10,
					"sorts": {}
				}, (data) => {
					if (this.reload) {
						this.listData = data.data.list;
						this.status = 'more';
					} else {
						this.listData = this.listData.concat(data.data.list);
						if (data.data.list.length > 0) {
							this.status = 'more';
						} else {
							this.status = 'noMore';
						}
					}
					this.reload = false;
					uni.stopPullDownRefresh();
				})
			},
			goDetail: function(e) {
				uni.navigateTo({
					url: '../message-detail/message-detail?id=' + e.id,
				});
			},
			goMessageSave: function(e) {
				uni.navigateTo({
					url: '../message-save/message-save?msgTypeId=' + e.id,
				});
			}
		}
	}
</script>

<style>
	.message-publish-type-title {
		margin: 15px;
		border-bottom: 1px dashed #B2B2B2;
		padding: 5px;
	}

	.uni-list-title {
		margin: 15px;
		border-bottom: 1px dashed #B2B2B2;
		padding: 5px;
	}

	.message-publish-type {
		display: flex;
		justify-content: space-around;
		border-bottom: 2px solid #ffd115;
		padding-bottom: 20px;
	}

	.message-publish-type-item {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.message-publish-type-item-img {}

	.message-publish-type-item-img-image {
		width: 50px;
		height: 50px;
		background-color: #eeeeee;
	}

	.message-publish-type-item-name {}

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
