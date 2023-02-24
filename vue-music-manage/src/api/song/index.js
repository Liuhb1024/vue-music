import {
    get,
    post,
    put,
    del
} from '@/api/http'

/**
 * 查询所有歌曲信息
 * @returns Promise
 */
export const queryAllSongs = () => get('song/queryallsongs')

/**
 * 根据歌手名查询其所有歌曲
 * @param {String} params
 * @returns Promise
 */
export const querySongsBySingerId = (params) => get('song/querysongsbysingerid', params)

/**
 * 根据歌单id查询其所有歌曲
 * @param {String} songListId
 * @returns Promise
 */
export const querySongsBySongListId = (songListId) => get('songlist/querysongsbysonglistid', {
    songListId
})

/**
 * 根据歌曲名查询歌曲
 * @param {String} name
 * @returns Promise
 */
export const querySongsByName = (name) => get('song/querysongsbyname', {
    name
})


/**
 * 添加歌曲信息
 * @param {Object} data 
 * @param {Object} config
 * @returns Promise
 */
export const addSong = (data, config) => post('song/add', data, config)

/**
 * 更新歌曲信息
 * @param {*} data 
 * @returns Promise
 */
export const modifySong = (data) => put('song/modify', data)

/**
 * 删除歌曲
 * @param {*} songId 
 * @returns Promise
 */
export const removeSong = (songId) => del(`song/remove/${songId}`)