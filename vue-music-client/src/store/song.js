import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
    searchResultsOfSong: [], // 音乐搜索页面保存的歌曲搜索结果
    playlist: [], // 播放条歌曲播放列表
    indexInPlaylist: -1, // 在播放列表中需要播放的音乐索引
    isPlaying: false, //播放状态
    id: -1, //正在播放的音乐id
    URL: '', // 音乐在数据库中存储的相对地址
    duration: 0, // 音乐持续时长
    curTime: 0, // 音乐当前播放时长
    givenTime: 0, // 指定播放时刻
    displayPercentage: 0, // 进度条播放进度百分比(整数)
    playingPercentage: 0, // 播放器进度百分比(小数)
    title: '', //歌名
    isAuto: true, //是否自动播放下一首
    isShowPlaylist: false, //是否显示播放列表
    pic: '', //歌曲封面
    lyric: '', //歌词
    volume: 20, //控制音量
    tempItem: {} //缓存临时的歌手或者歌单信息
}

const getters = {
    cachedSongs(state) {
        let res = state.searchResultsOfSong
        if (!res.length) {
            res = JSON.parse(sessionStorage.getItem('vueCachedSongs') || null)
        }
        return res
    },
    cachedPlaylist(state) {
        let res = state.playlist
        if (!res.length) {
            res = JSON.parse(localStorage.getItem('vueCachedPlaylist') || null)
        }
        return res || []
    },
    audioId(state) {
        return state.id
    },
    audioURL(state) {
        return state.URL
    },
    audioPlayingIndex(state) {
        return state.indexInPlaylist
    },
    audioPlayingState(state) {
        return state.isPlaying
    },
    audioPlayingURL(state, getters) {
        return `${getters.hostName}${state.URL}`
    },
    audioTitle(state) {
        return state.title
    },
    audioPic(state, getters) {
        return getters.hostName + state.pic
    },
    audioDuration(state) {
        return state.duration
    },
    audioCurrentTime(state) {
        return state.curTime
    },
    audioDisplayPercentage(state) {
        return state.displayPercentage
    },
    audioPlayingPercentage(state) {
        return state.playingPercentage
    },
    audioVolume(state) {
        return state.volume
    },
    isShowSongPlaylist(state) {
        return state.isShowPlaylist
    },
    isAutoNext(state) {
        return state.isAuto
    },
    audioTempItem(state) {
        return state.tempItem
    }
}

const actions = {

}

const mutations = {
    SAVE_SONGS_FOR_EVERY_SEARCH(state, songs) {
        state.searchResultsOfSong = songs
        sessionStorage.setItem('vueCachedSongs', JSON.stringify(songs))
    },
    SAVE_PLAYLIST(state, list) {
        state.playlist = list
        localStorage.setItem('vueCachedPlaylist', JSON.stringify(list))
    },
    SET_AUDIO_ID(state, audioId) {
        state.id = audioId
    },
    SET_AUDIO_URL(state, audioURL) {
        state.URL = audioURL
    },
    SET_IS_PLAYING(state, isPlaying) {
        state.isPlaying = isPlaying
    },
    SET_AUDIO_TITLE(state, title) {
        state.title = title
    },
    SET_AUDIO_PIC(state, pic) {
        state.pic = pic
    },
    SET_AUDIO_DURATION(state, duration) {
        state.duration = duration
    },
    SET_AUDIO_CURRENT_TIME(state, curTime) {
        state.curTime = curTime
    },
    SET_AUDIO_DISPLAY_PERCENTAGE(state, displayPercentage) {
        state.displayPercentage = displayPercentage
    },
    SET_AUDIO_PLAYING_PERCENTAGE(state, playingPercentage) {
        state.playingPercentage = playingPercentage
    },
    SET_AUDIO_PLAYING_INDEX(state, indexInPlaylist) {
        state.indexInPlaylist = indexInPlaylist
    },
    SET_AUDIO_VOLUME(state, volume) {
        state.volume = volume
    },
    SET_IS_SHOW_SONG_PLAYLIST(state, isVisible) {
        state.isShowPlaylist = isVisible
    },
    SET_IS_AUTO_NEXT(state, isAutoNext) {
        state.isAuto = isAutoNext
    },
    SET_TEMP_ITEM(state, item) {
        state.tempItem = item
    }
}

const song = {
    state,
    getters,
    actions,
    mutations
}

export default song