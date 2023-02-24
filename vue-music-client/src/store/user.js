import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const getters = {
    loginStatus(state) {
        return state.isLogged || JSON.parse(sessionStorage.getItem('userLoginStatus'))
    },
    userInfo(state) {
        let res = state.user
        if (!res) {
            res = JSON.parse(sessionStorage.getItem('userInfo'))
        }
        return res
    },
}

const actions = {

}

const mutations = {
    SET_LOGIN_STATUS(state, status) {
        state.isLogged = status
        sessionStorage.setItem('userLoginStatus', JSON.stringify(status))
    },
    SET_USER_INFO(state, user) {
        state.user = user
        sessionStorage.setItem('userInfo', JSON.stringify(user))
    }
}

const state = {
    isLogged: false,
    user: {},
}

const user = {
    state,
    getters,
    actions,
    mutations,
}

export default user