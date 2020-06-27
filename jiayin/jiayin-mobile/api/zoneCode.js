import request from '@/utils/request'

export function selectChildren(msgTypeParentId,onSuccess) {
	request({
		url: 'jiayin/msgType/findByParentId?parentId='+msgTypeParentId,
		onSuccess: onSuccess
	})
}
export function selectParent(onSuccess) {
	request({
		url: 'jiayin/msgType/findByParentId?parentId=',
		onSuccess: onSuccess
	})
}
export function findById(id,onSuccess) {
	request({
		url: 'jiayin/msgType/'+id,
		onSuccess: onSuccess
	})
}
export function findParentByChildId(id,onSuccess) {
	 request({
		url: 'jiayin/msgType/findParentByChildId/'+id,
		onSuccess: onSuccess
	})
}
export function findByParentId(parentId,onSuccess) {
	 request({
		url: 'jiayin/zonecode/findByParentId/?parentId='+parentId,
		onSuccess: onSuccess
	})
}