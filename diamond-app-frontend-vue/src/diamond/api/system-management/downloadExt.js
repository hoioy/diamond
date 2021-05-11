
import downloadReq from '@/utils/downloadReq'

const base_url = '/downloadTest'

// 部门ID下载相关证书材料
export function downloadByID(id) {
  return downloadReq({
    url: base_url + '/download',
    method: 'get',
    params: { id }
  })
}
