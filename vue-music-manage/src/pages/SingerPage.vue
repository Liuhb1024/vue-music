<template>
<div>
    <div class="handle-box">
        <div>
            <el-button type="primary" @click="addDialogFormVisible = true">添加歌手信息</el-button>
        </div>
        <div>
            <h2>歌手管理</h2>
        </div>
        <div>
            <el-input v-model="select_word" size="medium" class="handle-input" placeholder="输入歌手姓名以查询"></el-input>
        </div>
    </div>

    <!-- 歌手信息展示表格 -->
    <div class="table-container">
        <el-table :data="computedTableData" style="width: 100%" :cell-style="{textAlign: 'center'}" :header-cell-style="{textAlign: 'center'}" max-height="100%" size="medium" stripe border>
            <el-table-column type="index" label="序号" width="50" fixed>
                <template slot-scope="scope">
                    {{(currentPage-1)*pageSize+scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column prop="pic" label="歌手形象" width="110">
                <template slot-scope="scope">
                    <div class="singer-img">
                        <img :src="getAbsoluteUrlOfStaticResourcesThroughCurrentUrl(scope.row.pic)" alt="歌手形象">
                    </div>
                    <el-upload name="singer-img-file" class="" :action="getUploadUrl(scope.row.id)" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <el-button type="primary" size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="80" fixed></el-table-column>
            <el-table-column prop="sex" label="性别" width="50">
                <template slot-scope="scope">
                    {{convertToLocaleSexText(scope.row.sex)}}
                </template>
            </el-table-column>
            <el-table-column prop="birth" label="出生日期/成立日期" width="100">
                <template slot-scope="scope">
                    {{getFormattedDate(scope.row.birth)}}
                </template>
            </el-table-column>
            <el-table-column prop="location" label="国家/地区" width="120"></el-table-column>
            <el-table-column prop="introduction" label="简介"></el-table-column>
            <el-table-column fixed="right" label="操作" width="160">
                <template slot-scope="scope">
                    <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button type="text" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
                    <el-button type="text" size="small" @click="handleMovingToSongManagement(scope.row.id, scope.row.name)">歌曲管理</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="currentPage" :page-size="pageSize" layout="total, prev, pager, next, jumper" :total="tableData.length" background>
            </el-pagination>
        </div>
    </div>

    <!-- 添加歌手信息 -->
    <div class="container">
        <el-dialog title="添加歌手" :visible.sync="addDialogFormVisible" width="40%">
            <el-form :model="form" ref="form" :rules="rules">
                <el-form-item label="歌手名" prop="name" :label-width="formLabelWidth">
                    <el-input v-model="form.name" placeholder="请输入歌手名"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex" :label-width="formLabelWidth">
                    <el-radio-group v-model="form.sex" size="mini">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">组合</el-radio>
                        <el-radio :label="3">保密</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="出生日期/成立日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="form.birth" placeholder="选择出生日期" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="国家/地区" prop="location" :label-width="formLabelWidth">
                    <el-input v-model="form.location" placeholder="请输入地区" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="简介" prop="introduction" :label-width="formLabelWidth">
                    <el-input v-model="form.introduction" placeholder="请输入简介" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleAddSinger">确 定</el-button>
            </div>
        </el-dialog>
    </div>

    <!-- 编辑歌手信息 -->
    <div class="container">
        <el-dialog title="编辑歌手信息" :visible.sync="editDialogFormVisible" width="40%">
            <el-form :model="editForm" ref="editForm" :rules="rules">
                <el-form-item label="歌手名" prop="name" :label-width="formLabelWidth">
                    <el-input v-model="editForm.name" placeholder="请输入歌手名"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex" :label-width="formLabelWidth">
                    <el-radio-group v-model="editForm.sex" size="mini">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="2">组合</el-radio>
                        <el-radio :label="3">保密</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="出生日期/成立日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="editForm.birth" placeholder="选择出生日期" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="国家/地区" prop="location" :label-width="formLabelWidth">
                    <el-input v-model="editForm.location" placeholder="请输入地区" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="简介" prop="introduction" :label-width="formLabelWidth">
                    <el-input v-model="editForm.introduction" placeholder="请输入简介" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdits">确 定</el-button>
            </div>
        </el-dialog>
    </div>

    <!-- 删除歌手信息 -->
    <div class="container">
        <el-dialog title="删除歌手信息" :visible.sync="delDialogVisible" width="300px">
            <span>删除数据后不可恢复，是否继续？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="delDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="delSinger">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</div>
</template>

<script>
import {
    addSinger,
    queryAllSingers,
    modifySinger,
    removeSinger
} from '@/api/singer/index'

import {
    mixin
} from '@/mixins'

export default {
    data() {
        return {
            addDialogFormVisible: false, //设置添加歌手对话框是否显示
            editDialogFormVisible: false, //设置编辑歌手对话框是否显示
            delDialogVisible: false, //设置删除歌手对话框是否显示

            //添加框表单
            form: {
                id: -1, // 避免后端应用@RequestBody注解时因获取不到id报错
                name: '',
                sex: '',
                pic: '/img/singer-img/default_photo.jpg', //设置默认头像地址
                birth: '',
                location: '',
                introduction: '无',
            },
            //编写表单验证规则
            rules: {
                name: [{
                    required: true,
                    message: '请输入歌手名',
                    trigger: 'blur'
                }]
            },
            //编辑框表单
            editForm: {
                id: -1,
                name: '',
                sex: '',
                pic: '',
                birth: '',
                location: '',
                introduction: ''
            },
            formLabelWidth: '180px',
            select_word: '', //存放在搜索框中需要查询的歌手名
            tableData: [], //要显示到页面上的歌手信息数据
            tempTableData: [], //歌手信息缓存数据
            pagerCount: 9, //显示的页码数量
            pageSize: 5, // 分页大小
            currentPage: 1, //当前页
            delSingerId: -1 //要删除的歌手id
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
         * 获取歌手信息数据
         * @param {Number} keepPage 是否保持当前页码
         */
        getTableData(keepPage = false) {
            const currentPageBackup = keepPage ? this.currentPage : 1
            this.tableData = []
            this.tempTableData = []
            queryAllSingers().then(jsonData => {
                this.tableData = jsonData
                this.tempTableData = jsonData
                this.$nextTick(() => {
                    this.currentPage = currentPageBackup
                })
            }).catch(err => console.log(err))
        },
        /**
         * 添加歌手信息
         */
        handleAddSinger() {
            addSinger(this.form).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: jsonData.msg,
                        type: 'success',
                        duration: 2000
                    })
                    this.getTableData(true)  // 保持当前页码
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
        //设置要删除的歌手id 打开是否删除对话框
        handleDelete(singerId) {
            this.delSingerId = singerId,
            this.delDialogVisible = true
        },
        /**
         * 获取要更新的歌手图片地址
         * @param {Number} singerId 
         * @returns url
         */
        getUploadUrl(singerId) {
            return `${this.$store.state.HOST}/singer/updatesingerimg?id=${singerId}`
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
         * 弹出编辑歌手信息对话框
         * @param {Object} row 
         */
        handleEdit(row) {
            this.editDialogFormVisible = true
            this.editForm = {
                id: row.id,
                name: row.name,
                pic: row.pic,
                sex: row.sex,
                birth: row.birth,
                location: row.location,
                introduction: row.introduction
            }
        },
        /**
         * 更新歌手信息
         */
        saveEdits() {
            modifySinger(this.editForm).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: jsonData.msg,
                        type: 'success',
                        duration: 2000
                    })
                    this.getTableData(true)  // 保持当前页码
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
         * 删除歌手
         */
        delSinger() {
            removeSinger(this.delSingerId).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: '删除成功!',
                        type: 'success',
                        duration: 2000
                    })
                    this.delDialogVisible = false
                    this.getTableData(true)  // 保持当前页码
                } else {
                    this.$message({
                        message: '删除失败!',
                        type: 'error',
                        duration: 2000
                    })
                }
            }).catch(err => console.log(err))
        },
        /**
         * 转向歌曲编辑页面
         * @param {Number} singerId 
         * @param {String} singerName 
         */
        handleMovingToSongManagement(singerId, singerName) {
            this.$router.push({
                path: '/home/song',
                query: {
                    singerId,
                    singerName
                }
            })
        },
        handleAvatarSuccess(response) {
            if (response.code == 1) {
                this.getTableData(true)  // 保持当前页码
                this.$message({
                    message: response.msg,
                    type: 'success',
                    duration: 2000
                })
            } else {
                this.$message({
                    message: response.msg,
                    type: 'error',
                    duration: 2000
                })
            }
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
                for (let singer of this.tempTableData) {
                    if (singer.name.includes(this.select_word)) {
                        this.tableData.push(singer)
                    }
                }
            } else {
                this.tableData = this.tempTableData
            }
        }
    },
    mixins: [mixin]
};
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

.singer-img {
    margin-bottom: 5px;
    width: 100%;
    height: 80px;
    border-radius: 50%;
    // margin-bottom: 5px;
    overflow: hidden;

    img {
        width: 100%;
        height: 100%;
    }
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>
