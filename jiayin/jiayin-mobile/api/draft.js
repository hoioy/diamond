import request from '@/utils/request'

export function getPage(data,onSuccess,onComplete) {
	request({
		url: 'jiayin/msgDraft/page',
		method: 'POST',
		data: data,
		onSuccess: onSuccess,
		onComplete: onComplete
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgDraft/'+id,
		onSuccess: onSuccess
	})
}

export function deleteDraftById(id,onSuccess) {
	request({
		url: 'jiayin/msgDraft/'+id,
		method: 'delete',
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