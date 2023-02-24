<template>
<div class="audio-box">
    <audio ref="audio-player" :src="audioSrc" @canplay="startPlay" @ended="ended" preload="true" controls></audio>
</div>
</template>

<script>
import {
    mapState,
    mapGetters
} from 'vuex'

export default {
    name: 'AudioPlayer',
    data() {
        return {
            audioPlayer: null,
            audioSrc: ''
        }
    },
    mounted() {
        this.audioPlayer = this.$refs['audio-player']
    },
    methods: {
        startPlay() {
            this.audioPlayer.play()
        },
        ended() {

        },
        togglePlay() {
            if (this.isPlaying) {
                this.audioPlayer.load()
                this.$nextTick(() => this.audioPlayer.play())
            } else {
                this.$nextTick(() => this.audioPlayer.pause())
            }
        }
    },
    computed: {
        ...mapState(['isPlaying']),
        ...mapGetters(['audioPlayingUrl'])
    },
    watch: {
        isPlaying() {
            this.audioSrc = this.audioPlayingUrl
            this.togglePlay()
        },
        audioPlayingUrl() {
            this.audioSrc = this.audioPlayingUrl
            this.togglePlay()
        }
    }
}
</script>
