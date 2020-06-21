import request from '@/utils/request'

export function getPage(data,onSuccess) {
	request({
		url: 'jiayin/advert/search',
		method: 'POST',
		data: data,
		onSuccess: onSuccess
	})
}