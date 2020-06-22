<template>
	<view class="list-container">
		<jiayinMessageList ref="jiayinMessageList" :msgTypeId="selectedMsgType.id" :msgTypeName="selectedMsgType.typeName"
		 :fixed="true" top="70rpx" listMarginTop="70rpx"></jiayinMessageList>
	</view>
</template>

<script>
	import jiayinMessageList from '@/components/jiayin-message-list/jiayin-message-list.vue';

	export default {
		components: {
			jiayinMessageList
		},
		data() {
			return {
				selectedMsgType: {
					id: '',
					typeName: ''
				}
			}
		},
		onPullDownRefresh() {
			this.$refs.jiayinMessageList.pullDownRefresh();
		},
		onReachBottom() {
			this.$refs.jiayinMessageList.reachBottom();
		},
		onLoad(option) {
			this.selectedMsgType.id = option.msgTypeId
			this.selectedMsgType.typeName = option.msgTypeName
		},
		onReady() {
			uni.setNavigationBarTitle({
				title: this.selectedMsgType.typeName + '列表'
			});
		}
	}
</script>

<style>

</style>
