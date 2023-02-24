import {
    removeAffiliation,
    queryConsumerOwnSongListsByConsumerId
} from '@/api/songlist'

export const mixinOfSong = {
    methods: {
        /**
         * 设置播放器参数
         * @param {Song} audio 歌曲对象
         */
        setAudioPlayer(audio) {
            if (audio.id !== this.$store.getters.audioId) {
                this.$store.commit('SET_AUDIO_ID', audio.id)
                this.$store.commit('SET_AUDIO_URL', audio.url)
                this.$store.commit('SET_AUDIO_TITLE', audio.name)
                this.$store.commit('SET_AUDIO_PIC', audio.pic)
                this.$store.commit('SET_AUDIO_CURRENT_TIME', 0)
                this.$store.commit('SET_AUDIO_DURATION', 0)
                this.$store.commit('SET_AUDIO_DISPLAY_PERCENTAGE', 0)
                this.$store.commit('SET_AUDIO_PLAYING_PERCENTAGE', 0)
                this.$store.commit('SET_IS_PLAYING', true)
            } else {
                this.$store.commit('SET_IS_PLAYING', !this.$store.getters.audioPlayingState)
            }
        },
        findIndexInPlaylist(audio) {
            return this.cachedPlaylist.findIndex(song => song.id === audio.id)
        },
        handleSetAudioPlayer(audio) {
            //设置播放器参数
            this.setAudioPlayer(audio)
            //添加到播放列表
            this.handleAddSongToPlaylist(audio)
            //设置该音频在播放列表中的索引
            this.$store.commit('SET_AUDIO_PLAYING_INDEX', this.findIndexInPlaylist(audio))
        },
        handleAddSongToPlaylist(audio) {
            //查找播放列表中是否已存在该音乐，若存在，则不做任何处理
            if (this.findIndexInPlaylist(audio) > -1) {
                this.$message({
                    message: '已存在于播放列表中',
                    type: 'warning',
                    duration: 1600
                })
                return;
            }
            this.cachedPlaylist.push(audio)
            this.$message({
                message: '添加到播放列表成功',
                type: 'success',
                duration: 1600
            })
            this.$store.commit('SAVE_PLAYLIST', this.cachedPlaylist)
        },
        handleAddFavoriteSongToSongList(song) {
            this.songId = song.id
            this.songListDialogVisible = true
            queryConsumerOwnSongListsByConsumerId(this.userInfo.id).then(jsonData => {
                this.consumerOwnSongLists = jsonData
            }).catch(err => console.log(err))
        },
        handleRemoveFavoriteSongFromAffiliation(song) {
            this.delSongId = song.id
            this.confirmDeletionDialogVisible = true
        }
    }
}