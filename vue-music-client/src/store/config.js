import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const getters = {
    hostName(state) {
        return state.HOST
    }
}

const actions = {

}

const mutations = {
    
}

const state = {
    HOST: 'http://localhost:9000/myvue3project',
}

const config = {
    state,
    getters,
    actions,
    mutations,
}

export default config