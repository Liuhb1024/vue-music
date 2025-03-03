import {
    get,
    post
} from './http'

export const checkConsumerIfPresent = phoneNumOrUsername => get('consumer/checkIfPresent', phoneNumOrUsername)

export const register = data => post('consumer/register', data)

export const login = data => post('consumer/verify', data)

// 更新用户信息
export const updateUserInfo = (params) => {
  return request({
    url: `/consumer/update`,
    method: 'post',
    data: params
  })
}

// 更新密码
export const updatePassword = (params) => {
  return request({
    url: `/consumer/updatePassword`,
    method: 'post',
    data: params
  })
}