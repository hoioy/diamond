// const websiteUrl = 'http://localhost:7779/';
const websiteUrl = 'https://www.hoioy.com/';

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
		'Authorization': 'Bearer '
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
