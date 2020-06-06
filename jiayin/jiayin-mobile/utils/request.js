const websiteUrl = 'http://localhost:7779/';
// const websiteUrl = 'https://www.hoioy.com/';
const onDefaultFail = function(error, onError) {
	uni.removeStorageSync('token');
	uni.showToast({
		duration: 2000,
		title: error,
	});
}
const request = function(param) {
	var header = {
		'Authorization': 'Bearer BaseJwtToken80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E51585000D3466F254516E1AC47F8D8EA65DBC12DA90F777DBCF9275873CF4DFC0F951668D3468DF6C7F88E4648CE43DFE66B816E30DABD0B289C7F1C72CA3038E430FFDCB9EEA9AFE1847C44003D890AD53957CC50BA134979F02D8D6407A3FE7C38C31624F175CFE6602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB3539266446DC175AD291BB668F959B6E1B224FCBA21EAEAA4BCFC080F2A3C0BF4AD0C2C6609D16BA367EA056713031DD1BA3B6A'
	}
	if (uni.getStorageSync('token')) {
		header = {
			'Authorization': 'Bearer ' + uni.getStorageSync('token')
		}
	}

	uni.request({
		url: websiteUrl + param.url,
		method: param.method,
		data: param.data,
		header: header,
		fail: (error) => {
			onDefaultFail(error, param.onError)
		},
		success: (res) => {
			if (res.statusCode != 200) {
				if (res.data.status == 500) {
					onDefaultFail(res.errMsg, (logoutResult) => {
						uni.reLaunch({
							url: '../pages/user/user-login.vue'
						});
					})
				}
			} else {
				console.log(res.data);
				if (res.data.code !== 1 && res.data.code !== 200) {
					if (res.data.code === 2 || res.data.code === 3 || res.data.code === 500) {
						if (res.data.message.indexOf('Full authentication') >= 0) {
							onDefaultFail(res.data.message, (logoutResult) => {
								uni.reLaunch({
									url: '/pages/user/user-login.vue'
								});
							})
						} else {
							if (param.onError) {
								param.onError(error)
							}
						}
					}
				} else {
					if (param.onSuccess) {
						param.onSuccess(res.data)
					}
				}
			}
		}
	})
}

export default request
