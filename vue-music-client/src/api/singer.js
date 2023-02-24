import {
    get,
} from './http'

export const querySongsBySingerId = singerId => get('song/querysongsbysingerid', {singerId})