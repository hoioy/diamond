const websiteUrl = 'http://localhost:7779/';
// const websiteUrl = 'https://www.hoioy.com/';

const isTokenError = function(message) {
	if (message.indexOf('Full authentication') >= 0 ||
		message.indexOf('Invalid JWT') >= 0) {
		return true
	}
	return false
}

//获得当前页面路由信息
const getCurrentPageRoute = function() {
	var pages = getCurrentPages();
	return (pages[pages.length - 1]).route;
}

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

const onDefaultComplete = function(onComplete) {
	uni.hideLoading()
	if (onComplete) {
		onComplete()
	}
}

const handleRes = function(res, param) {
	if (res.statusCode != 200) {
		if (res.data.status == 500 || res.statusCode == 500) {
			onDefaultFail(res.data.message, (requestFailResult) => {
				if (isTokenError(res.data.message)) {
					if (getCurrentPageRoute() != '/pages/login/login') {
						uni.navigateTo({
							url: '/pages/login/login'
						});
					}
				}
			})
		}
	} else {
		if (typeof res.data == 'string' && res.data.constructor == String) {
			res.data = JSON.parse(res.data)
		}

		if (res.data.code !== 1 && res.data.code !== 200) {
			if (res.data.code === 2 || res.data.code === 3 || res.data.code === 500) {
				if (isTokenError(res.data.message)) {
					onDefaultFail(res.data.message, (requestFailResult) => {
						if (getCurrentPageRoute() != '/pages/login/login') {
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

const request = function(param) {
	var header = {
		'Authorization': 'Bearer BaseJwtToken80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E515B561D3FEE18DC1B81E4E5F5F0CAFB1E50C6AF57174A9731F9275873CF4DFC0F951668D3468DF6C7F5744C019F684898D45FE48A97B619CAE0243EA02F78FB2230FFDCB9EEA9AFE1847C44003D890AD557239B7D6E599359329FA8CD60908CF73729B45A0B7555AD602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB353926606826F30F6438AA10D54C9180FF64A6185EF552A466347268C2BE181922405722F5F63340CD744C88E288914E4704C31'
	}

	if (uni.getStorageSync('token')) {
		header = {
			'Authorization': 'Bearer ' + uni.getStorageSync('token')
		}
	}

	uni.showLoading({})
	
	if (param.isUpload) {
		//如果是上传文件
		uni.uploadFile({
			url: websiteUrl + param.url,
			files: param.files,
			formData: param.data,
			header: header,
			fail: (error) => {
				onDefaultFail(error, param.onError)
			},
			success: (res) => {
				handleRes(res, param)
			},
			complete() {
				onDefaultComplete(param.onComplete)
			}
		});
	} else {
		//如果是普通请求
		uni.request({
			url: websiteUrl + param.url,
			method: param.method,
			data: param.data,
			header: header,
			fail: (error) => {
				onDefaultFail(error, param.onError)
			},
			success: (res) => {
				handleRes(res, param)
			},
			complete: () => {
				onDefaultComplete(param.onComplete)
			}
		})
	}
}

export default request
