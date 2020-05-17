import store from 'store'

const websiteUrl = 'https://localhost:7779/';
// const websiteUrl = 'https://www.hoioy.com/';
const onDefaultFail = function(error, onError) {
	store.dispatch('Logout').then((logoutResult) => {
		uni.showToast({
			duration: 2000,
			title: error,
		});
		if (onError) {
			onError(error)
		}
	})
}
const request = function(param) {
	var header = {}
	if (uni.getStorageSync('token')) {
		header = {
			// 'Authorization': 'Bearer 80023F7F3F92DCF66807EB95AA8E0368BC1965542B72258DEF1F8DA556C3323FF4B097AE45E7E24C48071ED9C655D5DA8D829E2281D836AB516C89261A669900169AE1C75EE5F3BA67B68A4A97194E5178D27EEB8D108759C40DF6230853D5101BAD3F7C19AE839CF9275873CF4DFC0F951668D3468DF6C76562DCDE276708A4C558D9F7B2186DEDBCFF2A45EC68B97930FFDCB9EEA9AFE1847C44003D890AD575E1E4527CCD5E777E2F824C7508084FA0FB6FFB3AD67EF7602F520187EBF26DCEDBB84D204A3C5CA9A9815CA61C3357A2AA85CD49066D51F93A67BFEAEB900DD27ED9C4CF272563AFC2AA36A9516DB76A190530E17A7B1D2EA0EA59B0456A9331124B9BB353926676B9663F1A6FBEFC8FD7ECAFEA3A3274E42762165CB429F038BA38FD07BBF7B748F66BC824758F42BBCB4E8836173EE0'
			'Authorization': 'Bearer ' + uni.getStorageSync('token').access_token
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
							url: '../pages/user/user.vue'
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
									url: '/pages/user/user.vue'
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
