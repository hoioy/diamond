import request from '@/utils/request'

export function getPage(data) {
	return request({
		url: 'mapper/jiayin/message/page',
		method: 'POST',
		data: data
	})
}
