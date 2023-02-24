import {
    get,
} from './http'

/**
 * 查询所有歌手信息
 * @returns Promise
 */
export const queryAllSingers = () => get('singer/queryallsingers')


/**
 * 查询所有歌单信息
 * @returns Promise
 */
export const queryAllSongLists = () => get('songlist/queryallsonglists')

/**
 * 查询所有歌曲信息
 * @returns Promise
 */
export const queryAllSongs = () => get('song/queryallsongs')

export const querySongsByName = (kw) => get('song/querysongsbyname', kw)