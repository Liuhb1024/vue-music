<template>
<div class="my-music-song-list-wrapepr">
    <ul>
        <li v-for="item in songLists" :index="item.id" @click="handleDisplaySongListDetail(item.id)">
            <img :src="getCoverImgUrl(item.pic)" />
            <div class="left-button">
                <el-button @click.stop="handleRemoveSongList(/*歌单id*/item.id)" type="danger" icon="el-icon-delete" circle></el-button>
            </div>
            <span>{{ item.title }}</span>
        </li>
    </ul>
    <!-- 删除歌单信息 -->
    <div class="container">
        <el-dialog title="删除歌单信息" :visible.sync="delDialogVisible" width="300px">
            <span>删除歌单后不可恢复，是否继续？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="delDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="delSongList">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</div>
</template>

<script>
import {
    mixin
} from '@/mixins/index'

import {
    mapGetters
} from 'vuex'

import {
    querySongsBySongListId,
    reomoveFavoriteSongList,
    reomoveSongListsCreatedByConsumerThroughSongListId
} from '@/api/songlist'

export default {
    name: 'MyMusicSongList',
    props: ['songLists', 'myMusicSongListPageName'],
    mixins: [mixin],
    computed: {
        ...mapGetters(['userInfo'])
    },
    data() {
        return {
            delDialogVisible: false,
            delSongListId: -1, //要删除的歌单id
        }
    },
    mounted() {

    },
    methods: {
        getSongLists() {
            this.$emit('getSongLists')
        },
        handleDisplaySongListDetail(songListId) {
            //设置上次访问的歌单的id，并传递给父组件使用
            this.$emit('sendLastVisitedSongListId', songListId)
            //根据歌单id查询歌单详情
            querySongsBySongListId(songListId).then(jsonData => {
                let res = JSON.stringify(jsonData)
                this.$emit('sendSongsOfMyMusicPage', res)
            }).catch(err => console.log('获取歌单详情：', err))
        },
        handleRemoveSongList(songListId) {
            this.delSongListId = songListId
            this.delDialogVisible = true
        },
        delSongList() {
            if (this.myMusicSongListPageName === '用户创建的歌单') {
                reomoveSongListsCreatedByConsumerThroughSongListId(this.delSongListId).then(jsonData => {
                    if (jsonData.code) {
                        this.$message({
                            message: jsonData.msg,
                            type: 'success',
                            duration: 2000
                        })
                        this.delDialogVisible = false
                        this.getSongLists()
                    } else {
                        this.$message({
                            message: jsonData.msg,
                            type: 'error',
                            duration: 2000
                        })
                    }
                }).catch(err => console.log('删除用户创建的歌单:', err))
            } else {
                reomoveFavoriteSongList(this.userInfo.id, this.delSongListId).then(jsonData => {
                    if (jsonData.code) {
                        this.$message({
                            message: jsonData.msg,
                            type: 'success',
                            duration: 2000
                        })
                        this.delDialogVisible = false
                        this.getSongLists()
                    } else {
                        this.$message({
                            message: jsonData.msg,
                            type: 'error',
                            duration: 2000
                        })
                    }
                }).catch(err => console.log('删除收藏的歌单:', err))
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.my-music-song-list-wrapepr {
    li {
        width: 100%;
        height: 45px;
        position: relative;
        margin-bottom: 1px;
        line-height: 40px;
        overflow: hidden;
        padding: 0 10px;
        border-radius: 8px;
        transition: all 0.3s ease;
        background: rgba(255, 255, 255, 0.05);

        span {
            color: rgba(255, 255, 255, 0.9);  // 提高文字亮度
            font-size: 15px;
            font-weight: 500;
            letter-spacing: 0.5px;
            text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            transition: all 0.3s ease;
        }

        img {
            vertical-align: middle;
            width: 40px;
            height: 40px;
            margin-right: 10px;
            border-radius: 6px;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
        }

        .left-button {
            display: none;
            width: 40px;
            height: 45px;
            background-color: rgba(103, 195, 255, 0.1);  // 使用主题蓝色
            position: absolute;
            left: 0;
            top: 0;
            text-align: center;
            transition: all 0.3s ease;
        }

        &:hover {
            background: rgba(103, 195, 255, 0.15);
            transform: translateX(5px);

            span {
                color: #67c3ff;  // 悬浮时变为主题蓝色
            }

            div {
                display: block;
            }

            img {
                box-shadow: 0 4px 12px rgba(103, 195, 255, 0.3);
            }
        }
    }
}

// 删除按钮样式优化
:deep(.el-button--danger) {
    background: rgba(255, 71, 87, 0.2);
    border: none;
    
    &:hover {
        background: rgba(255, 71, 87, 0.8);
        transform: scale(1.1);
    }
}
</style>
