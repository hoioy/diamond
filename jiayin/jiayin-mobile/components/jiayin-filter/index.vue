<template name="cate-filter">
	<view>
		<view class="jiayin-filter" :style="{'position': fixed?'fixed':'relative','top':top}"  id="jiayin-filter-header">
			<view class="items" :class="activeIndex==index?'text-red':'text-grey'" v-for="(item, index) in filters" :key="index"
			 :data-index="index" @tap="changeSort">
				<text v-if="item.filterType!=2">{{ item.title }}</text>
				<picker v-else @change="bindPickerChange" :value="pickerIndex" :range="item.options||[]" range-key="title">
					{{item.options[pickerIndex]?item.options[pickerIndex].title:''}}
				</picker>
				<text v-if="item.filterType==2"></text>
				<image src="/static/img/jiayin-filter/normal.png" mode="widthFix" v-else-if="activeIndex!=index"></image>
				<image src="/static/img/jiayin-filter/up.png" mode="widthFix" v-else-if="activeIndex==index && activeAscState"></image>
				<image src="/static/img/jiayin-filter/down.png" mode="widthFix" v-else-if="activeIndex==index"></image>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
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
			filters: {
				type: Array,
				default: function() {
					return []
				}
			},
			initIndex: {
				type: Number,
				default: function() {
					return 0
				}
			}
		},
		data() {
			return {
				// 当前激活索引
				activeIndex: 0,
				// 默认升序
				activeAscState: true,
				pickerIndex: 0
			}
		},
		created: function(event) {
			this.activeIndex = this.initIndex
		},
		methods: {
			bindPickerChange: function(e) {
				this.pickerIndex =  e.detail.value
				this.$emit("sortChanged", {
					"optionIndex": this.pickerIndex
				})
			},
			changeSort: function(e) {
				const index = parseInt(e.currentTarget.dataset.index);
				const curActiveItem = this.filters[index]
				const filterType = curActiveItem.filterType || 0
				// 点击索引等于自身
				if (this.activeIndex == index) {
					//禁用升降序，则直接返回无需处理
					if (curActiveItem.filterType == 0){
						return
					} 
				}

				if (curActiveItem.filterType == 1) {
					// 升降序
					if (this.activeIndex == index) {
						//排序顺序颠倒
						this.activeAscState = !this.activeAscState
					} else {
						this.activeAscState = curActiveItem.initAscState || false
					}
				}
				
				this.activeIndex = index
				const sortField = curActiveItem.value !== undefined ? curActiveItem.value : index
				var data = {
					"sort": sortField,
					"order": this.activeAscState ? 1 : -1
				}
				this.$emit("sortChanged", data)
			}
		}
	};
</script>
<style lang="scss">
	.jiayin-filter {
		width: 100%;
		background: $uni-bg-color;
		border-bottom: 1px solid $uni-border-color;
		display: flex;
		flex-wrap: nowrap;
		picker{
			font-size: $uni-font-size-sm;
		}
	}

	.jiayin-filter .items {
		display: flex;
		flex-wrap: nowrap;
		width: 25%;
		justify-content: center;
		height: $jiayin-nav-bar-height;
		align-items: center;
	}

	.jiayin-filter .items image {
		width: 20px;
	}

	.jiayin-filter .items text {
		font-size: $uni-font-size-sm;
	}
</style>
