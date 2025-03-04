<template>
<div class="home-page">
    <div class="page-header">
        <div class="wave-animation">
            <div class="wave wave1"></div>
            <div class="wave wave2"></div>
            <div class="wave wave3"></div>
        </div>
        <div class="welcome-text">
            <h1>探索音乐的世界</h1>
            <p>发现、聆听、分享</p>
        </div>
    </div>
    
    <div class="home-container">
        <section class="section-wrapper" v-for="(item, index) in songInfo" :key="index">
            <div class="section-header">
                <div class="section-title">
                    <i :class="getIconClass(item.name)"></i>
                    <h2>{{ item.name }}</h2>
                </div>
                <div class="text-more">
                    <el-button type="primary" size="small" round @click="handleSeekForMoreResults(item.path)">
                        查看更多 <i class="el-icon-arrow-right"></i>
                    </el-button>
                </div>
            </div>
            <div class="content-wrapper">
                <ContentList :infoList="item.list"></ContentList>
            </div>
        </section>
    </div>
    
    <BackToTop />

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
import BackToTop from '@/components/common/BackToTop.vue'

export default {
    name: 'Home',
    components: {
        ContentList,
        BackToTop
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
        },
        getIconClass(name) {
            return name.includes('歌单') ? 'el-icon-headset' : 'el-icon-user'
        }
    }
}
</script>

<style lang="scss" scoped>
@import '@/assets/scss/effects';

.home-page {
    width: 100%;
    min-height: 100vh;
    background: #2d3a4b;
    padding-bottom: 80px;
    position: relative;
    overflow-x: hidden;

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

        // 音乐元素装饰
        .music-decor {
            width: 100%;
            height: 100%;
            position: relative;

            // CD 唱片效果
            &::before {
                content: '';
                position: absolute;
                left: 35%;
                top: 25%;
                width: 180px;
                height: 180px;
                border-radius: 50%;
                background: 
                    radial-gradient(circle at center, 
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

            // 音符动画
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
        height: 100%;
        background: linear-gradient(to left,
            rgba(32, 40, 51, 1) 0%,
            rgba(32, 40, 51, 1) 15%,
            rgba(45, 58, 75, 0.95) 30%,
            rgba(45, 58, 75, 0.6) 60%,
            rgba(45, 58, 75, 0) 100%
        );
        z-index: 0;

        // 波形动画
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

        // 圆环装饰
        .circles {
            .circle {
                border: 3px solid rgba(103, 195, 255, 0.5);
                box-shadow: 
                    0 0 40px rgba(103, 195, 255, 0.4),
                    inset 0 0 40px rgba(103, 195, 255, 0.4);
                background: linear-gradient(135deg,
                    rgba(103, 195, 255, 0.15) 0%,
                    rgba(64, 158, 255, 0.05) 100%
                );
            }
        }
    }

    .main-content {
        min-height: 100vh;
        padding: 0 450px 80px;
        position: relative;
        z-index: 1;
    }

    .page-header {
        position: relative;
        height: 280px;
        width: 100%;
        overflow: hidden;
        
        .wave-animation {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0;
            left: 0;
            
            .wave {
                position: absolute;
                width: 200%;
                height: 100%;
                background-repeat: repeat-x;
                background-position: 0 bottom;
                transform-origin: center bottom;

                &.wave1 {
                    bottom: 0;
                    opacity: 0.5;
                    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="%23409EFF" fill-opacity="0.35" d="M0,160L48,144C96,128,192,96,288,106.7C384,117,480,171,576,186.7C672,203,768,181,864,170.7C960,160,1056,160,1152,138.7C1248,117,1344,75,1392,53.3L1440,32L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>');
                    animation: wave 18s linear infinite;
                }
                
                &.wave2 {
                    bottom: -10px;
                    opacity: 0.3;
                    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="%2367C3FF" fill-opacity="0.35" d="M0,64L48,80C96,96,192,128,288,128C384,128,480,96,576,90.7C672,85,768,107,864,117.3C960,128,1056,128,1152,122.7C1248,117,1344,107,1392,101.3L1440,96L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>');
                    animation: wave 15s linear infinite;
                }
                
                &.wave3 {
                    bottom: -20px;
                    opacity: 0.2;
                    background-image: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1440 320"><path fill="%23FFFFFF" fill-opacity="0.35" d="M0,192L48,197.3C96,203,192,213,288,192C384,171,480,117,576,101.3C672,85,768,107,864,128C960,149,1056,171,1152,165.3C1248,160,1344,128,1392,112L1440,96L1440,320L1392,320C1344,320,1248,320,1152,320C1056,320,960,320,864,320C768,320,672,320,576,320C480,320,384,320,288,320C192,320,96,320,48,320L0,320Z"></path></svg>');
                    animation: wave 12s linear infinite;
                }
            }
        }

        .welcome-text {
            position: relative;
            z-index: 10;
            text-align: center;
            padding-top: 80px;
            
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
    }
    
    .home-container {
        padding: 0 450px;
        margin: 0 auto;
        max-width: 1920px;
        position: relative;
        z-index: 5;
        box-sizing: border-box;
        
        .section-wrapper {
            width: 100%;
            background: rgba(255, 255, 255, 0.03);
            border-radius: 12px;
            backdrop-filter: blur(10px);
            margin: 20px 0;
            padding: 20px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
            transition: all 0.4s ease;
            border: 1px solid rgba(255, 255, 255, 0.1);
            box-sizing: border-box;
            
            &:hover {
                transform: translateY(-8px);
                box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
                background: rgba(255, 255, 255, 0.08);
            }
            
            .section-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 25px;
                border-bottom: 1px solid rgba(255, 255, 255, 0.1);
                padding-bottom: 15px;
                
                .section-title {
                    display: flex;
                    align-items: center;
                    
                    i {
                        font-size: 28px;
                        color: #67c3ff;
                        margin-right: 12px;
                        background: rgba(64, 158, 255, 0.1);
                        width: 50px;
                        height: 50px;
                        border-radius: 50%;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        box-shadow: 0 0 15px rgba(103, 195, 255, 0.3);
                    }
                    
                    h2 {
                        font-size: 22px;
                        color: #ffffff;
                        margin: 0;
                        font-weight: 600;
                        letter-spacing: 1px;
                        text-shadow: 0 2px 4px rgba(0, 0, 0, 0.4);
                    }
                }
                
                .text-more {
                    .el-button {
                        border-radius: 20px;
                        padding: 10px 20px;
                        font-weight: 500;
                        letter-spacing: 0.5px;
                        box-shadow: 0 3px 10px rgba(64, 158, 255, 0.3);
                        
                        &:hover {
                            transform: translateX(5px);
                            box-shadow: 0 5px 15px rgba(64, 158, 255, 0.5);
                        }
                        
                        i {
                            margin-left: 5px;
                            font-weight: bold;
                            transition: transform 0.3s;
                        }
                        
                        &:hover i {
                            transform: translateX(3px);
                        }
                    }
                }
            }
            
            .content-wrapper {
                min-height: 280px;
                width: 100%;
                overflow: hidden;
                
                :deep(.el-card) {
                    background: rgba(255, 255, 255, 0.15);
                    border: none;
                    backdrop-filter: blur(5px);
                    
                    &:hover {
                        background: rgba(255, 255, 255, 0.25);
                    }
                    
                    .el-card__body {
                        color: #fff;
                    }
                }
                
                :deep(.el-row) {
                    width: 100%;
                    margin: 0 !important;
                }
                
                :deep(.el-col) {
                    padding: 10px;
                }
            }
        }
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

@keyframes wave {
    0% {
        transform: translateX(0) scaleY(1);
    }
    50% {
        transform: translateX(-25%) scaleY(1.1);
    }
    100% {
        transform: translateX(-50%) scaleY(1);
    }
}

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

// 媒体查询部分保持不变，但增加一些针对大屏幕的优化

// 大屏幕设备优化
@media screen and (min-width: 1600px) {
    .home-page {
        .home-container {
            padding: 0 5%;
            
            .section-wrapper {
                .content-wrapper {
                    :deep(.el-col-md-2) {
                        width: 16.66667%;
                    }
                }
            }
        }
    }
}

// 超大屏幕
@media screen and (min-width: 2000px) {
    .home-page {
        .home-container {
            padding: 0 8%;
        }
    }
}

// 响应式布局
@media screen and (max-width: 1800px) {
    .home-page {
        .left-panel, .right-panel {
            width: 350px;
        }
        .home-container {
            padding: 0 350px;
        }
    }
}

@media screen and (max-width: 1600px) {
    .home-page {
        .left-panel, .right-panel {
            width: 300px;
        }
        .home-container {
            padding: 0 300px;
        }
    }
}

@media screen and (max-width: 1400px) {
    .home-page {
        .left-panel, .right-panel {
            width: 250px;
        }
        .home-container {
            padding: 0 250px;
        }
    }
}

@media screen and (max-width: 1200px) {
    .home-page {
        .left-panel, .right-panel {
            display: none;
        }
        .home-container {
            padding: 0 20px;
        }
    }
}
</style>
