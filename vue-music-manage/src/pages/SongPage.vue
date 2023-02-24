<template>
<div>
    <div class="handle-box">
        <div>
            <el-button type="primary" @click="addDialogFormVisible = true">添加歌曲信息</el-button>
        </div>
        <div>
            <h2>歌手-歌曲管理</h2>
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
                <template slot-scope="scope">
                    {{getFormattedDate(scope.row.updateTime)}}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="160">
                <template slot-scope="scope">
                    <div class="song-file-box">
                        <div>
                            <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                        </div>
                        <div>
                            <el-button type="text" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
                        </div>
                        <div>
                            <el-upload name="song-file" :action="getSongFileUpdateUrl(scope.row.id)" :show-file-list="false" :on-success="handleSongFileSuccess" :before-upload="beforeSongFileUpload">
                                <el-button type="text" size="mini">替换音乐</el-button>
                            </el-upload>
                        </div>
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
        <el-dialog title="添加歌曲" :visible.sync="addDialogFormVisible" width="40%">
            <el-form :model="form" ref="form" :rules="rules" id="form">
                <el-form-item label="歌曲名" prop="name" :label-width="formLabelWidth">
                    <el-input v-model="form.name" name="name" placeholder="请输入歌曲名"></el-input>
                </el-form-item>
                <el-form-item label="专辑名" prop="introduction" :label-width="formLabelWidth">
                    <el-input v-model="form.introduction" name="introduction" placeholder="请输入专辑名" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="歌词" prop="lyric" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="form.lyric" name="lyric" placeholder="请输入歌词" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="歌曲上传" prop="url" :label-width="formLabelWidth">
                    <input type="file" name="music-file" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddSong">添 加</el-button>
            </div>
        </el-dialog>
    </div>
    <!-- name="music-file" -->

    <!-- 编辑歌曲信息 -->
    <div class="container">
        <el-dialog title="编辑歌曲信息" :visible.sync="editDialogFormVisible" width="40%">
            <el-form :model="editForm" ref="editForm" :rules="rules">
                <el-form-item label="ID" prop="id" :label-width="formLabelWidth">
                    <el-input v-model="editForm.id" type="text" disabled></el-input>
                </el-form-item>
                <el-form-item label="SingerID" prop="singerId" :label-width="formLabelWidth">
                    <el-input v-model="editForm.singerId" type="text" disabled></el-input>
                </el-form-item>
                <el-form-item label="歌曲名" prop="name" :label-width="formLabelWidth">
                    <el-input v-model="editForm.name" placeholder="请输入歌曲名"></el-input>
                </el-form-item>
                <el-form-item label="专辑名" prop="introduction" :label-width="formLabelWidth">
                    <el-input v-model="editForm.introduction" placeholder="请输入专辑名" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="歌曲地址" prop="url" :label-width="formLabelWidth">
                    <el-input v-model="editForm.url" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="歌词" prop="lyric" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="editForm.lyric" placeholder="请输入歌词" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="创建日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="editForm.createTime" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd" disabled></el-date-picker>
                </el-form-item>
                <el-form-item label="最后一次更新日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="editForm.updateTime" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd" disabled></el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdits">确 定</el-button>
            </div>
        </el-dialog>
    </div>

    <!-- 删除歌曲信息 -->
    <div class="container">
        <el-dialog title="删除歌曲信息" :visible.sync="delDialogVisible" width="300px">
            <span>删除数据后不可恢复，是否继续？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="delDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="delSong">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</div>
</template>

<script>
import {
    mixin
} from '@/mixins'
import {
    dateToString
} from '@/utils/index'

import {
    addSong,
    queryAllSongs,
    querySongsBySingerId,
    modifySong,
    removeSong
} from '@/api/song/index'

export default {
    name: 'SongPage',
    data() {
        return {
            singerId: -1, //接收从歌手管理页面跳转过来的歌手id
            singerName: '', //接收从歌手管理页面跳转过来的歌手名字
            addDialogFormVisible: false, //设置添加歌曲对话框是否显示
            editDialogFormVisible: false, //设置编辑歌曲对话框是否显示
            delDialogVisible: false, //设置删除歌曲对话框是否显示

            //添加框表单
            form: {
                id: -1, // 避免后端应用@RequestBody注解时因获取不到id报错
                singerId: -1,
                name: '',
                introduction: '',
                pic: '/img/song-img/default_music_icon.png', //设置默认歌曲封面图片地址
                createTime: '',
                updateTime: '',
                lyric: '[00:00:00]暂无歌词',
                url: '',
            },
            //编写表单验证规则
            rules: {
                name: [{
                    required: true,
                    message: '请输入歌曲名',
                    trigger: 'blur'
                }]
            },
            //编辑框表单
            editForm: {
                id: -1,
                singerId: -1,
                name: '',
                introduction: '',
                pic: '',
                createTime: '',
                updateTime: '',
                lyric: '',
                url: '',
            },
            formLabelWidth: '180px', //表单Label宽度
            select_word: '', //存放在搜索框中需要查询的歌曲名
            tableData: [], //要显示到页面上的歌曲信息数据
            tempTableData: [], //歌曲信息缓存数据
            pagerCount: 9, //显示的页码数量
            pageSize: 5, // 分页大小
            currentPage: 1, //当前页
            delSongId: -1, //要删除的歌曲id

            /* 接收从歌手编辑页面跳转过来的参数 */
            singerId: -1,
            singerName: '',

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
        handleClick(row) {
            console.log(row)
        },
        /**
         * 获取歌曲信息数据
         */
        getTableData() {
            this.tableData = []
            this.tempTableData = []
            queryAllSongs().then(jsonData => {
                let songList = jsonData.filter(song => song.singerId === this.singerId)
                for (let song of songList) {
                    song.visited = false
                }
                this.tempTableData = songList
                this.tableData = this.tempTableData
            }).catch(err => console.log(err))
        },
        /**
         * 添加歌曲信息
         */
        handleAddSong() {
            //使用js原生的FormData来上传文件
            let formData = new FormData(document.querySelector('#form'))
            formData.set('id', this.form.id)
            formData.set('singerId', this.singerId)
            formData.set('name', this.singerName + '-' + formData.get('name'))
            // formData.set('introduction', this.form.introduction)
            formData.set('pic', this.form.pic)
            formData.set('createTime', dateToString(new Date()))
            formData.set('updateTime', dateToString(new Date()))
            formData.set('url', this.form.url)
            // formData.set('lyric', this.form.lyric)
            /* for (let kv of formData) {
                console.log(kv[0], '-》', kv[1])
            } */
            addSong(formData, {
                'Content-Type': 'multipart/form-data;charset=UTF-8'
            }).then(jsonData => {
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
        /**
         * 弹出编辑歌曲信息对话框
         * @param {Object} row 
         */
        handleEdit(row) {
            this.editDialogFormVisible = true
            this.editForm = {
                id: row.id,
                singerId: row.singerId,
                name: row.name,
                introduction: row.introduction,
                createTime: row.createTime,
                updateTime: row.updateTime,
                pic: row.pic,
                lyric: row.lyric,
                url: row.url
            }
        },
        handleDelete(songId) {
            this.delSongId = songId
            this.delDialogVisible = true
        },
        /**
         * 更新歌曲基本信息
         */
        saveEdits() {
            this.editForm.updateTime = dateToString(new Date())
            modifySong(this.editForm).then(jsonData => {
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
            this.editDialogFormVisible = false
        },
        /**
         * 删除歌曲
         */
        delSong() {
            removeSong(this.delSongId).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: '删除成功!',
                        type: 'success',
                        duration: 2000
                    })
                    this.delDialogVisible = false
                    this.getTableData()
                } else {
                    this.$message({
                        message: '删除失败!',
                        type: 'error',
                        duration: 2000
                    })
                }
            }).catch(err => console.log(err))
        },
        getSongFileUpdateUrl(songId) {
            return `${this.$store.state.HOST}/song/updatesongfile?songId=${songId}`
        },
        beforeSongFileUpload(file) {
            // let extendsionTypeName = file.name.substring(file.name.lastIndexOf('.')+1)
            const validFileTypeList = ['audio/mp3', 'audio/flac', 'audio/ogg', 'audio/aac']
            const isNormalMusicType = validFileTypeList.some((type) => type == file.type)
            const isLt20M = file.size / 1024 / 1024 < 10
            if (!isNormalMusicType) {
                this.$message.error(`上传文件只能是 ${validFileTypeList} 格式!`)
            }
            this.$nextTick(() => {
                if (!isLt20M) {
                    this.$message.error(`上传文件的大小应小于于20MB!`)
                }
            })
            return isNormalMusicType && isLt20M
        },
        /**
         * 处理更新音乐成功后的回调
         * @param {Response} res - 响应体
         * @param {File} file 
         */
        handleSongFileSuccess( /*响应体*/ res, file) {
            // let _this = this
            if (res.code == 1) {
                this.getTableData()
                this.$message({
                    message: res.msg,
                    type: 'success',
                    duration: 2000
                })
            } else {
                this.$message.error({
                    message: res.msg,
                    type: 'error',
                    duration: 2000
                })
            }
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
        }
    },
    created() {
        this.singerId = Number(this.$route.query.singerId)
        this.singerName = this.$route.query.singerName
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
    display: flex;
    flex-wrap: nowrap;
    justify-content: space-between;
    align-items: center;

    >div:nth-child(2) {
        button {
            color: red;
        }
    }

    >div:nth-child(3) {
        button {
            color: rgb(110, 190, 90);
        }
    }
}
</style>
