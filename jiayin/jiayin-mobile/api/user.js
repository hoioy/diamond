import request from '@/utils/request'

export function addUser(data) {
	return request({
		url: 'system/user/save',
		method: 'POST',
		data: data
	})
}

export function getUser() {
	return request({
		url: 'user-details'
	})
}
