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
    max-width: 1200px; // 添加最大宽度限制
    ul {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;

        li {
            margin: 20px 50px;
            transition: all 0.3s;

            div {
                width: 100px;
                height: 100px;
            }

            >div:nth-of-type(1) {
                overflow: hidden;
                position: relative;
                cursor: pointer;
                border-radius: 8px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);

                &:hover::after {
                    content: "";
                    display: block;
                    position: absolute;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background-color: rgba(0, 0, 0, 0.4);
                    backdrop-filter: blur(2px);
                }

                img {
                    width: 100%;
                    height: 100%;
                    object-fit: cover;
                }
            }

            >div:nth-of-type(2) {
                margin-top: 8px;
                text-align: center;
                color: #ffffff;
                font-size: 13px;
                font-weight: 500;
                line-height: 1.4;
                max-width: 120px;
                margin-left: auto;
                margin-right: auto;
                
                background: linear-gradient(to bottom,
                    rgba(255, 255, 255, 1) 0%,
                    rgba(255, 255, 255, 0.9) 100%);
                -webkit-background-clip: text;
                background-clip: text;
                
                text-shadow: 0 1px 3px rgba(0, 0, 0, 0.6),
                            0 2px 6px rgba(0, 0, 0, 0.3);
                
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 2;
                -webkit-box-orient: vertical;
                word-break: break-word;

                transition: all 0.3s ease;
                &:hover {
                    transform: translateY(-1px);
                    text-shadow: 0 2px 5px rgba(0, 0, 0, 0.8);
                }
            }
        }
    }
}

@media screen and (max-width: 768px) {
    .content-wrapper ul li {
        margin: 20px;
        
        div {
            width: 90px;
            height: 90px;
        }

        >div:nth-of-type(2) {
            font-size: 12px;
            max-width: 100px;
        }
    }
}
</style>
