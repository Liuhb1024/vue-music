<template>
<div class="search-wrapper">
    <div class="search-nav" ref="searchNav">
        <ul>
            <li @click="handleChangeView('Song')">
                <span :class="{'active': currentViewName ==='SongSearch'}">歌曲</span>
            </li>
            <li @click="handleChangeView('SongList')">
                <span :class="{'active': currentViewName ==='SongListSearch'}">歌单</span>
            </li>
        </ul>
    </div>
    <div class="search-results-box">
        <component :is="currentViewName"></component>
    </div>
</div>
</template>

<script>
import SongSearch from '@/components/search/SongSearch.vue'
import SongListSearch from '@/components/search/SongListSearch.vue'

export default {
    name: 'Search',
    components: {
        SongSearch,
        SongListSearch
    },
    data() {
        return {
            currentViewName: 'SongSearch'
        }
    },
    methods: {
        handleChangeView(viewName) {
            this.currentViewName = viewName + 'Search'
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.search-wrapper {
    margin: 20px auto;

    .search-nav {
        width: 100%;
        height: 60px;
        // background-color: lightblue;

        ul {
            display: flex;
            justify-content: space-between;

            li {
                width: 50%;
                @include normal-border-style(2px, solid, gray);
                font-size: 2em;
                text-align: center;
                cursor: pointer;
            }
        }
    }

    .search-results-box {
        width: 100%;
        min-height: 700px;
    }
}
</style>
