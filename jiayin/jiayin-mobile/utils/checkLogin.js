var checkLogin = {

    alreadyLogin: function () {
    	let userinfojson=uni.getStorageSync('token')
		debugger
		if(userinfojson==""){
			console.log('跳转')
			uni.navigateTo({
				url: '/pages/login/login.vue'
			});
		}

    }

}


export default checkLogin;