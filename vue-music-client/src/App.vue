<template>
<div id="app">
    <AudioPlayer @playNextAudio="playNextAudio" />
    <AudioPlayBar ref="audioPlayerBar" />
    <SongPlaylist />
    <Header />
    <div class="router-view-wrapper">
        <router-view />
    </div>
    <ScrollToTop />
    <Footer />
</div>
</template>

<script>
import Header from '@/components/common/Header.vue'
import ScrollToTop from '@/components/common/ScrollToTop.vue'
import Footer from '@/components/common/Footer.vue'
import AudioPlayer from './components/AudioPlayer.vue'
import AudioPlayBar from './components/AudioPlayBar.vue'
import SongPlaylist from './components/SongPlaylist.vue'

export default {
    name: 'App',
    components: {
        Header,
        ScrollToTop,
        Footer,
        AudioPlayer,
        AudioPlayBar,
        SongPlaylist
    },
    methods: {
        playNextAudio() {
            this.$refs.audioPlayerBar.handlePlayNextSong()
        }
    },
    watch: {
        '$route'(to, from) {
            this.$store.commit('SET_ACTIVE_NAV', to.path)
            // console.log(to, from)
        }
    }
}
</script>

<style lang="scss">
* {
    margin: 0;
    padding: 0;
}

html,
body {
    height: 100%;
}

body {
    font-family: 'Montserrat', 'Noto Sans SC', 'Source Han Sans CN', '思源黑体 CN', 'Helvetica Neue', 'Helvetica', 'Arial', sans-serif;
}

// 添加自定义字体类
.app-title {
    font-family: 'Poppins', 'Noto Sans SC', 'Source Han Sans CN', '思源黑体 CN', sans-serif;
    font-weight: 600;
    letter-spacing: 1.2px;
}

.app-subtitle {
    font-family: 'Open Sans', 'Noto Sans SC', 'Source Han Sans CN', '思源黑体 CN', sans-serif;
    font-weight: 400;
}

ul,
li {
    list-style: none;
}

#app {
    /* font-family: Avenir, Helvetica, Arial, sans-serif; */
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    background-color: rgb(233, 231, 231);

    .router-view-wrapper {
        width: 60%;
        margin: 2px auto;
        background-color: #eee;
    }
}
</style>
