import request from '@/utils/request'

export function getPage(data,onSuccess,onComplete) {
	request({
		url: 'jiayin/msgPublished/page',
		method: 'POST',
		data: data,
		onSuccess: onSuccess,
		onComplete: onComplete
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgPublished/'+id,
		onSuccess: onSuccess
	})
}
export function delById(id,onSuccess) {
	return request({
		url: 'jiayin/msgPublished/'+id,
		method: 'DELETE',
		onSuccess: onSuccess
	})
}