<template>
	<view>
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
	import * as messageAPI from '@/api/message.js';
	import * as msgTypeAPI from '@/api/msgType.js';
	export default {
		data() {
			return {
				msgTypeList: []
			}
		},
		onLoad() {
			this.initMsgType()
		},
		methods: {
			initMsgType(){
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

				.nav-container-item-img-image {
					width: $uni-img-size-lg;
					height: $uni-img-size-lg;
				}

				.nav-container-item-name {}
			}
		}
	}
</style>
