<template>
<div class="song-list-album-wrapper">
    <!-- 左侧装饰面板 -->
    <div class="left-panel">
        <div class="music-decor">
            <span class="note">♪</span>
            <span class="note">♫</span>
            <span class="note">♩</span>
            <div class="cd-effect"></div>
        </div>
    </div>

    <!-- 右侧装饰面板 -->
    <div class="right-panel">
        <div class="wave-bars">
            <div class="bar" v-for="n in 12" :key="n"></div>
        </div>
        <div class="circles">
            <div class="circle" v-for="n in 3" :key="n"></div>
        </div>
    </div>

    <!-- 主要内容区域 -->
    <div class="main-content">
        <div class="album-info">
            <div class="album-img">
                <img :src="getCoverImgUrl(audioTempItem.pic)" />
            </div>
            <div>
                <div>
                    <h2>{{audioTempItem.title}}</h2>
                </div>
                <div>
                    <span>标签：</span>
                    <span>{{audioTempItem.style}}</span>
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
    querySongsBySongListId,
    addAffiliation
} from '@/api/songlist'

export default {
    name: 'SongListAlbum',
    mixins: [mixin, mixinOfSong],
    computed: {
        ...mapGetters(['audioTempItem', 'cachedPlaylist', 'userInfo'])
    },
    data() {
        return {
            songId: -1, //标识将被收藏到某一歌单的歌曲
            delSongId: -1, //标识将被从某一收藏歌单中删除的歌曲
            songArr: [],
            consumerOwnSongLists: [],
            songListDialogVisible: false,
        }
    },
    mounted() {
        querySongsBySongListId(this.$route.query.songListId).then(jsonData => {
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
.song-list-album-wrapper {
    width: 100%;
    min-height: 100vh;
    background: #2d3a4b;
    position: relative;
    overflow-x: hidden;

    // 左侧装饰面板
    .left-panel {
        position: fixed;
        left: 0;
        top: 0;
        width: 450px;
        height: 100%;
        background: linear-gradient(to right,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );
        z-index: 0;

        .music-decor {
            width: 100%;
            height: 100%;
            position: relative;

            .note {
                position: absolute;
                font-size: 45px;
                color: rgba(103, 195, 255, 0.8);
                text-shadow: 
                    0 0 20px rgba(103, 195, 255, 0.6),
                    0 0 40px rgba(103, 195, 255, 0.4);
                animation: float 3s ease-in-out infinite;
                z-index: 2;

                &:nth-child(1) {
                    left: 20%;
                    top: 15%;
                    font-size: 50px;
                }

                &:nth-child(2) {
                    left: 70%;
                    top: 40%;
                    font-size: 55px;
                    animation-delay: 0.5s;
                }

                &:nth-child(3) {
                    left: 30%;
                    top: 65%;
                    font-size: 48px;
                    animation-delay: 1s;
                }
            }

            .cd-effect {
                position: absolute;
                left: 35%;
                top: 25%;
                width: 180px;
                height: 180px;
                border-radius: 50%;
                background: radial-gradient(circle at center, 
                    rgba(255, 255, 255, 0.3) 0%,
                    rgba(255, 255, 255, 0.2) 30%,
                    rgba(255, 255, 255, 0.15) 60%,
                    transparent 80%
                );
                border: 4px solid rgba(103, 195, 255, 0.5);
                box-shadow: 
                    0 0 40px rgba(103, 195, 255, 0.4),
                    inset 0 0 40px rgba(103, 195, 255, 0.4);
                transform: translateX(-50%);
                animation: rotate 20s linear infinite;
            }
        }
    }

    // 右侧装饰面板
    .right-panel {
        position: fixed;
        right: 0;
        top: 0;
        width: 450px;
        height: 100%;
        background: linear-gradient(to left,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );
        z-index: 0;

        .wave-bars {
            position: absolute;
            right: 20%;
            top: 40%;
            transform: translateY(-50%);
            display: flex;
            gap: 8px;

            .bar {
                width: 4px;
                height: 40px;
                background: rgba(103, 195, 255, 0.4);
                border-radius: 2px;
                animation: waveBar 1.2s ease-in-out infinite;

                @for $i from 1 through 12 {
                    &:nth-child(#{$i}) {
                        animation-delay: $i * 0.1s;
                    }
                }
            }
        }

        .circles {
            position: absolute;
            right: 15%;
            top: 30%;
            display: flex;
            flex-direction: column;
            gap: 30px;

            .circle {
                width: 20px;
                height: 20px;
                border-radius: 50%;
                background: rgba(103, 195, 255, 0.3);
                animation: pulse 2s ease-in-out infinite;

                &:nth-child(1) { animation-delay: 0s; }
                &:nth-child(2) { animation-delay: 0.5s; }
                &:nth-child(3) { animation-delay: 1s; }
            }
        }
    }

    // 主要内容区域
    .main-content {
        max-width: 1200px;
        margin: 0 auto;
        padding: 40px 450px 80px;

        // 专辑信息卡片
        .album-info {
            display: flex;
            justify-content: flex-start;
            padding: 30px;
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            margin-bottom: 40px;

            .album-img {
                margin-right: 30px;

                img {
                    width: 208px;
                    border-radius: 12px;
                    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.3);
                }
            }

            div {
                color: #ffffff;

                h2 {
                    font-size: 28px;
                    font-weight: 600;
                    margin-bottom: 20px;
                    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
                }

                div {
                    margin-bottom: 15px;
                    
                    span:first-child {
                        color: rgba(255, 255, 255, 0.7);
                        margin-right: 10px;
                    }

                    span:last-child {
                        color: rgba(255, 255, 255, 0.9);
                    }
                }
            }
        }

        // 歌曲列表
        .album-content {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            padding: 25px;

            p {
                color: #ffffff;
                font-size: 22px;
                font-weight: 600;
                margin-bottom: 20px;
                text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
            }

            .song-item {
                display: flex;
                align-items: center;
                padding: 12px;
                border-radius: 8px;
                transition: all 0.3s ease;

                span {
                    color: rgba(255, 255, 255, 0.9);

                    &.item-index { width: 60px; }
                    &.item-title { flex: 2; }
                    &.item-name { flex: 1; }
                    &.item-intro { flex: 1; }

                    .fa-outline-dark-gray {
                        margin-left: 15px;
                        cursor: pointer;
                        color: rgba(255, 255, 255, 0.7);
                        transition: all 0.3s ease;

                        &:hover {
                            transform: scale(1.2);

                            .fa-bookmark-o { color: #67c3ff; }
                            .fa-plus-circle { color: #2ed573; }
                            .fa-play-circle-o { color: #67c3ff; }
                        }
                    }
                }

                &:hover {
                    background: rgba(103, 195, 255, 0.15);
                }
            }
        }
    }

    // 添加到歌单对话框样式
    .box-card {
        background: rgba(45, 58, 75, 0.95);
        backdrop-filter: blur(10px);
        border-radius: 15px;
        border: 1px solid rgba(103, 195, 255, 0.2);

        li {
            display: flex;
            align-items: center;
            padding: 10px;
            border-radius: 8px;
            transition: all 0.3s ease;
            cursor: pointer;

            &:hover {
                background: rgba(103, 195, 255, 0.15);
                transform: translateX(5px);
            }

            img {
                width: 50px;
                height: 50px;
                border-radius: 6px;
                margin-right: 15px;
            }

            span {
                color: #ffffff;
                font-size: 14px;
            }
        }
    }
}

// 动画效果
@keyframes rotate {
    from { transform: translateX(-50%) rotate(0deg); }
    to { transform: translateX(-50%) rotate(360deg); }
}

@keyframes float {
    0%, 100% {
        transform: translateY(0) rotate(-5deg);
        opacity: 0.8;
    }
    50% {
        transform: translateY(-20px) rotate(5deg);
        opacity: 1;
    }
}

@keyframes waveBar {
    0%, 100% {
        height: 40px;
        opacity: 0.8;
    }
    50% {
        height: 60px;
        opacity: 1;
    }
}

@keyframes pulse {
    0%, 100% {
        transform: scale(1);
        opacity: 0.8;
    }
    50% {
        transform: scale(1.2);
        opacity: 1;
    }
}

// 响应式布局
@media screen and (max-width: 1800px) {
    .song-list-album-wrapper {
        .main-content {
            padding: 40px 350px 80px;
        }
        .left-panel, .right-panel {
            width: 350px;
        }
    }
}

@media screen and (max-width: 1400px) {
    .song-list-album-wrapper {
        .main-content {
            padding: 40px 250px 80px;
        }
        .left-panel, .right-panel {
            width: 250px;
        }
    }
}

@media screen and (max-width: 1200px) {
    .song-list-album-wrapper {
        .main-content {
            padding: 20px;
        }
        .left-panel, .right-panel {
            display: none;
        }
    }
}
</style>
