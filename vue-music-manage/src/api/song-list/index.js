import {
    get,
    post,
    put,
    del
} from '@/api/http'

/**
 * 查询所有歌单信息
 * @returns Promise
 */
export const queryAllSongLists= () => get('songlist/queryallsonglists')

/**
 * 根据歌手名查询其所有歌单
 * @param {String} params
 * @returns Promise
 */
export const querySongListsBySingerId= (params) => get('songlist/querysonglistsbysingerid', params)

/**
 * 添加歌单信息
 * @param {Object} data 
 * @param {Object} config
 * @returns Promise
 */
export const addSongList = (data, config) => post('songlist/add', data, config)

/**
 * 更新歌单信息
 * @param {*} data 
 * @returns Promise
 */
export const modifySongList= (data) => put('songlist/modify', data)

/**
 * 删除歌单
 * @param {*} songId 
 * @returns Promise
 */
export const removeSongList= (songId) => del(`songlist/remove/${songId}`)