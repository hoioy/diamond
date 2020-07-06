<template>
	<view>
		<swiper class="swiper" circular :indicator-dots="true" :autoplay="true" :interval="5000" :duration="500">
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
		<uni-notice-bar v-if="notice!=''" class="notice-bar" :scrollable="true" :single="true" :text="notice" />
		<view class="list-container">
			<jiayinMessageList ref="jiayinMessageList" :msgTypetId="selectedMsgType.id" :msgTypeName="selectedMsgType.typeName"></jiayinMessageList>
		</view>
	</view>
</template>

<script>
	import uniNoticeBar from '@/components/uni-notice-bar/uni-notice-bar.vue'
	import jiayinMessageList from '@/components/jiayin-message-list/jiayin-message-list.vue';
	import * as msgTypeAPI from '@/api/msgType.js';
	import * as advertAPI from '@/api/advert.js';
	import * as noticeAPI from '@/api/notice.js';

	export default {
		components: {
			uniNoticeBar,
			jiayinMessageList
		},
		data() {
			return {
				msgTypeList: [],
				advertList: [],
				notice: "",
				selectedMsgType: {
					id: '',
					typeName: ''
				}
			}
		},
		onShow() {
			this.init(false)
		},
		onReady() {
			if (this.$refs.jiayinMessageList) {
				this.$refs.jiayinMessageList.init(this.selectedMsgType.id, this.selectedMsgType.typeName);
			}
		},
		onPullDownRefresh() {
			this.init(true)
			this.$refs.jiayinMessageList.pullDownRefresh();
		},
		onReachBottom() {
			this.$refs.jiayinMessageList.reachBottom();
		},
		methods: {
			init(isAll) {
				if (isAll) {
					this.initMsgType()
					this.initAdvertAPI()
					this.initNoticeAPI()
				} else {
					//为了减少前后端交互，不进行全量刷新
					if (this.msgTypeList.length <= 0) {
						this.initMsgType()
					}
					if (this.advertList.length <= 0) {
						this.initAdvertAPI()
					}
					if (this.notice == '') {
						this.initNoticeAPI()
					}
					if (this.$refs.jiayinMessageList && this.$refs.jiayinMessageList.pageDTO.list.length <=0) {
						debugger
						this.$refs.jiayinMessageList.init(this.selectedMsgType.id, this.selectedMsgType.typeName);
					}
				}
			},
			initNoticeAPI() {
				const that = this
				noticeAPI.getPage({
					"filters": {},
					"page": 1,
					"pageSize": 5,
					"sorts": [],
				}, (data) => {
					if (data.data.list) {
						that.notice = ''
						data.data.list.forEach(n => {
							that.notice += " " + n.content
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
			pageJump(url, msgTypeId, msgTypeName) {
				uni.navigateTo({
					url: url + '?msgTypeId=' + msgTypeId + '&msgTypeName=' + msgTypeName
				})
			}
		}
	}
</script>

<style lang="scss">
	.notice-bar {
		padding-left: 0;
		padding-right: 0;
		margin-bottom: 0px;
		width: 750rpx;
	}

	.swiper {
		width: 750rpx;
		height: 375rpx;

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
		padding: $uni-spacing-col-lg;
		flex-wrap: wrap;
		box-shadow: 1px 1px 5px $uni-border-color;
		border-radius: $uni-border-radius-lg;

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

	.list-container {
		width: 100%;
	}
</style>
