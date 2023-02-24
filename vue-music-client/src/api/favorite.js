import {
    get,
    post
} from './http'

export const addSongList = (data) => post('songlist/add', data)
