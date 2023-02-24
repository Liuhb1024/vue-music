<template>
<div class="content-wrapper">
    <ul>
        <li v-for="(item,index) in infoList" :key="index">
            <div @click="handleGoToDetail(item)">
                <!-- <span>{{ item }}</span> -->
                <img v-if="item.pic" :src="getCoverImgUrl(item.pic)" />
                <div v-if="!item.pic" v-loading="!item.pic" element-loading-text="拼命加载中"></div>
            </div>
            <div>{{ item.name || item.title }}</div>
        </li>
    </ul>
</div>
</template>

<script>
import {
    mixin
} from '@/mixins/index'

import {
    mixinOfSong
} from '@/mixins/song'

export default {
    name: 'ContentList',
    mixins: [mixin, mixinOfSong],
    data() {
        return {

        }
    },
    props: ['infoList'],
    methods: {
        handleGoToDetail(item) {
            this.$store.commit('SET_TEMP_ITEM', item)
            //如果item的name属性为空，说明item是歌手对象，否则是歌单对象
            if (item.name) {
                this.$router.push({
                    path: '/singer-album',
                    query: {
                        singerId: item.id
                    }
                })
            } else {
                this.$router.push({
                    path: '/song-list-album',
                    query: {
                        songListId: item.id
                    }
                })
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/overlap_levels';

.content-wrapper {
    margin: auto;

    ul {
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;

        li {
            margin: 40px 45px;
            transition: all 1s;

            div {
                width: 130px;
                height: 130px;
            }

            >div:nth-of-type(1) {
                // border-radius: 50%;
                overflow: hidden;
                position: relative;
                @include z-index-bottom;
                cursor: pointer;

                &:hover::after {
                    content: "";
                    display: block;
                    position: absolute;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background-color: rgba(56, 54, 54, 0.664);
                }

                img {
                    width: 100%;
                    height: 100%;

                    &:hover {
                        cursor: pointer;
                    }
                }
            }

            >div:nth-of-type(2) {
                text-align: center;
            }
        }
    }
}
</style>
