<template>
	<view>
		<view hover-class="uni-list-cell-hover" v-for="(value, key) in pageDTO.list" :key="key">
			<uni-swipe-action>
				<uni-swipe-action-item :options="options2" :show="isOpened" :auto-close="false" @change="change" @click="bindClick">
					<view class="message-list-item">
						<view class="message-list-item-cell">
							<view class="message-list-item-cell-body" @click="goMessageSaveFromDraft(value)">
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
				</uni-swipe-action-item>
			</uni-swipe-action>
		</view>
		<uni-load-more :status="loadMoreData.status" :icon-size="16" :content-text="loadMoreData.contentText" />
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
	import uniPopup from '@/components/uni-popup/uni-popup.vue';
	import uniSwipeAction from '@/components/uni-swipe-action/uni-swipe-action.vue'
	import uniSwipeActionItem from '@/components/uni-swipe-action-item/uni-swipe-action-item.vue'
	
	import * as messageAPI from '@/api/message.js';
	import * as draftAPI from '@/api/draft.js';
	
	export default {
		components: {
			uniLoadMore,
			uniPopup,
			uniSwipeAction,
			uniSwipeActionItem
		},
		data() {
			return {
				options2: [{
					text: '取消',
					style: {
						backgroundColor: '#007aff'
					}
				}, {
					text: '确认',
					style: {
						backgroundColor: '#dd524d'
					}
				}],
				pageDTO: {
					"filters": {
						
					},
					"list": [],
					"page": 1,
					"pageSize": 10,
					"sorts": [{
						"direction": "desc",
						"fieldName": "createdDate"
					}],
					"total": 0
				},
				loadMoreData: {
					status: 'more',
					contentText: {
						contentdown: '上拉加载更多',
						contentrefresh: '加载中',
						contentnomore: '没有更多'
					}
				},
				showTipData: {
					title: null,
					content: null,
					forWhich: 1, // publish：1，delete：2
					currentDraftMsg: null
				},
				reload: false, //是否刷新模式，false：瀑布流
			};
		},
		created: function(event) {
			this.initList();
		},
		methods: {
			pullDownRefresh() {
				this.initList();
			},
			reachBottom() {
				this.loadMoreData.status = 'more';
				this.pageDTO.page += 1;
				this.getList();
			},
			backPress() {
				this.$refs['showtip'].close()
			},
			initList() {
				this.reload = true;
				this.pageDTO.page = 1;
				this.getList();
			},
			getList() {
				this.loadMoreData.status = 'loading';
				
				draftAPI.getPage(this.pageDTO, (data) => {
					if (this.reload) {
						this.pageDTO.list = data.data.list;
						this.loadMoreData.status = 'more';
					} else {
						this.pageDTO.list = this.pageDTO.list.concat(data.data.list);
						if (data.data.list.length > 0) {
							this.loadMoreData.status = 'more';
						} else {
							this.loadMoreData.status = 'noMore';
						}
					}
					this.reload = false;
					uni.stopPullDownRefresh();
				})
			},
			goMessageSaveFromDraft(e) {
				uni.navigateTo({
					url: '../message-save/message-save?draftId=' + e.id
				});
			},
			publishMessage(draftMsg) {
				draftAPI.publishMessage(draftMsg, (data) => {
					this.pageDTO.list = [];
					this.getList();
					uni.showToast({
						duration: 2000,
						title: '发布消息成功'
					});
				})
			},
			deleteMessage(draftMsg) {
				draftAPI.deleteById(draftMsg.id, (data) => {
					this.pageDTO.list = [];
					this.getList();
					uni.showToast({
						duration: 2000,
						title: '删除草稿成功'
					});
				})
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

<style lang="scss">
	.message-list-item {
		display: flex;
		flex-direction: column;
		border-bottom: 1px solid #B2B2B2;

		.message-list-item-cell {
			display: flex;

			/* flex-wrap: nowrap; */
			.message-list-item-cell-body {
				margin-top: 10px;
				margin-bottom: 10px;
				display: flex;
				flex-direction: column;
				width: 600rpx;

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
			}

			.message-list-item-cell-button {
				color: #888888;

				.message-list-item-cell-button-pulish {
					margin-bottom: 10px;
				}

				.message-list-item-cell-button-delete {
					margin-bottom: 10px;
				}

			}
		}
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

			.uni-tip-button {
				flex: 1;
				text-align: center;
				font-size: 14px;
				color: #3b4144;
			}
		}

	}
</style>
