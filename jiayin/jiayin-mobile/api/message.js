import request from '@/utils/request'

export function getPage(data,onSuccess) {
	request({
		url: 'jiayin/message/page',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/message/selectById?id='+id,
		onSuccess: onSuccess
	})
}

export function addMessage(data,onSuccess) {
	return request({
		url: 'jiayin/message/save',
		method: 'POST',
		data: data
	})
}

export function updateMessage(data,onSuccess) {
	return request({
		url: 'jiayin/message/update',
		method: 'PUT',
		data: data
	})
}