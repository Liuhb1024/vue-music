<template>
<div class="home">
    <section class="section-wrapper" v-for="(item, index) in songInfo" :key="index">
        <div class="section-title">{{ item.name }}</div>
        <div class="text-more">
            <span @click="handleSeekForMoreResults(item.path)">更多&nbsp;&nbsp;<i class="fa fa-angle-right"></i></span>
        </div>
        <ContentList :infoList="item.list"></ContentList>
    </section>
</div>
</template>

<script>
import {
    queryAllSingers
} from '@/api/index'
import {
    queryAllSongLists
} from '@/api/index'

import ContentList from '@/components/common/ContentList.vue'

export default {
    name: 'Home',
    components: {
        ContentList
    },
    data() {
        return {
            songInfo: [{
                name: '歌单推荐',
                list: [],
                path: '/songlist'
            }, {
                name: '歌手推荐',
                list: [],
                path: '/singer'
            }]
        }
    },
    created() {
        this.getSongInfo()
    },
    methods: {
        getSongInfo() {
            queryAllSongLists().then(jsonData => {
                this.songInfo[0].list = jsonData.slice(0, 10)
            }).catch(err => console.log(err))
            queryAllSingers().then(jsonData => {
                this.songInfo[1].list = jsonData.slice(0, 10)
            }).catch(err => console.log(err))
        },
        handleSeekForMoreResults(path) {
            this.$router.push(path)
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.home {
    .section-wrapper {
        min-height: 300px;

        .section-title {
            font-size: 2em;
            text-align: center;

        }

        .text-more {
            text-align: right;

            span {
                display: inline-block;
                margin-right: 90px;
                &:hover {
                    @include button-hover;
                }
            }
        }
    }
}
</style>
