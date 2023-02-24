<template>
<div class="my-music-wrapper">
    <div v-if="!loginStatus" class="unlogin-title">
        <div class="inner">
            <h2>私人音乐空间，听我想听的歌</h2>
            <span>登录管理我的音乐</span>
            <el-button type="success" @click="handleLogIn" plain>立即登录</el-button>
        </div>

    </div>
    <div v-if="loginStatus" class="user-profile">
        <div class="profile-img">
            <img :src="getCoverImgUrl(userInfo.avatar)" />
        </div>
        <div class="profile-info">
            <span>{{ userInfo.username }}</span>
            <span>{{ userInfo.location }}</span>
        </div>
    </div>
    <div v-if="loginStatus" class="user-favorites">
        <div class="user-aside">
            <div class="user-created-favorites" @click="changeCurrentViewName('创建的歌单')">
                <span>我创建的歌单</span>
                <el-button @click="addFormDialogVisible = true" type="success" icon="el-icon-plus" circle></el-button>
                <MyMusicSongList myMusicSongListPageName="用户创建的歌单" :songLists="consumerOwnSonglists" @sendLastVisitedSongListId="getLastVisitedSongListId" @sendSongsOfMyMusicPage="displaySongListDetail" @getSongLists="getSongLists" />
            </div>
            <div class="user-marked-favorites" @click="changeCurrentViewName('收藏的歌单')">
                <span>收藏的歌单</span>
                <MyMusicSongList myMusicSongListPageName="用户收藏的歌单" :songLists="favoriteSonglists" @sendLastVisitedSongListId="getLastVisitedSongListId" @sendSongsOfMyMusicPage="displaySongListDetail" @getSongLists="getSongLists" />
            </div>
        </div>
        <div class="user-favorites-content">
            <p>歌曲列表</p>
            <hr />
            <ul>
                <li>
                    <div class="song-item">
                        <span class="item-index">序号</span>
                        <span class="item-title">歌曲名</span>
                        <span class="item-name">歌手</span>
                        <span class="item-intro">专辑</span>
                    </div>
                </li>

                <li v-for="(item, index) in songListDetail" :index="item.id">
                    <div class="song-item">
                        <span class="item-index">{{ index+1 }}</span>
                        <span class="item-title">
                            <span>{{ getSongName(item.name) }}</span>
                            <span v-if="componentViewName === '创建的歌单'" class="fa-outline-dark-gray" @click="handleRemoveFavoriteSongFromAffiliation(item)"><i class="fa fa-trash"></i></span>
                            <span class="fa-outline-dark-gray" @click="handleAddSongToPlaylist(item)"><i class="fa fa-plus-circle"></i></span>
                            <span class="fa-outline-dark-gray" @click="handleSetAudioPlayer(item)"><i class="fa fa-play-circle-o"></i></span>
                        </span>
                        <span class="item-name"> {{ getSingerName(item.name) }}</span>
                        <span class="item-intro">{{ item.introduction }}</span>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <!-- 添加用户歌单 -->
    <el-dialog title="新建歌单" :visible.sync="addFormDialogVisible" width="30%">
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
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/universal';

.my-music-wrapper {
    padding-top: 2px;

    .unlogin-title {
        min-height: 600px;
        display: flex;
        text-align: center;

        .inner {
            margin: auto;

            h2 {
                font-size: 3em;
            }

            span {
                font-size: 1.4em;
            }

            .el-button {
                display: block;
                margin: auto;
                margin-top: 30px;
            }
        }
    }

    .user-profile {
        text-align: center;

        .profile-img {
            width: 100px;
            height: 100px;
            margin: auto;

            img {
                width: 100%;
                height: 100%;
            }
        }

        .profile-info {}
    }

    .user-favorites {
        margin-top: 50px;

        display: flex;
        flex-wrap: nowrap;
        justify-content: flex-start;

        .user-aside {
            display: flex;
            flex-wrap: wrap;
            flex: 0 1 280px;

            >div {
                margin-right: 10px;
                padding: 10px;

                span {
                    font-weight: 800;
                }
            }

            .user-created-favorites {
                width: 100%;
                line-height: 40px;

                .el-button {
                    display: inline-block;
                    float: right;
                }
            }

            .user-marked-favorites {
                width: 100%;
                margin-top: 10px;
            }
        }

        .user-favorites-content {
            flex: auto;
            // border: 1px solid gray;
            @include playlist-content;
        }
    }
}
</style>
