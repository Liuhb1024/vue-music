<template>
<div class="my-music-wrapper">
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
        <!-- 未登录状态 -->
        <div v-if="!loginStatus" class="unlogin-title">
            <div class="inner">
                <h2>私人音乐空间</h2>
                <span>登录管理我的音乐</span>
                <el-button type="primary" @click="handleLogIn" class="login-btn">
                    立即登录
                    <i class="el-icon-right"></i>
                </el-button>
            </div>
        </div>

        <!-- 已登录状态 -->
        <div v-if="loginStatus">
            <!-- 用户信息卡片 -->
            <div class="user-profile glass-card">
                <div class="profile-img">
                    <img :src="getCoverImgUrl(userInfo.avatar)" />
                </div>
                <div class="profile-info">
                    <span class="username">{{ userInfo.username }}</span>
                    <span class="location">{{ userInfo.location }}</span>
                </div>
            </div>

            <!-- 歌单内容区域 -->
            <div class="user-favorites">
                <!-- 上方歌单区域 -->
                <div class="songlist-section">
                    <!-- 我创建的歌单 -->
                    <div class="songlist-card glass-card" @click="changeCurrentViewName('创建的歌单')">
                        <div class="section-header">
                            <div class="header-left">
                                <i class="el-icon-folder"></i>
                                <h3>我创建的歌单</h3>
                            </div>
                            <el-button @click.stop="addFormDialogVisible = true" 
                                     type="primary" 
                                     icon="el-icon-plus" 
                                     circle>
                            </el-button>
                        </div>
                        <MyMusicSongList 
                            myMusicSongListPageName="用户创建的歌单" 
                            :songLists="consumerOwnSonglists" 
                            @sendLastVisitedSongListId="getLastVisitedSongListId" 
                            @sendSongsOfMyMusicPage="displaySongListDetail" 
                            @getSongLists="getSongLists" />
                    </div>

                    <!-- 收藏的歌单 -->
                    <div class="songlist-card glass-card" @click="changeCurrentViewName('收藏的歌单')">
                        <div class="section-header">
                            <div class="header-left">
                                <i class="el-icon-star-on"></i>
                                <h3>收藏的歌单</h3>
                            </div>
                        </div>
                        <MyMusicSongList 
                            myMusicSongListPageName="用户收藏的歌单" 
                            :songLists="favoriteSonglists" 
                            @sendLastVisitedSongListId="getLastVisitedSongListId" 
                            @sendSongsOfMyMusicPage="displaySongListDetail" 
                            @getSongLists="getSongLists" />
                    </div>
                </div>

                <!-- 下方歌曲列表 -->
                <div class="songs-section glass-card">
                    <div class="content-header">
                        <div class="header-left">
                            <h3>歌曲列表</h3>
                            <span class="total-count">共 {{ songListDetail.length }} 首</span>
                        </div>
                        <div class="header-right">
                            <el-button type="primary" 
                                      size="small" 
                                      icon="el-icon-plus"
                                      @click="handleAddAllToPlaylist">
                                全部添加到播放列表
                            </el-button>
            </div>
        </div>
                    <div class="song-list">
            <ul>
                            <li class="list-header">
                    <div class="song-item">
                                    <span class="item-index">#</span>
                                    <span class="item-title">歌曲</span>
                        <span class="item-name">歌手</span>
                        <span class="item-intro">专辑</span>
                    </div>
                </li>

                            <li v-for="(item, index) in songListDetail" 
                                :key="item.id"
                                class="list-item"
                                @dblclick="handleSetAudioPlayer(item)">
                    <div class="song-item">
                                    <span class="item-index">{{ index + 1 }}</span>
                        <span class="item-title">
                                        <div class="song-info">
                                            <span class="song-name">{{ getSongName(item.name) }}</span>
                                        </div>
                                        <div class="operations">
                                            <span v-if="componentViewName === '创建的歌单'" 
                                                  class="operation-btn delete"
                                                  title="从歌单中删除"
                                                  @click.stop="handleRemoveFavoriteSongFromAffiliation(item)">
                                                <i class="fa fa-trash"></i>
                                            </span>
                                            <span class="operation-btn add"
                                                  title="添加到播放列表"
                                                  @click.stop="handleAddSongToPlaylist(item)">
                                                <i class="fa fa-plus-circle"></i>
                                            </span>
                                            <span class="operation-btn play"
                                                  title="播放"
                                                  @click.stop="handleSetAudioPlayer(item)">
                                                <i class="fa fa-play-circle-o"></i>
                                            </span>
                                        </div>
                        </span>
                                    <span class="item-name">{{ getSingerName(item.name) }}</span>
                        <span class="item-intro">{{ item.introduction }}</span>
                    </div>
                </li>
            </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 弹窗部分 -->
    <el-dialog title="新建歌单" :visible.sync="addFormDialogVisible" width="30%" class="custom-dialog">
        <el-form :model="form" ref="form" :rules="rules" id="form">
            <el-form-item label="歌单名" prop="title" :label-width="formLabelWidth">
                <el-input v-model="form.title" placeholder="请输入歌单名"></el-input>
            </el-form-item>
            <el-form-item label="简介" prop="introduction" :label-width="formLabelWidth">
                <el-input type="textarea" v-model="form.introduction" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="addFormDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addSongList">确 定</el-button>
        </span>
    </el-dialog>

    <el-dialog title="提示" :visible.sync="confirmDeletionDialogVisible" width="30%" center>
        <span>确认删除歌曲?</span>
        <span slot="footer" class="dialog-footer">
            <el-button @click="confirmDeletionDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="removeFavoriteSongFromAffiliation">确 定</el-button>
        </span>
    </el-dialog>
</div>
</template>

<script>
import {
    mapGetters
} from 'vuex'

import MyMusicSongList from '@/components/MyMusicSongList.vue'

import {
    mixin
} from '@/mixins/index'

import {
    mixinOfSong
}
from '@/mixins/song'

import {
    addSongList,
    querySongsBySongListId,
    removeAffiliation,
    queryConsumerOwnSongListsByConsumerId,
    queryFavoriteSongListsByConsumerId
} from '@/api/songlist'

export default {
    name: 'MyMusic',
    mixins: [mixin, mixinOfSong],
    components: {
        MyMusicSongList
    },
    computed: {
        ...mapGetters(['loginStatus', 'userInfo', 'cachedPlaylist'])
    },
    created() {
        if (Object.keys(this.userInfo).length) {
            this.getSongLists()
        }
    },
    mounted() {},
    data() {
        return {
            componentViewName:'',
            formLabelWidth: '60px', //表单Label宽度
            consumerOwnSonglists: [],
            favoriteSonglists: [],
            songListDetail: [],
            addFormDialogVisible: false,
            confirmDeletionDialogVisible: false,
            delSongId: -1, //要从用户创建或收藏的歌单中删除的歌曲id
            lastVisitedSongListId: -1, //最后一次点击的歌单id

            form: {
                id: -1,
                title: '',
                pic: '/img/song-list-img/default_song-list_img.jpg',
                introduction: '',
                style: '无',
                type: 1, // type值为1表示非管理员
                consumerId: -1
            },
            rules: {
                title: [{
                    required: true,
                    message: '请输入歌单名称',
                    trigger: 'blur'
                }]
            }
        }
    },
    methods: {
        handleLogIn() {
            this.$router.push('/login')
        },
        displaySongListDetail(songlists) {
            this.songListDetail = JSON.parse(songlists) || []
        },
        getSingerName(str) {
            return str.split('-')[0]
        },
        getSongName(str) {
            return str.split('-')[1]
        },
        addSongList() {
            this.form.consumerId = this.userInfo.id
            addSongList(this.form).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: jsonData.msg,
                        type: 'success',
                        duration: 2000
                    })
                    queryConsumerOwnSongListsByConsumerId(this.form.consumerId).then(jsonData => this.consumerOwnSonglists = jsonData).catch(err => console.log(err))
                } else {
                    this.$message({
                        message: jsonData.msg,
                        type: 'error',
                        duration: 2000
                    })
                }
            }).catch(err => console.log(err))
            this.addFormDialogVisible = false
        },
        getSongLists() {
            let consumerId = this.userInfo.id
            // let consumerId = 65
            queryConsumerOwnSongListsByConsumerId(consumerId).then(jsonData => this.consumerOwnSonglists = jsonData).catch(err => console.log(err))
            queryFavoriteSongListsByConsumerId(consumerId).then(jsonData => this.favoriteSonglists = jsonData).catch(err => console.log(err))
        },
        getLastVisitedSongListId(songListId) {
            this.lastVisitedSongListId = songListId
        },
        removeFavoriteSongFromAffiliation() {
            let data = {
                id: -1,
                songId: this.delSongId,
                songListId: this.lastVisitedSongListId
            }
            removeAffiliation(data).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: jsonData.msg,
                        type: 'success',
                        duration: 2000
                    })
                    querySongsBySongListId(this.lastVisitedSongListId).then(jsonData => this.songListDetail = jsonData).catch(err => console.log('获取歌单详情：', err))
                } else {
                    this.$message({
                        message: jsonData.msg,
                        type: 'error',
                        duration: 2000
                    })
                }
            }).catch(err => console.log(err))
            this.confirmDeletionDialogVisible = false
        },
        changeCurrentViewName(name) {
            this.componentViewName = name
        },
        handleAddAllToPlaylist() {
            this.songListDetail.forEach(song => {
                this.handleAddSongToPlaylist(song);
            });
            this.$message({
                message: '已添加全部歌曲到播放列表',
                type: 'success'
            });
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.my-music-wrapper {
    width: 100%;
    min-height: 100vh;
    background: #2d3a4b;
    padding-bottom: 80px;
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
            overflow: hidden;

            // CD 效果
            .cd-effect {
                position: absolute;
                left: 40%;
                top: 25%;
                width: 160px;
                height: 160px;
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

            // 音符效果
            .note {
                position: absolute;
                color: rgba(103, 195, 255, 0.6);
                font-size: 2em;
                animation: float 3s ease-in-out infinite;
                text-shadow: 0 0 20px rgba(103, 195, 255, 0.4);

                &:nth-child(1) {
                    left: 25%;
                    top: 15%;
                    animation-delay: 0s;
                }

                &:nth-child(2) {
                    left: 35%;
                    top: 45%;
                    animation-delay: 0.5s;
                }

                &:nth-child(3) {
                    left: 20%;
                    top: 75%;
                    animation-delay: 1s;
                }
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

        // 波形条效果
        .wave-bars {
            position: absolute;
            right: 25%;
            top: 50%;
            transform: translateY(-50%);
            display: flex;
            gap: 6px;

            .bar {
                width: 3px;
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

        // 圆圈效果
        .circles {
            position: absolute;
            right: 20%;
            top: 30%;
            display: flex;
            flex-direction: column;
            gap: 25px;

            .circle {
                width: 16px;
                height: 16px;
                border-radius: 50%;
                background: rgba(103, 195, 255, 0.3);
                animation: pulse 2s ease-in-out infinite;

                &:nth-child(1) { animation-delay: 0s; }
                &:nth-child(2) { animation-delay: 0.5s; }
                &:nth-child(3) { animation-delay: 1s; }
            }
        }
    }

    // 主内容区域
    .main-content {
        min-height: 100vh;
        padding: 0 450px 80px;
        position: relative;
        z-index: 1;

        .glass-card {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
        }

    .unlogin-title {
        min-height: 600px;
        display: flex;
        text-align: center;

        .inner {
            margin: auto;
                animation: fadeInUp 0.8s ease-out;

            h2 {
                font-size: 3em;
                    color: #fff;
                    margin-bottom: 20px;
                    text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
            }

            span {
                font-size: 1.4em;
                    color: rgba(255, 255, 255, 0.8);
            }

                .login-btn {
                margin-top: 30px;
                    padding: 12px 30px;
                    font-size: 16px;
                    background: rgba(103, 195, 255, 0.2);
                    border: none;
                    
                    &:hover {
                        background: rgba(103, 195, 255, 0.4);
                        transform: translateY(-2px);
                    }
                }
        }
    }

    .user-profile {
            padding: 30px;
            margin-bottom: 30px;
            display: flex;
            align-items: center;
            gap: 30px;

        .profile-img {
            width: 100px;
            height: 100px;
                border-radius: 50%;
                overflow: hidden;
                border: 4px solid rgba(103, 195, 255, 0.3);
                box-shadow: 0 0 20px rgba(103, 195, 255, 0.2);

            img {
                width: 100%;
                height: 100%;
                    object-fit: cover;
                }
            }

            .profile-info {
                display: flex;
                flex-direction: column;
                gap: 10px;

                .username {
                    font-size: 24px;
                    color: #fff;
                    font-weight: 600;
                }

                .location {
                    font-size: 16px;
                    color: rgba(255, 255, 255, 0.7);
                }
            }
    }

    .user-favorites {
            display: flex;
            flex-direction: column;
            gap: 30px;

            .songlist-section {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 30px;

                .songlist-card {
                    padding: 25px;
                    height: fit-content;

                    .section-header {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        margin-bottom: 20px;
                        padding-bottom: 15px;
                        border-bottom: 1px solid rgba(255, 255, 255, 0.1);

                        .header-left {
                            display: flex;
                            align-items: center;
                            gap: 10px;

                            i {
                                font-size: 20px;
                                color: rgba(103, 195, 255, 0.8);
                            }

                            h3 {
                                color: #fff;
                                font-size: 18px;
                                margin: 0;
                            }
                        }

                        .el-button {
                            background: rgba(103, 195, 255, 0.2);
                            border: none;
                            transition: all 0.3s ease;
                            
                            &:hover {
                                background: rgba(103, 195, 255, 0.4);
                                transform: rotate(90deg);
                            }
                        }
                    }

                    // 歌单列表样式
                    .songlist-item {
                        padding: 12px;
                        border-radius: 8px;
                        transition: all 0.3s ease;
                        cursor: pointer;
                        background: rgba(255, 255, 255, 0.05);
                        margin-bottom: 8px;

                        &:hover {
                            background: rgba(103, 195, 255, 0.15);
                            transform: translateX(5px);

                            .songlist-title {
                                color: #67c3ff;
                            }
                        }

                        .songlist-title {
                            color: #ffffff;
                            font-size: 17px;
                            font-weight: 600;
                            margin-bottom: 8px;
                            text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
                            display: flex;
                            align-items: center;
                            gap: 12px;
                            letter-spacing: 0.5px;

                            &::before {
                                content: '♪';
                                color: #67c3ff;
                                font-size: 20px;
                                text-shadow: 0 0 15px rgba(103, 195, 255, 0.5);
                            }

                            &:hover {
                                color: #67c3ff;
                                text-shadow: 0 0 15px rgba(103, 195, 255, 0.5);
                            }
                        }

                        .songlist-info {
                            display: flex;
                            align-items: center;
                            gap: 15px;

                            img {
                                width: 45px;
                                height: 45px;
                                border-radius: 8px;
                                object-fit: cover;
                                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                            }

                            .info-text {
                                flex: 1;

                                .title {
                                    color: rgba(255, 255, 255, 0.9);
                                    font-size: 16px;
                                    font-weight: 500;
                                    margin-bottom: 4px;
                                    letter-spacing: 0.5px;
                                    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);

                                    &:hover {
                                        color: #67c3ff;
                                    }
                                }

                                .count {
                                    color: rgba(255, 255, 255, 0.7);
                                    font-size: 14px;
                                }
                            }
                        }
                    }
                }
            }

            .songs-section {
                padding: 25px;
                .content-header {
                    margin-bottom: 25px;
                    padding-bottom: 15px;
                    border-bottom: 1px solid rgba(255, 255, 255, 0.1);

                    h3 {
                        color: #fff;
                        font-size: 20px;
                    }
                }

                .song-list {
                    .list-header {
                        background: rgba(103, 195, 255, 0.1);
                        border-radius: 8px;
                        margin-bottom: 10px;

                        .song-item {
                            span {
                                color: rgba(255, 255, 255, 0.9);
                                font-size: 16px;
                                font-weight: 500;
                            }
                        }
                    }

                    .list-item {
                        border-bottom: 1px solid rgba(255, 255, 255, 0.05);
                        transition: all 0.3s ease;

                        &:hover {
                            background: rgba(103, 195, 255, 0.15);
                            border-radius: 8px;

                            .operations {
                                opacity: 1;
                            }
                        }

                        .song-item {
                            display: grid;
                            grid-template-columns: 60px 3fr 1fr 1fr;
                            align-items: center;
                            padding: 15px;
                            color: #fff;

                            .item-index {
                                color: rgba(255, 255, 255, 0.9);
                            }

                            .item-name, .item-intro {
                                color: rgba(255, 255, 255, 0.9);
                            }

                            .item-title {
                                display: flex;
                                align-items: center;
                                justify-content: space-between;
                                padding-right: 20px;

                                .operations {
                                    opacity: 0;
                                    transition: opacity 0.3s ease;
                                    display: flex;
                                    gap: 20px;

                                    .operation-btn {
                                        cursor: pointer;
                                        transition: all 0.3s ease;
                                        font-size: 16px;
                                        color: rgba(255, 255, 255, 0.9);

                                        &:hover {
                                            transform: scale(1.2);

                                            &.delete .fa { color: #ff4757; }
                                            &.add .fa { color: #2ed573; }
                                            &.play .fa { color: #67c3ff; }
                                        }
                                    }
                                }
                            }

                            .song-info {
                                .song-name {
                                    color: #ffffff;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 动画效果
@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes rotate {
    from {
        transform: translateX(-50%) rotate(0deg);
    }
    to {
        transform: translateX(-50%) rotate(360deg);
    }
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
        opacity: 0.3;
    }
    50% {
        transform: scale(1.1);
        opacity: 0.6;
    }
}

// 响应式布局
@media screen and (max-width: 1800px) {
    .my-music-wrapper {
        .left-panel, .right-panel {
            width: 350px;
        }

        .left-panel .music-decor {
            .cd-effect {
                left: 45%;
                width: 140px;
                height: 140px;
            }
        }

        .right-panel {
            .wave-bars {
                right: 20%;
                gap: 6px;
            }
            
            .circles {
                right: 15%;
                gap: 25px;
            }
        }

        .main-content {
            padding: 0 350px 80px;
        }
    }
}

@media screen and (max-width: 1400px) {
    .my-music-wrapper {
        .left-panel, .right-panel {
            width: 250px;
        }

        .left-panel .music-decor {
            .cd-effect {
                left: 50%;
                width: 120px;
                height: 120px;
            }

            .note {
                font-size: 1.5em;
            }
        }

        .right-panel {
            .wave-bars {
                right: 15%;
                gap: 4px;
                
                .bar {
                    width: 2px;
                }
            }
            
            .circles {
                right: 12%;
                gap: 20px;
                
                .circle {
                    width: 14px;
                    height: 14px;
                }
            }
        }

        .main-content {
            padding: 0 250px 80px;
        }

        .user-favorites {
            .songlist-section {
                grid-template-columns: 1fr;
                gap: 20px;
            }
        }
    }
}

@media screen and (max-width: 1200px) {
    .my-music-wrapper {
        .left-panel, .right-panel {
            display: none;
        }

        .main-content {
            padding: 20px 20px 80px;
        }

        .user-favorites {
            gap: 20px;

            .songlist-section {
                gap: 15px;
            }

            .songs-section {
                padding: 15px;
            }
        }
    }
}
</style>
