<template>
<div>
    <div class="handle-box">
        <div>
            <el-button type="primary" @click="addDialogFormVisible = true">添加用户信息</el-button>
        </div>
        <div>
            <h2>用户管理</h2>
        </div>
        <div>
            <el-input v-model="select_word" size="medium" class="handle-input" placeholder="输入用户姓名以查询"></el-input>
        </div>
    </div>

    <!-- 用户信息展示表格 -->
    <div class="table-container">
        <el-table :data="computedTableData" style="width: 100%" :cell-style="{textAlign: 'center'}" :header-cell-style="{textAlign: 'center'}" max-height="100%" size="medium" stripe border>
            <el-table-column type="index" label="序号" width="50" fixed>
                <template slot-scope="scope">
                    {{(currentPage-1)*pageSize+scope.$index+1}}
                </template>
            </el-table-column>
            <el-table-column prop="avatar" label="用户头像" width="110" fixed>
                <template slot-scope="scope">
                    <div class="consumer-img">
                        <img :src="getAbsoluteUrlOfStaticResourcesThroughCurrentUrl(scope.row.avatar)" alt="头像">
                    </div>
                    <el-upload name="consumer-img-file" :action="getUploadUrl(scope.row.id)" :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <el-button type="primary" size="mini">更新图片</el-button>
                    </el-upload>
                </template>
            </el-table-column>
            <el-table-column prop="username" label="用户名" width="180"></el-table-column>
            <el-table-column prop="phoneNum" label="手机号码" width="110"></el-table-column>
            <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
            <el-table-column prop="sex" label="性别" width="50">
                <template slot-scope="scope">
                    {{convertToLocaleSexText(scope.row.sex)}}
                </template>
            </el-table-column>
            <el-table-column prop="birth" label="出生日期" width="100">
                <template slot-scope="scope">
                    {{getFormattedDate(scope.row.birth)}}
                </template>
            </el-table-column>
            <el-table-column prop="location" label="国家/地区" width="120"></el-table-column>
            <el-table-column prop="introduction" label="简介"></el-table-column>
            <el-table-column prop="birth" label="注册日期" width="100">
                <template slot-scope="scope">
                    {{getFormattedDate(scope.row.createTime)}}
                </template>
            </el-table-column>
            <el-table-column prop="updateTime" label="最后一次信息修改日期" width="100">
                <template slot-scope="scope">
                    {{getFormattedDate(scope.row.updateTime)}}
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="120">
                <div slot-scope="scope">
                    <el-button type="text" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-button type="text" size="small" @click="handleDelete(scope.row.id)">删除</el-button>
                </div>
            </el-table-column>
        </el-table>
        <div class="block">
            <el-pagination 
                @size-change="handleSizeChange" 
                @current-change="handleCurrentChange" 
                :current-page.sync="currentPage"
                :page-size="pageSize" 
                layout="total, prev, pager, next, jumper" 
                :total="tableData.length" 
                background>
            </el-pagination>
        </div>
    </div>

    <!-- 添加用户信息 -->
    <div class="container">
        <el-dialog title="添加用户" :visible.sync="addDialogFormVisible" width="650px">
            <el-form :model="form" ref="form" :rules="rules">
                <el-form-item label="用户名" prop="username" :label-width="formLabelWidth">
                    <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
                    <el-input v-model="form.password" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
                <el-form-item label="手机号码" prop="phoneNum" :label-width="formLabelWidth">
                    <el-input v-model="form.phoneNum" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">
                    <el-input v-model="form.email" placeholder="请输入邮箱"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex" :label-width="formLabelWidth">
                    <el-radio-group v-model="form.sex" size="mini">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="出生日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="form.birth" placeholder="选择出生日期" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="简介" prop="introduction" :label-width="formLabelWidth">
                    <el-input v-model="form.introduction" placeholder="请输入简介" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="国家/地区" prop="location" :label-width="formLabelWidth">
                    <el-input v-model="form.location" placeholder="请输入地区" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="注册日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="form.createTime" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd" disabled></el-date-picker>
                </el-form-item>
                <el-form-item label="最后一次信息修改日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="form.updateTime" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd" disabled></el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addDialogFormVisible = false">取&nbsp;消</el-button>
                <el-button type="primary" @click="handleAdd">添&nbsp;加</el-button>
            </div>
        </el-dialog>
    </div>

    <!-- 编辑用户信息 -->
    <div class="container">
        <el-dialog title="编辑用户信息" :visible.sync="editDialogFormVisible" width="40%">
            <el-form :model="editForm" ref="editForm" :rules="editFormRules">
                <el-form-item label="用户名" prop="username" :label-width="formLabelWidth">
                    <el-input v-model="editForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
                    <el-input v-model="editForm.password" placeholder="请输入密码" show-password></el-input>
                </el-form-item>
                <el-form-item label="手机号码" prop="phoneNum" :label-width="formLabelWidth">
                    <el-input v-model="editForm.phoneNum" placeholder="请输入手机号"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">
                    <el-input v-model="editForm.email" placeholder="请输入邮箱"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex" :label-width="formLabelWidth">
                    <el-radio-group v-model="editForm.sex" size="mini">
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="1">男</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="出生日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="editForm.birth" placeholder="选择出生日期" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
                <el-form-item label="简介" prop="introduction" :label-width="formLabelWidth">
                    <el-input v-model="editForm.introduction" placeholder="请输入简介" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="国家/地区" prop="location" :label-width="formLabelWidth">
                    <el-input v-model="editForm.location" placeholder="请输入地区" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="注册日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="editForm.createTime" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd" disabled></el-date-picker>
                </el-form-item>
                <el-form-item label="最后一次信息修改日期" :label-width="formLabelWidth">
                    <el-date-picker type="date" v-model="editForm.updateTime" format="yyyy年 M 月 d 日" value-format="yyyy-MM-dd" disabled></el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="editDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveEdits">确 定</el-button>
            </div>
        </el-dialog>
    </div>

    <!-- 删除用户信息 -->
    <div class="container">
        <el-dialog title="删除用户信息" :visible.sync="delDialogVisible" width="300px">
            <span>删除数据后不可恢复，是否继续？</span>
            <div slot="footer" class="dialog-footer">
                <el-button @click="delDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="delConsumer">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</div>
</template>

<script>
import {
    addConsumer,
    queryAllConsumers,
    modifyConsumer,
    removeConsumer
} from '@/api/consumer/index'

import {
    mixin
} from '@/mixins'

import {
    dateToString
} from '@/utils'

export default {
    data() {
        return {
            addDialogFormVisible: false, //设置添加用户对话框是否显示
            editDialogFormVisible: false, //设置编辑用户对话框是否显示
            delDialogVisible: false, //设置删除用户对话框是否显示

            //添加框表单
            form: {
                id: -1,
                username: '',
                password: '',
                sex: 0,
                phoneNum: '',
                email: '',
                birth: '',
                location: '',
                introduction: '无',
                avatar: '/img/consumer-img/default_user_photo.png', //设置默认头像地址
                createTime: dateToString(new Date()),
                updateTime: dateToString(new Date())
            },
            //编写表单验证规则
            rules: {
                username: [{
                    required: true,
                    message: '请输入用户名',
                    trigger: 'blur'
                }],
                password: [{
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                }, {
                    min: 3,
                    max: 20,
                    message: '长度为3到20个字符',
                    trigger: 'blur'
                }],
                email: [{
                    required: true,
                    message: '请输入邮箱',
                    trigger: 'blur'
                }],
                phoneNum: [{
                    required: true,
                    message: '请输入手机号',
                    trigger: 'blur'
                }, {
                    max: 11,
                    message: '长度为11个字符',
                    trigger: 'blur'
                }]
            },
            editFormRules: {
                username: [{
                    required: true,
                    message: '请输入用户名',
                    trigger: 'blur'
                }],
                password: [{
                    required: true,
                    message: '请输入密码',
                    trigger: 'blur'
                }, {
                    min: 3,
                    max: 20,
                    message: '长度为3到20个字符',
                    trigger: 'blur'
                }],
                email: [{
                    required: true,
                    message: '请输入邮箱',
                    trigger: 'blur'
                }],
                phoneNum: [{
                    required: true,
                    message: '请输入手机号',
                    trigger: 'blur'
                }, {
                    max: 11,
                    message: '长度为11个字符',
                    trigger: 'blur'
                }]
            },
            //编辑框表单
            editForm: {
                id: -1,
                username: '',
                password: '',
                sex: 0,
                phoneNum: '',
                email: '',
                birth: '',
                location: '',
                introduction: '无',
                avatar: '',
                createTime: '',
                updateTime: ''
            },
            formLabelWidth: '180px',
            select_word: '', //存放在搜索框中需要查询的用户名
            tableData: [], //要显示到页面上的用户信息数据
            tempTableData: [], //用户信息缓存数据
            pagerCount: 9, //显示的页码数量
            pageSize: 5, // 分页大小
            currentPage: 1, //当前页
            delConsumerId: -1 //要删除的用户id
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
         * 获取用户信息数据
         * @param {Number} keepPage 是否保持当前页码
         */
        getTableData(keepPage = false) {
            const currentPageBackup = keepPage ? this.currentPage : 1
            this.tableData = []
            this.tempTableData = []
            
            queryAllConsumers().then(jsonData => {
                this.tableData = jsonData
                this.tempTableData = jsonData
                this.$nextTick(() => {
                    this.currentPage = currentPageBackup
                })
            }).catch(err => console.log(err))
        },
        /**
         * 添加用户信息
         */
        handleAdd() {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    addConsumer(this.form).then(jsonData => {
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
                } else {
                    this.$message({
                        message: '信息填写有误，请检查后再次提交!',
                        type: 'error',
                        duration: 2000
                    })
                }
            })
        },
        //设置要删除的用户id 打开是否删除对话框
        handleDelete(ConsumerId) {
            this.delConsumerId = ConsumerId,
                this.delDialogVisible = true
        },
        /**
         * 获取要更新的用户图片地址
         * @param {Number} ConsumerId 
         * @returns url
         */
        getUploadUrl(consumerId) {
            return `${this.$store.state.HOST}/consumer/update-consumer-img?id=${consumerId}`
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
         * 弹出编辑用户信息对话框
         * @param {Object} row 
         */
        handleEdit(row) {
            this.editDialogFormVisible = true
            this.editForm = {
                id: row.id,
                username: row.username,
                password: row.password,
                sex: row.sex,
                phoneNum: row.phoneNum,
                email: row.email,
                birth: row.birth,
                location: row.location,
                introduction: row.introduction,
                avatar: row.avatar,
                createTime: row.createTime,
                updateTime: row.updateTime
            }
        },
        /**
         * 更新用户信息
         */
        saveEdits() {
            this.editForm.updateTime = dateToString(new Date())
            modifyConsumer(this.editForm).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: jsonData.msg,
                        type: 'success',
                        duration: 2000
                    })
                    this.getTableData(true)  // 传入true表示保持当前页码
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
         * 删除用户
         */
        delConsumer() {
            removeConsumer(this.delConsumerId).then(jsonData => {
                if (jsonData.code == 1) {
                    this.$message({
                        message: '删除成功!',
                        type: 'success',
                        duration: 2000
                    })
                    this.delDialogVisible = false
                    this.getTableData(true)  // 传入true表示保持当前页码
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
         * 转向用户编辑页面
         * @param {Number} ConsumerId 
         * @param {String} ConsumerName 
         */
        handleMovingToSongManagement(ConsumerId, ConsumerName) {
            this.$router.push({
                path: '/home/song',
                query: {
                    ConsumerId,
                    ConsumerName
                }
            })
        },
        handleAvatarSuccess(response) {
            if (response.code == 1) {
                this.getTableData(true)  // 传入true表示保持当前页码
                
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
                for (let Consumer of this.tempTableData) {
                    if (Consumer.name.includes(this.select_word)) {
                        this.tableData.push(Consumer)
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

.consumer-img {
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
</style>
