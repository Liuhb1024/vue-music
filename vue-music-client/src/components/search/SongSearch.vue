<template>
<div class="song-search-wrapper">
    <div>歌曲搜索结果</div>
    <AlbumContent :searchResults="cachedSongs"></AlbumContent>
</div>
</template>

<script>
import {
    mapGetters
} from 'vuex'
import {
    mixin
} from '@/mixins'

import AlbumContent from './AlbumContent.vue'

export default {
    name: 'SongSearch',
    mixins: [mixin],
    components: {
        AlbumContent
    },
    data() {
        return {}
    },
    computed: {
        ...mapGetters(['cachedSongs'])
    },
    mounted() {
        this.searchForSongs()
    },
    methods: {

    }
}
</script>

<style lang="scss" scoped>
.song-search-wrapper {
    height: 100%;
}
</style>
