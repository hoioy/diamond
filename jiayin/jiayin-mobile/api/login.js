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


