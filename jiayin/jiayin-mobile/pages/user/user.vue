<template>
	<view>
		<view class="user-container">
			<view class="user-container-info-avatar">
				<image v-if="userinfo.avatar" :src="userinfo.avatar"></image>
				<image v-else src="/static/img/user-default.png"></image>
			</view>
			<view class="user-container-login" v-if="!alreadyLogin">
				<button type="default" plain="true" @click="loginIn">点此登录</button>
			</view>
			<view class="user-container-info-name" v-if="alreadyLogin">
				<text>{{userinfo.userName}}</text>
			</view>
		</view>

		<view class="nav-container">
			<view class="nav-container-cell" v-for="(item, key) in navs" :key="key">
				<view class="nav-container-item" @tap="pageJump(item.path)">
					<view>
						<image class="nav-container-item-img-image" :src="item.icon"></image>
					</view>
					<view class="nav-container-item-name">{{item.title}}</view>
				</view>
			</view>
		</view>

		<uni-list class="list-container">
			<uni-list-item title="联系我们" thumb="/static/img/user/contact.png" @click="pageJump('/pages/contact/contact-our')"/>
			<uni-list-item title="问题反馈" thumb="/static/img/user/feedback.png"  @click="pageJump('/pages/feedback/feedback')"/>
			<uni-list-item title="关于嘉荫" thumb="/static/img/user/about.png"  @click="pageJump('/pages/about/about')"/>
		</uni-list>
	</view>

</template>

<script>
	import uniList from '@/components/uni-list/uni-list.vue'
	import uniListItem from '@/components/uni-list-item/uni-list-item.vue'
	import * as userAPI from '@/api/user.js';
	export default {
		components: {
			uniList,
			uniListItem
		},
		data() {
			return {
				userinfo: {
					userName: '',
					avatar: ''
				},
				alreadyLogin: false,
				navs: [{
						icon: '/static/img/user/collect.png',
						title: '收藏',
						path: '/pages/collect/collect-list/collect-list'
					},
					{
						icon: '/static/img/user/publish.png',
						title: '我的发布',
						path: '/pages/publish-history/history-list/publish-history-list'
					}
				]

			}
		},
		onShow() {
			userAPI.getUser((res) => {
				if (res.data) {
					this.userinfo = res.data;
					if (this.userinfo.id == '6613831cac9e4597abbd0138116a8f3c') {
						this.userinfo.avatar = '/static/img/user-default.png'
					}
					this.alreadyLogin = true
				}
			})
		},
		methods: {
			//页面跳转 
			pageJump: function(url) {
				uni.navigateTo({
					url
				})
			},
			loginIn() {
				uni.navigateTo({
					url: '/pages/login/login'
				});
			}
		}
	}
</script>

<style lang="scss">
	.user-container {
		width: 750rpx;
		background-color: $jiayin-bg-color;
		display: flex;
		align-items: center;

		.user-container-login {
			margin-left: $uni-spacing-row-lg;
			margin-top: $uni-spacing-col-lg;
			margin-bottom: $uni-spacing-col-lg;
		}

		.user-container-info-avatar {
			margin-left: $uni-spacing-row-lg;
			margin-top: $uni-spacing-col-lg;
			margin-bottom: $uni-spacing-col-lg;

			image {
				width: $uni-img-size-lg;
				height: $uni-img-size-lg;
				border-radius: $uni-border-radius-lg;
			}
		}

		.user-container-info-name {
			margin-left: $uni-spacing-row-lg;
			margin-top: $uni-spacing-col-lg;
			margin-bottom: $uni-spacing-col-lg;
			font-size: $uni-font-size-base;
			color: $uni-color-title;
			align-self: flex-start;
		}
	}

	.nav-container {
		display: flex;
		justify-content: space-around;
		margin-top: $uni-spacing-col-lg;

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

	.list-container {
		margin-top: $uni-spacing-row-lg;
		color: $uni-color-paragraph;
		font-size: $uni-font-size-lg;
	}
</style>
