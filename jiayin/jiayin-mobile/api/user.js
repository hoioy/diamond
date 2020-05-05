import request from '@/utils/request'

export function addUser(data,onSuccess) {
	 request({
		url: 'system/user/save',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function getUser(onSuccess) {
	 request({
		url: 'user-details',
		onSuccess:onSuccess
	})
}
