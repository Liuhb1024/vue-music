<template>
<div class="songlist-search-wrapper">
    歌单搜索结果
    <!-- <ul>
        <li>
            <div class="song-list-item">
                <span class="item-index">序号</span>
                <span class="item-title">歌单</span>
                <span class="item-intro">简介</span>
                <span class="item-style">风格</span>
            </div>
        </li>

        <li v-for="(item, index) in searchResults" :key="index">
            <div class="song-list-item">
                <span class="item-index">{{ index+1 }}</span>
                <span class="item-title">{{ item.title }}</span>
                <span class="item-name"> {{ item.name}}</span>
                <span class="item-intro">{{ item.introduction }}</span>
            </div>
        </li>
    </ul> -->
    <ContentList :info-list="searchResults"></ContentList>
</div>
</template>

<script>
import {
    querySongListsByTitle
} from '@/api/songlist'

import ContentList from '../common/ContentList.vue'

export default {
    name: 'SongListSearch',
    components:{
        ContentList
    },
    data() {
        return {
            searchResults: []
        }
    },
    methods: {

    },
    mounted() {
        querySongListsByTitle(this.$route.query.kw).then(jsonData => {
            this.searchResults = jsonData
        }).catch(err => console.log(err))
    }
}
</script>

<style lang="scss" scoped>
.songlist-search-wrapper {
    height: 100%;
}
</style>
