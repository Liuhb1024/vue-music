<template>
<div class="singer-album-wrapper">
    <div class="album-info">
        <div class="album-img">
            <img :src="getCoverImgUrl(audioTempItem.pic)" />
        </div>
        <div>
            <div>
                <h1>{{audioTempItem.name}}</h1>
            </div>
            <div>
                <span>地区：</span>
                <span>{{audioTempItem.location}}</span>
            </div>
            <div>
                <span>出生日期：</span>
                <span>{{audioTempItem.birth}}</span>
            </div>
            <div>
                <span>介绍：</span>
                <span>{{audioTempItem.introduction}}</span>
            </div>
        </div>
    </div>
    <hr />
    <div class="album-content">
        <p>歌曲列表</p>
        <ul>
            <li>
                <div class="song-item">
                    <span class="item-index">序号</span>
                    <span class="item-title">歌曲名</span>
                    <span class="item-name">歌手</span>
                    <span class="item-intro">专辑</span>
                </div>
            </li>

            <li v-for="(item, index) in songArr" :key="index">
                <div class="song-item">
                    <span class="item-index">{{ index+1 }}</span>
                    <span class="item-title">
                        <span>{{ getSongName(item.name) }}</span>
                        <span class="fa-outline-dark-gray" @click="handleAddFavoriteSongToSongList(item)"><i class="fa fa-bookmark-o"></i></span>
                        <span class="fa-outline-dark-gray" @click="handleAddSongToPlaylist(item)"><i class="fa fa-plus-circle"></i></span>
                        <span class="fa-outline-dark-gray" @click="handleSetAudioPlayer(item)"><i class="fa fa-play-circle-o"></i></span>
                    </span>
                    <span class="item-name"> {{ getSingerName(item.name) }}</span>
                    <span class="item-intro">{{ item.introduction }}</span>
                </div>
            </li>
        </ul>
    </div>

    <el-card class="box-card" v-if="songListDialogVisible">
        <div slot="header" class="clearfix">
            <span>添加到歌单</span>
            <el-button @click="songListDialogVisible = false" style="float: right; padding: 3px 0" type="text"><i class="fa fa-close"></i></el-button>
        </div>
        <ul>
            <li v-for="item in consumerOwnSongLists" :index="item.id" @click="addFavoriteSongToSongList(item)">
                <img :src="getCoverImgUrl(item.pic)" />&nbsp;&nbsp;
                <span>{{ item.title }}</span>
            </li>
        </ul>
    </el-card>
</div>
</template>

<script>
import {
    mapGetters
} from 'vuex'

import {
    mixin
} from '@/mixins/index'

import {
    mixinOfSong
} from '@/mixins/song'

import {
    addAffiliation
} from '@/api/songlist'

import {
    querySongsBySingerId
} from '@/api/singer'

export default {
    name: 'SingerAlbum',
    mixins: [mixin, mixinOfSong],
    computed: {
        ...mapGetters(['audioTempItem', 'cachedPlaylist', 'userInfo'])
    },
    data() {
        return {
            songId: -1, //标识将被收藏到某一歌单的歌曲
            songArr: [],
            consumerOwnSongLists: [],
            songListDialogVisible: false
        }
    },
    mounted() {
        querySongsBySingerId(this.$route.query.singerId).then(jsonData => {
            this.songArr = jsonData
        }).catch(err => console.log(err))
    },
    methods: {
        getSingerName(str) {
            return str.split('-')[0]
        },
        getSongName(str) {
            return str.split('-')[1]
        },
        addFavoriteSongToSongList( /*歌单*/ item) {
            let affiliation = {
                id: -1,
                songListId: item.id,
                songId: this.songId,
            }
            addAffiliation(affiliation).then(jsonData => {
                if (jsonData.code === 1) {
                    this.$message({
                        message: jsonData.msg,
                        type: 'success',
                        duration: 2000
                    })
                } else {
                    this.$message({
                        message: jsonData.msg,
                        type: 'error',
                        duration: 2000
                    })
                }
            }).catch(err => console.log(err))
            this.songListDialogVisible = false
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/universal';

.singer-album-wrapper {
    padding: 30px 50px;

    .album-info {
        display: flex;
        justify-content: flex-start;

        h1 {
            font-size: 3em;
        }

        .album-img {
            margin-right: 20px;

            img {
                width: 208px;
            }
        }
    }

    .album-content {
        margin-top: 40px;
        @include playlist-content;
    }
}

.box-card {
    width: 450px;
    position: fixed;
    top: 25%;
    left: 40%;
    transition: all 1s;

    li {
        cursor: pointer;
        // background-color: rgb(63, 87, 167);

        &:hover {
            background-color: rgb(105, 212, 119);
        }

        img {
            width: 50px;
            height: 50px;
            vertical-align: middle;
        }
    }
}
</style>
