import request from '@/utils/request'

export function getPage(data,onSuccess) {
	request({
		url: 'jiayin/msgCollect/page',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgCollect/'+id,
		onSuccess: onSuccess
	})
}

export function addMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgCollect',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function updateMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgCollect',
		method: 'PUT',
		data: data,
		onSuccess: onSuccess
	})
}