<template>
	<view>
		<view class="list-container">
			<jiayinIndexList class="index-list" ref="jiayinIndexList" apiType="collect" :swipeOptions="swipeOptions"></jiayinIndexList>
		</view>
	</view>
</template>

<script>
	import jiayinIndexList from '@/components/jiayin-index-list/jiayin-index-list.vue';

	export default {
		components: {
			jiayinIndexList
		},
		data() {
			return {
				swipeOptions: [{
					text: '取消收藏',
					style: {
						backgroundColor: '#dd6572'
					}
				}]
			}
		},
		onReady() {
			if (this.$refs.jiayinIndexList) {
				this.$refs.jiayinIndexList.initList();
			}
		},
		onPullDownRefresh() {
			this.$refs.jiayinIndexList.pullDownRefresh();
		},
		onReachBottom() {
			this.$refs.jiayinIndexList.reachBottom();
		},
		onBackPress() {
			this.$refs.jiayinIndexList.backPress();
		}
	}
</script>

<style lang="scss">
	.list-container {
		.index-list {
			width: 750rpx;
		}
	}
</style>
