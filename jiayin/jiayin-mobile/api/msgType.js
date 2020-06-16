import request from '@/utils/request'

export function selectChildren(msgTypeParentId,onSuccess) {
	request({
		url: 'jiayin/msgType/selectChildByParentId/'+msgTypeParentId,
		onSuccess: onSuccess
	})
}
export function selectParent(onSuccess) {
	request({
		url: 'jiayin/msgType/selectParent',
		onSuccess: onSuccess
	})
}
export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgType/'+id,
		onSuccess: onSuccess
	})
}

export function deleteById(id,onSuccess) {
	request({
		url: 'jiayin/msgType/'+id,
		method: 'delete',
		onSuccess: onSuccess
	})
}