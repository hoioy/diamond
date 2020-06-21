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
		'Authorization': 'Bearer BaseJwtToken80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E51791D467579FDCB3E1044A52C8BF270DA6451CDFE2B24A9DCF9275873CF4DFC0F951668D3468DF6C7EFFB833CBDD04DB87D88E4A037CB541EE60FBC4519569E0630FFDCB9EEA9AFE1847C44003D890AD54D45D0BCF9E344C7B194F3FE43D4F85214EBC4D89E9B03BD602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB3539266623EF2B0C76599105D36F371F246ED06847296ADC231F0327FD95C8D1F67EC4D41D7D040C64533694D1E8ED61A06186F'
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
				if (res.data.status == 500) {
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
