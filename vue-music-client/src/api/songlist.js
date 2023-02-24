import {
    get,
    post,
    del
} from './http'

export const querySongsBySongListId = songListId => get('songlist/querysongsbysonglistid', {
    songListId
})

/**
 * 根据名称查询歌单
 * @returns Promise
 */
export const querySongListsByTitle = title => get('songlist/querysonglistsbytitle', {
    title
})

/**
 * 查询所有歌单
 * @returns Promise
 */
export const queryAllSongLists = () => get('songlist/queryallsonglists')

/**
 * 查询用户创建的歌单
 * @returns Promise
 */
export const queryConsumerOwnSongListsByConsumerId = (consumerId) => get('songlist/querysonglistsbyconsumerid', {
    consumerId
})

/**
 * 查询用户收藏的歌单
 * @returns Promise
 */
export const queryFavoriteSongListsByConsumerId = (consumerId) => get('favorite/querysonglistsbyconsumerid', {
    consumerId
})

/**
 * 添加歌单信息
 * @param {Object} data 
 * @returns Promise
 */
export const addSongList = (data) => post('songlist/add', data)

/**
 * 删除用户创建的歌单信息
 * @param {String} songListId 
 * @returns Promise
 */
export const reomoveSongListsCreatedByConsumerThroughSongListId = (songListId) => del(`songlist/remove/${songListId}`)

/**
 * 删除用户收藏的歌单信息
 * @param {Number} consumerId 用户id
 * @param {Number} songListId 歌单id
 * @returns Promise
 */
export const reomoveFavoriteSongList = (consumerId, songListId) => del('favorite/remove', {
    consumerId,
    songListId
}, {
    'Content-Type': 'application/x-www-form-urlencoded'
})

/**
 * 添加用户收藏的音乐到歌单
 * @param {Affiliation} data 
 * @returns Promise
 */
export const addAffiliation = (data) => post('affiliation/add', data)

/**
 * 从用户的歌单中删除用户收藏的音乐
 * @param {Affiliation} data 
 * @returns Promise
 */
export const removeAffiliation = (data) => del('affiliation/remove', data)