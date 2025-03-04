<template>
<div class="song-list-wrapper">
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
            <h1>精选歌单</h1>
            <p>发现你的音乐品味</p>
        </div>

        <div class="filter-section">
            <div class="tag-list">
                <ul>
                    <li :class="{active: item.name === style}" 
                        v-for="(item,index) in songStyleList" 
                        :key="index" 
                        @click="handleChangeStyle(item.name)">
                        <span>{{ item.name }}</span>
                    </li>
                </ul>
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
    width: 100%;
    min-height: 100vh;
    background: #2d3a4b;
    padding-bottom: 80px;
    position: relative;
    overflow-x: hidden;

    .left-panel, .right-panel {
        height: 100%;
        z-index: 0;
    }

    .left-panel {
        position: fixed;
        left: 0;
        top: 0;
        width: 450px;
        background: linear-gradient(to right,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );
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

    .right-panel {
        position: fixed;
        right: 0;
        top: 0;
        width: 450px;
        background: linear-gradient(to left,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );

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

        .circles {
            position: absolute;
            bottom: 20%;
            left: 50%;
            transform: translateX(-50%);
            display: flex;
            gap: 20px;

            .circle {
                width: 40px;
                height: 40px;
                border-radius: 50%;
                border: 3px solid rgba(103, 195, 255, 0.5);
                box-shadow: 
                    0 0 40px rgba(103, 195, 255, 0.4),
                    inset 0 0 40px rgba(103, 195, 255, 0.4);
                background: linear-gradient(135deg,
                    rgba(103, 195, 255, 0.15) 0%,
                    rgba(64, 158, 255, 0.05) 100%
                );
                animation: pulse 2s ease-in-out infinite;

                &:nth-child(2) {
                    animation-delay: 0.4s;
                }

                &:nth-child(3) {
                    animation-delay: 0.8s;
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
                            box-shadow: 0 5px 15px rgba(103, 195, 255, 0.2);
                        }

                        &.active {
                            background: rgba(103, 195, 255, 0.8);
                            box-shadow: 0 5px 15px rgba(103, 195, 255, 0.3);
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
                    transition: all 0.3s ease;
                    
                    &:hover {
                        background-color: rgba(103, 195, 255, 0.2);
                        transform: translateY(-2px);
                    }
                    
                    &.active {
                        background-color: rgba(103, 195, 255, 0.8);
                        box-shadow: 0 5px 15px rgba(103, 195, 255, 0.3);
                    }
                }
            }
        }
    }
}

// 动画效果
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

@keyframes rotate {
    from {
        transform: translateX(-50%) rotate(0deg);
    }
    to {
        transform: translateX(-50%) rotate(360deg);
    }
}

@keyframes float {
    0%, 100% {
        transform: translateY(0) rotate(-5deg);
        opacity: 0.8;
    }
    50% {
        transform: translateY(-20px) rotate(5deg);
        opacity: 1;
    }
}

@keyframes waveBar {
    0%, 100% {
        height: 40px;
        opacity: 0.8;
    }
    50% {
        height: 60px;
        opacity: 1;
    }
}

@keyframes pulse {
    0%, 100% {
        transform: scale(1);
        opacity: 0.3;
    }
    50% {
        transform: scale(1.1);
        opacity: 0.6;
    }
}

// 响应式布局保持与 Singer 页面一致
@media screen and (max-width: 1800px) {
    .song-list-wrapper {
        .left-panel, .right-panel {
            width: 350px;
        }
        .main-content {
            padding: 0 350px;
        }
    }
}

@media screen and (max-width: 1400px) {
    .song-list-wrapper {
        .left-panel, .right-panel {
            width: 250px;
        }
        .main-content {
            padding: 0 250px;
        }
    }
}

@media screen and (max-width: 1200px) {
    .song-list-wrapper {
        .left-panel, .right-panel {
            display: none;
        }
        .main-content {
            padding: 0 20px;
        }
    }
}
</style>
