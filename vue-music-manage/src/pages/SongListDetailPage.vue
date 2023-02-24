<template>
<div>
    <div class="handle-box">
        <div>
            <el-button type="primary" @click="addDialogFormVisible = true">添加歌曲信息</el-button>
        </div>
        <div>
            <h2>歌单-歌曲管理</h2>
        </div>
        <div>
            <el-input v-model="select_word" size="medium" class="handle-input" placeholder="输入歌曲名以查询"></el-input>
        </div>
    </div>

    <!-- 歌曲信息展示表格 -->
    <div class="table-container">
        <el-table :data="computedTableData" style="width: 100%" max-height="100%" :header-cell-style="{textAlign: 'center'}" size="medium" stripe border>
            <el-table-column label="序号" width="50" type="index" fixed>
                <template slot-scope="scope">
                    {{(currentPage-1)*pageSize+scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column prop="name" label="歌曲名" width="150" fixed></el-table-column>
            <el-table-column prop="introduction" label="专辑" width="100" fixed></el-table-column>
            <el-table-column prop="pic" label="歌曲封面" width="160">
                <template slot-scope="scope">
                    <div class="song-img-box">
                        <div class="audio-play-button-box" @click="setSongToPlay(scope.row.id, scope.row.url, scope.row.visited)">
                            <div v-if="scope.row.visited === true">
                                <i class="fa fa-pause-circle-o"></i>
                            </div>
                            <div v-if="scope.row.visited === false">
                                <i class="fa fa-play-circle"></i>
                            </div>
                        </div>
                        <div>
                            <img :src="getAbsoluteUrlOfStaticResourcesThroughCurrentUrl(scope.row.pic)" alt="歌曲形象">
                        </div>
                        <div>
                            <el-upload name="song-img-file" :action="getUploadUrl(scope.row.id)" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                                <el-button type="primary" size="mini">更新图片</el-button>
                            </el-upload>
                        </div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="lyric" label="歌词">
                <template scope="scope">
                    <p style="height:90px">{{scope.row.lyric}}</p>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="100" align="center">
                <template slot-scope="scope">
                    {{getFormattedDate(scope.row.createTime)}}
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="最后一次更新时间" width="100" align="center">
                <template v-slot:default="scope">
                    {{getFormattedDate(scope.row.updateTime)}}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="160">
                <template v-slot:default="scope">
                    <div class="song-file-box">
                        <el-button type="text" size="small" @click="handleOpenDelAffiliationDialog(scope.row.id)">删除</el-button>
                    </div>
                </template>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-size="pageSize" layout="total, prev, pager, next, jumper" :total="tableData.length" background>
            </el-pagination>
        </div>
    </div>

    <!-- 添加歌曲信息 -->
    <div class="container">
        <el-dialog title="添加歌曲" :visible.sync="addDialogFormVisible" width="400px">
            <el-form :model="form" ref="form" :rules="rules" id="form">
                <el-form-item label="歌单名" prop="songListTitle" :label-width="formLabelWidth">
                    <el-input v-model="form.songListTitle" name="songListTitle" placeholder="请输入歌单名" disabled></el-input>
                </el-form-item>
                <el-form-item label="歌曲名" prop="songName" :label-width="formLabelWidth">
                    <el-select @change="handleSelectChange" v-model="form.songName" placeholder="请选择" filterable clearable>
                        <el-option v-for="item in cachedSongs" :key="item.id" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddAffiliation">添 加</el-button>
            </div>
        </el-dialog>
    </div>

    <!-- 删除歌曲信息 -->
    <div class="container">
        <el-dialog title="删除歌曲信息" :visible.sync="delDialogVisible" width="300px">
            <span>删除数据后不可恢复，是否继续？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="delDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleDelAffiliation">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</div>
</template>

<script>
import {
    mapGetters
} from 'vuex'

import {
    mixin
} from '@/mixins'

import {
    querySongsBySongListId
} from '@/api/song/index'

import {
    addAffiliation,
    removeAffiliation
} from '@/api/affiliation'

export default {
    name: 'SongPage',
    data() {
        return {
            addDialogFormVisible: false, //设置添加歌曲对话框是否显示
            delDialogVisible: false, //设置删除歌曲对话框是否显示

            //添加框表单
            form: {
                id: -1, // 避免后端应用@RequestBody注解时因获取不到id报错
                songId: -1,
                songName: '',
                songListId: -1,
                songListTitle: ''
            },
            //编写表单验证规则
            rules: {
                songName: [{
                    required: true,
                    message: '请输入歌曲名',
                    trigger: 'blur'
                }]
            },
            formLabelWidth: '70px', //表单Label宽度
            select_word: '', //存放在搜索框中需要查询的歌曲名
            tableData: [], //要显示到页面上的歌曲信息数据
            tempTableData: [], //歌曲信息缓存数据
            pagerCount: 7, //显示的页码数量
            pageSize: 5, // 分页大小
            currentPage: 1, //当前页
            delSongId: -1, //要删除的歌曲id
            lastVisitedSongId: -1, //上次播放音频的id
        }
    },
    methods: {
        handleClose(done) {
            /* this.$confirm('确认关闭？')
                .then(_ => {
                    done()
                })
                .catch(_ => {}) */
        },
        querySongForSongId(songName) {
            //当歌曲名输入框的内容发生变化时，搜索名称相似的歌曲
        },
        /**
         * 获取歌曲信息数据
         */
        getTableData() {
            this.tableData = []
            this.tempTableData = []
            querySongsBySongListId(this.$route.query.songListId).then(jsonData => {
                let songList = jsonData
                for (let song of songList) {
                    song.visited = false
                }
                this.tempTableData = songList
                this.tableData = songList
            }).catch(err => console.log(err))
        },
        /**
         * 添加歌曲信息
         */
        handleAddAffiliation() {
            const {
                songName,
                songListTitle,
                ...formData
            } = this.form
            addAffiliation(formData).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: jsonData.msg,
                        type: 'success',
                        duration: 2000
                    })
                    this.getTableData()
                } else {
                    this.$message({
                        message: jsonData.msg,
                        type: 'error',
                        duration: 2000
                    })
                }
            }).catch(err => console.log(err))
            this.addDialogFormVisible = false
        },
        handleSelectChange(songId) {
            this.form.songId = songId
        },
        /**
         * 获取要更新的歌曲图片地址
         * @param {Number} singerId 
         * @returns url
         */
        getUploadUrl(songId) {
            return `${this.$store.state.HOST}/song/updatesongimg?songId=${songId}`
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        /**
         * 更新分页插件的当前页编号
         * @param {Number} currentPage 
         */
        handleCurrentChange(currentPage) {
            this.currentPage = currentPage
        },
        handleOpenDelAffiliationDialog(songId) {
            this.delSongId = songId
            this.delDialogVisible = true
        },
        /**
         * 删除歌曲
         */
        handleDelAffiliation() {
            let data = {
                id: -1,
                songId: this.delSongId,
                songListId: Number(this.$route.query.songListId)
            }
            removeAffiliation(data).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: jsonData.msg,
                        type: 'success',
                        duration: 2000
                    })
                    this.delDialogVisible = false
                    this.getTableData()
                } else {
                    this.$message({
                        message: jsonData.msg,
                        type: 'error',
                        duration: 2000
                    })
                }
            }).catch(err => console.log(err))
        },
        /**
         * 
         * @param {Number} audioId 歌曲id
         * @param {Strign} audioUrl 歌曲相对地址
         */
        setSongToPlay(audioId, audioUrl) {
            //获取上次播放的歌曲
            let lastSong = null;
            for (let i = 0; i < this.tableData.length; i++) {
                if (this.tableData[i].id === this.lastVisitedSongId) {
                    lastSong = this.tableData[i]
                    break;
                }
            }
            if (audioId !== this.lastVisitedSongId || this.lastVisitedSongId === -1) {
                let curSong = null
                for (let i = 0; i < this.tableData.length; i++) {
                    if (this.tableData[i].id === audioId) {
                        curSong = this.tableData[i]
                        break;
                    }
                }
                //如果这不是首次播放，则直接commit新的歌曲地址
                if (lastSong) {
                    lastSong.visited = false
                    this.$store.commit('SET_AUDIO_URL', audioUrl)
                } else {
                    this.$store.dispatch('setAudioState', audioUrl)
                }
                curSong.visited = true
            } else {
                lastSong.visited = !lastSong.visited
                this.$store.commit('SET_IS_PLAYING', lastSong.visited)
            }
            this.lastVisitedSongId = audioId
        }
    },
    computed: {
        computedTableData() {
            return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
        },
        ...mapGetters(['cachedSongs'])
    },
    created() {
        this.form.songListId = this.$route.query.songListId
        this.form.songListTitle = this.$route.query.songListTitle
        this.getTableData()
    },
    beforeDestroy() {
        this.$store.commit('SET_IS_PLAYING', false)
    },
    watch: {
        select_word() {
            if (this.select_word !== '') {
                this.tableData = []
                for (let song of this.tempTableData) {
                    if (song.name.toLowerCase().includes(this.select_word)) {
                        this.tableData.push(song)
                    }
                }
            } else {
                this.tableData = this.tempTableData
            }
        }
    },
    mixins: [mixin]
}
</script>

<style lang="scss" scoped>
.handle-box {
    margin-bottom: 10px;
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: space-between;
}

.handle-input {
    display: inline-block;
}

.song-img-box {
    width: 100%;
    height: 100%;
    position: relative;
    overflow: hidden;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
    align-content: space-between;

    img {
        width: 90px;
        min-height: 90px;
    }

    .audio-play-button-box {
        position: absolute;
        z-index: 10;
        width: 90px;
        height: 90px;
        font-size: 24px;
        cursor: pointer;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: rgba(255, 228, 196, 0.452);

        div {
            font-size: 2em;
            color: rgb(61, 57, 57);
        }
    }
}

.song-file-box {
    text-align: center;
}
</style>
