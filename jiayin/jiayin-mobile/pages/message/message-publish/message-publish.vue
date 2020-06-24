<template>
	<view>
		<view class="nav-container-title">新增一条消息</view>
		<view class="nav-container">
			<view class="nav-container-cell" v-for="(value, key) in msgTypeList" :key="key" @click="goMessageSave(value)">
				<view class="nav-container-item">
					<view class="nav-container-item-img">
						<image class="nav-container-item-img-image" :src="value.icon"></image>
					</view>
					<view class="nav-container-item-name">新增{{ value.typeName }}消息</view>
				</view>
			</view>
		</view>
		<view class="uni-list-title">草稿箱</view>
		<view class="list-container">
			<jiayinIndexList ref="jiayinIndexList"></jiayinIndexList>
		</view>
	</view>
</template>

<script>
	import jiayinIndexList from '@/components/jiayin-index-list/jiayin-index-list.vue';
	import * as msgTypeAPI from '@/api/msgType.js';
	export default {
		components: {
			jiayinIndexList
		},
		data() {
			return {
				msgTypeList: []
			};
		},
		onShow() {
			this.initMsgType();
		},
		onPullDownRefresh() {
			this.$refs.jiayinIndexList.pullDownRefresh();
		},
		onReachBottom() {
			this.$refs.jiayinIndexList.reachBottom();
		},
		onBackPress() {
			this.$refs.jiayinIndexList.backPress();
		},
		methods: {
			initMsgType() {
				var that = this;
				msgTypeAPI.selectParent((data) => {
					that.msgTypeList = data.data
				})
			},
			goMessageSave(e) {
				uni.navigateTo({
					url: '../message-save/message-save?msgTypeId=' + e.id + '&msgTypeName=' + e.typeName
				});
			}
		}
	}
</script>

<style lang="scss">
	.nav-container-title {
		margin: 15px;
		border-bottom: 1px dashed #B2B2B2;
		padding: 5px;
	}

	.nav-container {
		display: flex;
		margin-top: $uni-spacing-col-lg;
		flex-wrap: wrap;
		width: 750rpx;

		.nav-container-cell {
			width: 33%;

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

	.uni-list-title {
		margin: 15px;
		border-bottom: 1px dashed #B2B2B2;
		padding: 5px;
	}
</style>
