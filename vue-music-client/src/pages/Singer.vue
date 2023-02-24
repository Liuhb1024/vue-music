<template>
<div class="singer-wrapper">
    <div class="tag-list">
        <ul>
            <li :class="{active: item.type === gender}" v-for="(item, index) in genderTypeList" :index="index" @click="handleChangeGenderTag(item.type)">
                <span>{{ item.gender }}</span>
            </li>
        </ul>
        <ul>
            <li :class="{active: item.location === location}" v-for="(item, index) in locationTypeList" :index="index" @click="handleChangeLocationTag(item.location)">
                <span>{{ item.location }}</span>
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
    queryAllSingers
} from '@/api'

import {
    singerType
} from '@/assets/js/data/singer'

import ContentList from '@/components/common/ContentList.vue'

export default {
    name: 'Singer',
    components: {
        ContentList
    },
    data() {
        return {
            searchResults: [],
            filteredSearchResults: [],
            gender: 3,
            location: '全部',
            genderTypeList: [],
            locationTypeList: [],
            pageSize: 10,
            currentPage: 1
        }
    },
    mounted() {
        queryAllSingers().then(jsonData => this.searchResults = jsonData).catch(err => console.log(err))
        this.genderTypeList = singerType.genderTypeList
        this.locationTypeList = singerType.locationTypeList
    },
    methods: {
        handleCurrentChange(currentPage) {
            this.currentPage = currentPage
        },
        handleChangeGenderTag(gender) {
            this.gender = gender
        },
        handleChangeLocationTag(location) {
            this.location = location
        }
    },
    computed: {
        computedData() {
            this.currentPage = 1 // 需要将页码调整为1
            //根据地区进行筛选
            if (this.location === '全部') {
                this.filteredSearchResults = this.searchResults
            } else if (this.location === '其他') {
                this.filteredSearchResults = this.searchResults.filter(singer => {
                    let reg = new RegExp('中国.*|台湾|香港|日本|韩国|美国|英国', 'g')
                    return !reg.test(singer.location)
                })
            } else {
                this.filteredSearchResults = this.searchResults.filter(singer => singer.location.includes(this.location))
            }
            //根据性别进行筛选，如果不是全部（gender等于3）则进行筛选
            if (this.gender !== 3) {
                this.filteredSearchResults = this.filteredSearchResults.filter(singer => singer.sex === this.gender)
            }
            return this.filteredSearchResults.slice(this.pageSize * (this.currentPage - 1), this.pageSize * this.currentPage)
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.singer-wrapper {

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
