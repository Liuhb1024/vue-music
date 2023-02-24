import {
    get,
    post
} from './http'

export const checkConsumerIfPresent = phoneNumOrUsername => get('consumer/checkIfPresent', phoneNumOrUsername)

export const register = data => post('consumer/register', data)

export const login = data => post('consumer/verify', data)