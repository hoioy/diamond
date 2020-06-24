import request from '@/utils/request'

export function getPage(data,onSuccess) {
	request({
		url: 'jiayin/msgPublished/page',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgPublished/'+id,
		onSuccess: onSuccess
	})
}