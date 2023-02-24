<template>
<div>
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
        <el-row :gutter="10">
            <el-col :sm="12" :md="12" :lg="12">
                <h3>用户性别比例</h3>
            </el-col>
            <el-col :sm="12" :md="12" :lg="12">
                Placeholder
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
            chartSettings: {
                area: true
            },
            extend: {
                series: {
                    // 图形上方显示数值
                    label: {
                        show: true,
                        position: "top"
                    }
                }
            },
        }
    },
    created() {
        this.getConsumers()
        this.getSingers()
        this.getSongs()
        this.getPlaylists()
    },
    mounted() {

    },
    methods: {
        getConsumers() {
            queryAllConsumers().then(jsonData => {
                this.consumers = jsonData
                this.consumerCount = jsonData.length
                this.$store.commit('SET_CACHED_CONSUMERS', jsonData)
            })
        },
        getSingers() {
            queryAllSingers().then(jsonData => {
                this.$store.commit('SET_CACHED_SINGERS', jsonData)
                this.singerCount = jsonData.length
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
            for (let c of this.consumers) {
                if (sex == c.sex) {
                    count++;
                }
            }
            return count
        }
    }
}
</script>

<style lang="scss" scoped>
.grid-content {
    border-radius: 4px;
    min-height: 100px;
    display: flex;
    flex-flow: wrap;
    justify-content: center;
    align-items: center;
    color: darkgray;

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
</style>
