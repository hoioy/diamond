<template>
	<view>
		<uni-notice-bar v-if="notice!=''" class="notice-bar" :scrollable="true" :single="true" :text="notice" />
		<swiper class="swiper" circular :indicator-dots="true" :autoplay="true" :interval="2000" :duration="500">
			<swiper-item v-for="(item, key) in advertList" :key="key">
				<view class="swiper-item">
					<image :src="item.icon"></image>
				</view>
			</swiper-item>
		</swiper>
		<view class="nav-container">
			<view class="nav-container-cell" v-for="(item, key) in msgTypeList" :key="key">
				<view class="nav-container-item" @tap="pageJump(item.path,item.id,item.typeName)">
					<view>
						<image class="nav-container-item-img-image" :src="item.icon"></image>
					</view>
					<view class="nav-container-item-name">{{item.typeName}}</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import uniNoticeBar from '@/components/uni-notice-bar/uni-notice-bar.vue'
	import * as messageAPI from '@/api/message.js';
	import * as msgTypeAPI from '@/api/msgType.js';
	import * as advertAPI from '@/api/advert.js';
	import * as noticeAPI from '@/api/notice.js';

	export default {
		components: {
			uniNoticeBar
		},
		data() {
			return {
				msgTypeList: [],
				advertList: [],
				notice: ""
			}
		},
		onLoad() {
			this.initMsgType()
			this.initAdvertAPI()
			this.initNoticeAPI()
		},
		methods: {
			initNoticeAPI() {
				const that = this
				noticeAPI.getPage({
					"filters": {},
					"page": 1,
					"pageSize": 5,
					"sorts": [],
				}, (data) => {
					if (data.data.list) {
						data.data.list.forEach(n => {
							that.notice += " "+n.content
						})
					} else {
						that.notice = ''
					}

				})
			},
			initAdvertAPI() {
				const that = this
				advertAPI.getPage({
					"filters": {},
					"page": 1,
					"pageSize": 5,
					"sorts": [],
				}, (data) => {
					that.advertList = data.data.list;
				})
			},
			initMsgType() {
				const that = this
				msgTypeAPI.selectParent(function(data) {
					that.msgTypeList = data.data
				})
			},
			pageJump(url, msgTypeParentId, title) {
				uni.navigateTo({
					url: url + '?msgTypeParentId=' + msgTypeParentId + '&title=' + title
				})
			}
		}
	}
</script>

<style lang="scss">
	.notice-bar {
		padding-left: 0;
		padding-right: 0;
	}

	.swiper {
		height: 250rpx;

		.swiper-item {
			height: 100%;
			width: 100%;

			image {
				height: 100%;
				width: 100%;
			}
		}
	}

	.nav-container {
		display: flex;
		margin-top: $uni-spacing-col-lg;
		flex-wrap: wrap;
		width: 750rpx;

		.nav-container-cell {
			width: 25%;

			.nav-container-item {
				display: flex;
				flex-direction: column;
				align-items: center;
				margin-top: $uni-spacing-col-lg;

				.nav-container-item-img-image {
					width: $uni-img-size-lg;
					height: $uni-img-size-lg;
				}

				.nav-container-item-name {}
			}
		}
	}
</style>
