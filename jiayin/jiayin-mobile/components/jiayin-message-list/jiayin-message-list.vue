<template>
	<view>
		<!-- 搜索板块 -->
		<view class="index-header">
			<!-- filters：过滤选项设置， sortChanged：排序更改的事件监听方法-->
			<jiayinFilter :filters="messageFilters" @sortChanged="messageFilterChanged"></jiayinFilter>
		</view>
		<view class="uni-list">
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in pageDTO.list" :key="key" @click="goDetail(value)">
				<view class="message-list-item">
					<!-- <image class="message-list-item-logo" :src="value.cover"></image> -->
					<view class="message-list-item-body">
						<view class="message-list-item-title">{{ value.title }}</view>
						<view class="message-list-item-content">{{ value.content }}</view>
						<view class="message-list-item-contacts-views">
							<view class="message-list-item-type">{{ value.msgTypeName}}</view>
							<view class="message-list-item-views">浏览:{{ value.views }}次</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<uni-load-more :status="loadMoreData.status" :icon-size="16" :content-text="loadMoreData.contentText" />
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
			msgTypeParentId: {
				type: String,
				default: function() {
					return ""
				}
			},
			msgTypeParentName: {
				type: String,
				default: function() {
					return ""
				}
			}
		},
		data() {
			return {
				pageDTO: {
					"filters": {
						"msgTypeId": ""
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
			this.initList();
			this.initMsgType();
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
			initMsgType() {
				var that = this;
				that.jiayinFilterData.msgTypelistData = [{
					title: that.msgTypeParentName + '信息',
					value: ''
				}];

				msgTypeAPI.selectChildren(this.msgTypeParentId, function(data) {
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
				this.pageDTO.filters.msgTypeParentId = this.msgTypeParentId;

				messageAPI.getPage(this.pageDTO, (data) => {
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
	.index-header {
		box-shadow: 1px 1px 5px $uni-border-color;
		border-radius: $uni-border-radius-lg;
	}

	.message-list-item {
		display: flex;
		flex-direction: column;
	}

	.message-list-item-body {
		margin-top: 10px;
		margin-bottom: 10px;
		display: flex;
		flex-direction: column;
		width: 750rpx;
	}

	.message-list-item-title {
		font-size: 17pt;
		color: #000000;
		padding-left: 15px;
	}

	.message-list-item-status-expareTime {
		display: flex;
		justify-content: space-between;
		margin-top: 8px;
		font-size: 11pt;
		color: #888888;
		padding-left: 15px;
		padding-right: 15px;
	}

	.message-list-item-type {
		background-color: #09BB07;
		color: #FFFFFF;
		padding-left: 5px;
		padding-right: 5px;
		padding-top: 2px;
		padding-bottom: 2px;
		border-radius: 5rpx;
	}

	.message-list-item-status-color {}

	.message-list-item-expareTime {}

	.message-list-item-content {
		margin-top: 8px;
		font-size: 14pt;
		color: #353535;
		padding-left: 15px;
		padding-right: 15px;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.message-list-item-contacts-views {
		display: flex;
		justify-content: space-between;
		margin-top: 8px;
		font-size: 11pt;
		color: #888888;
		padding-left: 15px;
		padding-right: 15px;
	}

	.message-list-item-contacts {}

	.message-list-item-views {}
</style>
