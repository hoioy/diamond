const websiteUrl = 'http://localhost:7779/';
// const websiteUrlw = 'http://10.0.60.59:7779/';

const request = function(data) {
	if (uni.getStorageSync('token')) {
		return uni.request({
			url: websiteUrl + data.url,
			method: data.method,
			data: data.data,
			header: {
				'Authorization': 'Bearer ' + uni.getStorageSync('token').access_token
				// 'Authorization': 'Bearer ' + uni.getStorageSync('token').access_token
			}
		})
	} else {
		return uni.request({
			url: websiteUrl + data.url,
			method: data.method,
			data: data.data
		})
	}
}

export default request
