<template>
	<view class="uni-container">
		<view class="user">
			<view>
				<image class="userhead" :src="userinfo.avatarUrl" mode="aspectFit"></image>
			</view>
			<view>
				<view>
					<text>{{userinfo.nickName}}</text>
				</view>
				<view>
					<button v-if="alreadyLogin" @click="loginOut"> 退出登录</button>
					<button v-if="!alreadyLogin" @click="loginIn"> 登录</button>
				</view>
			</view>

		</view>
		<view class="my">
			<view class="my_item" v-for="(item,index) in navs" :key="index" @click="pageJump(item.path)">
				<view :class="item.icon"></view>
				<view class="my_font">{{item.title}}</view>
			</view>
		</view>
	</view>

</template>

<script>
	export default {
		data() {
			return {
				userinfo: {
					nickName: "",
					avatarUrl: ""
				},
				alreadyLogin: false,
				navs: [{
						icon: 'iconfont icon-shoucang',
						title: '收藏',
						path: '/pages/collect/collect-list/collect-list'
					},
					{
						icon: 'iconfont .icon-draft',
						title: '我的发布',
						path: '/pages/publish-history/history-list/publish-history-list'
					},
					{
						icon: 'iconfont .icon-lianxiwomen1-copy',
						title: '联系我们',
						path: '/pages/contact/contact-our'
					},
				]

			}
		},
		onLoad() {

		},
		onShow() {
			let userinfojson = uni.getStorageSync('userinfo')
			if(userinfojson){
					this.alreadyLogin = true
			}
			this.userinfo = JSON.parse(userinfojson);
		},
		methods: {
			//页面跳转
			pageJump(url) {
				console.log(url)
				uni.navigateTo({
					url
				})
			},
			loginIn() {
				uni.navigateTo({
					url: '/pages/login/login'
				});
			},
			loginOut() {

				uni.removeStorage({
					key: 'token',
					success: function(res) {
						console.log('移除token成功');
					}
				})
				uni.removeStorage({
					key: 'userinfo',
					success: function(res) {
						console.log('移除userinfo成功');
					}
				})
				this.userinfo = {}
				console.log("退出成功")
			}

		}
	}
</script>

<style>
	.userhead {
		left: auto;
		height: 100rpx;
		width: 100rpx;
	}

	.user {
		width: 750rpx;
		height: 200rpx;
		background-color: #FFD700;
		z-index: -1;
		display: flex;
	}

	.my {
		width: 90%;
		height: 250rpx;
		display: flex;
		background-color: #FFFFFF;
		margin: 10rpx auto;

	}

	.my_item {
		width: 25%;
		text-align: center;

	}

	.iconfont {
		width: 100rpx;
		height: 100rpx;
		background: #FFFFFF;
		margin: 10rpx auto;
		line-height: 120rpx;
		color: #FFD700;
		font-size: 50rpx;
	}

	.my_font {
		font-size: 30rpx;
	}
</style>
