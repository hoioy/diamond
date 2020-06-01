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
		'Authorization': 'Bearer 80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E515B01BCEE78CA87A4D92BF6349BD895223526864FCE21D166F9275873CF4DFC0F951668D3468DF6C70E330BB7C4C8F2A9F71D012285412526DE5777A42FB3B5EA30FFDCB9EEA9AFE1847C44003D890AD53FF374A512BB4CFFB80AB55FD1DBBE84232CD26D3C0CCEF1602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB353926618AEACF28F0CD3A705BDC2065D9DA2E724DD39637E4D95D4A9A2E43F5C7080EE49A5593DC5BFC1182A26DE8D7C02A412'
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
