<template>
<div class="singer-wrapper">
    <!-- 左侧装饰面板 -->
    <div class="left-panel">
        <div class="music-decor">
            <span class="note">♪</span>
            <span class="note">♫</span>
            <span class="note">♩</span>
        </div>
    </div>

    <!-- 右侧装饰面板 -->
    <div class="right-panel">
        <div class="wave-bars">
            <div class="bar" v-for="n in 12" :key="n"></div>
        </div>
        <div class="circles">
            <div class="circle" v-for="n in 3" :key="n"></div>
        </div>
    </div>

    <!-- 主要内容区域 -->
    <div class="main-content">
        <div class="header-section">
            <h1>歌手列表</h1>
            <p>发现优秀的音乐人</p>
        </div>

        <div class="filter-section">
            <div class="tag-list">
                <div class="tag-group">
                    <ul>
                        <li :class="{active: item.type === gender}" 
                            v-for="(item, index) in genderTypeList" 
                            :key="index" 
                            @click="handleChangeGenderTag(item.type)">
                            <span>{{ item.gender }}</span>
                        </li>
                    </ul>
                </div>
                <div class="tag-group">
                    <ul>
                        <li :class="{active: item.location === location}" 
                            v-for="(item, index) in locationTypeList" 
                            :key="index" 
                            @click="handleChangeLocationTag(item.location)">
                            <span>{{ item.location }}</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="content-section">
            <ContentList :info-list="computedData"></ContentList>
        </div>

        <div class="pagination-box">
            <el-pagination 
                layout="total, prev, pager, next" 
                :total="filteredSearchResults.length" 
                @current-change="handleCurrentChange" 
                background>
            </el-pagination>
        </div>
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
            this.currentPage = 1
        },
        handleChangeLocationTag(location) {
            this.location = location
            this.currentPage = 1
        }
    },
    computed: {
        computedData() {
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
    width: 100%;
    min-height: 100vh;
    background: #2d3a4b;
    padding-bottom: 80px;
    position: relative;
    overflow-x: hidden;

    // 左侧装饰面板
    .left-panel {
        position: fixed;
        left: 0;
        top: 0;
        width: 450px;
        height: 100%;
        background: linear-gradient(to right,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );
        z-index: 0;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 40px;

        .music-decor {
            width: 100%;
            height: 100%;
            position: relative;

            &::before {
                content: '';
                position: absolute;
                left: 35%;
                top: 25%;
                width: 180px;
                height: 180px;
                border-radius: 50%;
                background: radial-gradient(circle at center, 
                    rgba(255, 255, 255, 0.3) 0%,
                    rgba(255, 255, 255, 0.2) 30%,
                    rgba(255, 255, 255, 0.15) 60%,
                    transparent 80%
                );
                border: 4px solid rgba(103, 195, 255, 0.5);
                box-shadow: 
                    0 0 40px rgba(103, 195, 255, 0.4),
                    inset 0 0 40px rgba(103, 195, 255, 0.4);
                transform: translateX(-50%);
                animation: rotate 20s linear infinite;
            }

            .note {
                position: absolute;
                font-size: 45px;
                color: rgba(103, 195, 255, 0.8);
                text-shadow: 
                    0 0 20px rgba(103, 195, 255, 0.6),
                    0 0 40px rgba(103, 195, 255, 0.4);
                animation: float 3s ease-in-out infinite;
                z-index: 2;

                &:nth-child(1) {
                    left: 20%;
                    top: 15%;
                    font-size: 50px;
                }

                &:nth-child(2) {
                    left: 70%;
                    top: 40%;
                    font-size: 55px;
                    animation-delay: 0.5s;
                }

                &:nth-child(3) {
                    left: 30%;
                    top: 65%;
                    font-size: 48px;
                    animation-delay: 1s;
                }
            }
        }
    }

    // 右侧装饰面板
    .right-panel {
        position: fixed;
        right: 0;
        top: 0;
        width: 450px;
        height: 100%;
        background: linear-gradient(to left,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );
        z-index: 0;

        .wave-bars {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            display: flex;
            gap: 8px;
            padding: 40px;

            .bar {
                width: 5px;
                height: 40px;
                background: linear-gradient(to bottom,
                    rgba(103, 195, 255, 1) 0%,
                    rgba(103, 195, 255, 0.4) 100%
                );
                border-radius: 4px;
                animation: waveBar 1.5s ease-in-out infinite;
                box-shadow: 
                    0 0 20px rgba(103, 195, 255, 0.4),
                    0 0 40px rgba(103, 195, 255, 0.2);

                @for $i from 1 through 12 {
                    &:nth-child(#{$i}) {
                        animation-delay: #{$i * 0.1}s;
                    }
                }
            }
        }
    }

    // 主要内容区域
    .main-content {
        padding: 0 450px 80px;
        margin: 0 auto;
        max-width: 1920px;
        position: relative;
        z-index: 1;

        .header-section {
            text-align: center;
            padding: 60px 0 40px;

            h1 {
                color: #ffffff;
                font-size: 42px;
                font-weight: 600;
                margin-bottom: 15px;
                text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
                animation: fadeInDown 0.8s ease-out;
            }

            p {
                color: rgba(255, 255, 255, 0.9);
                font-size: 18px;
                text-shadow: 0 1px 8px rgba(0, 0, 0, 0.2);
                animation: fadeInUp 0.8s ease-out 0.2s both;
            }
        }

        .filter-section {
            margin-bottom: 40px;
            
            .tag-list {
                background: rgba(255, 255, 255, 0.05);
                border-radius: 15px;
                padding: 20px;
                backdrop-filter: blur(10px);

                .tag-group {
                    margin-bottom: 15px;
                    
                    &:last-child {
                        margin-bottom: 0;
                    }

                    ul {
                        display: flex;
                        flex-wrap: wrap;
                        gap: 15px;

                        li {
                            display: inline-block;
                            padding: 8px 20px;
                            border-radius: 25px;
                            background: rgba(103, 195, 255, 0.1);
                            color: #ffffff;
                            font-size: 16px;
                            cursor: pointer;
                            transition: all 0.3s ease;
                            
                            &:hover {
                                background: rgba(103, 195, 255, 0.2);
                                transform: translateY(-2px);
                            }

                            &.active {
                                background: rgba(103, 195, 255, 0.8);
                                box-shadow: 0 5px 15px rgba(103, 195, 255, 0.3);
                            }
                        }
                    }
                }
            }
        }

        .pagination-box {
            text-align: center;
            margin-top: 40px;
            padding: 20px 0;

            :deep(.el-pagination) {
                .btn-prev,
                .btn-next,
                .el-pager li {
                    background-color: rgba(103, 195, 255, 0.1);
                    color: #ffffff;
                    border: none;
                    
                    &:hover {
                        background-color: rgba(103, 195, 255, 0.2);
                    }
                    
                    &.active {
                        background-color: rgba(103, 195, 255, 0.8);
                    }
                }
            }
        }
    }
}

// 动画
@keyframes fadeInDown {
    from {
        opacity: 0;
        transform: translateY(-20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

@keyframes fadeInUp {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

// 响应式布局
@media screen and (max-width: 1800px) {
    .singer-wrapper {
        .left-panel, .right-panel {
            width: 350px;
        }
        .main-content {
            padding: 0 350px;
        }
    }
}

@media screen and (max-width: 1400px) {
    .singer-wrapper {
        .left-panel, .right-panel {
            width: 250px;
        }
        .main-content {
            padding: 0 250px;
        }
    }
}

@media screen and (max-width: 1200px) {
    .singer-wrapper {
        .left-panel, .right-panel {
            display: none;
        }
        .main-content {
            padding: 0 20px;
        }
    }
}
</style>
