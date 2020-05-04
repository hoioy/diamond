import request from '@/utils/request'

export function findMenu() {
	return request({
		url: 'system/menu/query-all',
		method: 'POST',
		data: {}
	})
}
