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
		'Authorization': 'Bearer BaseJwtToken80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E5174D18F32F26E85E8D38DB4BD26A9BF420D8E91ED3944CD26F9275873CF4DFC0F951668D3468DF6C7F88E4648CE43DFE6E985C6C86106E50DD6C396C6FCC709FC30FFDCB9EEA9AFE1847C44003D890AD5B1B3863970DCA34EDA8F11EB3EC9E9223729B45A0B7555AD602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB3539266906C0820A66272C3B40F0763DE38306BF2CEB476A9CF308C2C16F73BDAD7ED24AF319B2CAC0F466C4EBC8A4E5E021E7F'
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
