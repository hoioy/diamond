<template>
	<view>
		<!-- 搜索板块 -->
		<view class="index-header">
			<!-- filters：过滤选项设置， sortChanged：排序更改的事件监听方法-->
			<jiayinFilter :filters="messageFilters" @sortChanged="messageFilterChanged" :fixed="fixed" :top="top"></jiayinFilter>
		</view>
		<view class="uni-list" :style="{ marginTop: listMarginTop }">
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in pageDTO.list" :key="key" @click="goDetail(value)">
				<view class="message-list-item">
					<!-- <image class="message-list-item-logo" :src="value.cover"></image> -->
					<view class="message-list-item-body">
						<view class="message-list-item-title">{{ value.title }}</view>
						<view class="message-list-item-content">{{ value.content }}</view>
						<view class="message-list-item-contacts-views">
							<view class="message-list-item-type" :style="{backgroundColor: value.msgTypeColor}">{{ value.msgTypeName}}</view>
							<view class="message-list-item-views">浏览:{{ value.views }}次</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<uni-load-more class="load-more" :status="loadMoreData.status" :icon-size="16" :content-text="loadMoreData.contentText" />
	</view>
</template>

<script>
	import jiayinFilter from '@/components/jiayin-filter/index.vue';
	import uniLoadMore from '@/components/uni-load-more/uni-load-more.vue';
	import * as messageAPI from '@/api/message.js';
	import * as msgTypeAPI from '@/api/msgType.js';
	export default {
		components: {
			uniLoadMore,
			jiayinFilter
		},
		props: {
			// 是否固定至顶部
			fixed: {
				type: Boolean,
				default: function() {
					return false
				}
			},
			// 固定至顶部时离顶部的距离
			top: {
				type: String,
				default: function() {
					return "0rpx"
				}
			},
			// 列表相对固定至顶部时离顶部的距离
			listMarginTop: {
				type: String,
				default: function() {
					return "0rpx"
				}
			}
		},
		data() {
			return {
				pageDTO: {
					"filters": {
						"msgTypeId": "",
						"msgTypeName": "",
					},
					"list": [],
					"page": 1,
					"pageSize": 20,
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
				reload: false, //是否刷新模式，false：瀑布流
				jiayinFilterData: {
					msgTypelistData: []
				}
			};
		},
		computed: {
			messageFilters: function() {
				// 参考的下拉选项如下，可从服务器端加载：
				//options:[{name:'不限',value:""},{name:'酒水',value:"js",children:[{name:'啤酒',value:"pj"}]}]},
				// const cateOptions=this.msgTypelistData.map(function (item){
				// 	return {name:item.Name,value:item.Fid}
				// });
				// filterType为0，普通方式，无排序，1：排序模式，2：下拉筛选模式，当前支持一级，多级可自行扩展
				return [{
						title: '选择类型',
						value: 0,
						filterType: 2,
						options: this.jiayinFilterData.msgTypelistData
					},
					// {title:'推荐',value:0,filterType:0,disableAscDesc:true},
					{
						title: '标题',
						value: 'title',
						filterType: 1
					},
					// {title:'人气', value:3, filterType:1},
					{
						title: '最新',
						value: 'createdDate',
						filterType: 1
					},
					{
						title: '最热门',
						value: 'views',
						filterType: 1,
						initAscState: true
					}
				]
			}
		},
		created: function(event) {
			
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
			init(msgTId,msgTName){
				this.pageDTO.filters.msgTypeId = msgTId
				this.pageDTO.filters.msgTypeName = msgTName
				this.initMsgType();
				this.initList();
			},
			initMsgType() {
				var that = this;
				that.jiayinFilterData.msgTypelistData = [{
					title: '全部' + that.pageDTO.filters.msgTypeName + '信息',
					value: that.pageDTO.filters.msgTypeId
				}];

				msgTypeAPI.selectChildren(that.pageDTO.filters.msgTypeId, function(data) {
					if (data.data) {
						data.data.forEach(item => {
							that.jiayinFilterData.msgTypelistData.push({
								title: item.typeName,
								value: item.id
							})
						})
					}
				})
			},
			initList() {
				this.reload = true;
				this.pageDTO.page = 1;
				this.getList();
			},
			getList() {
				this.loadMoreData.status = 'loading';

				messageAPI.getPage(this.pageDTO, (data) => {
					if (this.reload) {
						this.pageDTO.list = data.data.list;
					} else {
						this.pageDTO.list = this.pageDTO.list.concat(data.data.list);
					}
					
					if (data.data.list.length > 0) {
						this.loadMoreData.status = 'more';
					} else {
						this.loadMoreData.status = 'noMore';
					}
					
					this.reload = false;
					uni.stopPullDownRefresh();
				})
			},
			goDetail: function(e) {
				uni.navigateTo({
					url: '/pages/message/message-detail/message-detail?id=' + e.id,
				});
			},
			// 排序，筛选更改
			messageFilterChanged(filter) {

				if (filter.optionIndex >= 0) {
					this.pageDTO.filters.msgTypeId = this.jiayinFilterData.msgTypelistData[filter.optionIndex].value
				}

				if (filter.sort && filter.sort != 0) {
					this.pageDTO.sorts = [{
						"direction": filter.order > 0 ? "asc" : "desc",
						"fieldName": filter.sort
					}]
				}
				this.initList()
			}
		}
	}
</script>

<style lang="scss">
	.uni-list-cell {
		box-shadow: 1px 1px 1px 1px $uni-bg-color-grey;
		border-radius: $uni-border-radius-base;

		.message-list-item {
			display: flex;
			flex-direction: column;

			.message-list-item-body {
				margin-top: $uni-spacing-col-base;
				margin-bottom: $uni-spacing-col-base;
				display: flex;
				flex-direction: column;
				width: 750rpx;

				.message-list-item-title {
					font-size: $uni-font-size-lg;
					color: $uni-color-title;
					padding-left: $uni-spacing-row-base;
				}

				.message-list-item-content {
					margin-top: $uni-spacing-col-base;
					font-size: $uni-font-size-base;
					color: $uni-text-color;
					padding-left: $uni-spacing-row-base;
					padding-right: $uni-spacing-row-base;
					white-space: nowrap;
					overflow: hidden;
					text-overflow: ellipsis;
				}

				.message-list-item-contacts-views {
					display: flex;
					justify-content: space-between;
					align-items: flex-end;
					margin-top: $uni-spacing-col-base;
					font-size: $uni-font-size-sm;
					color: $uni-text-color-grey;
					padding-left: $uni-spacing-row-base;
					padding-right: $uni-spacing-row-base;

					.message-list-item-type {
						color: $uni-text-color-inverse;
						padding-left: $uni-spacing-row-sm;
						padding-right: $uni-spacing-row-sm;
						padding-top: $uni-spacing-col-sm;
						padding-bottom: $uni-spacing-col-sm;
						border-radius: $uni-border-radius-lg;
					}

					.message-list-item-views {
						align-items: flex-end;
					}
				}
			}
		}
	}

	.load-more {
		width: 750rpx;
	}
</style>
