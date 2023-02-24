<template>
<div class="song-list-wrapper">
    <div class="tag-list">
        <ul>
            <li :class="{active: item.name === style}" v-for="(item,index) in songStyleList" :index="index" @click="handleChangeStyle(item.name)">
                <span>{{ item.name }}</span>
            </li>
        </ul>
    </div>
    <ContentList :info-list="computedData"></ContentList>
    <div class="pagination-box">
        <el-pagination layout="total, prev, pager, next" :total="filteredSearchResults.length" @current-change="handleCurrentChange" background></el-pagination>
    </div>
</div>
</template>

<script>
import {
    queryAllSongLists
} from '@/api/index'

import {
    songStyleList
} from '@/assets/js/data/song-style'

import ContentList from '@/components/common/ContentList.vue'

export default {
    name: 'SongList',
    components: {
        ContentList
    },
    data() {
        return {
            searchResults: [],
            filteredSearchResults: [],
            style: '全部',
            pageSize: 10,
            currentPage: 1,
            songStyleList: []
        }
    },
    mounted() {
        //获取到所有歌单数据并用作歌单缓存
        queryAllSongLists().then(jsonData => this.searchResults = jsonData).catch(err => console.log(err))
        //设置需要用于查询的歌单风格数据
        this.songStyleList = songStyleList
    },
    methods: {
        handleCurrentChange(currentPage) {
            this.currentPage = currentPage
        },
        handleChangeStyle(style) {
            this.style = style
        }
    },
    computed: {
        computedData() {
            if (this.style === '全部') {
                this.filteredSearchResults = this.searchResults
            } else {
                this.filteredSearchResults = this.searchResults.filter(songlist => songlist.style === this.style)
            }
            return this.filteredSearchResults.slice(this.pageSize * (this.currentPage - 1), this.pageSize * this.currentPage)
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.song-list-wrapper {
    .tag-list {
        padding-top: 30px;
        padding-left: 20px;

        ul {
            li {
                display: inline-block;
                margin-right: 10px;
                font-size: 2em;
                
                &:hover {
                    @include button-hover;
                }
            }
        }
    }

    .pagination-box {
        text-align: center;
    }
}
</style>
