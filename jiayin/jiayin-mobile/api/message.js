import request from '@/utils/request'

export function getPage(data,onSuccess) {
	request({
		url: 'jiayin/message/page',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/message/'+id,
		onSuccess: onSuccess
	})
}

// export function saveMessage(data,onSuccess) {
// 	return request({
// 		url: 'jiayin/message',
// 		method: 'POST',
// 		data: data,
// 		onSuccess: onSuccess
// 	})
// }

// export function updateMessage(data,onSuccess) {
// 	return request({
// 		url: 'jiayin/message',
// 		method: 'PUT',
// 		data: data,
// 		onSuccess: onSuccess
// 	})
// }