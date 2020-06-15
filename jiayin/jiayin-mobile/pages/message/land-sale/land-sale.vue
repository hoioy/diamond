<template>
	<view>
		<!-- 搜索板块 -->
		<view class="index-header">
			<!-- filters：过滤选项设置， sortChanged：排序更改的事件监听方法，showShape：是否显示右侧模板选择按钮，shapeValue：初始化的模板值，2：双列，1：单列，具体可自行控制，shapeChanged:右侧的模板选择按钮事件监听方法-->
			<jiayinFilter :filters="messageFilters" @sortChanged="messageFilterChanged" :showShape="true" :shapeValue="2" :fixed="true"
			 top="60"></jiayinFilter>
		</view>
		<view class="uni-list">
			<view class="uni-list-cell" hover-class="uni-list-cell-hover" v-for="(value, key) in listData" :key="key" @click="goDetail(value)">
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
		<uni-load-more :status="status" :icon-size="16" :content-text="contentText" />
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
		data() {
			return {
				listData: [],
				last_page: 1,
				sorts: [{
					"direction": "asc",
					"fieldName": "createdDate"
				}],
				filters: {
					"msgTypeId":"",
					"msgTypeParentId":""
					
				},
				reload: false, //是否刷新模式，false：瀑布流
				status: 'more',
				contentText: {
					contentdown: '上拉加载更多',
					contentrefresh: '加载中',
					contentnomore: '没有更多'
				},
				jiayinFilterData: {
					msgTypelistData: []
				}
			};
		},
		onLoad: function(option){
			console.log(option)
			this.filters.msgTypeParentId=option.msgTypeParentId
			this.initList(option.msgTypeParentId);
			this.initMsgType(option.msgTypeParentId);
		},
		onPullDownRefresh() {
			this.initList();
		},
		onReachBottom() {
			this.status = 'more';
			this.last_page += 1;
			this.getList();
		},
		methods: {
			initMsgType(option) {
				var that = this;
				that.jiayinFilterData.msgTypelistData = [{
					name: '全部',
					value: 0
				}];
				console.log(option)
				
				
				msgTypeAPI.selectChildren(option, function(data) {
					if (data.data) {
						data.data.forEach(item => {
							that.jiayinFilterData.msgTypelistData.push({
								name: item.typeName,
								value: item.id
							})
						})
					}
				})
			},
			initList(data) {
				this.reload = true;
				this.last_page = 1;
				this.getList(data);
			
			},
			getList(data) {
				this.status = 'loading';
				messageAPI.getPage({
					"filters": this.filters,
					"page": this.last_page,
					"pageSize": 10,
					"sorts": this.sorts,
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
			goDetail: function(e) {
				uni.navigateTo({
					url: '../message-detail/message-detail?id=' + e.id,
				});
			},
			// 排序，筛选更改
			messageFilterChanged(filter) {
				console.log("filter:", filter)
				if (filter.option != null) {
					this.filters.msgTypeId=filter.option
				}else{
					this.filters.msgTypeId=""
				}
				if (filter.sort != 0) {
					this.sorts = [{
						"direction": filter.order > 0 ? "asc" : "desc",
						"fieldName": filter.sort
					}]
				}
				this.initList()
			}
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
						title: '消息类别',
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
		}
	}
</script>

<style>
	.uni-list {
		margin-top: 90rpx;
	}

	.message-list-item {
		display: flex;
		flex-direction: column;
		border-bottom: 1px solid #B2B2B2;
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
