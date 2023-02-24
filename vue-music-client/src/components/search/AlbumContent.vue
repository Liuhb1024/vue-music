<template>
<div class="album-wrapper">
    <h2>
        <slot></slot>
    </h2>
    <!-- <el-row :gutter="20">
        <el-col :span="2">序号</el-col>
        <el-col :span="10">歌曲名</el-col>
        <el-col :span="6">歌手</el-col>
        <el-col :span="6">专辑</el-col>
    </el-row>
    <el-row :gutter="20" v-for="(item, index) in searchResults" :key="index">
        <el-col :span="2">
            <div>{{ index+1 }} </div>
        </el-col>
        <el-col :span="10">
            <div>{{ getSongName(item.name) }}</div>
        </el-col>
        <el-col :span="6">
            <div>{{ getSingerName(item.name) }}</div>
        </el-col>
        <el-col :span="6">
            <div>{{ item.introduction }}</div>
        </el-col>
    </el-row> -->
    <ul>
        <li>
            <div class="song-item">
                <span class="item-index">序号</span>
                <span class="item-title">歌曲名</span>
                <span class="item-name">歌手</span>
                <span class="item-intro">专辑</span>
            </div>
        </li>

        <li v-for="(item, index) in searchResults" :key="index">
            <div class="song-item">
                <span class="item-index">{{ index+1 }}</span>
                <span class="item-title">
                    <span>{{ getSongName(item.name) }}</span>
                    <span class="fa-outline-dark-gray" @click="handleAddSongToPlaylist(item)"><i class="fa fa-plus-circle"></i></span>
                    <span class="fa-outline-dark-gray" @click="handleSetAudioPlayer(item)"><i class="fa fa-play-circle-o"></i></span>
                </span>
                <span class="item-name"> {{ getSingerName(item.name) }}</span>
                <span class="item-intro">{{ item.introduction }}</span>
            </div>
        </li>
    </ul>
</div>
</template>

<script>
import {
    mapGetters
} from 'vuex'

import {
    mixinOfSong
} from '@/mixins/song'

export default {
    name: 'AlbumContent',
    props: ['searchResults'],
    mixins: [mixinOfSong],
    data() {
        return {}
    },
    computed: {

    },
    computed: {
        ...mapGetters(['cachedPlaylist'])
    },
    methods: {
        getSingerName(str) {
            return str.split('-')[0]
        },
        getSongName(str) {
            return str.split('-')[1]
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/universal';

.album-wrapper {
    @include playlist-content;
}
</style>
