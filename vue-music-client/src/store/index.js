import Vue from 'vue'
import Vuex from 'vuex'
import config from './config'
import song from './song'
import user from './user'

Vue.use(Vuex)

const getters = {
    activeNav(state, getters) {
        let str = state.activeNav
        if (!str) {
            str = JSON.parse(sessionStorage.getItem('activeNav'))
        }
        return str
    },
    loginStatus: (state, getters) => {
        return getters['user/loginStatus']
    },
    userInfo: (state, getters) => {
        return getters['user/userInfo']
    }
}

const mutations = {
    SET_ACTIVE_NAV(state, title) {
        state.activeNav = title
        sessionStorage.setItem('activeNav', JSON.stringify(title))
    }
}

const state = {
    activeNav: '/'
}

const store = new Vuex.Store({
    state,
    getters,
    mutations,
    modules: {
        config,
        song, 
        user
    }
})

export default store