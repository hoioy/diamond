import request from '@/utils/request'

export function findMenu(onSuccess) {
	request({
		url: 'system/menu/query-all',
		method: 'POST',
		data: {},
		onSuccess: onSuccess
	})
}
