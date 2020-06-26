<template>
	<view>
		<view hover-class="uni-list-cell-hover" v-for="(value, key) in pageDTO.list" :key="key">
			<uni-swipe-action class="uni-swipe-action">
				<uni-swipe-action-item :options="swipeOptions" :show="value.isOptionOpened" :auto-close="false" @change="bindOptionChange($event,value)"
				 @click="bindOptionClick($event,value)">
					<view class="index-list-item-cell" @tap="goMessageSaveFromDraft(value)">
						<view class="index-list-item-cell-title">{{ value.msgTitle }}</view>
						<view class="index-list-item-cell-content">
							<view class="index-list-item-cell-content-type" :style="{backgroundColor: value.msgTypeColor}">{{ value.msgTypeName}}</view>
							<view class="index-list-item-cell-content-date">{{ value.createdDate |formatDate}}</view>
						</view>
					</view>
				</uni-swipe-action-item>
			</uni-swipe-action>
		</view>
		<uni-load-more :status="loadMoreData.status" :icon-size="16" :content-text="loadMoreData.contentText" />
		<uni-popup ref="showtip" type="center" :mask-click="false">
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

	import dateFormat from '@/utils/date.js'
	import * as messageAPI from '@/api/message.js';
	import * as draftAPI from '@/api/draft.js';
	import * as publishAPI from '@/api/publish.js';

	export default {
		components: {
			uniLoadMore,
			uniPopup,
			uniSwipeAction,
			uniSwipeActionItem
		},
		data() {
			return {
				swipeOptions: [{
					text: '发布',
					style: {
						backgroundColor: '#FFD700',
						color: '#2C405A'
					}
				}, {
					text: '删除',
					style: {
						backgroundColor: '#dd6572'
					}
				}],
				pageDTO: {
					"filters": {},
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

		},
		filters: {
			formatDate(date) {
				let nDate = new Date(date);
				return dateFormat.formatDate(nDate, "yyyy年MM月dd日 hh时mm分");
			}
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
					data.data.list.forEach(item => {
						item.isOptionOpened = false
					})
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
			goMessageSaveFromDraft(item) {
				uni.navigateTo({
					url: '../message-save/message-save?messageId=' + item.msgId
				});
			},
			bindOptionChange(isOpened, value) {
				if (isOpened) {
					this.pageDTO.list.forEach(item => {
						item.isOptionOpened = (item.id == value.id)
					})
				} else {
					this.pageDTO.list.forEach(item => {
						if (item.id == value.id) {
							item.isOptionOpened = false
						}
					})
				}
			},
			bindOptionClick(e, value) {
				switch (e.content.text) {
					case '发布':
						this.showTipData.title = ''
						this.showTipData.content = '确定要发布此消息？'
						this.showTipData.forWhich = 1
						this.showTipData.currentDraftMsg = value
						this.$nextTick(() => {
							this.$refs['showtip'].open()
						})
						break;
					case '删除':
						this.showTipData.title = ''
						this.showTipData.content = '确定要删除此消息？'
						this.showTipData.forWhich = 2
						this.showTipData.currentDraftMsg = value
						this.$nextTick(() => {
							this.$refs['showtip'].open()
						})
						break;
				}
			},
			onSure() {
				const that = this
				switch (that.showTipData.forWhich) {
					case 1:
						messageAPI.findById(that.showTipData.currentDraftMsg.msgId, function(data) {
							publishAPI.publish(data.data, (publishData) => {
								that.initList();
								uni.showToast({
									duration: 2000,
									title: '发布消息成功'
								});
							})
						})

						break;
					case 2:
						draftAPI.deleteById(that.showTipData.currentDraftMsg.id, (data) => {
							that.initList();
							uni.showToast({
								duration: 2000,
								title: '删除草稿成功'
							});
						})
						break;
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
	.uni-list-cell-hover {
		width: 750rpx;
	}

	.uni-swipe-action {
		box-shadow: 1px 1px 1px 1px $uni-bg-color-grey;
		border-radius: $uni-border-radius-base;
		margin-top: $uni-spacing-col-base;
		margin-bottom: $uni-spacing-col-base;
	}

	.index-list-item-cell {
		display: flex;
		flex-direction: column;
		width: 100%;
		margin-top: $uni-spacing-col-base;

		.index-list-item-cell-title {
			font-size: $uni-font-size-lg;
			color: $uni-color-title;
			padding-left: $uni-spacing-row-base;
		}

		.index-list-item-cell-content {
			display: flex;
			justify-content: space-between;
			margin-top: $uni-spacing-col-base;
			padding-left: $uni-spacing-row-base;

			.index-list-item-cell-content-type {
				color: $uni-text-color-inverse;
				padding-left: $uni-spacing-row-sm;
				padding-right: $uni-spacing-row-sm;
				padding-top: $uni-spacing-col-sm;
				padding-bottom: $uni-spacing-col-sm;
				border-radius: $uni-border-radius-lg;
			}

			.index-list-item-cell-content-date {
				color: $uni-text-color-grey;
				padding-right: $uni-spacing-row-base;
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
