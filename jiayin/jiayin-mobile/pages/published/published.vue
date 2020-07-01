<template>
	<view>
		<view class="list-container">
			<jiayinIndexList class="index-list" ref="jiayinIndexList" apiType="published" :swipeOptions="swipeOptions"></jiayinIndexList>
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
					text: '取消发布',
					style: {
						backgroundColor: '#dd6572'
					}
				}]
			}
		},
		onShow() {
			if (this.$refs.jiayinIndexList) {
				debugger
				this.$refs.jiayinIndexList.initList();
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