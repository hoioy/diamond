<template>
	<view>
		<view class="message-publish-type-title">新增一条消息</view>
		<view class="message-publish-type">
			<view class="message-publish-type-cell" v-for="(value, key) in msgTypelistData" :key="key" @click="goMessageSave(value)">
				<view class="message-publish-type-item">
					<view class="message-publish-type-item-img">
						<image class="message-publish-type-item-img-image" :src="'../../../static/img/msgType/'+value.typeName+'.png'"
						 @error="imageError"></image>
					</view>
					<view class="message-publish-type-item-name">新增{{ value.typeName }}消息</view>
				</view>
			</view>
		</view>
		<view class="uni-list-title">草稿箱</view>
		<view>
			<view hover-class="uni-list-cell-hover" v-for="(value, key) in listData" :key="key">
				<view class="message-list-item">
					<view class="message-list-item-cell">
						<view class="message-list-item-cell-body" @click="goMessageSave(value)">
							<view class="message-list-item-cell-body-title">{{ value.title }}</view>
							<view class="message-list-item-cell-body-content">{{ value.content }}</view>
							<view class="message-list-item-cell-body-date">{{ value.createdDate }}</view>
						</view>
						<view class="message-list-item-cell-button">
							<button class="message-list-item-cell-button-pulish" @tap="openPopupPublish(value)">发布</button>
							<button class="message-list-item-cell-button-delete" @tap="openPopupDelete(value)">删除</button>
						</view>
					</view>
				</view>
			</view>
		</view>
		<uni-load-more :status="status" :icon-size="16" :content-text="contentText" />
		<uni-popup ref="showtip" type="center" :mask-click="false" @change="popupChange">
			<view class="uni-tip">
				<text class="uni-tip-title">{{showTipData.title}}</text>
				<text class="uni-tip-content">{{showTipData.content}}</text>
				<view class="uni-tip-group-button">
					<text class="uni-tip-button" @click="onCancel()">取消</text>
					<text class="uni-tip-button" @click="onSure()">确定</text>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	import uniPopup from '@/components/uni-popup/uni-popup.vue'
	import * as messageAPI from '@/api/message.js';
	import * as draftAPI from '@/api/draft.js';
	import * as msgTypeAPI from '@/api/msgType.js';

	export default {
		components: {
			uniLoadMore,
			uniPopup,
		},
		data() {
			return {
				listData: [],
				msgTypelistData: [],
				last_page: 1,
				reload: false, //是否刷新模式，false：瀑布流
				status: 'more',
				contentText: {
					contentdown: '上拉加载更多',
					contentrefresh: '加载中',
					contentnomore: '没有更多'
				},
				showTipData:{
					title:null,
					content:null,
					forWhich: 1 ,// publish：1，delete：2
					currentDraftMsg: null
				}
			};
		},
		onLoad() {
			this.getList();
			this.initMsgType();
		},
		onPullDownRefresh() {
			this.reload = true;
			this.last_page = 1;
			this.getList();
		},
		onReachBottom() {
			this.status = 'more';
			this.last_page += 1;
			this.getList();
		},
		onBackPress() {
			this.$refs['showtip'].close()
		},
		methods: {
			imageError(e) {
				console.error('image发生error事件，携带值为' + e.detail.errMsg)
			},
			initMsgType() {
				var that = this;
				msgTypeAPI.selectParent((data) => {
					that.msgTypelistData = data.data
				})
			},
			getList() {
				this.status = 'loading';
				draftAPI.getPage({
					"filters": {},
					"page": this.last_page,
					"pageSize": 10,
					"sorts": {}
				}, (data) => {
					if (this.reload) {
						this.listData = data.data.list;
						this.status = 'more';
					} else {
						this.listData = this.listData.concat(data.data.list);
						if (data.data.list.length > 0) {
							this.status = 'more';
						} else {
							this.status = 'noMore';
						}
					}
					this.reload = false;
					uni.stopPullDownRefresh();
				})
			},
			goMessageSave(e) {
				uni.navigateTo({
					url: '../message-save/message-save?msgTypeId=' + e.id,
				});
			},
			publishMessage(draftMsg) {
				console.log('publishMessage:')
			},
			deleteMessage(draftMsg) {
				console.log('deleteMessage:')
			},
			popupChange(e) {
				console.log('是否打开:' + e.show)
			},
			openPopupPublish(draftItem) {
				this.showTipData.title = ''
				this.showTipData.content = '确定要发布此消息？'
				this.showTipData.forWhich = 1
				this.showTipData.currentDraftMsg = draftItem
				this.$nextTick(() => {
					this.$refs['showtip'].open()
				})
			},
			openPopupDelete(draftItem) {
				this.showTipData.title = ''
				this.showTipData.content = '确定要删除此消息？'
				this.showTipData.forWhich = 2
				this.showTipData.currentDraftMsg = draftItem
				this.$nextTick(() => {
					this.$refs['showtip'].open()
				})
			},
			onSure() {
				switch (this.showTipData.forWhich) {
					case 1:
						this.publishMessage(this.showTipData.currentDraftMsg);
						break
					case 2:
						this.deleteMessage(this.showTipData.currentDraftMsg);
						break
				}
				this.$refs['showtip'].close()
			},
			onCancel() {
				this.$refs['showtip'].close()
			}
		}
	}
</script>

<style>
	.message-publish-type-title {
		margin: 15px;
		border-bottom: 1px dashed #B2B2B2;
		padding: 5px;
	}

	.uni-list-title {
		margin: 15px;
		border-bottom: 1px dashed #B2B2B2;
		padding: 5px;
	}

	.message-publish-type {
		display: flex;
		justify-content: space-around;
		border-bottom: 2px solid #ffd115;
		padding-bottom: 20px;
	}

	.message-publish-type-item {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.message-publish-type-item-img {}

	.message-publish-type-item-img-image {
		width: 50px;
		height: 50px;
		background-color: #eeeeee;
	}

	.message-publish-type-item-name {}

	.message-list-item {
		display: flex;
		flex-direction: column;
		border-bottom: 1px solid #B2B2B2;
	}

	.message-list-item-cell {
		display: flex;
		/* flex-wrap: nowrap; */
	}

	.message-list-item-cell-body {
		margin-top: 10px;
		margin-bottom: 10px;
		display: flex;
		flex-direction: column;
		width: 600rpx;
	}

	.message-list-item-cell-body-title {
		font-size: 17pt;
		color: #000000;
		padding-left: 15px;
	}

	.message-list-item-cell-body-content {
		margin-top: 8px;
		font-size: 14pt;
		color: #353535;
		padding-left: 15px;
		padding-right: 15px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.message-list-item-cell-body-date {
		font-size: 11pt;
		color: #888888;
		padding-left: 15px;
		padding-right: 15px;
	}

	.message-list-item-cell-button {
		color: #888888;
	}

	.message-list-item-cell-button-pulish {
		margin-bottom: 10px;
	}

	.message-list-item-cell-button-delete {
		margin-bottom: 10px;
	}

	/* 提示窗口 */
	.uni-tip {
		/* #ifndef APP-NVUE */
		display: flex;
		flex-direction: column;
		/* #endif */
		padding: 15px;
		width: 300px;
		background-color: #fff;
		border-radius: 10px;
	}

	.uni-tip-title {
		margin-bottom: 10px;
		text-align: center;
		font-weight: bold;
		font-size: 16px;
		color: #333;
	}

	.uni-tip-content {
		/* padding: 15px;*/
		font-size: 14px;
		color: #666;
	}

	.uni-tip-group-button {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		margin-top: 20px;
	}

	.uni-tip-button {
		flex: 1;
		text-align: center;
		font-size: 14px;
		color: #3b4144;
	}
</style>
