<template>
	<view class="list-container">
		<jiayinMessageList ref="jiayinMessageSubList"></jiayinMessageList>
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
			this.$refs.jiayinMessageSubList.pullDownRefresh();
		},
		onReachBottom() {
			this.$refs.jiayinMessageSubList.reachBottom();
		},
		onLoad(option) {
			this.selectedMsgType.id = option.msgTypeId
			this.selectedMsgType.typeName = option.msgTypeName
		},
		onReady() {
			uni.setNavigationBarTitle({
				title: this.selectedMsgType.typeName + '信息列表'
			});
			
			if (this.$refs.jiayinMessageSubList) {
				this.$refs.jiayinMessageSubList.init(this.selectedMsgType.id,this.selectedMsgType.typeName);
			}
		}
	}
</script>

<style>
	.list-container {
		width: 100%
	}
</style>
