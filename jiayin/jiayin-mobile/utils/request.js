const websiteUrl = 'http://localhost:7779/';
// const websiteUrl = 'https://www.hoioy.com/';

const onDefaultFail = function(error, onError) {
	uni.removeStorageSync('token');
	uni.showToast({
		duration: 2000,
		title: error,
	});
	if (onError) {
		onError()
	}
}

const isTokenError = function(message) {
	if (message.indexOf('Full authentication') >= 0 ||
		message.indexOf('Invalid JWT') >= 0) {
		return true
	}
	return false
}

const getCurrentPageRoute = function () {
	var pages = getCurrentPages();
	return (pages[pages.length - 1]).route;
}

const request = function(param) {
	var header = {
		'Authorization': 'Bearer BaseJwtToken80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E51F7488523D36CB143AB1602169501D9395E319653712EB984F9275873CF4DFC0F951668D3468DF6C7D9137075F6812D8D007F53170DFCE515A054DE60B26EFFB730FFDCB9EEA9AFE1847C44003D890AD5880CA0625BE2751ABC7B3AC72AF23EC5215B7994E0B17A9B602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB353926662CC41E1D38C36074674A4CC5A0685818B9AA7550C2C2B18338F7FCA0D7C3D2CC9D014FEAB4DA9D43DAEDE0E3A39FAA9'
	}
	
	if (uni.getStorageSync('token')) {
		header = {
			'Authorization': 'Bearer ' + uni.getStorageSync('token')
		}
		// console.info(header.Authorization)
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
				if (res.data.status == 500 || res.statusCode == 500) {
					onDefaultFail(res.data.message, (requestFailResult) => {
						if (isTokenError(res.data.message)) {
							if(getCurrentPageRoute()!='/pages/login/login'){
								uni.navigateTo({
									url: '/pages/login/login'
								});
							}
						}
					})
				}
			} else {
				if (res.data.code !== 1 && res.data.code !== 200) {
					if (res.data.code === 2 || res.data.code === 3 || res.data.code === 500) {
						if (isTokenError(res.data.message)) {
							onDefaultFail(res.data.message, (requestFailResult) => {
								if(getCurrentPageRoute()!='/pages/login/login'){
									uni.navigateTo({
										url: '/pages/login/login'
									});
								}
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
