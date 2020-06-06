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

export function addCollect(data,onSuccess) {
	return request({
		url: 'jiayin/msgCollect',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}
export function delCollect(data,onSuccess) {
	return request({
		url: 'jiayin/msgCollect/'+data,
		method: 'DELETE',
		onSuccess: onSuccess
	})
}
