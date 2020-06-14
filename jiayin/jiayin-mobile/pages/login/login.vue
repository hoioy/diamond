<template>
	<view class="content">
		<view v-if="hasProvider">
			<!-- #ifdef MP-WEIXIN -->
			<button v-if="needBeforeLogin" open-type='getUserInfo'>获取授权</button>
			<!-- #endif -->
			<button v-if="!hasLogin" type="primary" class="primary" @tap="oauth('weixin')">一键登录</button>
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
				hasProvider: true,
				account: 'admin',
				password: 'admin',
				positionTop: 0,
				isDevtools: false,
				hasLogin: uni.getStorageSync('token') ,
				needBeforeLogin: false, //当清除缓存时候需要用户重新授权
				user: {}
			}
		},
		computed: mapState(['forcedLogin']),
		onLoad() {
				console.log('onLoad')
			this.initData();
		},
		onShow(){
			console.log('onshow')
			var token =uni.getStorageSync('token')
			console.log(token)
			if(token===''){
				console.log('init hasLogin')
				this.hasLogin=false
			}else{
					this.hasLogin=true
			}
		},
		methods: {
			...mapMutations(['login']),
			initData() {
				if (uni.getStorageSync('token')) {
					userAPI.getUser((res) => {
						this.user = res.data.data;
					})
				}
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
			jwtLogin(param) {
				loginAPI.login(param, (data) => { //res为一个数组，数组第一项为错误信息，第二项为返回数据
				  uni.setStorage({
				    key: 'token',
				    data: data.data,
				    success: function () {
				        console.log('tokensuccess');
				    }
				  })
				})
			},
			oauthGetUserInfo(provider, openId, sessionKey) {
				console.log(provider, openId, sessionKey)
				uni.getUserInfo({
					provider: provider,
					success: (infoRes) => {
						//获取用户信息成功后进行缓存
					let userinfo=	JSON.stringify(infoRes.userInfo)
						uni.setStorage({
						  key: 'userinfo',
						  data: userinfo,
						  success: function () {
						      console.log('userinfo success');
						  }
						})
						console.log(infoRes.userInfo.avatarUrl)
						loginAPI.bindDiamondUaaUser({
							name: infoRes.userInfo.nickName,
							loginName: openId,
							avatarUrl: infoRes.userInfo.avatarUrl,
							country: infoRes.userInfo.country,
							province: infoRes.userInfo.province,
							city: infoRes.userInfo.city,
							language: infoRes.userInfo.language
						}, (data) => { //res为一个数组，数组第一项为错误信息，第二项为返回数据
							//绑定用户成功后，调用/auth接口登录后端获取自定义jwt token
							this.jwtLogin({
								username: openId,
								password: '123456'
							})
						})
					},
					fail: (err)=>{
						console.error(err)
						this.needBeforeLogin = true;
						uni.showToast({
						    title: '获取微信用户失败:'+err,
							icon: 'none',
						    duration: 2000
						});
					}
				});
			},
			oauth(value) {
				uni.login({
					provider: value,
					success: (res) => {
						loginAPI.weChatMiniAppLogin('wxccbae6dc90e98a2f', res.code, (data) => {
							//绑定用户成功后，调用/auth接口登录后端获取自定义jwt token
							this.oauthGetUserInfo(value, data.data.openid, data.data.sessionKey)
						})
					},
					fail: (err) => {
						console.error('授权登录失败：' + JSON.stringify(err));
					}
				});
			},
			getUserInfo({
				detail
			}) {
				if (detail.userInfo) {
					uni.showToast({
					    title: '登陆成功',
					    duration: 2000
					});
				} else {
					uni.showToast({
					    title: '登陆成功',
					    duration: 2000
					});
				}
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
