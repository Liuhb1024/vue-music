<template>
<div class="audio-box" hidden>
    <audio ref="player" :src="audioSrc" @durationchange="handleDurationChange" @canplay="startPlay" @ended="ended" preload="true" @timeupdate="handleTimeUpdate" controls></audio>
</div>
</template>

<script>
import {
    mapGetters
} from 'vuex'

export default {
    name: 'AudioPlayer',
    data() {
        return {
            player: null,
            audioSrc: '',
            duration: 0,
            currentTime: 0,
        }
    },
    mounted() {
        this.player = this.$refs['player']
        this.player.volume = this.audioVolume / 100
    },
    methods: {
        startPlay() {
            this.player.play()
        },
        ended() {
            this.$store.commit('SET_IS_PLAYING', false)
            this.$store.commit('SET_AUDIO_CURRENT_TIME', 0)
            if (this.isAutoNext) {
                this.$emit('playNextAudio')
            }
        },
        togglePlay() {
            if (this.audioPlayingState) {
                this.player.currentTime = this.audioCurrentTime
                this.$nextTick(() => this.player.play())
            } else {
                this.$nextTick(() => this.player.pause())
                this.$store.commit('SET_AUDIO_CURRENT_TIME', this.player.currentTime)
            }
        },
        handleDurationChange() {
            this.duration = this.player.duration
            this.$store.commit('SET_AUDIO_DURATION', this.duration)
        },
        handleTimeUpdate() {
            let currentTime = this.player.currentTime
            // 计算播放进度
            let percentage = Number((currentTime / this.duration).toFixed(2)) * 100
            // 保存状态
            this.$store.commit('SET_AUDIO_CURRENT_TIME', currentTime)
            this.$store.commit('SET_AUDIO_DISPLAY_PERCENTAGE', percentage || 0)
        }
    },
    computed: {
        ...mapGetters(['audioPlayingState', 'audioPlayingURL', 'audioTitle', 'audioPic', 'audioCurrentTime', 'audioVolume', 'audioPlayingPercentage', 'isAutoNext'])
    },
    watch: {
        audioPlayingState() {
            this.togglePlay()
        },
        audioPlayingURL() {
            this.audioSrc = this.audioPlayingURL
            this.player.load()
        },
        audioVolume(volume) {
            this.player.volume = (volume / 100)
        },
        audioPlayingPercentage() {
            this.player.currentTime = Math.floor(this.audioPlayingPercentage * this.duration)
            this.$store.commit('SET_AUDIO_CURRENT_TIME', this.player.currentTime)
            this.$store.commit('SET_IS_PLAYING', true)
        }
    }
}
</script>
