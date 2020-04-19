import request from '@/utils/request'
import downloadReq from '@/utils/downloadReq'
const base_url = '/system/jiayin/message'

export function queryPageLogs(data) {
    return request({
        url: base_url + '/selectByPage',
        method: 'post',
        data
    })
}

export function add(data) {
    return request({
        url: base_url + '/save',
        method: 'post',
        data
    })
}

export function deleteById(id) {
    return request({
        url: base_url + '/deleteById',
        method: 'delete',
        params: { id }
    })
}

export function deleteByIds(data) {
    return request({
        url: base_url + '/deleteByIds',
        method: 'delete',
        data
    })
}

export function update(data) {
    return request({
        url: base_url + '/update',
        method: 'put',
        data
    })
}


}

