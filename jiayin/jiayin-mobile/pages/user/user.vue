<template>
	<view class="content">
		<!-- <view class="input-group">
			<view class="input-row border">
				<text class="title">账号：</text>
				<m-input class="m-input" type="text" clearable focus v-model="account" placeholder="请输入账号"></m-input>
			</view>
			<view class="input-row">
				<text class="title">密码：</text>
				<m-input type="password" displayable v-model="password" placeholder="请输入密码"></m-input>
			</view>
		</view>
		<view class="btn-row">
			<button type="primary" class="primary" @tap="bindLogin">登录</button>
		</view>
		<view class="action-row">
			<navigator url="../reg/reg">注册账号</navigator>
			<text>|</text>
			<navigator url="../pwd/pwd">忘记密码</navigator>
		</view> -->
		<!-- <view class="oauth-row" v-if="hasProvider" v-bind:style="{top: positionTop + 'px'}"> -->
		<!-- <view class="oauth-image" v-for="provider in providerList" :key="provider.value"> -->
		<!-- <image :src="provider.image" @tap="oauth(provider.value)"></image> -->
		<!-- #ifdef MP-WEIXIN -->
		<!-- <button v-if="!isDevtools" open-type="getUserInfo" @getuserinfo="getUserInfo"></button> -->
		<!-- #endif -->
		<!-- </view> -->
		<!-- </view> -->
		<view class="uni-header-logo">
			<image class="uni-header-image" :src="user.avatarUrl"></image>
		</view>
		<view class="uni-common-mt">
			<view class="uni-form-item uni-column">
				<view class="title">{{user.userName}}</view>
			</view>
			<view class="uni-form-item uni-column">
				<view class="title">{{user.loginName}}</view>
			</view>
			<view class="uni-form-item uni-column">
				<view class="title">{{user.phone}}</view>
			</view>
			<view class="uni-form-item uni-column">
				<view class="title">{{user.email}}</view>
			</view>
		</view>
		<view v-if="hasLogin">
			<view class="uni-panel">
				<view class="uni-panel-h">
					<navigator url="../pwd/pwd"></navigator>
					<text class="uni-panel-text">问题反馈</text>
					<text class="uni-panel-icon uni-icon">&#xe470;</text>
				</view>
			</view>
		</view>
		<view v-if="hasProvider">
			<button v-if="hasLogin" type="default" @tap="bindLogout">退出</button>
			<button v-if="!hasLogin" type="primary" class="primary" @tap="oauth('weixin')">微信登录</button>
		</view>
	</view>
</template>

<script>
	import * as loginAPI from '@/api/login.js';
	import * as userAPI from '@/api/user.js';
	import {
		mapState,
		mapMutations
	} from 'vuex'
	import mInput from '@/components/m-input.vue'

	export default {
		components: {
			mInput
		},
		data() {
			return {
				providerList: [],
				hasProvider: false,
				account: 'admin',
				password: 'admin',
				positionTop: 0,
				isDevtools: false,
				hasLogin: this.$store.state.authentication.token,
				user: this.$store.state.authentication.user
			}
		},
		onLoad() {
			userAPI.getUser().then(data => {
				var [error, res] = data;
				if (res.data.code == 200) {
					this.user = res.data.data;
				}
			})
		},
		computed: mapState(['forcedLogin']),
		methods: {
			...mapMutations(['login']),
			bindLogout() {
				this.$store.dispatch('Logout').then(() => {
					this.hasLogin = false
					this.user = {}
				})
			},
			initProvider() {
				const filters = ['weixin', 'qq', 'sinaweibo'];
				uni.getProvider({
					service: 'oauth',
					success: (res) => {
						if (res.provider && res.provider.length) {
							for (let i = 0; i < res.provider.length; i++) {
								if (~filters.indexOf(res.provider[i])) {
									this.providerList.push({
										value: res.provider[i],
										image: '../../static/img/' + res.provider[i] + '.png'
									});
								}
							}
							this.hasProvider = true;
						}
					},
					fail: (err) => {
						console.error('获取服务供应商失败：' + JSON.stringify(err));
					}
				});
			},
			initPosition() {
				/**
				 * 使用 absolute 定位，并且设置 bottom 值进行定位。软键盘弹出时，底部会因为窗口变化而被顶上来。
				 * 反向使用 top 进行定位，可以避免此问题。
				 */
				this.positionTop = uni.getSystemInfoSync().windowHeight - 100;
			},
			// bindLogin() {
			// 	/**
			// 	 * 客户端对账号信息进行一些必要的校验。
			// 	 * 实际开发中，根据业务需要进行处理，这里仅做示例。
			// 	 */
			// 	if (this.account.length < 5) {
			// 		uni.showToast({
			// 			icon: 'none',
			// 			title: '账号最短为 5 个字符'
			// 		});
			// 		return;
			// 	}
			// 	if (this.password.length < 5) {
			// 		uni.showToast({
			// 			icon: 'none',
			// 			title: '密码最短为 5 个字符'
			// 		});
			// 		return;
			// 	}
			// 	/**
			// 	 * 下面简单模拟下服务端的处理
			// 	 * 检测用户账号密码是否在已注册的用户列表中
			// 	 * 实际开发中，使用 uni.request 将账号信息发送至服务端，客户端在回调函数中获取结果信息。
			// 	 */
			// 	jwtLogin({
			// 		username: this.account,
			// 		password: this.password
			// 	})
			// },
			jwtLogin(param) {
				const toMainTmp = this.toMain
				loginAPI.login(param).then(data => { //res为一个数组，数组第一项为错误信息，第二项为返回数据
					var [error, res] = data;
					if (res.data.code == 200) {
						this.$store.dispatch('LoginSuccess', res.data.data).then(() => {
							loginAPI.getUser().then(userData => {
								var [userDataError, userDataRes] = userData;
								if (userDataRes.data.code == 200) {
									this.$store.dispatch('GetUserSuccesss', userDataRes.data.data).then(() => {
										toMainTmp();
									})
								}
							})
						})
					} else {
						this.toast('用户账号或密码不正确')
					}
				})
			},
			oauthGetUserInfo(provider, openId, sessionKey) {
				uni.getUserInfo({
					provider: provider,
					success: (infoRes) => {
						loginAPI.bindDiamondUaaUser({
							name: infoRes.userInfo.nickName,
							loginName: openId,
							avatarUrl: infoRes.userInfo.avatarUrl,
							country: infoRes.userInfo.country,
							province: infoRes.userInfo.province,
							city: infoRes.userInfo.city,
							language: infoRes.userInfo.language
						}).then(data => { //res为一个数组，数组第一项为错误信息，第二项为返回数据
							var [error, res] = data;
							if (res.data.code == 200) {
								//绑定用户成功后，调用/auth接口登录后端获取自定义jwt token
								this.jwtLogin({
									username: openId,
									password: '123456'
								})
							} else {
								this.toast('绑定用户失败')
							}
						})
					},
					fail() {
						this.toast('服务器获取token失败，请尝试重新登录')
					}
				});
			},
			toast(msg) {
				this.$store.dispatch('Logout').then(() => {
					uni.showToast({
						icon: 'none',
						title: msg,
					});
				})
			},
			oauth(value) {
				uni.login({
					provider: value,
					success: (res) => {
						loginAPI.weChatMiniAppLogin('wxccbae6dc90e98a2f', res.code)
							.then(data => { //res为一个数组，数组第一项为错误信息，第二项为返回数据
								var [error, res] = data;
								if (res.data.code == 200) {
									//绑定用户成功后，调用/auth接口登录后端获取自定义jwt token
									this.oauthGetUserInfo(value, res.data.data.openid, res.data.data.sessionKey)
								} else {
									this.toast('服务器获取token失败，请尝试重新登录')
								}
							})
					},
					fail: (err) => {
						this.toast('授权登录失败')
						console.error('授权登录失败：' + JSON.stringify(err));
					}
				});
			},
			getUserInfo({
				detail
			}) {
				if (detail.userInfo) {
					this.toMain();
				} else {
					uni.showToast({
						icon: 'none',
						title: '登陆失败'
					});
				}
			},
			toMain() {
				uni.reLaunch({
					url: '/pages/message/message-list/message-list'
				});
			}
		},
		onReady() {
			this.initPosition();
			this.initProvider();
			// #ifdef MP-WEIXIN
			this.isDevtools = uni.getSystemInfoSync().platform === 'devtools';
			// #endif
		}
	}
</script>

<style>
	@import '../../common/uni-nvue.css';
	.action-row {
		display: flex;
		flex-direction: row;
		justify-content: center;
	}

	.action-row navigator {
		color: #007aff;
		padding: 0 10px;
	}

	.oauth-row {
		display: flex;
		flex-direction: row;
		justify-content: center;
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
	}

	.oauth-image {
		position: relative;
		width: 50px;
		height: 50px;
		border: 1px solid #dddddd;
		border-radius: 50px;
		margin: 0 20px;
		background-color: #ffffff;
	}

	.oauth-image image {
		width: 30px;
		height: 30px;
		margin: 10px;
	}

	.oauth-image button {
		position: absolute;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		opacity: 0;
	}
</style>
