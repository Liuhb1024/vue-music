<template>
<div>
    <div class="handle-box">
        <div>
            <el-button type="primary" @click="addDialogFormVisible = true">添加歌单信息</el-button>
        </div>
        <div>
            <h2>歌单管理</h2>
        </div>
        <div>
            <el-input v-model="select_word" size="medium" class="handle-input" placeholder="输入歌单名以查询"></el-input>
        </div>
    </div>

    <!-- 歌单信息展示表格 -->
    <div class="table-container">
        <el-table :data="computedTableData" style="width: 100%" max-height="100%" :cell-style="{textAlign: 'center'}" :header-cell-style="{textAlign: 'center'}" size="medium" stripe border>
            <el-table-column label="序号" width="50" type="index" fixed>
                <template slot-scope="scope">
                    {{(currentPage-1)*pageSize+scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column prop="title" label="歌单名" width="350" fixed></el-table-column>
            <el-table-column label="歌单类型" width="100">
                <template #default="scope">
                    {{convertSongListTypeToText(scope.row.type)}}
                </template>
            </el-table-column>
            <el-table-column prop="style" label="风格" width="150"></el-table-column>
            <el-table-column prop="pic" label="歌单封面" width="140">
                <template slot-scope="scope">
                    <div class="song-list-img-box">
                        <div>
                            <img :src="getAbsoluteUrlOfStaticResourcesThroughCurrentUrl(scope.row.pic)" alt="歌单封面">
                        </div>
                        <el-upload name="song-list-img-file" :action="getUploadUrl(scope.row.id)" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                            <el-button type="primary" size="mini">更新图片</el-button>
                        </el-upload>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="introduction" label="歌单简介">
            </el-table-column>
            <el-table-column label="创建者" width="100">
                <template #default="scope">
                    {{getSongListCreatorName(scope.row.consumerId)}}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="160">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button type="text" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
                    <el-button type="text" size="small" @click="handleMovingToSongListEdit(scope.row.id, scope.row.title)">歌曲管理</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-size="pageSize" layout="total, prev, pager, next, jumper" :total="tableData.length" background>
            </el-pagination>
        </div>
    </div>

    <!-- 添加歌单信息 -->
    <div class="container">
        <el-dialog title="添加歌单" :visible.sync="addDialogFormVisible" width="40%">
            <el-form :model="form" ref="form" :rules="rules" id="form">
                <el-form-item label="歌单名" prop="title" :label-width="formLabelWidth">
                    <el-input v-model="form.title" placeholder="请输入歌单名"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="type" :label-width="formLabelWidth">
                    <el-input v-model="form.type" placeholder="请输入歌单类型" autocomplete="off" disabled></el-input>
                </el-form-item>
                <el-form-item label="风格" prop="style" :label-width="formLabelWidth">
                    <el-input v-model="form.style" placeholder="请输入风格" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="简介" prop="introduction" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="form.introduction" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddSongList">添 加</el-button>
            </div>
        </el-dialog>
    </div>
    <!-- name="music-file" -->

    <!-- 编辑歌单信息 -->
    <div class="container">
        <el-dialog title="编辑歌单信息" :visible.sync="editDialogFormVisible" width="40%">
            <el-form :model="editForm" ref="editForm" :rules="rules">
                <el-form-item label="ID" prop="id" :label-width="formLabelWidth">
                    <el-input v-model="editForm.id" type="text" disabled></el-input>
                </el-form-item>
                <el-form-item label="歌单名" prop="title" :label-width="formLabelWidth">
                    <el-input v-model="editForm.title" type="text"></el-input>
                </el-form-item>
                <el-form-item label="歌单类型" prop="type" :label-width="formLabelWidth">
                    <el-input v-model="editForm.type" type="text" disabled></el-input>
                </el-form-item>
                <el-form-item label="歌单封面图片" prop="pic" :label-width="formLabelWidth">
                    <el-input v-model="editForm.pic" type="text" disabled></el-input>
                </el-form-item>
                <el-form-item label="风格" prop="style" :label-width="formLabelWidth">
                    <el-input v-model="editForm.style" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="歌单简介" prop="introduction" :label-width="formLabelWidth">
                    <el-input type="textarea" v-model="editForm.introduction" :autosize="{ minRows: 2, maxRows: 10}" placeholder="请输入歌词" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdits">确 定</el-button>
            </div>
        </el-dialog>
    </div>

    <!-- 删除歌单信息 -->
    <div class="container">
        <el-dialog title="删除歌单信息" :visible.sync="delDialogVisible" width="300px">
            <span>删除歌单后不可恢复，是否继续？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="delDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="delSongList">确 定</el-button>
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
    queryConsumerById
} from '@/api/consumer/index'

import {
    addSongList,
    queryAllSongLists,
    modifySongList,
    removeSongList
} from '@/api/song-list/index'

export default {
    name: 'SongListPage',
    data() {
        return {
            addDialogFormVisible: false, //设置添加歌单对话框是否显示
            editDialogFormVisible: false, //设置编辑歌单对话框是否显示
            delDialogVisible: false, //设置删除歌单对话框是否显示

            //添加框表单
            form: {
                id: -1,
                type: '官方歌单',
                consumerId: 0,
                title: '',
                pic: '/img/song-list-img/default_song-list_img.jpg',
                introduction: '无',
                style: ''
            },
            //编写表单验证规则
            rules: {
                title: [{
                    required: true,
                    message: '请输入歌单名',
                    trigger: 'blur'
                }]
            },
            //编辑框表单
            editForm: {
                id: -1,
                type: 0,
                consumerId: -1,
                title: '',
                pic: '',
                introduction: '',
                style: ''
            },
            formLabelWidth: '180px', //表单Label宽度
            select_word: '', //存放在搜索框中需要查询的歌单名
            tableData: [], //要显示到页面上的歌单信息数据
            tempTableData: [], //歌单信息缓存数据
            pagerCount: 9, //显示的页码数量
            pageSize: 5, // 分页大小
            currentPage: 1, //当前页
            delSongListId: -1, //要删除的歌单id
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
         * 获取所有歌单
         */
        getTableData() {
            this.tableData = []
            this.tempTableData = []
            queryAllSongLists().then(jsonData => {
                this.tempTableData = jsonData
                this.tableData = this.tempTableData
            }).catch(err => console.log(err))
        },
        /**
         * 添加歌单信息
         */
        handleAddSongList() {
            this.form.type = 0 // 官方歌单，type值为0
            addSongList(this.form).then(jsonData => {
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
        convertSongListTypeToText(type) {
            return type ? '用户歌单' : '官方歌单'
        },
        getSongListCreatorName(creatorId) {
            if (creatorId) {
                let res = null
                queryConsumerById(creatorId).then(jsonData => res = jsonData).catch(err => console.log(err))
                return res
            } else {
                return '管理员'
            }
        },
        /**
         * 获取要更新的歌单图片地址
         * @param {Number} singerId 
         * @returns url
         */
        getUploadUrl(songListId) {
            return `${this.$store.state.HOST}/songlist/updatesonglistimg?songListId=${songListId}`
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
         * 弹出编辑歌单信息对话框
         * @param {Object} row 
         */
        handleEdit(row) {
            this.editDialogFormVisible = true
            this.editForm = {
                id: row.id,
                type: this.convertSongListTypeToText(row.type),
                consumerId: row.consumerId,
                title: row.title,
                pic: row.pic,
                introduction: row.introduction,
                style: row.style
            }
        },
        handleDelete(songListId) {
            this.delSongListId = songListId
            this.delDialogVisible = true
        },
        /**
         * 更新歌单基本信息
         */
        saveEdits() {
            this.editForm.type = 0
            modifySongList(this.editForm).then(jsonData => {
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
        // 删除歌单
        delSongList() {
            removeSongList(this.delSongListId).then(jsonData => {
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
        //返回
        getSongFileUpdateUrl(songId) {
            return `${this.$store.state.HOST}/song/updatesongfile?songId=${songId}`
        },
        /**
         * 转向歌曲详情页面
         * @param {Number} singerId 
         * @param {String} singerName 
         */
        handleMovingToSongListEdit(songListId, songListTitle) {
            this.$router.push({
                path: '/home/songlistdetail',
                query: {
                    songListId,
                    songListTitle
                }
            })
        }
    },
    computed: {
        computedTableData() {
            return this.tableData.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
        }
    },
    created() {
        this.getTableData()
    },
    watch: {
        select_word() {
            if (this.select_word !== '') {
                this.tableData = []
                for (let songList of this.tempTableData) {
                    if (songList.title.includes(this.select_word)) {
                        this.tableData.push(songList)
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

.song-list-img-box {
    width: 100%;
    height: 100%;
    overflow: hidden;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: center;
    align-content: space-between;

    img {
        width: 90px;
        // transform: scale(0.5)
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
