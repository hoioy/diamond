import request from '@/utils/request'

export function getPage(data,onSuccess) {
	request({
		url: 'jiayin/msgDraft/page',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgDraft/selectById?id='+id,
		onSuccess: onSuccess
	})
}

export function addMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgDraft/save',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function deleteById(id,onSuccess) {
	request({
		url: 'jiayin/msgDraft/deleteById?id='+id,
		method: 'delete',
		onSuccess: onSuccess
	})
}

export function updateMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgDraft/update',
		method: 'PUT',
		data: data,
		onSuccess: onSuccess
	})
}

export function publishMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgDraft/publish',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}