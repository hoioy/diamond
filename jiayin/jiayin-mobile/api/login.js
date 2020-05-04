import request from '@/utils/request'

export function login(data) {
	return request({
		url: 'auth', 
		method: 'POST',
		data: data
	})
}

export function getUser() {
	return request({
		url: 'user-details'
	})
}

//微信登录等OAuth2登录成功后做用户绑定
export function bindDiamondUaaUser(userInfo) {
	return request({
		url: 'bindDiamondUaaUser', 
		method: 'POST',
		data: userInfo
	})
}


