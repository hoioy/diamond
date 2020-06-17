import request from '@/utils/request'

export function publish(data,onSuccess) {
	return request({
		url: 'jiayin/publish/publish',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}


export function saveDraft(data,onSuccess) {
	return request({
		url: 'jiayin/publish/savedraft',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}