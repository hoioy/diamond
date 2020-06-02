<template>
		<view>
			<view class="uni-list">
				<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in listData" :key="key" @click="goDetail(value)">
					<view class="message-list-item">
						<!-- <image class="message-list-item-logo" :src="value.cover"></image> -->
						<view class="message-list-item-body">
							<view class="message-list-item-title">{{ value.publishTitle }}</view>
							<view class="message-list-item-content">{{ value.content }}</view>
							<view class="message-list-item-contacts-views">
								<view class="message-list-item-contacts">{{ value.contacts+":"+value.contactPhone }}</view>
								<view class="message-list-item-views">浏览:{{ value.views }}次</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
</template>

<script>
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	import * as publishHistoryAPI from '@/api/publishHistory.js';
	export default {
		data() {
			return {
				listData: [],
				last_page: 1,
				reload: false, //是否刷新模式，false：瀑布流
				status: 'more',
				contentText: {
					contentdown: '上拉加载更多',
					contentrefresh: '加载中',
					contentnomore: '没有更多'
				}
			}
		},
		onShow(){
			this.initList();
		},
		onPullDownRefresh() {
			this.initList();
		},
		onReachBottom() {
			this.status = 'more';
			this.last_page += 1;
			this.getList();
		},
		methods: {
			initList(){
				this.reload = true;
				this.last_page = 1;
				this.getList();
			},
			getList() {
				this.status = 'loading';
				publishHistoryAPI.getPage({
					"filters": {},
					"page": this.last_page,
					"pageSize": 10,
					"sorts": []
				}, (data) => {
					if (this.reload) {
						this.listData = data.data.list;
						console.log(this.listData)
						this.status = 'more';
					} else {
						this.listData = this.listData.concat(data.data.list);
						debugger
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
