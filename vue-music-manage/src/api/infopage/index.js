import {
    get,
    post,
    put,
    del
} from '../http'

// 获取用户登录状态
export const getLoginStatus = (params) => post('consumer/login/status', params)

// 查询所有用户
export const queryAllConsumers = () => get('consumer/query-all')

/**
 * 添加用户信息
 * @param {Object} data 
 * @returns 
 */
export const addConsumer = (data) => post('consumer/add', data)

/**
 * 修改用户信息
 * @param {Object} data 
 * @returns 操作结果 JSON
 */
export const modifyConsumer = (data) => put('consumer/modify', data)

/**
 * 删除用户
 * @param {Number} consumerId 
 * @returns 操作结果 JSON
 */
export const removeConsumer = (consumerId) => del(`consumer/remove/${consumerId}`)