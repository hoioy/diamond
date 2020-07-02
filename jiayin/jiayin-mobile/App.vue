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
	
	@font-face {font-family: "iconfont";
	  src: url('~@/static/iconfont/iconfont.eot?t=1593699055917'); /* IE9 */
	  src: url('~@/static/iconfont/iconfont.eot?t=1593699055917#iefix') format('embedded-opentype'), /* IE6-IE8 */
	  url('data:application/x-font-woff2;charset=utf-8;base64,d09GMgABAAAAAAQ4AAsAAAAACGwAAAPrAAEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHEIGVgCDBgqETINvATYCJAMMCwgABCAFhG0HPhs9B8gOJSHHwFAwUABgBF9jr/vuLgCoCNSXARoflYmKJBYmFkgqMDrR1Fa3rpP5x9N+10HIRBcHFUnXKXtp1h09kieyf9w7/avzsGg+y3Jae59/DDDeGtAYw6ItXICYcMPYTQQN8TSBesPMENYDwkhAWAEmBeKIzaIB4YxRaaCGWk11xcoinoCA2nTNtwGAR/79+ANsQpikKgMzN3b8mcDrQ+q5M/Ew7zDYRs3g6HR2GOvImAEUYrvSuYUYJWcQ1XtK/ubCakVIU0V4PLjQV1dv9IeXiGpA2ShTJvTJRYkwl+DTWSCrXH+Iao/R+QyAfxO8iySPG+oZ6tPOGjR1qVEpbVVq5dOGVGp3ujmZ3DiqUmlGIpdW6BU0nG1SpJAa9p1VJpEKgL9UgUwiOxamK1HJhUrpxUrF5QQKtVi1qEiRQpGUSS3lQyPlUSlFVdXto8XL2wdbVUx6a2WFvHNEtEhzLE31zemsUyPogfLx9NU2tMneGqOePRuUQrH05nLFLdRiIlccG6H/pU1nG+RJuxqUS4zJmwpKGxuVqHqGqpFSA4p68+nHCdm6+yD6jC559B6cSRmdhpie3HsvstKheaPfHkNx6g463dv6N5uLczTl8xnnbptcrrY0TKsqNrmhQk/DZZlLyDLiWyOQ64eTRMfvV5V3ORn/xd4M6ed0roGfh7y4yb3FVfH5fa1ixmvvfFA8Mu793pL9Lu/7rr/0g7fvzW3Xmk/fb+814dleFxN/5XKuJV1Nyjz+NDDM9HVTjd/N/YT7gfmXZsid2zwXqXbW7ZfGC40iN+2b601ehwUIVvwWhcTnTqhZiU+N7fY3BSb/a2stub+dTBXMQlJ/iljTTwfyPQ9bS6gd1/Au+LyfF4Cf63mAaPjDL/O0djJnp9T8N2GxAt71xD7wsby/vIOl5CsA1L4OV/B79UUD8lfqS4ApF1ehURi6s7NXZU/IfNPjBwBvu/vDVV2TC6m7VlcCSY0hyGqNoIUyA1UaVKBarSWoNy1wfYMOGKIodZhyQoLQqhuSZi+QtTqHFso9qNLtHVRrDSLU24yOHRuMBVt0DYNpOMyE6HsgNgNNRpAFFxWVMTArNZGGpSWcQYExDjsCWVtY5TOBcDKMTTGHw2XZ4DgCIRiaBAVI58GJiSiUgqHxMAO3iMPxFBdLS6TqRhYMNAmIWoOB0eBgTBC6PSBsDKhkCK2NEy18PgaMJVUiGqyB1OOlgGE42N1DrFmw6oAGKpM7kW7lGg4uFhs4HAKCwKCSQAIkOywRJlGQlOph8WAMOAtxA+IpXFgq1ZCuSovl1Unvtw7qgQtypMhR1F7GoakMWjJLRDisAQAA') format('woff2'),
	  url('~@/static/iconfont/iconfont.woff?t=1593699055917') format('woff'),
	  url('~@/static/iconfont/iconfont.ttf?t=1593699055917') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+ */
	  url('~@/static/iconfont/iconfont.svg?t=1593699055917#iconfont') format('svg'); /* iOS 4.1- */
	}
	
	.iconfont {
	  font-family: "iconfont" !important;
	  font-size: 16px;
	  font-style: normal;
	  -webkit-font-smoothing: antialiased;
	  -moz-osx-font-smoothing: grayscale;
	}
	
	.icon-shoucang:before {
	  content: "\e50f";
	}
	
	.icon-shoucang1:before {
	  content: "\e639";
	}
	

</style>
