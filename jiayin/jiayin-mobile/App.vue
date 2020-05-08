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

<style>
	/* 头条小程序需要把 iconfont 样式放到组件外 */
	@import "components/m-icon/m-icon.css";

	/*每个页面公共css */
	page {
		min-height: 100%;
		display: flex;
		font-size: 16px;
	}

	/* #ifdef MP-BAIDU */
	page {
		width: 100%;
		height: 100%;
		display: block;
	}

	swan-template {
		width: 100%;
		min-height: 100%;
		display: flex;
	}

	/* 原生组件模式下需要注意组件外部样式 */
	custom-component {
		width: 100%;
		min-height: 100%;
		display: flex;
	}

	/* #endif */

	/* #ifdef MP-ALIPAY */
	page {
		min-height: 100vh;
	}

	/* #endif */

	/* 原生组件模式下需要注意组件外部样式 */
	m-input {
		width: 100%;
		/* min-height: 100%; */
		display: flex;
		flex: 1;
	}

	.content {
		display: flex;
		flex: 1;
		flex-direction: column;
		background-color: #efeff4;
		padding: 10px;
	}

	.input-group {
		background-color: #ffffff;
		margin-top: 20px;
		position: relative;
	}

	.input-group::before {
		position: absolute;
		right: 0;
		top: 0;
		left: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.input-group::after {
		position: absolute;
		right: 0;
		bottom: 0;
		left: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.input-row {
		display: flex;
		flex-direction: row;
		position: relative;
		font-size: 18px;
		line-height: 40px;
	}

	.input-row .title {
		width: 72px;
		padding-left: 15px;
	}

	.input-row.border::after {
		position: absolute;
		right: 0;
		bottom: 0;
		left: 8px;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #c8c7cc;
	}

	.btn-row {
		margin-top: 25px;
		padding: 10px;
	}

	button.primary {
		background-color: #0faeff;
	}
	@font-face {font-family: "iconfont";
	  src: url('~@/static/font/iconfont.eot?t=1588864743099'); /* IE9 */
	  src: url('~@/static/font/iconfont.eot?t=1588864743099#iefix') format('embedded-opentype'), /* IE6-IE8 */
	  url('data:application/x-font-woff2;charset=utf-8;base64,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') format('woff2'),
	  url('~@/static/font/iconfont.woff?t=1588864743099') format('woff'),
	  url('~@/static/font/iconfont.ttf?t=1588864743099') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+ */
	  url('~@/static/font/iconfont.svg?t=1588864743099#iconfont') format('svg'); /* iOS 4.1- */
	}
	
	.iconfont {
	  font-family: "iconfont" !important;
	  font-size: 16px;
	  font-style: normal;
	  -webkit-font-smoothing: antialiased;
	  -moz-osx-font-smoothing: grayscale;
	}
	
	.icon-xinxixiugai:before {
	  content: "\e61a";
	}
	
	.icon-draft:before {
	  content: "\e501";
	}
	
	.icon-lianxiwomen1-copy:before {
	  content: "\e614";
	}
	
	.icon-shoucang:before {
	  content: "\e618";
	}
	
	.icon-lianxiwomen:before {
	  content: "\e51f";
	}
	
	.icon-fabu:before {
	  content: "\e678";
	}
	
	.icon-ziyuan:before {
	  content: "\e60d";
	}
	
	.icon-bx-wode:before {
	  content: "\e502";
	}
	
	.icon-bussiness-man:before {
	  content: "\e6ef";
	}
	
	.icon-shouye:before {
	  content: "\e6ae";
	}
	
	.icon-zuji:before {
	  content: "\e648";
	}
	
	.icon-shouye1:before {
	  content: "\e656";
	}
	
	.icon-huaban:before {
	  content: "\e505";
	}
	

</style>
