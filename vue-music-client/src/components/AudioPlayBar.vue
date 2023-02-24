<template>
<div class="play-bar-wrapper" :class="{'play-bar-wrapper-display': !isVisible}">
    <div class="chevron-box" @click="isVisible = !isVisible">
        <span v-if="isVisible"><i class="fa fa-chevron-down"></i></span>
        <span v-if="!isVisible"><i class="fa fa-chevron-up"></i></span>
    </div>
    <div class="play-control">
        <!-- 上一首 -->
        <div class="item" @click="handlePlayPrevSong">
            <i class="fa fa-step-backward"></i>
        </div>
        <!-- 播放 -->
        <div class="item" v-if="!audioPlayingState" @click="togglePlay">
            <i class="fa fa-play"></i>
        </div>
        <!-- 暂停 -->
        <div class="item" v-if="audioPlayingState" @click="togglePlay">
            <i class="fa fa-pause"></i>
        </div>
        <!-- 下一首 -->
        <div class="item" @click="handlePlayNextSong">
            <i class="fa fa-step-forward"></i>
        </div>
        <!-- 歌曲图片 -->
        <div class="item">
            <img :src="audioPic" />
        </div>
    </div>
    <div class="play-info">
        <!-- 歌曲名-歌手 -->
        <div class="title-box">
            {{ audioTitle }}
        </div>
        <!-- 已播放时长/歌曲总时长 -->
        <div class="current-time">
            <span>{{ getFormattedTimeStr(this.audioCurrentTime) }} &#47; </span>
            <span>{{ getFormattedTimeStr(this.audioDuration) }}</span>
        </div>
        <!-- 播放进度 -->
        <div class="play-progress">
            <div class="progress-box" @click="handleSetAudioProgress($event)">
                <el-progress ref="progress" :percentage="percentage" :showText="false" :stroke-width="12"></el-progress>
                <!-- <div ref="bg" class="bg">
                        <div ref="curProgress"></div>
                        <div ref="dot" class="dot"></div>
                    </div> -->
            </div>
        </div>
    </div>
    <div class="other-control-button-group">
        <span><i class="fa fa-volume-up"></i></span>
        <el-slider v-model="volume" @input="handleChangeVolume"></el-slider>
        <span><i class="fa fa-list" @click.stop="handleOpenPlaylist"></i></span>
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
    name: 'AudioPlayBar',
    mixins: [mixinOfSong],
    data() {
        return {
            currentTime: 0,
            duration: 0,
            percentage: 0,
            volume: 0,
            isVisible: true //设置是否显示播放条
        }
    },
    computed: {
        ...mapGetters(['audioId', 'audioPlayingIndex', 'cachedPlaylist', 'audioURL', 'audioPlayingState', 'audioTitle', 'audioPic', 'audioDuration', 'audioCurrentTime', 'audioDisplayPercentage', 'audioVolume', 'isShowSongPlaylist'])
    },
    mounted() {
        //设置播放器音量
        this.volume = this.audioVolume
        //如果播放列表中已有待播放音乐，则设置播放条封面
        if (this.cachedPlaylist.length) {
            this.$store.commit('SET_AUDIO_PIC', this.cachedPlaylist[0].pic)
        }
    },
    methods: {
        togglePlay() {
            //如果不是第一次播放，那么直接开始播放音频
            if (this.audioURL != '') {
                if (this.audioPlayingState) {
                    this.$store.commit('SET_IS_PLAYING', false)
                } else {
                    this.$store.commit('SET_IS_PLAYING', true)
                }
            } else if (this.cachedPlaylist.length) { //否则检查本地缓存中的播放列表，若不为空则播放第一首歌
                this.setAudioPlayer(this.cachedPlaylist[0])
            } else {
                this.$nextTick(() => this.$message('播放列表为空'))
            }
        },
        /**
         * 将秒数格式化为字符串
         * @param {Number} seconds 
         * @returns format as "00:00"
         */
        getFormattedTimeStr(seconds) {
            let str = ''
            seconds = parseInt(seconds)
            let minute = parseInt(seconds / 60)
            let second = seconds % 60
            minute = (minute >= 10 ? '' : '0') + minute + ':'
            second = (second >= 10 ? '' : '0') + second
            str = minute + second
            return str
        },
        handleSetAudioProgress($event) {
            if (this.audioURL.trim() !== '') {
                let target = $event.target
                if (target.className === 'el-progress-bar__inner') {
                    target = target.parentElement
                }
                let percentage = ($event.offsetX / target.offsetWidth).toFixed(2)
                this.$store.commit('SET_AUDIO_PLAYING_PERCENTAGE', Number(percentage))
                this.$store.commit('SET_AUDIO_DISPLAY_PERCENTAGE', percentage * 100 || 0)
            }
        },
        handleChangeVolume() {
            this.$store.commit('SET_AUDIO_VOLUME', this.volume)
        },
        handleOpenPlaylist() {
            this.$store.commit('SET_IS_SHOW_SONG_PLAYLIST', !this.isShowSongPlaylist)
        },
        handlePlayPrevSong() {
            let audioNum = this.cachedPlaylist.length
            if (audioNum) {
                let index = this.audioPlayingIndex
                // 如果未播放过，则播放第一首
                if (index === -1) {
                    index = 0
                } else {
                    index = (this.audioPlayingIndex - 1 + audioNum) % audioNum
                }
                this.$store.commit('SET_AUDIO_PLAYING_INDEX', index)
                //获取到需要播放的音乐在audioPlaylist中的索引后播放音乐
                let audio = this.cachedPlaylist[index]
                this.setAudioPlayer(audio)
            }
        },
        handlePlayNextSong() {
            let audioNum = this.cachedPlaylist.length
            if (audioNum) {
                let index = this.audioPlayingIndex
                // 如果未播放过，则播放第一首
                if (index === -1) {
                    index = 0
                } else {
                    index = (this.audioPlayingIndex + 1) % audioNum
                }
                this.$store.commit('SET_AUDIO_PLAYING_INDEX', index)
                //获取到需要播放的音乐在audioPlaylist中的索引后播放音乐
                let audio = this.cachedPlaylist[index]
                this.setAudioPlayer(audio)
            }
        }
    },
    watch: {
        audioDisplayPercentage() {
            this.percentage = this.audioDisplayPercentage
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';
@import '@/assets/scss/overlap_levels';
@import '@/assets/scss/position_attrs';

.play-bar-wrapper {
    min-width: 100vw;
    height: $play-bar-wrapper-bottom-position;
    box-sizing: border-box;
    box-shadow: 0 -2px 3px 0 $theme-color;
    background-color: white;
    position: fixed;
    @include z-index-lower;
    bottom: 0;
    display: flex;
    justify-content: center;
    transition: all 1s;

    .chevron-box {
        line-height: 32px;
        position: absolute;
        right: 48%;
        bottom: 75px;
        color: rgb(122, 172, 236);
        font-size: 2em;

        span:hover {
            cursor: pointer;
            vertical-align: middle;
        color: $theme-color;

        }
    }

    .play-control {
        width: 260px;
        height: 70px;
        margin: 0 10px 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 25px;

        .item {
            min-width: 50px;
            min-height: 50px;
            margin-right: 10px;
            text-align: center;
            line-height: 70px;
            color: rgb(92, 162, 219);

            i:hover {
                @include button-hover;
            }

            img {
                display: block;
                width: 50px;
                height: 50px;

                &::before {
                    background-color: #eeeeee;
                    border: #aaaaaa;
                    display: block;
                    height: 100%;
                    content: attr(alt);
                    text-align: center;
                }
            }
        }

        >div {
            &:nth-of-type(2) {
                font-size: 30px;
            }
        }
    }

    .play-info {
        min-width: 700px;
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        align-items: flex-start;

        .title-box {
            width: 50%;
            min-height: 25px;
        }

        .current-time {
            width: 40%;
            text-align: right;

            span:nth-of-type(2) {
                color: rgb(121, 119, 119);
            }
        }

        .play-progress {
            width: 100%;
            height: 30px;
            display: flex;
            align-items: flex-start;

            .progress-box {
                width: 100%;
                margin: auto 0;
                cursor: pointer;
            }
        }
    }

    .other-control-button-group {
        min-width: 160px;
        margin: 0 10px;
        display: flex;
        align-items: center;

        span {
            i {
                font-size: 25px;
                &:hover {
                    @include button-hover;
                    cursor: pointer;
                }
            }
            @include button-link;
        }

        .el-slider {
            margin: 0 20px 0 15px;
            width: 100%;
        }
    }
}

.play-bar-wrapper-display {
    bottom: -$play-bar-wrapper-bottom-position;
    background-color: $theme-color;
}
</style>
