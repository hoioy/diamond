<template>
	<view>
		<view class="uni-list">
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in listData" :key="key" @click="goDetail(value)">
				<view class="uni-media-list">
					<!-- <image class="uni-media-list-logo" :src="value.cover"></image> -->
					<view class="uni-media-list-body">
						<view class="uni-media-list-text-top">{{ value.title }}</view>
						<view class="uni-media-list-text-bottom">
							<text>{{ value.content }}</text>
							<text>{{ value.createdDate }}</text>
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

	export default {
		components: {
			uniLoadMore
		},
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
			};
		},
		onLoad() {
			
		},
		onShow() {
			var token = uni.getStorageSync('token').access_token;
			if (!token) {
				uni.reLaunch({
					url: '/pages/user/user'
				});
			}else{
				this.getList();
			}
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
			trigger(e) {
				uni.navigateTo({
					url: '/pages/message/message-detail/message-detail'
				});
			},
			getList() {
				this.status = 'loading';
				messageAPI.getPage({
					"filters": {},
					"page": this.last_page,
					"pageSize": 10,
					"sorts": {}
				},(data) => {
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
					url: '/pages/message/message-list/message-list.vue?id=' + e.id,
				});
			}
		}
	}
</script>

<style>

</style>
