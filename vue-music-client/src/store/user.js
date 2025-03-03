import Vue from 'vue'
import Vuex from 'vuex'
import { setUserInfo, getUserInfo, setLoginStatus, getLoginStatus } from '@/utils/storage';

Vue.use(Vuex)

const getters = {
    loginStatus: state => state.loginStatus,
    userInfo: state => state.userInfo
}

const actions = {

}

const mutations = {
    SET_LOGIN_STATUS(state, status) {
        state.loginStatus = status;
        setLoginStatus(status);
    },
    SET_USER_INFO(state, userInfo) {
        state.userInfo = userInfo;
        setUserInfo(userInfo);
    }
}

const state = {
    loginStatus: getLoginStatus(),
    userInfo: getUserInfo() || {}
}

const user = {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
}

export default user