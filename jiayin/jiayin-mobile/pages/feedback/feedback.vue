<template>
	<view class="page">
		<view class='feedback-title'>
			<text>问题或需求建议</text>
			<text class="feedback-quick" @tap="chooseMsg">快速键入</text>
		</view>
		<view class="feedback-body">
			<textarea placeholder="请详细描述你的问题和意见..." v-model="sendDate.content" class="feedback-textare"></textarea>
		</view>
		<view>
			<view class="feedback-body feedback-uploader">
				<view class="uni-uploader">
					<view class="uni-uploader-head">
						<view class="uni-uploader-title">点击预览问题反馈图片</view>
						<view class="uni-uploader-info">{{imageList.length}}/4</view>
					</view>
					<view class="uni-uploader-body">
						<view class="uni-uploader__files">
							<block v-for="(image,index) in imageList" :key="index">
								<view class="uni-uploader__file" style="position: relative;">
									<image class="uni-uploader__img" :src="image" @tap="previewImage(index)"></image>
									<view class="close-view" @click="close(index)">x</view>
								</view>
							</block>
							<view class="uni-uploader__input-box" v-show="imageList.length < 4">
								<view class="uni-uploader__input" @tap="chooseImg"></view>
							</view>
						</view>
					</view>
				</view>
			</view>
			<view class='feedback-title'>
				<text>选填信息</text>
			</view>
			<view class="feedback-body">
				<input class="feedback-input" v-model="sendDate.contact" placeholder="(QQ/邮箱/手机号等,方便我们联系您)" />
			</view>
		</view>

		<button type="default" class="feedback-submit" @tap="send">提交</button>
		
		<view>todolist: 1. 后端问题反馈数据表，2. 后端上传图片接口调通 3.前端图片压缩</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				msgContents: ["界面显示错乱", "启动缓慢，太卡了", "UI太难看了", "经常崩溃"],
				imageList: [],
				sendDate: {
					score: 0,
					content: "",
					contact: ""
				}
			}
		},
		onLoad() {

		},
		methods: {
			close(e) {
				this.imageList.splice(e, 1);
			},
			chooseMsg() { //快速输入
				uni.showActionSheet({
					itemList: this.msgContents,
					success: (res) => {
						this.sendDate.content = this.msgContents[res.tapIndex];
					}
				})
			},
			chooseImg() { //选择图片
				uni.chooseImage({
					sourceType: ["camera", "album"],
					sizeType: "compressed",
					count: 4 - this.imageList.length,
					success: (res) => {
						this.imageList = this.imageList.concat(res.tempFilePaths);
					}
				})
			},
			chooseStar(e) { //点击评星
				this.sendDate.score = e;
			},
			previewImage(index) { //预览图片
				uni.previewImage({
					urls: this.imageList,
					current: this.imageList[index]
				});
			},
			send() { //发送反馈
				console.log(JSON.stringify(this.sendDate));
				if (this.imageList.length === 0) {
					uni.showModal({
						content: '至少选择一张图片',
						showCancel: false
					})
					return
				}
				if (this.sendDate.content.length === 0) {
					uni.showModal({
						content: '请输入问题和意见',
						showCancel: false
					})
					return
				}
				uni.showLoading({
					title: '上传中...'
				})
				let imgs = this.imageList.map((value, index) => {
					return {
						name: "image" + index,
						uri: value
					}
				})
				debugger
					var header = {
						'Authorization': 'Bearer BaseJwtToken80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E511A60071C37A046D7D601CFAC13499792237A0892B577F9F2F9275873CF4DFC0F951668D3468DF6C7D9137075F6812D8D09F657EE099AD4B480B1AEA4AE09AC4230FFDCB9EEA9AFE1847C44003D890AD50C68132FD3EC35F0A460B547C763314B1D5E22C42E1ACE18602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB3539266D9AD3037367BDA62C5F47FC5D9806D29995D79A02129B12BD4674FE02C65C21B56BDF5D92434DAE1CA31DD63C244261D'
					}
	
				uni.uploadFile({
					url: "http://localhost:7779/jiayin/file/upload",
					files: imgs,
					formData: this.sendDate,
					header: header,
					success: (res) => {
						debugger
						if (typeof res.data === 'string') {
							res.data = JSON.parse(res.data)
						}
						if (res.statusCode === 200 && res.data ) {
							uni.showModal({
								content: '反馈成功',
								showCancel: false
							})
							this.imageList = [];
							this.sendDate = {
								score: 0,
								content: "",
								contact: ""
							}
						} else if (res.statusCode !== 200) {
							uni.showModal({
								content: '反馈失败，错误码为：' + res.statusCode,
								showCancel: false
							})
						} else {
							uni.showModal({
								content: '反馈失败',
								showCancel: false
							})
						}
					},
					fail: (res) => {
						console.log(JSON.stringify(res))
					},
					complete() {
						uni.hideLoading()
					}
				});
			}
		}
	}
</script>

<style lang="scss">
	.page {
		width: 750rpx;
		padding: $uni-spacing-row-base;
	}

	/*问题反馈*/
	.feedback-title {
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;
		color: $uni-color-subtitle;
		font-size: $uni-font-size-base;
	}

	.feedback-quick {
		position: relative;
		padding-right: $uni-spacing-row-lg;
	}

	.feedback-quick:after {
		font-family: uniicons;
		font-size: $uni-font-size-lg;
		content: '\e581';
		position: absolute;
		right: 0;
		top: 50%;
		color: #bbb;
		-webkit-transform: translateY(-50%);
		transform: translateY(-50%);
	}

	.feedback-body {
		background: #fff;
	}

	.feedback-textare {
		min-height: 200rpx;
		font-size: $uni-font-size-lg;
		line-height: 50rpx;
		width: 100%;
		box-sizing: border-box;
		padding: $uni-spacing-col-base;
		margin-top: $uni-spacing-row-base;
		margin-bottom: $uni-spacing-row-base;
		border: 1px solid $uni-border-color;
	}

	.feedback-input {
		font-size: $uni-font-size-lg;
		height: 50rpx;
		min-height: 50rpx;
		padding: $uni-spacing-col-base;
		margin-top: $uni-spacing-row-base;
		line-height: 50rpx;
		border: 1px solid $uni-border-color;
	}

	.feedback-uploader {
		padding-bottom: $uni-spacing-col-base;
	}

	.feedback-submit {
		background: $jiayin-bg-color;
		color: #000000;
		margin-top: $uni-spacing-row-base;
	}

	.feedback-submit:hover,
	.feedback-submit:active {
		background: $jiayin-bg-color-active;
	}

	/* 上传 */
	.uni-uploader {
		flex: 1;
		flex-direction: column;

		.uni-uploader-head {
			display: flex;
			flex-direction: row;
			justify-content: space-between;

			.uni-uploader-info {
				color: $uni-border-color;
			}
		}

		.uni-uploader-body {
			margin-top: $uni-spacing-col-base;

			.uni-uploader__files {
				display: flex;
				flex-direction: row;
				flex-wrap: wrap;

				.uni-uploader__file {
					margin: 10rpx;
					width: 210rpx;
					height: 210rpx;

					.uni-uploader__img {
						display: block;
						width: 210rpx;
						height: 210rpx;
					}
				}

				.uni-uploader__input-box {
					position: relative;
					margin: 10rpx;
					width: 208rpx;
					height: 208rpx;
					border: 2rpx solid #D9D9D9;

					.uni-uploader__input {
						position: absolute;
						z-index: 1;
						top: 0;
						left: 0;
						width: 100%;
						height: 100%;
						opacity: 0;
					}
				}

				.uni-uploader__input-box:before,
				.uni-uploader__input-box:after {
					content: " ";
					position: absolute;
					top: 50%;
					left: 50%;
					-webkit-transform: translate(-50%, -50%);
					transform: translate(-50%, -50%);
					background-color: #D9D9D9;
				}

				.uni-uploader__input-box:before {
					width: 4rpx;
					height: 79rpx;
				}

				.uni-uploader__input-box:after {
					width: 79rpx;
					height: 4rpx;
				}

				.uni-uploader__input-box:active {
					border-color: #999999;
				}

				.uni-uploader__input-box:active:before,
				.uni-uploader__input-box:active:after {
					background-color: #999999;
				}
			}
		}
	}

	.close-view {
		text-align: center;
		line-height: 14px;
		height: 16px;
		width: 16px;
		border-radius: 50%;
		background: #FF5053;
		color: #FFFFFF;
		position: absolute;
		top: -6px;
		right: -4px;
		font-size: 12px;
	}
</style>
