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
export function bindOAuth2User(userInfo, onSuccess) {
	request({
		url: 'bindOAuth2User',
		method: 'POST',
		data: userInfo,
		onSuccess: onSuccess
	})
}

export function weChatMiniAppLogin(appId, code, onSuccess) {
	request({
		url: 'wx/user/' + appId + "/login?code=" + code,
		onSuccess: onSuccess
	})
}
