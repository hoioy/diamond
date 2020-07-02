import request from '@/utils/request'

export function publish(data,onSuccess,onError) {
	return request({
		url: 'jiayin/publish/publish',
		method: 'POST',
		data: data,
		onSuccess: onSuccess,
		onError:onError
	})
}

export function rePublish(data,onSuccess,onError) {
	return request({
		url: 'jiayin/publish/re-publish',
		method: 'POST',
		data: data,
		onSuccess: onSuccess,
		onError:onError
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