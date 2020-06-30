<template>
	<view class="container">
		<text>欢迎联系我们！</text>
		<text>发送邮件给我们，我们会及时回复您！</text>
		<text class="email">appjiayin@163.com</text>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			
		}
	}
</script>

<style lang="scss">
	.container {
		min-height: 100%;
		display: flex;
		flex-flow: column;
		width: 100%;
		align-items: center;
	
		text {
			margin-top: $uni-spacing-row-base;
			color: $uni-text-color;
		}
		
		.email{
			font-size: $uni-font-size-lg;
			font-weight: bold;
		}
	}
</style>
