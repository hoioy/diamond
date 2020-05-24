import request from '@/utils/request'

export function selectParent(onSuccess) {
	request({
		url: 'jiayin/msgType/selectParent',
		onSuccess: onSuccess
	})
}