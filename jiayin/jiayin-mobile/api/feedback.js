import request from '@/utils/request'

export function uploadFileAndData(jsonData,imgs,onSuccess) {	
	return request({
		isUpload: true,
		url: 'jiayin/file/upload',
		files: imgs,
		data: jsonData,
		onSuccess: onSuccess
	})
}
