import request from '@/utils/request'

export function selectParent(onSuccess) {
	request({
		url: 'jiayin/msgType/selectParent',
		onSuccess: onSuccess
	})
}

export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgType/selectById?id='+id,
		onSuccess: onSuccess
	})
}

export function deleteById(id,onSuccess) {
	request({
		url: 'jiayin/msgType/deleteById?id='+id,
		method: 'delete',
		onSuccess: onSuccess
	})
}