import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
    HOST: 'http://localhost:9000/myvue3project',
    isPlaying: false,
    audioId: -1,
    audioUrl: '',
    singerName: '[singer_name]',
    songs: [],
    playlists: [],
    singers: [],
    consumers: []
}

const getters = {
    formattedSingerName(state, getters) {

    },
    audioPlayingUrl(state) {
        return `${state.HOST}${state.audioUrl}`
    },
    cachedSongs(state) {
        let res = state.songs
        if (!res.length) {
            res = JSON.parse(sessionStorage.getItem('backendCachedSongs'))
        }
        return res
    },
    cachedPlaylists(state) {
        let res = state.playlists
        if (!res.length) {
            res = JSON.parse(sessionStorage.getItem('backendCachedPlaylists'))
        }
        return res
    },
    cachedSingers(state) {
        let res = state.singers
        if (!res.length) {
            res = JSON.parse(sessionStorage.getItem('backendCachedSingers'))
        }
        return res
    },
    cachedConsumers(state) {
        let res = state.consumers
        if (!res.length) {
            res = JSON.parse(sessionStorage.getItem('backendCachedConsumers'))
        }
        return res
    }
}

const actions = {
    modifyName(context, singerName) {
        context.commit('MODIFY_NAME', singerName)
    },
    setAudioState(context, audioUrl) {
        context.commit('SET_AUDIO_URL', audioUrl)
        context.commit('SET_IS_PLAYING', true)
    }
}

const mutations = {
    MODIFY_NAME(state, singerName) {
        state.singerName = singerName
    },
    SET_AUDIO_URL(state, audioUrl) {
        state.audioUrl = audioUrl
    },
    SET_IS_PLAYING(state, isPlaying) {
        state.isPlaying = isPlaying
    },
    SET_CACHED_SONGS(state, songs) {
        state.songs = songs
        sessionStorage.setItem('backendCachedSongs',JSON.stringify(songs))
    },
    SET_CACHED_PLAYLISTS(state, playlists) {
        state.playlists = playlists
        sessionStorage.setItem('backendCachedPlaylists',JSON.stringify(playlists))
    },
    SET_CACHED_SINGERS(state, singers) {
        state.singers = singers
        sessionStorage.setItem('backendCachedSingers',JSON.stringify(singers))
    },
    SET_CACHED_CONSUMERS(state, consumers) {
        state.consumers = consumers
        sessionStorage.setItem('backendCachedConsumers',JSON.stringify(consumers))
    }
}

export default new Vuex.Store({
    state,
    getters,
    actions,
    mutations
})