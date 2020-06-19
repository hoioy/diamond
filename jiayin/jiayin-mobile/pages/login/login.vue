<template>
	<view class="container">
		<image src="/static/img/logo/logo.png" mode="widthFix"></image>
		<text>登陆后体验更好！</text>
		<button type="primary" class="primary" open-type="getUserInfo" @getuserinfo="weixinLogin()" @tap="weixinLogin()">一键登录</button>
	</view>
</template>

<script>
	import * as loginAPI from '@/api/login.js';
	import * as userAPI from '@/api/user.js';

	export default {
		data() {
			return {
				needBeforeLogin: false, //TODO 当清除缓存时候需要用户重新授权
			}
		},
		methods: {
			weixinLogin() {
				uni.login({
					provider: "weixin",
					success: (res) => {
						loginAPI.weChatMiniAppLogin('wxccbae6dc90e98a2f', res.code, (data) => {
							//后端返回登录态
							uni.getUserInfo({
								provider: "weixin",
								success: (infoRes) => {
									//微信用户信息存储到后端数据库中
									loginAPI.bindDiamondUaaUser({
										name: infoRes.userInfo.nickName,
										loginName: data.data.openid,
										avatar: infoRes.userInfo.avatarUrl,
										country: infoRes.userInfo.country,
										province: infoRes.userInfo.province,
										city: infoRes.userInfo.city,
										language: infoRes.userInfo.language
									}, (bindDiamondUaaUserData) => {
										//绑定用户成功后，调用/auth接口登录后端获取自定义jwt token
										loginAPI.login({
											username: data.data.openid,
											password: '123456'
										}, (loginData) => { //res为一个数组，数组第一项为错误信息，第二项为返回数据
											uni.setStorage({
												key: 'token',
												data: loginData.data,
												success: function() {
													console.log('tokensuccess');
													uni.navigateBack()
												}
											})
										})
									})
								},
								fail: (err) => {
									console.error(err)
									uni.showToast({
										title: '获取微信用户失败:' + err,
										icon: 'none',
										duration: 2000
									});
								}
							});
						})
					},
					fail: (err) => {
						console.error('授权登录失败：' + JSON.stringify(err));
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.container {
		min-height: 100%;
		display: flex;
		flex-flow: column;
		width: 100%;
		align-items: center;
		justify-content: center;
		image {
			width: 30%;
			margin-top: -10%;
			border-radius: $uni-border-radius-circle;
		}

		text {
			margin-top: $uni-spacing-row-base;
			color: $uni-text-color-grey;
		}

		button {
			margin-top: 10%;
			background-color: $jiayin-bg-color;
			color: $uni-color-title;
			width: 50%;
		}
	}
</style>
