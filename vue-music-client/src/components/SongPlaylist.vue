<template>
<div ref="playlist-wrapper" class="playlist-wrapper" v-show="isShowSongPlaylist">
    <div class="play-control">
        <div class="tool-bar" ref="tool-bar">
            <div @click="playAllSongsInPlaylist">播放全部</div>
            <div @click="clearPlaylist">清空列表</div>
            <div @click="downloadSong">下载歌曲</div>
        </div>
    </div>
    <div class="playlist-main">
        <el-row :gutter="20">
            <el-col :span="1">
                <el-checkbox 
                    v-model="selectAll"
                    @change="handleSelectAll">
                </el-checkbox>
            </el-col>
            <el-col :span="12">歌曲</el-col>
            <el-col :span="4">歌手</el-col>
            <el-col :span="7">专辑</el-col>
        </el-row>
        <el-row v-for="(item, index) in cachedPlaylist" :key="index" :gutter="20">
            <el-col :span="1">
                <span>
                    <el-checkbox 
                        v-model="item.selected"
                        @change="handleSelect">
                    </el-checkbox>
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
        return {
            selectAll: false
        }
    },
    computed: {
        ...mapGetters(['audioId', 'isShowSongPlaylist', 'cachedSongs', 'cachedPlaylist', 'audioPlayingState'])
    },
    mounted() {
        // 将播放列表元素移动到body下直接挂载
        document.body.appendChild(this.$refs['playlist-wrapper']);
        this.$refs['playlist-wrapper'].classList.add('top-layer');
        
        // 原有的事件监听代码保持不变
        let _this = this
        document.addEventListener('click', function () {
            _this.$store.commit('SET_IS_SHOW_SONG_PLAYLIST', false)
        }, false)

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
        async downloadSong() {
            // 检查是否有选中的歌曲
            const selectedSongs = this.cachedPlaylist.filter(song => song.selected);
            
            if (selectedSongs.length === 0) {
                this.$message.warning('请选择要下载的歌曲');
                return;
            }

            try {
                for (const song of selectedSongs) {
                    this.$message({
                        message: `正在下载: ${song.name}`,
                        type: 'info'
                    });

                    // 创建下载链接
                    const link = document.createElement('a');
                    // 使用正确的端口号和上下文路径
                    link.href = `http://localhost:9000/myvue3project/song/download?url=${encodeURIComponent(song.url)}`;
                    link.target = '_blank';
                    const fileName = `${this.getSingerName(song.name)}-${this.getSongName(song.name)}.mp3`;
                    link.download = fileName;
                    
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);

                    this.$message.success(`${song.name} 下载已开始`);
                }
            } catch (error) {
                console.error('下载失败:', error);
                this.$message.error('下载失败，请稍后重试');
            }
        },
        // 全选/取消全选
        handleSelectAll(val) {
            this.cachedPlaylist.forEach(item => {
                this.$set(item, 'selected', val);
            });
        },
        
        // 单个选择
        handleSelect() {
            this.selectAll = this.cachedPlaylist.every(item => item.selected);
        },

        // 初始化歌曲选中状态
        initSongSelection() {
            this.cachedPlaylist.forEach(item => {
                this.$set(item, 'selected', false);
            });
        }
    },
    watch: {
        cachedPlaylist: {
            handler(newVal) {
                if (newVal && newVal.length > 0) {
                    this.initSongSelection();
                }
            },
            immediate: true
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
    padding: 20px;
    position: fixed;
    left: 25%;
    bottom: 70px;
    background: rgba(255, 255, 255, 0.98);
    border-radius: 15px;
    border: 1px solid rgba(103, 195, 255, 0.3);
    box-shadow: 
        0 8px 32px rgba(0, 0, 0, 0.2),
        0 0 0 1px rgba(103, 195, 255, 0.2),
        0 0 20px rgba(103, 195, 255, 0.1);
    backdrop-filter: blur(10px);
    z-index: 10000 !important;

    .play-control {
        .tool-bar {
            display: flex;
            justify-content: flex-start;
            gap: 10px;
            transition: all 0.3s;

            > div {
                width: 100px;
                height: 36px;
                border-radius: 8px;
                border: 1px solid rgba(103, 195, 255, 0.3);
                font-size: 14px;
                text-align: center;
                line-height: 36px;
                transition: all 0.3s ease;
                background: rgba(255, 255, 255, 0.9);

                &:hover {
                    background: rgba(103, 195, 255, 0.15);
                    transform: translateY(-2px);
                    box-shadow: 0 4px 12px rgba(103, 195, 255, 0.2);
                }
            }

            > div:nth-of-type(1) {
                color: #ffffff;
                background: rgba(103, 195, 255, 0.9);
                border: none;

                &:hover {
                    background: rgba(103, 195, 255, 1);
                    transform: translateY(-2px);
                }
            }
        }
    }

    .playlist-main {
        padding: 20px 0;

        .el-row {
            margin-top: 10px;
            display: flex;
            border-radius: 8px;
            transition: all 0.3s ease;

            .el-col {
                line-height: 40px;
                vertical-align: middle;
            }

            &:not(:first-child) {
                padding: 0 10px;

                &:hover {
                    background: rgba(103, 195, 255, 0.05);
                }

                .el-col > span {
                    transition: all 0.3s ease;

                    &:hover {
                        cursor: pointer;
                        color: #67c3ff;
                    }
                }

                .audio-playing {
                    color: #67c3ff;
                    font-weight: 500;
                }
            }

            &:first-child {
                color: rgba(45, 58, 75, 0.9);
                font-weight: 500;
                border-bottom: 1px solid rgba(103, 195, 255, 0.2);
                margin-bottom: 15px;
                padding-bottom: 10px;
            }
        }
    }
}
</style>
