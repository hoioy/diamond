<template>
	<view>
		<view class="cate-section">
			<view v-for="(menu,index) in menuData" :key="menu.id">
				<view class="cate-item" @click="toPage(menu)">
					<image class="cate-item-image" src="/static/temp/c8.png"></image>
					<text class="cate-item-text">{{menu.menuName}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import * as menuAPI from '@/api/menu.js';

	export default {
		data() {
			return {
				// hasLogin: this.$store.state.authentication.token,
				// user: this.$store.state.authentication.user,
				menuData: []
			};
		},
		onLoad() {
			this.getMenuData();
		},
		methods: {
			getMenuData() {
				menuAPI.findMenu().then(data => {
					var [error, res] = data;
					if (res.data.code == 200) {
						this.menuData = res.data.data;
					}
				})
			},
			toPage: function(menu) {
				console.log(menu)
				uni.navigateTo({
					url: '../student/student-list/student-list'
				});
			}
		}
	}
</script>

<style lang="scss">
	page {
		.cate-section {
			border-radius: 16rpx 16rpx 0 0;
			margin-top: 20rpx;
		}
	}

	/* 分类 */
	.cate-section {
		display: flex;
		justify-content: flex-start;
		align-items: center;
		flex-wrap: wrap;

		.cate-item {
			display: flex;
			flex-direction: column;
			align-items: stretch;
			padding: 20rpx;

			.cate-item-text {
				font-size: $uni-font-size-sm + 2rpx;
				color: $uni-text-color;
				width: 150rpx;
				text-overflow: ellipsis;
				white-space: nowrap;
				overflow: hidden;
			}

			.cate-item-image {
				width: 110rpx;
				height: 110rpx;
				border-radius: 50%;
				opacity: .7;
				box-shadow: 4rpx 4rpx 20rpx rgba(250, 67, 106, 0.3);
			}
		}
	}
</style>
