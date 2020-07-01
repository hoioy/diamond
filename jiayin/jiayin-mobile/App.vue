<script>
	export default {
		onLaunch: function() {
			console.log('App Launch');
		},
		onShow: function() {
			console.log('App Show');
		},
		onHide: function() {
			console.log('App Hide');
		}
	}
</script>

<style lang="scss">
	page {
		min-height: 100%;
		display: flex;
		font-size: $uni-font-size-base;
	}
</style>
