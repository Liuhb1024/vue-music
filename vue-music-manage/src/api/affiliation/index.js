import {
    post,
    del
} from '@/api/http'

/**
 * 添加歌曲所属歌单信息
 * @param {Affiliation} data 
 * @returns Promise
 */
export const addAffiliation = (data) => post('affiliation/add', data)

/**
 * 删除歌曲所属歌单信息
 * @param {Affiliation} data 
 * @returns Promise
 */
export const removeAffiliation= (data) => del('affiliation/remove', data)
