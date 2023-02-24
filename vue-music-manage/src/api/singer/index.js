import {
    get,
    post,
    put,
    del
} from '../http'

// 获取用户登录状态
export const getLoginStatus = (params) => post('admin/login/status', params)

// 查询所有歌手
export const queryAllSingers = () => get('singer/queryallsingers')

// 添加歌手信息
export const addSinger = (data) => post('singer/add', data)

/**
 * 修改歌手信息
 * @param {JSON} data 
 * @returns 操作结果 JSON
 */
export const modifySinger = (data) => put('singer/modify', data)

export const removeSinger = (singerId) => del(`singer/remove/${singerId}`)