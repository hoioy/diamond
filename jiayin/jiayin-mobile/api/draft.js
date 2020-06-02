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
		url: 'jiayin/msgDraft/'+id,
		onSuccess: onSuccess
	})
}

export function addMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgDraft',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function deleteById(id,onSuccess) {
	request({
		url: 'jiayin/msgDraft/'+id,
		method: 'delete',
		onSuccess: onSuccess
	})
}

export function updateMessage(data,onSuccess) {
	return request({
		url: 'jiayin/msgDraft',
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