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
		url: 'jiayin/msgCollect/selectById?id='+id,
		onSuccess: onSuccess
	})
}

export function addMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgCollect/save',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function updateMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgCollect/update',
		method: 'PUT',
		data: data,
		onSuccess: onSuccess
	})
}