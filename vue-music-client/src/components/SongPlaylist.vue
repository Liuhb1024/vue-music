<template>
<div ref="playlist-wrapper" class="playlist-wrapper" v-show="isShowSongPlaylist">
    <div class="play-control">
        <div class="tool-bar" ref="tool-bar">
            <div @click="playAllSongsInPlaylist">播放全部</div>
            <div @click="addSongsToSongListCreatedByCustomer">添加到</div>
            <div @click="clearPlaylist">清空列表</div>
            <div @click="downloadSong">下载歌曲</div>
        </div>
    </div>
    <div class="playlist-main">
        <el-row :gutter="20">
            <el-col :span="1">
                <el-checkbox></el-checkbox>
            </el-col>
            <el-col :span="12">歌曲</el-col>
            <el-col :span="4">歌手</el-col>
            <el-col :span="7">专辑</el-col>
        </el-row>
        <el-row v-for="(item, index) in cachedPlaylist" :key="index" :gutter="20">
            <el-col :span="1">
                <span>
                    <el-checkbox></el-checkbox>
                </span>
            </el-col>
            <el-col :span="12">
                <span @click="handlePlayAudio(item)" :class="{'audio-playing': audioId === item.id}">{{getSongName(item.name)}}</span>&nbsp;&nbsp;
                <span v-if="audioId === item.id && audioPlayingState"><i class="fa fa-circle-o-notch fa-spin"></i></span>
            </el-col>
            <el-col :span="4">
                <span @click="handleSearchSingers(getSingerName(item.name))">{{getSingerName(item.name)}}</span>
            </el-col>
            <el-col :span="7">
                <span> {{item.introduction}}</span>
            </el-col>
        </el-row>
    </div>
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
    name: 'SongPlaylist',
    mixins: [mixinOfSong],
    data() {
        return {}
    },
    computed: {
        ...mapGetters(['audioId', 'isShowSongPlaylist', 'cachedSongs', 'cachedPlaylist', 'audioPlayingState'])
    },
    mounted() {
        //设置当点击页面中的任何地方时关闭播放列表（除点击播放列表以外）
        let _this = this
        document.addEventListener('click', function () {
            _this.$store.commit('SET_IS_SHOW_SONG_PLAYLIST', false)
        }, false)

        //阻止冒泡，设置点击播放列表容器时，不关闭该容器
        this.$refs['playlist-wrapper'].addEventListener('click', function ($event) {
            $event.stopPropagation()
        })
    },
    methods: {
        getSingerName(str) {
            return str.split('-')[0]
        },
        getSongName(str) {
            return str.split('-')[1]
        },
        handlePlayAudio(audio) {
            //设置播放器参数
            this.setAudioPlayer(audio)
            //设置正在播放音频在播放列表中的索引
            let index = this.cachedPlaylist.findIndex(song => audio.id === song.id)
            this.$store.commit('SET_AUDIO_PLAYING_INDEX', index)
        },
        handleSearchSingers(singerName) {
            console.log(singerName)
        },
        playAllSongsInPlaylist() {
            alert('别点啦，还没做完捏!')
        },
        addSongsToSongListCreatedByCustomer() {
            alert('别点啦，还没做完捏!')
        },
        clearPlaylist() {
            this.$store.commit('SAVE_PLAYLIST', [])
        },
        downloadSong(){
         alert('别点啦，还没写完呢。')
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';
@import '@/assets/scss/overlap_levels';

.playlist-wrapper {
    width: 50%;
    height: 650px;
    padding: 10px;
    position: fixed;
    left: 25%;
    bottom: 70px;
    background-color: rgb(255, 255, 255);
    border-radius: 4px;
    @include normal-border-style(2px, solid, $theme-color);
    @include z-index-middle;

    .play-control {
        .tool-bar {
            display: flex;
            justify-content: flex-start;
            transition: all 1s;

            >div {
                width: 100px;
                height: 36px;
                margin-right: 10px;
                border-radius: 3px;
                @include normal-border-style(1px, solid, rgb(197, 184, 184));

                font-size: 1em;
                text-align: center;
                line-height: 36px;

                &:hover {
                    @include button-link;
                    background-color: rgb(245, 242, 242);
                }
            }

            >div:nth-of-type(1) {
                color: #eee;
                background-color: rgb(118, 209, 147);
            }
        }
    }

    .playlist-main {
        padding: 10px 0;

        .el-row {
            margin-top: 10px;
            display: flex;

            .el-col {
                line-height: 40px;
                vertical-align: middle;
            }

            &:not(:first-child) {
                .el-col>span:hover {
                    cursor: pointer;
                    color: $theme-color;
                }

                .audio-playing {
                    color: $theme-color;
                }
            }
        }
    }
}
</style>
