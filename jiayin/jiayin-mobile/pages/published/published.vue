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
				}],
				isOnIniting: false
			}
		},
		onShow() {
			if (this.$refs.jiayinIndexList) {
				this.initIndexList()
			}
		},
		onReady() {
			if (!this.isOnIniting && this.$refs.jiayinIndexList &&
				this.$refs.jiayinIndexList.pageDTO.list.length <= 0) {
				this.initIndexList()
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
		},
		methods: {
			initIndexList() {
				this.isOnIniting = true
				var that = this
				this.$refs.jiayinIndexList.initList(function() {
					that.isOnIniting = false
				});
			},
		}
	}
</script>

<style lang="scss">
	.list-container {
		width: 750rpx;

		.index-list {
			width: 750rpx;
		}
	}
</style>
