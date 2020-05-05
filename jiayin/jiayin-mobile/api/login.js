import request from '@/utils/request'

export function login(data, onSuccess) {
	request({
		url: 'auth',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function getUser(onSuccess) {
	request({
		url: 'user-details',
		onSuccess: onSuccess
	})
}

//微信登录等OAuth2登录成功后做用户绑定
export function bindDiamondUaaUser(userInfo, onSuccess) {
	request({
		url: 'bindDiamondUaaUser',
		method: 'POST',
		data: userInfo,
		onSuccess: onSuccess
	})
}
//微信登录等OAuth2登录成功后做用户绑定
export function weChatMiniAppLogin(appId, code, onSuccess) {
	request({
		url: 'wx/user/' + appId + "/login?code=" + code,
		onSuccess: onSuccess
	})
}
