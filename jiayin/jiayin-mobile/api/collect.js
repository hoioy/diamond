import request from '@/utils/request'

export function getPage(data,onSuccess,onComplete) {
	request({
		url: 'jiayin/msgCollect/page',
		method: 'POST',
		data: data,
		onSuccess: onSuccess,
		onComplete: onComplete
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgCollect/'+id,
		onSuccess: onSuccess
	})
}

export function addCollect(data,onSuccess) {
	return request({
		url: 'jiayin/msgCollect',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}
export function delCollect(id,onSuccess) {
	return request({
		url: 'jiayin/msgCollect/'+id,
		method: 'DELETE',
		onSuccess: onSuccess
	})
}
