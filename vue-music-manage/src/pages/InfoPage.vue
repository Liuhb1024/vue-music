<template>
<div>
    <div class="news-section">
        <el-row :gutter="30">
            <el-col :sm="12" :md="12" :lg="12">
                <div class="news-container">
                    <div class="news-header">
                        <h3><i class="el-icon-bell"></i> 系统公告</h3>
                        <el-button type="text">更多</el-button>
                    </div>
                    <div class="news-list">
                        <div v-for="(notice, index) in systemNotices" :key="index" class="news-item">
                            <div class="news-content">
                                <el-tag size="small" :type="notice.type">{{ notice.tag }}</el-tag>
                                <span class="news-title">{{ notice.title }}</span>
                            </div>
                            <span class="news-time">{{ notice.time }}</span>
                        </div>
                    </div>
                </div>
            </el-col>

            <el-col :sm="12" :md="12" :lg="12">
                <div class="news-container">
                    <div class="news-header">
                        <h3><i class="el-icon-data-analysis"></i> 数据动态</h3>
                        <el-button type="text">更多</el-button>
                    </div>
                    <div class="news-list">
                        <div v-for="(activity, index) in recentActivities" :key="index" class="news-item">
                            <div class="news-content">
                                <el-tag size="small" :type="activity.type">{{ activity.tag }}</el-tag>
                                <span class="news-title">{{ activity.title }}</span>
                            </div>
                            <span class="news-time">{{ activity.time }}</span>
                        </div>
                    </div>
                </div>
            </el-col>
        </el-row>
    </div>

    <div class="overview">
        <el-row :gutter="10">
            <el-col :sm="6" :md="6" :lg="6">
                <div class="bg-purple">
                    <el-card>
                        <div class="grid-content">
                            <div><span>{{ consumerCount }}</span></div>
                            <div>用户总数</div>
                        </div>
                    </el-card>
                </div>
            </el-col>
            <el-col :sm="6" :md="6" :lg="6">
                <div class="bg-purple">
                    <el-card>
                        <div class="grid-content">
                            <div><span>{{ songCount }}</span></div>
                            <div>歌曲总数</div>
                        </div>
                    </el-card>
                </div>
            </el-col>
            <el-col :sm="6" :md="6" :lg="6">
                <div class="bg-purple">
                    <el-card>
                        <div class="grid-content">
                            <div><span>{{ singerCount }}</span></div>
                            <div>歌手总数</div>
                        </div>
                    </el-card>
                </div>
            </el-col>
            <el-col :sm="6" :md="6" :lg="6">
                <div class="bg-purple">
                    <el-card>
                        <div class="grid-content">
                            <div><span>{{ songListCount }}</span></div>
                            <div>歌单总数</div>
                        </div>
                    </el-card>
                </div>
            </el-col>
        </el-row>
    </div>

    <div class="stat-info">
        <el-row :gutter="30">
            <el-col :sm="12" :md="12" :lg="12">
                <div class="chart-container">
                    <h3>用户性别比例</h3>
                    <ve-pie :data="chartData" :settings="chartSettings" :extend="extend" height="300px"></ve-pie>
                </div>
            </el-col>
            <el-col :sm="12" :md="12" :lg="12">
                <div class="chart-container">
                    <h3>歌手性别比例</h3>
                    <ve-pie :data="singerChartData" :settings="chartSettings" :extend="extend" height="300px"></ve-pie>
                </div>
            </el-col>
        </el-row>
        <el-row :gutter="30" class="mt-20">
            <el-col :sm="12" :md="12" :lg="12">
                <div class="chart-container">
                    <h3>歌手类型分布</h3>
                    <ve-histogram :data="singerTypeData" :settings="histogramSettings" :extend="extend" height="300px"></ve-histogram>
                </div>
            </el-col>
            <el-col :sm="12" :md="12" :lg="12">
                <div class="chart-container">
                    <h3>歌单风格分布</h3>
                    <ve-bar :data="songListStyleData" :settings="barSettings" :extend="extend" height="300px"></ve-bar>
                </div>
            </el-col>
        </el-row>
    </div>
</div>
</template>

<script>
import {
    queryAllConsumers
} from '@/api/consumer'

import {
    queryAllSongs
} from '@/api/song'
import {
    queryAllSingers
} from '@/api/singer'
import {
    queryAllSongLists
} from '@/api/song-list'

export default {
    name: 'InfoPage',
    data() {
        return {
            consumers: [],
            singers: [],
            songCount: 0,
            consumerCount: 0,
            songListCount: 0,
            singerCount: 0,
            consumerRate: 0,
            chartData: {
                columns: ['性别', '总数'],
                rows: [{
                    '性别': '男',
                    '总数': 0
                }, {
                    '性别': '女',
                    '总数': 0
                }]
            },
            singerChartData: {
                columns: ['性别', '总数'],
                rows: [{
                    '性别': '男',
                    '总数': 0
                }, {
                    '性别': '女',
                    '总数': 0
                }, {
                    '性别': '组合',
                    '总数': 0
                }, {
                    '性别': '保密',
                    '总数': 0
                }]
            },
            chartSettings: {
                area: true
            },
            extend: {
                series: {
                    label: {
                        show: true,
                        position: "top"
                    }
                }
            },
            singerTypeData: {
                columns: ['类型', '数量'],
                rows: [
                    { '类型': '流行', '数量': 25 },
                    { '类型': '摇滚', '数量': 18 },
                    { '类型': '民谣', '数量': 15 },
                    { '类型': '电子', '数量': 12 }
                ]
            },
            songListStyleData: {
                columns: ['风格', '数量'],
                rows: [
                    { '风格': '华语', '数量': 30 },
                    { '风格': '欧美', '数量': 22 },
                    { '风格': '日韩', '数量': 16 },
                    { '风格': '轻音乐', '数量': 12 }
                ]
            },
            histogramSettings: {
                textStyle: { color: '#666' },
                itemStyle: {
                    borderRadius: 5,
                    color: function(params) {
                        const colors = ['#1890ff', '#52c41a', '#fa8c16', '#f5222d'];
                        return colors[params.dataIndex];
                    }
                }
            },
            barSettings: {
                textStyle: { color: '#666' },
                itemStyle: {
                    borderRadius: 5,
                    color: function(params) {
                        const colors = ['#722ed1', '#13c2c2', '#eb2f96', '#faad14'];
                        return colors[params.dataIndex];
                    }
                }
            },
            systemNotices: [
                {
                    title: '系统升级维护通知',
                    time: '2024-03-20',
                    type: 'warning',
                    tag: '维护'
                },
                {
                    title: '新功能上线：歌单智能推荐',
                    time: '2024-03-18',
                    type: 'success',
                    tag: '更新'
                },
                {
                    title: '音乐版权更新公告',
                    time: '2024-03-15',
                    type: 'info',
                    tag: '公告'
                }
            ],
            recentActivities: [
                {
                    title: '新增注册用户 8 人',
                    time: '今天 14:30',
                    type: 'success',
                    tag: '用户'
                },
                {
                    title: '新增音乐作品 56 首',
                    time: '今天 10:20',
                    type: 'primary',
                    tag: '音乐'
                },
                {
                    title: '歌单收藏量增长 23%',
                    time: '昨天 16:45',
                    type: 'warning',
                    tag: '数据'
                }
            ]
        }
    },
    created() {
        this.getConsumers()
        this.getSingers()
        this.getSongs()
        this.getPlaylists()
    },
    mounted() {
        this.updateGenderChart()
    },
    methods: {
        getConsumers() {
            queryAllConsumers().then(jsonData => {
                this.consumers = jsonData
                this.consumerCount = jsonData.length
                this.$store.commit('SET_CACHED_CONSUMERS', jsonData)
                this.updateGenderChart()
            }).catch(error => {
                console.error('获取用户数据失败:', error)
                this.$message.error('获取用户数据失败')
            })
        },
        getSingers() {
            queryAllSingers().then(jsonData => {
                this.singers = jsonData
                this.$store.commit('SET_CACHED_SINGERS', jsonData)
                this.singerCount = jsonData.length
                this.updateGenderChart()
            })
        },
        getSongs() {
            queryAllSongs().then(jsonData => {
                this.$store.commit('SET_CACHED_SONGS', jsonData)
                this.songCount = jsonData.length
            })
        },
        getPlaylists() {
            queryAllSongLists().then(jsonData => {
                this.$store.commit('SET_CACHED_PLAYLISTS', jsonData)
                this.songListCount = jsonData.length
            })
        },
        getCertainSexNumOfComsumer(sex) {
            let count = 0
            if (!this.consumers || !this.consumers.length) {
                return 0
            }
            for (let c of this.consumers) {
                if (Number(c.sex) === Number(sex)) {
                    count++;
                }
            }
            return count
        },
        updateGenderChart() {
            const maleCount = this.getCertainSexNumOfComsumer(1)
            const femaleCount = this.getCertainSexNumOfComsumer(0)
            
            this.chartData = {
                columns: ['性别', '总数'],
                rows: [
                    {
                        '性别': '男',
                        '总数': maleCount || 0
                    },
                    {
                        '性别': '女',
                        '总数': femaleCount || 0
                    }
                ]
            }

            this.singerChartData.rows[0]['总数'] = this.getCertainSexNumOfSinger(1)
            this.singerChartData.rows[1]['总数'] = this.getCertainSexNumOfSinger(0)
            this.singerChartData.rows[2]['总数'] = this.getCertainSexNumOfSinger(2)
            this.singerChartData.rows[3]['总数'] = this.getCertainSexNumOfSinger(3)
        },
        getCertainSexNumOfSinger(sex) {
            let count = 0
            for (let s of this.singers) {
                if (sex == s.sex) {
                    count++;
                }
            }
            return count
        },
    }
}
</script>

<style lang="scss" scoped>
.stat-info {
    margin-top: 20px;
    .chart-container {
        background: #fff;
        padding: 20px;
        border-radius: 4px;
        box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
        h3 {
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }
    }
}
.mt-20 {
    margin-top: 20px;
}
.overview {
    .bg-purple {
        margin-bottom: 10px;
        .el-card {
            transition: all 0.3s ease;
            &:hover {
                transform: translateY(-5px);
            }
        }
        &:nth-child(1) .el-card {
            background: linear-gradient(135deg, #1890ff, #36a3ff);
            .grid-content {
                color: white;
            }
        }
        &:nth-child(2) .el-card {
            background: linear-gradient(135deg, #52c41a, #73d13d);
            .grid-content {
                color: white;
            }
        }
        &:nth-child(3) .el-card {
            background: linear-gradient(135deg, #fa8c16, #ffa940);
            .grid-content {
                color: white;
            }
        }
        &:nth-child(4) .el-card {
            background: linear-gradient(135deg, #f5222d, #ff4d4f);
            .grid-content {
                color: white;
            }
        }
    }
}
.grid-content {
    border-radius: 4px;
    min-height: 100px;
    display: flex;
    flex-flow: wrap;
    justify-content: center;
    align-items: center;

    div:nth-of-type(1) {
        width: 100%;
        font-size: 40px;
        font-weight: bold;
    }
}
.bg-purple {
    background: #d3dce6;
}
.bg-purple-light {
    background: #e5e9f2;
}
.news-section {
    margin-bottom: 20px;
    
    .news-container {
        background: #fff;
        padding: 15px;
        border-radius: 8px;
        box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
        height: 100%;
        
        .news-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
            
            h3 {
                margin: 0;
                color: #303133;
                font-size: 16px;
                
                i {
                    color: #409EFF;
                    margin-right: 6px;
                }
            }
        }
        
        .news-list {
            .news-item {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 8px 0;
                border-bottom: 1px solid #ebeef5;
                
                &:last-child {
                    border-bottom: none;
                }
                
                .news-content {
                    display: flex;
                    align-items: center;
                    
                    .el-tag {
                        margin-right: 8px;
                        transform: scale(0.9);
                    }
                    
                    .news-title {
                        color: #606266;
                        font-size: 13px;
                        
                        &:hover {
                            color: #409EFF;
                            cursor: pointer;
                        }
                    }
                }
                
                .news-time {
                    color: #909399;
                    font-size: 12px;
                }
            }
        }
    }
}

@media screen and (max-width: 768px) {
    .news-section {
        .el-col {
            margin-bottom: 20px;
            
            &:last-child {
                margin-bottom: 0;
            }
        }
    }
}
</style>
